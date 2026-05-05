package com.ia.para.devs.mockai.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de saída representando um endpoint de um mock.
 */
@Schema(description = "Dados de um endpoint mock")
public class MockEndpointResponse {

    @Schema(description = "Path do endpoint")
    private String path;

    @Schema(description = "Método HTTP do endpoint")
    private String httpMethod;

    @Schema(description = "Status HTTP da resposta simulada")
    private int responseStatus;

    @Schema(description = "Corpo da resposta simulada em JSON")
    private String responseBody;

    public MockEndpointResponse() {}

    public MockEndpointResponse(String path, String httpMethod, int responseStatus, String responseBody) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    public String getPath() { return path; }
    public String getHttpMethod() { return httpMethod; }
    public int getResponseStatus() { return responseStatus; }
    public String getResponseBody() { return responseBody; }
}
