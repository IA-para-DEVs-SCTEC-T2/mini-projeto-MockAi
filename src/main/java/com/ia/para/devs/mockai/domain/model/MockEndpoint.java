package com.ia.para.devs.mockai.domain.model;

/**
 * Representa um endpoint individual extraído de uma especificação OpenAPI.
 * Contém path, método HTTP e o corpo de resposta simulada.
 */
public class MockEndpoint {

    private String path;
    private String httpMethod;
    private int responseStatus;
    private String responseBody;

    public MockEndpoint(String path, String httpMethod, int responseStatus, String responseBody) {
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
