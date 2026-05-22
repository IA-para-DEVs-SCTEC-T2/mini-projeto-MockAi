package com.ia.para.devs.mockai.application.service;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.para.devs.mockai.domain.port.out.GetEndpointsBySpecificationIdPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Testes unitários para GetEndpointsBySpecificationIdService.
 */
@ExtendWith(MockitoExtension.class)
class GetEndpointsBySpecificationIdServiceTest {

    @Mock
    private GetEndpointsBySpecificationIdPort port;

    @InjectMocks
    private GetEndpointsBySpecificationIdService service;

    @Test
    @DisplayName("Deve retornar endpoints do port quando specificationId é válido")
    void shouldReturnEndpointsFromPort() {
        UUID specId = UUID.randomUUID();
        EndpointDefinitionEntity entity = new EndpointDefinitionEntity();
        entity.setPath("/items");
        when(port.findAllBySpecificationId(specId)).thenReturn(List.of(entity));

        List<EndpointDefinitionEntity> result = service.findAllBySpecificationId(specId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPath()).isEqualTo("/items");
        verify(port).findAllBySpecificationId(specId);
    }

    @Test
    @DisplayName("Deve lançar NullPointerException quando specificationId é null")
    void shouldThrowWhenSpecificationIdIsNull() {
        assertThatThrownBy(() -> service.findAllBySpecificationId(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("specificationId");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há endpoints")
    void shouldReturnEmptyListWhenNoEndpoints() {
        UUID specId = UUID.randomUUID();
        when(port.findAllBySpecificationId(specId)).thenReturn(List.of());

        List<EndpointDefinitionEntity> result = service.findAllBySpecificationId(specId);

        assertThat(result).isEmpty();
    }
}
