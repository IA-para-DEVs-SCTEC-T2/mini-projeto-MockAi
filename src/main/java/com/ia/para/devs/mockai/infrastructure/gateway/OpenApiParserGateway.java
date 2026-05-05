package com.ia.para.devs.mockai.infrastructure.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ia.para.devs.mockai.domain.model.EndpointParameter;
import com.ia.para.devs.mockai.domain.model.EndpointResponse;
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
 * Realiza o parsing de especificações OpenAPI 3.x em formato JSON ou YAML,
 * extraindo os padrões e características completos de cada endpoint:
 * parâmetros (path/query/header/cookie), request body, múltiplas respostas,
 * metadados (summary, description, operationId, tags) e segurança.
 */
@Component
public class OpenApiParserGateway implements OpenApiParser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    @Override
    public OpenApiSpec parse(String specContent) {
        try {
            JsonNode root = parseToJsonNode(specContent);

            String title = root.path("info").path("title").asText("Sem título");
            String version = root.path("info").path("version").asText("1.0.0");
            List<MockEndpoint> endpoints = extractEndpoints(root);

            return new OpenApiSpec(title, version, endpoints);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao parsear especificação OpenAPI: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // Parsing do conteúdo
    // -------------------------------------------------------------------------

    private JsonNode parseToJsonNode(String content) throws Exception {
        String trimmed = content.trim();
        ObjectMapper mapper = (trimmed.startsWith("{") || trimmed.startsWith("["))
                ? JSON_MAPPER
                : YAML_MAPPER;
        return mapper.readTree(content);
    }

    // -------------------------------------------------------------------------
    // Extração de endpoints
    // -------------------------------------------------------------------------

    private List<MockEndpoint> extractEndpoints(JsonNode root) {
        List<MockEndpoint> endpoints = new ArrayList<>();
        JsonNode paths = root.path("paths");
        if (paths.isMissingNode() || paths.isEmpty()) {
            return endpoints;
        }

        Iterator<Map.Entry<String, JsonNode>> pathIterator = paths.fields();
        while (pathIterator.hasNext()) {
            Map.Entry<String, JsonNode> pathEntry = pathIterator.next();
            String path = pathEntry.getKey();
            JsonNode pathItem = pathEntry.getValue();

            // Parâmetros definidos no nível do path (compartilhados por todos os métodos)
            List<EndpointParameter> pathLevelParams = extractParameters(pathItem.path("parameters"));

            for (String method : List.of("get", "post", "put", "patch", "delete", "head", "options", "trace")) {
                JsonNode operation = pathItem.path(method);
                if (!operation.isMissingNode()) {
                    endpoints.add(buildEndpoint(path, method.toUpperCase(), operation, pathLevelParams));
                }
            }
        }
        return endpoints;
    }

    private MockEndpoint buildEndpoint(String path, String httpMethod, JsonNode operation,
                                       List<EndpointParameter> pathLevelParams) {
        // --- Metadados ---
        String summary = operation.path("summary").asText(null);
        String description = operation.path("description").asText(null);
        String operationId = operation.path("operationId").asText(null);
        List<String> tags = extractTags(operation.path("tags"));
        boolean requiresAuth = !operation.path("security").isMissingNode()
                && !operation.path("security").isEmpty();

        // --- Parâmetros (merge path-level + operation-level) ---
        List<EndpointParameter> parameters = new ArrayList<>(pathLevelParams);
        parameters.addAll(extractParameters(operation.path("parameters")));

        // --- Request body ---
        String requestBodyExample = extractRequestBodyExample(operation.path("requestBody"));
        boolean requestBodyRequired = operation.path("requestBody").path("required").asBoolean(false);

        // --- Respostas ---
        List<EndpointResponse> responses = extractAllResponses(operation.path("responses"));

        // Resposta principal: primeiro status de sucesso (2xx) ou o primeiro disponível
        EndpointResponse primary = responses.stream()
                .filter(r -> r.getStatus() >= 200 && r.getStatus() < 300)
                .findFirst()
                .orElse(responses.isEmpty() ? new EndpointResponse(200, "OK", "{}") : responses.get(0));

        return new MockEndpoint(
                path, httpMethod, summary, description, operationId, tags, requiresAuth,
                parameters, requestBodyExample, requestBodyRequired,
                primary.getStatus(), primary.getBody(), responses
        );
    }

    // -------------------------------------------------------------------------
    // Extração de parâmetros
    // -------------------------------------------------------------------------

    private List<EndpointParameter> extractParameters(JsonNode parametersNode) {
        List<EndpointParameter> params = new ArrayList<>();
        if (parametersNode.isMissingNode() || !parametersNode.isArray()) {
            return params;
        }
        for (JsonNode param : parametersNode) {
            String name = param.path("name").asText("");
            String in = param.path("in").asText("query");
            String desc = param.path("description").asText(null);
            boolean required = param.path("required").asBoolean(false);
            String type = param.path("schema").path("type").asText("string");
            params.add(new EndpointParameter(name, in, desc, required, type));
        }
        return params;
    }

    // -------------------------------------------------------------------------
    // Extração de tags
    // -------------------------------------------------------------------------

    private List<String> extractTags(JsonNode tagsNode) {
        List<String> tags = new ArrayList<>();
        if (tagsNode.isMissingNode() || !tagsNode.isArray()) {
            return tags;
        }
        for (JsonNode tag : tagsNode) {
            tags.add(tag.asText());
        }
        return tags;
    }

    // -------------------------------------------------------------------------
    // Extração do request body
    // -------------------------------------------------------------------------

    private String extractRequestBodyExample(JsonNode requestBodyNode) {
        if (requestBodyNode.isMissingNode()) {
            return null;
        }
        // Tenta pegar o example direto do content application/json
        JsonNode example = requestBodyNode
                .path("content")
                .path("application/json")
                .path("example");

        if (!example.isMissingNode()) {
            return example.toString();
        }

        // Fallback: tenta construir um exemplo a partir do schema properties
        JsonNode properties = requestBodyNode
                .path("content")
                .path("application/json")
                .path("schema")
                .path("properties");

        if (!properties.isMissingNode()) {
            return buildExampleFromProperties(properties);
        }

        return null;
    }

    private String buildExampleFromProperties(JsonNode properties) {
        StringBuilder sb = new StringBuilder("{");
        Iterator<Map.Entry<String, JsonNode>> fields = properties.fields();
        boolean first = true;
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            if (!first) sb.append(",");
            first = false;
            String type = field.getValue().path("type").asText("string");
            JsonNode exampleNode = field.getValue().path("example");
            String value = exampleNode.isMissingNode()
                    ? defaultValueForType(type)
                    : exampleNode.toString();
            sb.append("\"").append(field.getKey()).append("\":").append(value);
        }
        sb.append("}");
        return sb.toString();
    }

    private String defaultValueForType(String type) {
        return switch (type) {
            case "integer", "number" -> "0";
            case "boolean" -> "true";
            case "array" -> "[]";
            default -> "\"\"";
        };
    }

    // -------------------------------------------------------------------------
    // Extração de todas as respostas
    // -------------------------------------------------------------------------

    private List<EndpointResponse> extractAllResponses(JsonNode responsesNode) {
        List<EndpointResponse> responses = new ArrayList<>();
        if (responsesNode.isMissingNode() || responsesNode.isEmpty()) {
            responses.add(new EndpointResponse(200, "OK", "{}"));
            return responses;
        }

        Iterator<Map.Entry<String, JsonNode>> it = responsesNode.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            int status = parseStatus(entry.getKey());
            String desc = entry.getValue().path("description").asText("");
            String body = extractResponseBody(entry.getValue());
            responses.add(new EndpointResponse(status, desc, body));
        }
        return responses;
    }

    private int parseStatus(String statusStr) {
        try {
            return Integer.parseInt(statusStr);
        } catch (NumberFormatException e) {
            return 200;
        }
    }

    private String extractResponseBody(JsonNode responseNode) {
        JsonNode example = responseNode
                .path("content")
                .path("application/json")
                .path("example");
        return example.isMissingNode() ? "{}" : example.toString();
    }
}
