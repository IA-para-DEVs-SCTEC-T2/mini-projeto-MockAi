package com.ia.para.devs.mockai.infrastructure.persistence.adapter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.EndpointDefinitionRepository;

/**
 * Testes unitários para ListEndpointsAdapter.
 */
@ExtendWith(MockitoExtension.class)
class ListEndpointsAdapterTest {

    @Mock
    private EndpointDefinitionRepository endpointDefinitionRepository;

    @InjectMocks
    private ListEndpointsAdapter listEndpointsAdapter;

    @Test
    @DisplayName("Deve retornar lista vazia quando repositório não tem registros")
    void shouldReturnEmptyListWhenRepositoryIsEmpty() {
        when(endpointDefinitionRepository.findAll()).thenReturn(List.of());

        List<EndpointDefinitionEntity> result = listEndpointsAdapter.findAll();

        assertThat(result).isEmpty();
        verify(endpointDefinitionRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar todos os endpoints do repositório")
    void shouldReturnAllEndpointsFromRepository() {
        EndpointDefinitionEntity entity = new EndpointDefinitionEntity();
        entity.setPath("/items");
        entity.setHttpMethod("POST");
        entity.setDescription("Cria item");

        when(endpointDefinitionRepository.findAll()).thenReturn(List.of(entity));

        List<EndpointDefinitionEntity> result = listEndpointsAdapter.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPath()).isEqualTo("/items");
        verify(endpointDefinitionRepository).findAll();
    }
}
