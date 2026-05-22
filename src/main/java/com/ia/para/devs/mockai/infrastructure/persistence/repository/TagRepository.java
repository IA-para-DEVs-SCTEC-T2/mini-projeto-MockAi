package com.ia.para.devs.mockai.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.TagEntity;

/**
 * Repositório Spring Data JPA para TagEntity.
 */
public interface TagRepository extends JpaRepository<TagEntity, UUID> {

    Optional<TagEntity> findByName(String name);
}
