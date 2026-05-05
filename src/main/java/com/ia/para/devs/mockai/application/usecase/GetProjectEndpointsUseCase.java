package com.ia.para.devs.mockai.application.usecase;

import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * Caso de uso: retorna os endpoints de um projeto mock pelo seu slug.
 * Permite consultar via GET /{slug}/endpoints.
 */
@Component
public class GetProjectEndpointsUseCase {

    private final MockDefinitionRepository repository;

    public GetProjectEndpointsUseCase(MockDefinitionRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca o MockDefinition pelo slug e retorna seus endpoints.
     *
     * @param slug identificador amigável do projeto (ex: "usuarios", "fiscalizacao")
     * @return MockDefinition com todos os endpoints e características
     * @throws NoSuchElementException se nenhum projeto for encontrado com o slug informado
     */
    public MockDefinition execute(String slug) {
        return repository.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException(
                        "Projeto não encontrado com slug: '" + slug + "'. " +
                        "Verifique os projetos disponíveis em GET /mocks"));
    }
}
