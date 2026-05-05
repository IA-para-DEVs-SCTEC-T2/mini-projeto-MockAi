package com.ia.para.devs.mockai.application.usecase;

import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Caso de uso: lista todos os projetos mock com sumário.
 * Diferente do ListMocksUseCase (que retorna dados completos para o endpoint /mocks),
 * este é usado pelo endpoint /projects para retornar uma visão leve com slugs e URLs.
 */
@Component
public class ListProjectsUseCase {

    private final MockDefinitionRepository repository;

    public ListProjectsUseCase(MockDefinitionRepository repository) {
        this.repository = repository;
    }

    public List<MockDefinition> execute() {
        return repository.findAll();
    }
}
