package com.ia.para.devs.mockai.infrastructure.persistence.adapter;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.EndpointDefinitionRepository;

/**
 * Testes unitários para EndpointDefinitionQueryAdapter.
 * Valida a delegação de consulta ao repositório.
 */
@ExtendWith(MockitoExtension.class)
class EndpointDefinitionQueryAdapterTest {

    @Mock
    private EndpointDefinitionRepository endpointDefinitionRepository;

    @InjectMocks
    private EndpointDefinitionQueryAdapter adapter;

    @Test
    @DisplayName("Deve retornar endpoints do repositório por specificationId")
    void shouldReturnEndpointsFromRepository() {
        UUID specId = UUID.randomUUID();
        EndpointDefinitionEntity entity = new EndpointDefinitionEntity();
        entity.setPath("/items");
        entity.setHttpMethod("GET");

        when(endpointDefinitionRepository.findAllByApiSpecificationId(specId))
                .thenReturn(List.of(entity));

        List<EndpointDefinitionEntity> result = adapter.findAllBySpecificationId(specId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPath()).isEqualTo("/items");
        verify(endpointDefinitionRepository).findAllByApiSpecificationId(specId);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há endpoints para a spec")
    void shouldReturnEmptyListWhenNoEndpoints() {
        UUID specId = UUID.randomUUID();
        when(endpointDefinitionRepository.findAllByApiSpecificationId(specId))
                .thenReturn(List.of());

        List<EndpointDefinitionEntity> result = adapter.findAllBySpecificationId(specId);

        assertThat(result).isEmpty();
    }
}
