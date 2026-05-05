package com.ia.para.devs.mockai.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

/**
 * DTO de sumário de um projeto mock.
 * Retornado na listagem de projetos — sem os detalhes completos dos endpoints
 * para manter a resposta leve.
 */
@Schema(description = "Sumário de um projeto mock com seus slugs e URLs de acesso")
public class ProjectSummaryResponse {

    @Schema(description = "Identificador único do projeto", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome do projeto extraído da spec", example = "Usuarios API")
    private String name;

    @Schema(description = "Descrição do projeto")
    private String description;

    @Schema(description = "Slug amigável usado na URL", example = "usuarios")
    private String slug;

    @Schema(description = "URL para consultar os endpoints do projeto", example = "/usuarios/endpoints")
    private String endpointsUrl;

    @Schema(description = "URL para deletar o projeto pelo slug", example = "/usuarios")
    private String deleteUrl;

    @Schema(description = "Quantidade de endpoints cadastrados no projeto", example = "5")
    private int endpointCount;

    @Schema(description = "Sumário dos endpoints (path + método HTTP)")
    private List<EndpointSummary> endpoints;

    public ProjectSummaryResponse() {}

    public ProjectSummaryResponse(UUID id, String name, String description, String slug,
                                   int endpointCount, List<EndpointSummary> endpoints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.endpointsUrl = "/" + slug + "/endpoints";
        this.deleteUrl = "/" + slug;
        this.endpointCount = endpointCount;
        this.endpoints = endpoints;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSlug() { return slug; }
    public String getEndpointsUrl() { return endpointsUrl; }
    public String getDeleteUrl() { return deleteUrl; }
    public int getEndpointCount() { return endpointCount; }
    public List<EndpointSummary> getEndpoints() { return endpoints; }

    /**
     * Sumário mínimo de um endpoint: método HTTP + path.
     */
    @Schema(description = "Sumário de um endpoint")
    public static class EndpointSummary {

        @Schema(description = "Método HTTP", example = "POST")
        private String method;

        @Schema(description = "Path do endpoint", example = "/usuarios")
        private String path;

        @Schema(description = "Resumo da operação", example = "Criar um novo usuário")
        private String summary;

        public EndpointSummary() {}

        public EndpointSummary(String method, String path, String summary) {
            this.method = method;
            this.path = path;
            this.summary = summary;
        }

        public String getMethod() { return method; }
        public String getPath() { return path; }
        public String getSummary() { return summary; }
    }
}
