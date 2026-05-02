package com.ia.para.devs.mockai.application.usecase;

import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Caso de uso: lista todos os mocks cadastrados.
 */
@Component
public class ListMocksUseCase {

    private final MockDefinitionRepository repository;

    public ListMocksUseCase(MockDefinitionRepository repository) {
        this.repository = repository;
    }

    /**
     * Retorna todos os mocks persistidos.
     *
     * @return lista de MockDefinition
     */
    public List<MockDefinition> execute() {
        return repository.findAll();
    }
}
