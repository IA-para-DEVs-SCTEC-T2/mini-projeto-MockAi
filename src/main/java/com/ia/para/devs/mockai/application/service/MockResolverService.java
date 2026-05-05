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
 * Dado um mockId, path e método HTTP, localiza o endpoint correspondente e retorna a resposta simulada.
 */
@Service
public class MockResolverService {

    private final MockDefinitionRepository repository;

    public MockResolverService(MockDefinitionRepository repository) {
        this.repository = repository;
    }

    /**
     * Resolve a resposta simulada para um endpoint mock.
     *
     * @param mockId     id do mock cadastrado
     * @param path       path do endpoint requisitado
     * @param httpMethod método HTTP da requisição
     * @return MockEndpoint com a resposta simulada
     * @throws NoSuchElementException se o mock ou endpoint não for encontrado
     */
    public MockEndpoint resolve(UUID mockId, String path, String httpMethod) {
        MockDefinition definition = repository.findById(mockId)
                .orElseThrow(() -> new NoSuchElementException("Mock não encontrado com id: " + mockId));

        Optional<MockEndpoint> endpoint = definition.getEndpoints().stream()
                .filter(e -> e.getPath().equals(path) && e.getHttpMethod().equalsIgnoreCase(httpMethod))
                .findFirst();

        return endpoint.orElseThrow(() ->
                new NoSuchElementException("Endpoint não encontrado: " + httpMethod + " " + path));
    }
}
