package com.ia.para.devs.mockai.domain.model;

/**
 * Representa uma resposta possível de um endpoint, com status, descrição e corpo de exemplo.
 * Permite que o mock retorne respostas diferentes conforme o status HTTP.
 */
public class EndpointResponse {

    private int status;
    private String description;
    private String body;

    public EndpointResponse() {}

    public EndpointResponse(int status, String description, String body) {
        this.status = status;
        this.description = description;
        this.body = body;
    }

    public int getStatus() { return status; }
    public String getDescription() { return description; }
    public String getBody() { return body; }

    public void setStatus(int status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }
    public void setBody(String body) { this.body = body; }
}
