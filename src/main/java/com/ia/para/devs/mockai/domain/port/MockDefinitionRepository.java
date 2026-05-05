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

    Optional<MockDefinition> findBySlug(String slug);

    List<MockDefinition> findAll();

    void deleteById(UUID id);

    boolean existsById(UUID id);

    /**
     * Conta quantos mocks existem cujo slug começa com o prefixo informado.
     * Usado para gerar slugs incrementais: usuarios, usuarios-2, usuarios-3...
     */
    long countBySlugStartingWith(String slugBase);
}
