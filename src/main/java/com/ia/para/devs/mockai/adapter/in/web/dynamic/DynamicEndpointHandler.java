package com.ia.para.devs.mockai.adapter.in.web.dynamic;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.domain.port.in.GenerateEndpointResponseUseCase;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Handler genérico para endpoints registrados dinamicamente.
 *
 * <p>Delega a geração do corpo de resposta ao {@link GenerateEndpointResponseUseCase},
 * que utiliza IA para produzir um JSON realista e coerente com o schema do endpoint.
 * Em caso de falha na geração por IA, aplica fallback estático via
 * {@link DynamicResponseBodyBuilder}.</p>
 */
@Component
public class DynamicEndpointHandler {

    private final SpringWebDynamicRouteRegistry routeRegistry;
    private final DynamicResponseBodyBuilder responseBodyBuilder;
    private final GenerateEndpointResponseUseCase generateEndpointResponseUseCase;
    private final ObjectMapper objectMapper;

    /**
     * Cria uma nova instância do handler com as dependências necessárias.
     *
     * @param routeRegistry                    registro de rotas dinâmicas do Spring MVC
     * @param responseBodyBuilder              construtor de corpo de resposta estático a partir de schemas OpenAPI
     * @param generateEndpointResponseUseCase  caso de uso de geração de resposta por IA
     * @param objectMapper                     serializador JSON para parse da resposta da IA
     */
    public DynamicEndpointHandler(
            SpringWebDynamicRouteRegistry routeRegistry,
            DynamicResponseBodyBuilder responseBodyBuilder,
            GenerateEndpointResponseUseCase generateEndpointResponseUseCase,
            ObjectMapper objectMapper) {
        this.routeRegistry = routeRegistry;
        this.responseBodyBuilder = responseBodyBuilder;
        this.generateEndpointResponseUseCase = generateEndpointResponseUseCase;
        this.objectMapper = objectMapper;
    }

    /**
     * Processa a requisição HTTP para um endpoint mockado dinamicamente.
     *
     * <p>Tenta gerar o corpo de resposta via IA. Se a IA retornar um JSON válido,
     * utiliza-o como corpo da resposta com {@code Content-Type: application/json}.
     * Se o resultado for {@code null} (schema ausente), retorna apenas o status HTTP
     * sem corpo. Em caso de qualquer exceção, aplica fallback estático via
     * {@link DynamicResponseBodyBuilder}.</p>
     *
     * @param request requisição HTTP recebida
     * @return resposta HTTP com o corpo gerado pela IA, corpo estático ou apenas status
     */
    public ResponseEntity<Object> handle(HttpServletRequest request) {
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String httpMethod = request.getMethod();

        EndpointDefinitionEntity endpoint = routeRegistry.getEndpointDefinition(pattern, httpMethod);
        if (endpoint == null) {
            return ResponseEntity.notFound().build();
        }

        EndpointResponseEntity response = selectDefaultResponse(endpoint);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        int statusCode = parseStatusCode(response.getStatusCode());
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(statusCode);

        try {
            String aiBody = generateEndpointResponseUseCase.generate(endpoint);

            if (aiBody == null) {
                return builder.build();
            }

            Object parsedBody = objectMapper.readValue(aiBody, Object.class);
            return builder.contentType(MediaType.APPLICATION_JSON).body(parsedBody);

        } catch (Exception ex) {
            String componentsJson = endpoint.getApiSpecification() != null
                    ? endpoint.getApiSpecification().getComponentsJson()
                    : null;
            Object staticBody = responseBodyBuilder.buildResponseBody(response.getResponseSchema(), componentsJson);
            MediaType mediaType = parseMediaType(response.getContentType());

            if (staticBody == null) {
                return builder.build();
            }
            return builder.contentType(mediaType).body(staticBody);
        }
    }

    private EndpointResponseEntity selectDefaultResponse(EndpointDefinitionEntity endpoint) {
        if (endpoint.getResponses() == null || endpoint.getResponses().isEmpty()) {
            return null;
        }

        return endpoint.getResponses().stream()
                .filter(response -> "200".equals(response.getStatusCode()))
                .findFirst()
                .or(() -> endpoint.getResponses().stream()
                        .filter(response -> "201".equals(response.getStatusCode()))
                        .findFirst())
                .or(() -> endpoint.getResponses().stream()
                        .filter(response -> "204".equals(response.getStatusCode()))
                        .findFirst())
                .orElseGet(() -> endpoint.getResponses().iterator().next());
    }

    private MediaType parseMediaType(String contentType) {
        try {
            if (contentType == null || contentType.isBlank() || "*/*".equals(contentType)) {
                return MediaType.APPLICATION_JSON;
            }
            return MediaType.parseMediaType(contentType);
        } catch (IllegalArgumentException ex) {
            return MediaType.APPLICATION_JSON;
        }
    }

    private int parseStatusCode(String statusCode) {
        try {
            return statusCode != null ? Integer.parseInt(statusCode) : HttpStatus.OK.value();
        } catch (NumberFormatException ex) {
            return HttpStatus.OK.value();
        }
    }
}
