package com.ia.para.devs.mockai.domain.port;

import com.ia.para.devs.mockai.domain.model.MockDefinition;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port de saída: contrato de persistência para MockDefinition.
 * Implementado pela camada de infrastructure.
 */
public interface MockDefinitionRepository {

    MockDefinition save(MockDefinition mockDefinition);

    Optional<MockDefinition> findById(UUID id);

    List<MockDefinition> findAll();

    void deleteById(UUID id);

    boolean existsById(UUID id);
}
