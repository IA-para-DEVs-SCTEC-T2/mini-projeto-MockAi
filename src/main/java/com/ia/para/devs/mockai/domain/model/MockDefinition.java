package com.ia.para.devs.mockai.domain.model;

import java.util.List;
import java.util.UUID;

/**
 * Entidade de domínio que representa um mock cadastrado.
 * Sem dependências de frameworks externos.
 */
public class MockDefinition {

    private UUID id;
    private String name;
    private String description;
    private List<MockEndpoint> endpoints;

    public MockDefinition(UUID id, String name, String description, List<MockEndpoint> endpoints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.endpoints = endpoints;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<MockEndpoint> getEndpoints() { return endpoints; }
}
