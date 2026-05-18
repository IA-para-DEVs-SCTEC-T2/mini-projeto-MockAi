package com.ia.para.devs.mockai.adapter.in.web;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ia.para.devs.mockai.adapter.in.web.dto.EndpointResponse;
import com.ia.para.devs.mockai.domain.port.in.ListEndpointsUseCase;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Testes unitários para EndpointController.
 */
@ExtendWith(MockitoExtension.class)
class EndpointControllerTest {

    @Mock
    private ListEndpointsUseCase listEndpointsUseCase;

    @InjectMocks
    private EndpointController endpointController;

    @Test
    @DisplayName("Deve retornar HTTP 200 com lista vazia quando não há endpoints")
    void shouldReturn200WithEmptyListWhenNoEndpoints() {
        when(listEndpointsUseCase.listAll()).thenReturn(List.of());

        ResponseEntity<List<EndpointResponse>> response = endpointController.listEndpoints();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar HTTP 200 com endpoints mapeados corretamente")
    void shouldReturn200WithMappedEndpoints() {
        EndpointDefinitionEntity entity = new EndpointDefinitionEntity();
        entity.setPath("/items");
        entity.setHttpMethod("GET");
        entity.setDescription("Lista itens");

        when(listEndpointsUseCase.listAll()).thenReturn(List.of(entity));

        ResponseEntity<List<EndpointResponse>> response = endpointController.listEndpoints();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);

        EndpointResponse dto = response.getBody().get(0);
        assertThat(dto.path()).isEqualTo("/items");
        assertThat(dto.httpMethod()).isEqualTo("GET");
        assertThat(dto.description()).isEqualTo("Lista itens");
    }

    @Test
    @DisplayName("Deve mapear múltiplos endpoints corretamente")
    void shouldMapMultipleEndpoints() {
        EndpointDefinitionEntity e1 = new EndpointDefinitionEntity();
        e1.setPath("/items");
        e1.setHttpMethod("GET");
        e1.setDescription("Lista itens");

        EndpointDefinitionEntity e2 = new EndpointDefinitionEntity();
        e2.setPath("/items/{id}");
        e2.setHttpMethod("DELETE");
        e2.setDescription("Remove item");

        when(listEndpointsUseCase.listAll()).thenReturn(List.of(e1, e2));

        ResponseEntity<List<EndpointResponse>> response = endpointController.listEndpoints();

        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).extracting(EndpointResponse::path)
                .containsExactly("/items", "/items/{id}");
        assertThat(response.getBody()).extracting(EndpointResponse::httpMethod)
                .containsExactly("GET", "DELETE");
    }
}
