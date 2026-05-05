package com.ia.para.devs.mockai.application.service;

import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.model.MockEndpoint;
import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço responsável por resolver qual resposta retornar para uma chamada a um endpoint mock.
 *
 * Dado um mockId (UUID) ou slug, path e método HTTP, localiza o endpoint correspondente
 * e retorna a resposta simulada definida na spec OpenAPI original.
 *
 * Suporta path templates com parâmetros (ex: /usuarios/{id} bate com /usuarios/42).
 */
@Service
public class MockResolverService {

    private final MockDefinitionRepository repository;

    public MockResolverService(MockDefinitionRepository repository) {
        this.repository = repository;
    }

    /**
     * Resolve pelo UUID do mock.
     */
    public MockEndpoint resolve(UUID mockId, String path, String httpMethod) {
        MockDefinition definition = repository.findById(mockId)
                .orElseThrow(() -> new NoSuchElementException("Mock não encontrado com id: " + mockId));
        return findEndpoint(definition, path, httpMethod);
    }

    /**
     * Resolve pelo slug do projeto.
     */
    public MockEndpoint resolveBySlug(String slug, String path, String httpMethod) {
        MockDefinition definition = repository.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException("Projeto não encontrado com slug: '" + slug + "'"));
        return findEndpoint(definition, path, httpMethod);
    }

    // -------------------------------------------------------------------------
    // Lógica de matching de endpoint
    // -------------------------------------------------------------------------

    private MockEndpoint findEndpoint(MockDefinition definition, String path, String httpMethod) {
        // 1. Tenta match exato primeiro
        Optional<MockEndpoint> exact = definition.getEndpoints().stream()
                .filter(e -> e.getHttpMethod().equalsIgnoreCase(httpMethod)
                        && e.getPath().equals(path))
                .findFirst();

        if (exact.isPresent()) return exact.get();

        // 2. Tenta match por template de path (ex: /usuarios/{id} bate com /usuarios/42)
        Optional<MockEndpoint> template = definition.getEndpoints().stream()
                .filter(e -> e.getHttpMethod().equalsIgnoreCase(httpMethod)
                        && pathMatchesTemplate(e.getPath(), path))
                .findFirst();

        return template.orElseThrow(() ->
                new NoSuchElementException(
                        "Endpoint não encontrado: " + httpMethod + " " + path +
                        " no projeto '" + definition.getName() + "'"));
    }

    /**
     * Verifica se um path concreto bate com um template OpenAPI.
     * Ex: template="/usuarios/{id}", path="/usuarios/42" → true
     *     template="/usuarios/{id}/roles", path="/usuarios/42/roles" → true
     */
    private boolean pathMatchesTemplate(String template, String concretePath) {
        // Converte o template OpenAPI em regex: /usuarios/{id} → /usuarios/[^/]+
        String regex = template
                .replaceAll("\\{[^/]+}", "[^/]+")
                .replace("/", "\\/");
        return concretePath.matches(regex);
    }
}
