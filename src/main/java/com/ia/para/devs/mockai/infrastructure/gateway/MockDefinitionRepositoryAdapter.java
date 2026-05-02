package com.ia.para.devs.mockai.infrastructure.gateway;

import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.MockDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.mapper.MockDefinitionMapper;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.MockDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter que implementa o port MockDefinitionRepository do domínio.
 * Faz a ponte entre o domínio e a persistência JPA.
 */
@Component
public class MockDefinitionRepositoryAdapter implements MockDefinitionRepository {

    private final MockDefinitionJpaRepository jpaRepository;
    private final MockDefinitionMapper mapper;

    public MockDefinitionRepositoryAdapter(MockDefinitionJpaRepository jpaRepository,
                                           MockDefinitionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public MockDefinition save(MockDefinition mockDefinition) {
        MockDefinitionEntity entity = mapper.toEntity(mockDefinition);
        MockDefinitionEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<MockDefinition> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MockDefinition> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
