package com.ia.para.devs.mockai.application.usecase;

import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * Caso de uso: remove um projeto mock pelo seu slug.
 * Mais conveniente que deletar pelo UUID, pois o slug é legível e visível na URL.
 */
@Component
public class DeleteProjectBySlugUseCase {

    private final MockDefinitionRepository repository;

    public DeleteProjectBySlugUseCase(MockDefinitionRepository repository) {
        this.repository = repository;
    }

    /**
     * Remove o projeto identificado pelo slug.
     *
     * @param slug identificador amigável do projeto (ex: "usuarios", "fiscalizacao")
     * @throws NoSuchElementException se nenhum projeto for encontrado com o slug informado
     */
    public void execute(String slug) {
        MockDefinition definition = repository.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException(
                        "Projeto não encontrado com slug: '" + slug + "'"));
        repository.deleteById(definition.getId());
    }
}
