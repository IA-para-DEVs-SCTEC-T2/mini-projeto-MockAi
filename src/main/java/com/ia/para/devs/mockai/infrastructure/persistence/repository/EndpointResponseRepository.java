package com.ia.para.devs.mockai.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointResponseEntity;

/**
 * Repositório Spring Data JPA para EndpointResponseEntity.
 */
public interface EndpointResponseRepository extends JpaRepository<EndpointResponseEntity, UUID> {
}
