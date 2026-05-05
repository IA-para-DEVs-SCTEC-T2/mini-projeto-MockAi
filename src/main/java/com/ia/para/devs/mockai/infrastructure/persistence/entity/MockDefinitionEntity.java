package com.ia.para.devs.mockai.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * Entidade JPA que representa um mock persistido no banco H2.
 * O campo slug é o identificador amigável usado na URL: /{slug}/endpoints.
 * Não deve ser exposta fora da camada de infrastructure.
 */
@Entity
@Table(name = "mock_definitions")
public class MockDefinitionEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String endpointsJson;

    public MockDefinitionEntity() {}

    public MockDefinitionEntity(UUID id, String name, String description, String slug, String endpointsJson) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.endpointsJson = endpointsJson;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSlug() { return slug; }
    public String getEndpointsJson() { return endpointsJson; }

    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setEndpointsJson(String endpointsJson) { this.endpointsJson = endpointsJson; }
}
