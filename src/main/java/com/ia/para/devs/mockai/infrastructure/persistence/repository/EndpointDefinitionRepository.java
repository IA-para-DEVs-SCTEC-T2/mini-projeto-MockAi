package com.ia.para.devs.mockai.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Repositório Spring Data JPA para EndpointDefinitionEntity.
 */
public interface EndpointDefinitionRepository extends JpaRepository<EndpointDefinitionEntity, UUID> {

    @EntityGraph(attributePaths = {"responses", "pathParameters", "tags", "apiSpecification"})
    List<EndpointDefinitionEntity> findAllByApiSpecificationId(UUID apiSpecificationId);
}
