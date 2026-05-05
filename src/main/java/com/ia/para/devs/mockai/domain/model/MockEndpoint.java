package com.ia.para.devs.mockai.domain.model;

import java.util.List;

/**
 * Representa um endpoint individual extraído de uma especificação OpenAPI.
 * Além do path, método e resposta principal, carrega os padrões e características
 * completos do endpoint: parâmetros, múltiplas respostas, descrição, operationId,
 * tags, se requer autenticação e o schema do request body.
 */
public class MockEndpoint {

    // --- Identificação ---
    private String path;
    private String httpMethod;

    // --- Metadados extraídos da spec ---
    private String summary;
    private String description;
    private String operationId;
    private List<String> tags;
    private boolean requiresAuth;

    // --- Parâmetros (path, query, header, cookie) ---
    private List<EndpointParameter> parameters;

    // --- Request body ---
    private String requestBodyExample;
    private boolean requestBodyRequired;

    // --- Resposta principal (compatibilidade com fluxo existente) ---
    private int responseStatus;
    private String responseBody;

    // --- Todas as respostas possíveis ---
    private List<EndpointResponse> responses;

    public MockEndpoint() {}

    /**
     * Construtor de compatibilidade com o fluxo existente (path, method, status, body).
     */
    public MockEndpoint(String path, String httpMethod, int responseStatus, String responseBody) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    /**
     * Construtor completo com todos os padrões e características do endpoint.
     */
    public MockEndpoint(String path, String httpMethod, String summary, String description,
                        String operationId, List<String> tags, boolean requiresAuth,
                        List<EndpointParameter> parameters, String requestBodyExample,
                        boolean requestBodyRequired, int responseStatus, String responseBody,
                        List<EndpointResponse> responses) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.summary = summary;
        this.description = description;
        this.operationId = operationId;
        this.tags = tags;
        this.requiresAuth = requiresAuth;
        this.parameters = parameters;
        this.requestBodyExample = requestBodyExample;
        this.requestBodyRequired = requestBodyRequired;
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
        this.responses = responses;
    }

    public String getPath() { return path; }
    public String getHttpMethod() { return httpMethod; }
    public String getSummary() { return summary; }
    public String getDescription() { return description; }
    public String getOperationId() { return operationId; }
    public List<String> getTags() { return tags; }
    public boolean isRequiresAuth() { return requiresAuth; }
    public List<EndpointParameter> getParameters() { return parameters; }
    public String getRequestBodyExample() { return requestBodyExample; }
    public boolean isRequestBodyRequired() { return requestBodyRequired; }
    public int getResponseStatus() { return responseStatus; }
    public String getResponseBody() { return responseBody; }
    public List<EndpointResponse> getResponses() { return responses; }

    public void setPath(String path) { this.path = path; }
    public void setHttpMethod(String httpMethod) { this.httpMethod = httpMethod; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setDescription(String description) { this.description = description; }
    public void setOperationId(String operationId) { this.operationId = operationId; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public void setRequiresAuth(boolean requiresAuth) { this.requiresAuth = requiresAuth; }
    public void setParameters(List<EndpointParameter> parameters) { this.parameters = parameters; }
    public void setRequestBodyExample(String requestBodyExample) { this.requestBodyExample = requestBodyExample; }
    public void setRequestBodyRequired(boolean requestBodyRequired) { this.requestBodyRequired = requestBodyRequired; }
    public void setResponseStatus(int responseStatus) { this.responseStatus = responseStatus; }
    public void setResponseBody(String responseBody) { this.responseBody = responseBody; }
    public void setResponses(List<EndpointResponse> responses) { this.responses = responses; }
}
