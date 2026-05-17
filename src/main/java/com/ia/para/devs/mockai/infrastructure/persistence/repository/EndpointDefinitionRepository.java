package com.ia.para.devs.mockai.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Repositório Spring Data JPA para EndpointDefinitionEntity.
 */
public interface EndpointDefinitionRepository extends JpaRepository<EndpointDefinitionEntity, UUID> {
}
