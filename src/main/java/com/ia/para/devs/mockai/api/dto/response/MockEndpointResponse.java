package com.ia.para.devs.mockai.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO de saída representando um endpoint de um mock com todos os seus padrões e características.
 */
@Schema(description = "Dados completos de um endpoint mock, incluindo parâmetros, respostas e metadados")
public class MockEndpointResponse {

    // --- Identificação ---
    @Schema(description = "Path do endpoint", example = "/usuarios/{id}")
    private String path;

    @Schema(description = "Método HTTP do endpoint", example = "GET")
    private String httpMethod;

    // --- Metadados ---
    @Schema(description = "Resumo do endpoint", example = "Buscar usuário por ID")
    private String summary;

    @Schema(description = "Descrição detalhada do endpoint")
    private String description;

    @Schema(description = "Identificador único da operação", example = "buscarUsuarioPorId")
    private String operationId;

    @Schema(description = "Tags associadas ao endpoint")
    private List<String> tags;

    @Schema(description = "Indica se o endpoint requer autenticação", example = "true")
    private boolean requiresAuth;

    // --- Parâmetros ---
    @Schema(description = "Parâmetros do endpoint (path, query, header, cookie)")
    private List<EndpointParameterResponse> parameters;

    // --- Request body ---
    @Schema(description = "Exemplo do corpo da requisição em JSON")
    private String requestBodyExample;

    @Schema(description = "Indica se o corpo da requisição é obrigatório", example = "true")
    private boolean requestBodyRequired;

    // --- Resposta principal (compatibilidade) ---
    @Schema(description = "Status HTTP da resposta principal simulada", example = "201")
    private int responseStatus;

    @Schema(description = "Corpo da resposta principal simulada em JSON")
    private String responseBody;

    // --- Todas as respostas ---
    @Schema(description = "Todas as respostas possíveis do endpoint")
    private List<EndpointResponseDetail> responses;

    public MockEndpointResponse() {}

    /**
     * Construtor de compatibilidade com o fluxo existente.
     */
    public MockEndpointResponse(String path, String httpMethod, int responseStatus, String responseBody) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    /**
     * Construtor completo com todos os padrões e características.
     */
    public MockEndpointResponse(String path, String httpMethod, String summary, String description,
                                 String operationId, List<String> tags, boolean requiresAuth,
                                 List<EndpointParameterResponse> parameters, String requestBodyExample,
                                 boolean requestBodyRequired, int responseStatus, String responseBody,
                                 List<EndpointResponseDetail> responses) {
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
    public List<EndpointParameterResponse> getParameters() { return parameters; }
    public String getRequestBodyExample() { return requestBodyExample; }
    public boolean isRequestBodyRequired() { return requestBodyRequired; }
    public int getResponseStatus() { return responseStatus; }
    public String getResponseBody() { return responseBody; }
    public List<EndpointResponseDetail> getResponses() { return responses; }
}
