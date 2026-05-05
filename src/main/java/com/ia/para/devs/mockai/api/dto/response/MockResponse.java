package com.ia.para.devs.mockai.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

/**
 * DTO de saída com os dados completos de um mock cadastrado.
 * O campo endpointsUrl indica a URL para consultar os endpoints do projeto:
 * /{slug}/endpoints
 */
@Schema(description = "Dados de um mock cadastrado")
public class MockResponse {

    @Schema(description = "Identificador único do mock")
    private UUID id;

    @Schema(description = "Nome do mock")
    private String name;

    @Schema(description = "Descrição do mock")
    private String description;

    @Schema(description = "Slug amigável do projeto, usado na URL de acesso", example = "usuarios")
    private String slug;

    @Schema(description = "URL para consultar os endpoints do projeto", example = "/usuarios/endpoints")
    private String endpointsUrl;

    @Schema(description = "Lista de endpoints do mock")
    private List<MockEndpointResponse> endpoints;

    public MockResponse() {}

    public MockResponse(UUID id, String name, String description, List<MockEndpointResponse> endpoints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.endpoints = endpoints;
    }

    public MockResponse(UUID id, String name, String description, String slug,
                        String endpointsUrl, List<MockEndpointResponse> endpoints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.endpointsUrl = endpointsUrl;
        this.endpoints = endpoints;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSlug() { return slug; }
    public String getEndpointsUrl() { return endpointsUrl; }
    public List<MockEndpointResponse> getEndpoints() { return endpoints; }
}
