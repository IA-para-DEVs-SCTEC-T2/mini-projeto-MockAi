package com.ia.para.devs.mockai.adapter.in.web.dynamic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.ia.para.devs.mockai.application.port.out.DynamicRouteRegistryPort;
import com.ia.para.devs.mockai.application.util.HttpMethodMapper;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Adapter de saída que registra rotas dinâmicas no Spring MVC em tempo de execução.
 */
@Component
public class SpringWebDynamicRouteRegistry implements DynamicRouteRegistryPort {

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
            RequestMappingInfo mapping = RequestMappingInfo
                    .paths(endpoint.getPath())
                    .methods(requestMethod)
                    .build();

            String endpointKey = createLookupKey(requestMethod.name(), endpoint.getPath());
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
    public void unregisterAll() {
        for (UUID specId : new ArrayList<>(registeredMappings.keySet())) {
            unregisterRoutes(specId);
        }
    }

    @Override
    public EndpointDefinitionEntity getEndpointDefinition(String pathPattern, String httpMethod) {
        return endpointLookup.get(createLookupKey(httpMethod, pathPattern));
    }

    private String createLookupKey(String httpMethod, String pathPattern) {
        return httpMethod + "::" + pathPattern;
    }
}
