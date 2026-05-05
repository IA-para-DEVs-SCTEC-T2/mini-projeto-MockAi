package com.ia.para.devs.mockai.infrastructure.persistence.repository;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.MockDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface Spring Data JPA para persistência de MockDefinitionEntity.
 * Usada internamente pelo adapter de persistência — não exposta fora da infrastructure.
 */
public interface MockDefinitionJpaRepository extends JpaRepository<MockDefinitionEntity, UUID> {

    Optional<MockDefinitionEntity> findBySlug(String slug);

    long countBySlugStartingWith(String slugBase);
}
