package com.ia.para.devs.mockai.infrastructure.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ia.para.devs.mockai.domain.model.MockEndpoint;
import com.ia.para.devs.mockai.domain.model.OpenApiSpec;
import com.ia.para.devs.mockai.domain.port.OpenApiParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Implementa o port OpenApiParser do domínio.
 * Realiza o parsing de especificações OpenAPI 3.x em formato JSON ou YAML.
 */
@Component
public class OpenApiParserGateway implements OpenApiParser {

    /**
     * Realiza o parsing da especificação OpenAPI.
     * Detecta automaticamente se o conteúdo é JSON ou YAML.
     *
     * @param specContent conteúdo da especificação em JSON ou YAML
     * @return OpenApiSpec com os dados extraídos
     */
    @Override
    public OpenApiSpec parse(String specContent) {
        try {
            JsonNode root = parseToJsonNode(specContent);

            String title = root.path("info").path("title").asText("Sem título");
            String version = root.path("info").path("version").asText("1.0.0");
            List<MockEndpoint> endpoints = extractEndpoints(root);

            return new OpenApiSpec(title, version, endpoints);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao parsear especificação OpenAPI: " + e.getMessage(), e);
        }
    }

    private JsonNode parseToJsonNode(String content) throws Exception {
        String trimmed = content.trim();
        ObjectMapper mapper = trimmed.startsWith("{") || trimmed.startsWith("[")
                ? new ObjectMapper()
                : new ObjectMapper(new YAMLFactory());
        return mapper.readTree(content);
    }

    private List<MockEndpoint> extractEndpoints(JsonNode root) {
        List<MockEndpoint> endpoints = new ArrayList<>();
        JsonNode paths = root.path("paths");

        Iterator<Map.Entry<String, JsonNode>> pathIterator = paths.fields();
        while (pathIterator.hasNext()) {
            Map.Entry<String, JsonNode> pathEntry = pathIterator.next();
            String path = pathEntry.getKey();

            Iterator<Map.Entry<String, JsonNode>> methodIterator = pathEntry.getValue().fields();
            while (methodIterator.hasNext()) {
                Map.Entry<String, JsonNode> methodEntry = methodIterator.next();
                String httpMethod = methodEntry.getKey().toUpperCase();

                int responseStatus = extractFirstResponseStatus(methodEntry.getValue());
                String responseBody = extractResponseBody(methodEntry.getValue(), responseStatus);

                endpoints.add(new MockEndpoint(path, httpMethod, responseStatus, responseBody));
            }
        }
        return endpoints;
    }

    private int extractFirstResponseStatus(JsonNode operation) {
        JsonNode responses = operation.path("responses");
        if (responses.isEmpty()) return 200;
        String firstStatus = responses.fieldNames().next();
        try {
            return Integer.parseInt(firstStatus);
        } catch (NumberFormatException e) {
            return 200;
        }
    }

    private String extractResponseBody(JsonNode operation, int status) {
        JsonNode responseBody = operation
                .path("responses")
                .path(String.valueOf(status))
                .path("content")
                .path("application/json")
                .path("example");

        return responseBody.isMissingNode() ? "{}" : responseBody.toString();
    }
}
