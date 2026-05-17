package com.ia.para.devs.mockai.adapter.in.web.dynamic;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Handler genérico para endpoints registrados dinamicamente.
 *
 * Retorna um payload de exemplo compatível com o schema de resposta
 * persistido em EndpointResponseEntity.responseSchema.
 */
@Component
public class DynamicEndpointHandler {

    private final SpringWebDynamicRouteRegistry routeRegistry;
    private final DynamicResponseBodyBuilder responseBodyBuilder;

    public DynamicEndpointHandler(
            SpringWebDynamicRouteRegistry routeRegistry,
            DynamicResponseBodyBuilder responseBodyBuilder) {
        this.routeRegistry = routeRegistry;
        this.responseBodyBuilder = responseBodyBuilder;
    }

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

        MediaType mediaType = parseMediaType(response.getContentType());
        int statusCode = parseStatusCode(response.getStatusCode());

        String componentsJson = endpoint.getApiSpecification() != null
                ? endpoint.getApiSpecification().getComponentsJson()
                : null;

        Object body = responseBodyBuilder.buildResponseBody(response.getResponseSchema(), componentsJson);

        ResponseEntity.BodyBuilder builder = ResponseEntity.status(statusCode);
        if (body == null) {
            return builder.build();
        }
        return builder.contentType(mediaType).body(body);
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
