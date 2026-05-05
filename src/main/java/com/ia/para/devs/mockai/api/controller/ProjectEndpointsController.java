package com.ia.para.devs.mockai.api.controller;

import com.ia.para.devs.mockai.api.dto.response.EndpointParameterResponse;
import com.ia.para.devs.mockai.api.dto.response.EndpointResponseDetail;
import com.ia.para.devs.mockai.api.dto.response.MockEndpointResponse;
import com.ia.para.devs.mockai.api.dto.response.ProjectSummaryResponse;
import com.ia.para.devs.mockai.application.usecase.DeleteProjectBySlugUseCase;
import com.ia.para.devs.mockai.application.usecase.GetProjectEndpointsUseCase;
import com.ia.para.devs.mockai.application.usecase.ListProjectsUseCase;
import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.model.MockEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller que gerencia projetos mock via URL amigável por slug.
 *
 * Endpoints disponíveis:
 *   GET  /projects              → lista todos os projetos com slugs e URLs
 *   GET  /{slug}/endpoints      → endpoints completos de um projeto
 *   DELETE /{slug}              → remove um projeto pelo slug
 */
@RestController
@Tag(name = "Projetos", description = "Listagem, consulta e remoção de projetos mock por slug")
public class ProjectEndpointsController {

    private final GetProjectEndpointsUseCase getProjectEndpointsUseCase;
    private final ListProjectsUseCase listProjectsUseCase;
    private final DeleteProjectBySlugUseCase deleteProjectBySlugUseCase;

    public ProjectEndpointsController(GetProjectEndpointsUseCase getProjectEndpointsUseCase,
                                       ListProjectsUseCase listProjectsUseCase,
                                       DeleteProjectBySlugUseCase deleteProjectBySlugUseCase) {
        this.getProjectEndpointsUseCase = getProjectEndpointsUseCase;
        this.listProjectsUseCase = listProjectsUseCase;
        this.deleteProjectBySlugUseCase = deleteProjectBySlugUseCase;
    }

    // -------------------------------------------------------------------------
    // GET /projects — lista todos os projetos com slugs e URLs
    // -------------------------------------------------------------------------

    @GetMapping("/projects")
    @Operation(
            summary = "Listar projetos",
            description = "Retorna todos os projetos mock cadastrados com seus slugs, URLs de acesso e sumário dos endpoints. " +
                          "Use endpointsUrl para consultar os detalhes completos de cada projeto."
    )
    @ApiResponse(responseCode = "200", description = "Lista de projetos retornada com sucesso")
    public ResponseEntity<List<ProjectSummaryResponse>> listProjects() {
        List<ProjectSummaryResponse> projects = listProjectsUseCase.execute().stream()
                .map(this::toProjectSummary)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    // -------------------------------------------------------------------------
    // GET /{slug}/endpoints — endpoints completos de um projeto
    // -------------------------------------------------------------------------

    @GetMapping("/{slug}/endpoints")
    @Operation(
            summary = "Listar endpoints do projeto",
            description = "Retorna todos os endpoints e suas características completas para o projeto identificado pelo slug. " +
                          "O slug é gerado automaticamente a partir do título da spec OpenAPI ao fazer upload. " +
                          "Se já existir um projeto com o mesmo slug, um sufixo incremental é adicionado: " +
                          "usuarios → usuarios-2 → usuarios-3"
    )
    @ApiResponse(responseCode = "200", description = "Endpoints do projeto retornados com sucesso")
    @ApiResponse(responseCode = "404", description = "Projeto não encontrado com o slug informado")
    public ResponseEntity<ProjectEndpointsResponse> getEndpoints(
            @Parameter(description = "Slug do projeto (ex: usuarios, fiscalizacao, usuarios-2)", required = true)
            @PathVariable String slug) {

        MockDefinition definition = getProjectEndpointsUseCase.execute(slug);

        List<MockEndpointResponse> endpoints = definition.getEndpoints().stream()
                .map(this::toEndpointResponse)
                .collect(Collectors.toList());

        ProjectEndpointsResponse response = new ProjectEndpointsResponse(
                definition.getSlug(),
                definition.getName(),
                definition.getDescription(),
                "/" + definition.getSlug() + "/endpoints",
                endpoints
        );

        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // DELETE /{slug} — remove projeto pelo slug
    // -------------------------------------------------------------------------

    @DeleteMapping("/{slug}")
    @Operation(
            summary = "Remover projeto pelo slug",
            description = "Remove um projeto mock e todos os seus endpoints pelo slug. " +
                          "Mais conveniente que deletar pelo UUID. " +
                          "Exemplo: DELETE /usuarios remove o projeto com slug 'usuarios'."
    )
    @ApiResponse(responseCode = "204", description = "Projeto removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Projeto não encontrado com o slug informado")
    public ResponseEntity<Void> deleteBySlug(
            @Parameter(description = "Slug do projeto a ser removido (ex: usuarios, fiscalizacao)", required = true)
            @PathVariable String slug) {

        deleteProjectBySlugUseCase.execute(slug);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------------------------------
    // Helpers de mapeamento
    // -------------------------------------------------------------------------

    private ProjectSummaryResponse toProjectSummary(MockDefinition definition) {
        List<ProjectSummaryResponse.EndpointSummary> summaries = definition.getEndpoints().stream()
                .map(e -> new ProjectSummaryResponse.EndpointSummary(
                        e.getHttpMethod(), e.getPath(), e.getSummary()))
                .collect(Collectors.toList());

        return new ProjectSummaryResponse(
                definition.getId(),
                definition.getName(),
                definition.getDescription(),
                definition.getSlug(),
                definition.getEndpoints().size(),
                summaries
        );
    }

    private MockEndpointResponse toEndpointResponse(MockEndpoint e) {
        List<EndpointParameterResponse> params = e.getParameters() == null ? List.of() :
                e.getParameters().stream()
                        .map(p -> new EndpointParameterResponse(
                                p.getName(), p.getIn(), p.getDescription(), p.isRequired(), p.getType()))
                        .collect(Collectors.toList());

        List<EndpointResponseDetail> responses = e.getResponses() == null ? List.of() :
                e.getResponses().stream()
                        .map(r -> new EndpointResponseDetail(r.getStatus(), r.getDescription(), r.getBody()))
                        .collect(Collectors.toList());

        return new MockEndpointResponse(
                e.getPath(), e.getHttpMethod(), e.getSummary(), e.getDescription(),
                e.getOperationId(), e.getTags(), e.isRequiresAuth(),
                params, e.getRequestBodyExample(), e.isRequestBodyRequired(),
                e.getResponseStatus(), e.getResponseBody(), responses
        );
    }

    // -------------------------------------------------------------------------
    // DTO de resposta para GET /{slug}/endpoints
    // -------------------------------------------------------------------------

    public static class ProjectEndpointsResponse {

        private String slug;
        private String projectName;
        private String description;
        private String endpointsUrl;
        private List<MockEndpointResponse> endpoints;

        public ProjectEndpointsResponse(String slug, String projectName, String description,
                                         String endpointsUrl, List<MockEndpointResponse> endpoints) {
            this.slug = slug;
            this.projectName = projectName;
            this.description = description;
            this.endpointsUrl = endpointsUrl;
            this.endpoints = endpoints;
        }

        public String getSlug() { return slug; }
        public String getProjectName() { return projectName; }
        public String getDescription() { return description; }
        public String getEndpointsUrl() { return endpointsUrl; }
        public List<MockEndpointResponse> getEndpoints() { return endpoints; }
    }
}
