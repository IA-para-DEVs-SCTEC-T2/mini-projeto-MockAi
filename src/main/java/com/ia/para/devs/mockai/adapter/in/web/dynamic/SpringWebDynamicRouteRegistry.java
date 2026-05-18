package com.ia.para.devs.mockai.adapter.in.web.dynamic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.ia.para.devs.mockai.application.util.HttpMethodMapper;
import com.ia.para.devs.mockai.domain.port.out.DynamicRouteRegistryPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.PathParameterEntity;

/**
 * Adapter de saída que registra rotas dinâmicas no Spring MVC em tempo de execução.
 *
 * <p>Quando um path parameter possui {@code format: uuid}, o path é registrado com
 * uma regex constraint no Spring MVC (ex: {@code /owner/{id:[0-9a-f]{8}-...}}),
 * garantindo que rotas literais como {@code /owner/all} não sejam capturadas
 * pelo endpoint parametrizado.</p>
 */
@Component
public class SpringWebDynamicRouteRegistry implements DynamicRouteRegistryPort {

    private static final String UUID_REGEX =
            "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    private final RequestMappingHandlerMapping handlerMapping;
    private final ObjectProvider<DynamicEndpointHandler> dynamicEndpointHandlerProvider;
    private final Map<UUID, List<RequestMappingInfo>> registeredMappings = new ConcurrentHashMap<>();
    private final Map<UUID, List<String>> registeredEndpointKeys = new ConcurrentHashMap<>();
    private final Map<String, RequestMappingInfo> mappingByEndpointKey = new ConcurrentHashMap<>();
    private final Map<String, EndpointDefinitionEntity> endpointLookup = new ConcurrentHashMap<>();
    private final Method handlerMethod;

    public SpringWebDynamicRouteRegistry(
            RequestMappingHandlerMapping handlerMapping,
            ObjectProvider<DynamicEndpointHandler> dynamicEndpointHandlerProvider) {
        this.handlerMapping = handlerMapping;
        this.dynamicEndpointHandlerProvider = dynamicEndpointHandlerProvider;
        try {
            this.handlerMethod = DynamicEndpointHandler.class.getMethod("handle", jakarta.servlet.http.HttpServletRequest.class);
        } catch (NoSuchMethodException exception) {
            throw new IllegalStateException("Método de handler dinâmico não encontrado", exception);
        }
    }

    @Override
    public void registerRoutes(UUID specificationId, List<EndpointDefinitionEntity> endpoints) {
        List<RequestMappingInfo> mappings = new ArrayList<>();
        List<String> endpointKeys = new ArrayList<>();

        for (EndpointDefinitionEntity endpoint : endpoints) {
            RequestMethod requestMethod = HttpMethodMapper.map(endpoint.getHttpMethod());
            String resolvedPath = resolvePathWithConstraints(endpoint);

            RequestMappingInfo mapping = RequestMappingInfo
                    .paths(resolvedPath)
                    .methods(requestMethod)
                    .build();

            String endpointKey = createLookupKey(requestMethod.name(), resolvedPath);
            RequestMappingInfo existingMapping = mappingByEndpointKey.remove(endpointKey);
            if (existingMapping != null) {
                handlerMapping.unregisterMapping(existingMapping);
            }

            DynamicEndpointHandler dynamicEndpointHandler = dynamicEndpointHandlerProvider.getObject();
            handlerMapping.registerMapping(mapping, dynamicEndpointHandler, handlerMethod);
            mappings.add(mapping);
            mappingByEndpointKey.put(endpointKey, mapping);
            endpointLookup.put(endpointKey, endpoint);
            endpointKeys.add(endpointKey);
        }

        registeredMappings.put(specificationId, mappings);
        registeredEndpointKeys.put(specificationId, endpointKeys);
    }

    @Override
    public void unregisterRoutes(UUID specificationId) {
        List<RequestMappingInfo> mappings = registeredMappings.remove(specificationId);
        List<String> endpointKeys = registeredEndpointKeys.remove(specificationId);

        if (mappings != null) {
            for (RequestMappingInfo mapping : mappings) {
                handlerMapping.unregisterMapping(mapping);
            }
        }

        if (endpointKeys != null) {
            for (String endpointKey : endpointKeys) {
                endpointLookup.remove(endpointKey);
                mappingByEndpointKey.remove(endpointKey);
            }
        }
    }

    /**
     * Remove todas as rotas dinâmicas registradas, independente da especificação.
     * Chamado antes de registrar novas rotas para garantir que endpoints de specs
     * anteriores (com UUIDs diferentes) sejam removidos.
     */
    @Override
    public void unregisterAll() {
        for (UUID specId : new ArrayList<>(registeredMappings.keySet())) {
            unregisterRoutes(specId);
        }
    }

    @Override
    public EndpointDefinitionEntity getEndpointDefinition(String pathPattern, String httpMethod) {
        return endpointLookup.get(createLookupKey(httpMethod, pathPattern));
    }

    /**
     * Resolve o path do endpoint aplicando regex constraints nos path parameters
     * quando o formato do parâmetro exige validação (ex: {@code format: uuid}).
     *
     * <p>Exemplo: {@code /owner/{id}} com {@code format: uuid} →
     * {@code /owner/{id:[0-9a-fA-F]{8}-...-[0-9a-fA-F]{12}}}</p>
     *
     * @param endpoint definição do endpoint com seus path parameters
     * @return path com constraints de regex aplicadas onde necessário
     */
    private String resolvePathWithConstraints(EndpointDefinitionEntity endpoint) {
        String path = endpoint.getPath();
        Set<PathParameterEntity> parameters = endpoint.getPathParameters();

        if (parameters == null || parameters.isEmpty()) {
            return path;
        }

        for (PathParameterEntity param : parameters) {
            String regex = resolveRegexForFormat(param.getFormat());
            if (regex != null) {
                String placeholder = "{" + param.getName() + "}";
                String constrained = "{" + param.getName() + ":" + regex + "}";
                path = path.replace(placeholder, constrained);
            }
        }

        return path;
    }

    /**
     * Retorna a expressão regular correspondente ao formato OpenAPI informado.
     * Retorna {@code null} quando o formato não requer constraint de regex.
     *
     * @param format formato OpenAPI do parâmetro (ex: "uuid", "int64")
     * @return regex para uso como constraint no path do Spring MVC, ou {@code null}
     */
    private String resolveRegexForFormat(String format) {
        if (format == null || format.isBlank()) {
            return null;
        }
        if ("uuid".equalsIgnoreCase(format)) {
            return UUID_REGEX;
        }
        return null;
    }

    private String createLookupKey(String httpMethod, String pathPattern) {
        return httpMethod + "::" + pathPattern;
    }
}
