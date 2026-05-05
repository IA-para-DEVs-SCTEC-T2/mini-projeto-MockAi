package com.ia.para.devs.mockai.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.application.service.MockResolverService;
import com.ia.para.devs.mockai.domain.model.MockEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Executor — simula chamadas reais aos endpoints mock.
 *
 * Padrão de URL:
 *   {METHOD} http://localhost:8080/mock/{slug}/{path}
 *
 * Exemplos:
 *   GET    http://localhost:8080/mock/fiscalizacao/fiscalizacoes
 *   GET    http://localhost:8080/mock/fiscalizacao/fiscalizacoes/1
 *   POST   http://localhost:8080/mock/usuarios/usuarios
 *   PUT    http://localhost:8080/mock/usuarios/usuarios/1
 *   DELETE http://localhost:8080/mock/usuarios/usuarios/1
 *   PATCH  http://localhost:8080/mock/fiscalizacao/fiscalizacoes/1
 *
 * O método HTTP da requisição é usado diretamente — não é necessário passar nenhum parâmetro extra.
 * O sistema localiza o endpoint correspondente (com suporte a path templates como /fiscalizacoes/{id})
 * e retorna o status HTTP e o corpo de resposta definidos na spec OpenAPI.
 */
@RestController
@Tag(name = "Executor", description = "Simula chamadas reais aos endpoints mock — use {METHOD} /mock/{slug}/{path}")
public class MockExecutorController {

    private final MockResolverService mockResolverService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MockExecutorController(MockResolverService mockResolverService) {
        this.mockResolverService = mockResolverService;
    }

    @RequestMapping(value = "/mock/{slug}/**")
    @Operation(
            summary = "Executar endpoint mock",
            description = """
                    Simula uma chamada real a um endpoint mock. Use o método HTTP desejado diretamente na requisição.
                    
                    Padrão: {METHOD} /mock/{slug}/{path}
                    
                    Exemplos:
                    - GET    /mock/fiscalizacao/fiscalizacoes          → lista fiscalizações (200)
                    - GET    /mock/fiscalizacao/fiscalizacoes/1        → busca por id (200)
                    - POST   /mock/usuarios/usuarios                   → cria usuário (201)
                    - PUT    /mock/usuarios/usuarios/1                 → atualiza usuário (200)
                    - DELETE /mock/usuarios/usuarios/1                 → remove usuário (204)
                    - PATCH  /mock/fiscalizacao/fiscalizacoes/1        → atualiza status (200)
                    
                    Suporta path templates: /fiscalizacoes/{id} bate com /fiscalizacoes/42
                    """
    )
    @ApiResponse(responseCode = "200", description = "Resposta simulada retornada — o status real está no campo X-Mock-Status")
    @ApiResponse(responseCode = "404", description = "Projeto ou endpoint não encontrado")
    public ResponseEntity<Object> execute(
            @Parameter(description = "Slug do projeto mock (ex: fiscalizacao, usuarios)", required = true)
            @PathVariable String slug,
            HttpServletRequest request) {

        // Extrai o path após /mock/{slug}
        String fullUri = request.getRequestURI();
        String prefix = "/mock/" + slug;
        String endpointPath = fullUri.startsWith(prefix)
                ? fullUri.substring(prefix.length())
                : "/";

        if (endpointPath.isEmpty()) endpointPath = "/";

        String httpMethod = request.getMethod();

        MockEndpoint endpoint = mockResolverService.resolveBySlug(slug, endpointPath, httpMethod);

        Object responseBody = parseBody(endpoint.getResponseBody());

        return ResponseEntity
                .status(endpoint.getResponseStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Mock-Slug", slug)
                .header("X-Mock-Path", endpointPath)
                .header("X-Mock-Method", httpMethod)
                .body(responseBody);
    }

    private Object parseBody(String body) {
        if (body == null || body.isBlank() || body.equals("{}")) {
            return null;
        }
        try {
            return objectMapper.readValue(body, Object.class);
        } catch (Exception e) {
            return body;
        }
    }
}
