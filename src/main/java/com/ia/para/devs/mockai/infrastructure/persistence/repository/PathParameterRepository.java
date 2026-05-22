package com.ia.para.devs.mockai.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.PathParameterEntity;

/**
 * Repositório Spring Data JPA para PathParameterEntity.
 */
public interface PathParameterRepository extends JpaRepository<PathParameterEntity, UUID> {
}
