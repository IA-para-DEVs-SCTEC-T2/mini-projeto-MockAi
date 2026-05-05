package com.ia.para.devs.mockai.domain.model;

import java.util.List;

/**
 * Encapsula os dados extraídos de uma especificação OpenAPI após parsing.
 * Representa o resultado intermediário antes da criação do MockDefinition.
 */
public class OpenApiSpec {

    private String title;
    private String version;
    private List<MockEndpoint> endpoints;

    public OpenApiSpec(String title, String version, List<MockEndpoint> endpoints) {
        this.title = title;
        this.version = version;
        this.endpoints = endpoints;
    }

    public String getTitle() { return title; }
    public String getVersion() { return version; }
    public List<MockEndpoint> getEndpoints() { return endpoints; }
}
