package com.ia.para.devs.mockai.application.service;

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

import com.ia.para.devs.mockai.domain.port.out.ListEndpointsPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Testes unitários para ListEndpointsService.
 */
@ExtendWith(MockitoExtension.class)
class ListEndpointsServiceTest {

    @Mock
    private ListEndpointsPort listEndpointsPort;

    @InjectMocks
    private ListEndpointsService listEndpointsService;

    @Test
    @DisplayName("Deve retornar lista vazia quando não há endpoints persistidos")
    void shouldReturnEmptyListWhenNoEndpoints() {
        when(listEndpointsPort.findAll()).thenReturn(List.of());

        List<EndpointDefinitionEntity> result = listEndpointsService.listAll();

        assertThat(result).isEmpty();
        verify(listEndpointsPort).findAll();
    }

    @Test
    @DisplayName("Deve retornar todos os endpoints quando existem registros")
    void shouldReturnAllEndpointsWhenTheyExist() {
        EndpointDefinitionEntity entity = new EndpointDefinitionEntity();
        entity.setPath("/items");
        entity.setHttpMethod("GET");
        entity.setDescription("Lista itens");

        when(listEndpointsPort.findAll()).thenReturn(List.of(entity));

        List<EndpointDefinitionEntity> result = listEndpointsService.listAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPath()).isEqualTo("/items");
        assertThat(result.get(0).getHttpMethod()).isEqualTo("GET");
        assertThat(result.get(0).getDescription()).isEqualTo("Lista itens");
        verify(listEndpointsPort).findAll();
    }

    @Test
    @DisplayName("Deve delegar a chamada ao port de saída")
    void shouldDelegateToPort() {
        when(listEndpointsPort.findAll()).thenReturn(List.of());

        listEndpointsService.listAll();

        verify(listEndpointsPort).findAll();
    }
}
