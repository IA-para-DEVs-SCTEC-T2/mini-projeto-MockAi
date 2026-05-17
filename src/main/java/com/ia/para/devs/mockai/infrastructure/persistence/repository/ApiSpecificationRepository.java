package com.ia.para.devs.mockai.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.ApiSpecificationEntity;

/**
 * Repositório Spring Data JPA para ApiSpecificationEntity.
 */
public interface ApiSpecificationRepository extends JpaRepository<ApiSpecificationEntity, UUID> {
}
