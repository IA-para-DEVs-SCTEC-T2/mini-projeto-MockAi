package com.ia.para.devs.mockai.infrastructure.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.model.MockEndpoint;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.MockDefinitionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converte entre MockDefinitionEntity (JPA) e MockDefinition (domínio).
 * Responsabilidade única e testável isoladamente.
 */
@Component
public class MockDefinitionMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converte entidade JPA para modelo de domínio.
     */
    public MockDefinition toDomain(MockDefinitionEntity entity) {
        List<MockEndpoint> endpoints = deserializeEndpoints(entity.getEndpointsJson());
        return new MockDefinition(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getSlug(), endpoints);
    }

    /**
     * Converte modelo de domínio para entidade JPA.
     */
    public MockDefinitionEntity toEntity(MockDefinition domain) {
        String endpointsJson = serializeEndpoints(domain.getEndpoints());
        return new MockDefinitionEntity(domain.getId(), domain.getName(), domain.getDescription(),
                domain.getSlug(), endpointsJson);
    }

    private String serializeEndpoints(List<MockEndpoint> endpoints) {
        try {
            return objectMapper.writeValueAsString(endpoints);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Erro ao serializar endpoints", e);
        }
    }

    private List<MockEndpoint> deserializeEndpoints(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<MockEndpoint>>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Erro ao desserializar endpoints", e);
        }
    }
}
