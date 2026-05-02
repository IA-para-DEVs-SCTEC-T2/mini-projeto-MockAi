package com.ia.para.devs.mockai.application.usecase;

import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Caso de uso: remove um mock pelo id.
 * Valida existência antes de deletar.
 */
@Component
public class DeleteMockUseCase {

    private final MockDefinitionRepository repository;

    public DeleteMockUseCase(MockDefinitionRepository repository) {
        this.repository = repository;
    }

    /**
     * Executa a remoção do mock.
     *
     * @param id identificador do mock a ser removido
     * @throws NoSuchElementException se o mock não for encontrado
     */
    public void execute(UUID id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Mock não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
