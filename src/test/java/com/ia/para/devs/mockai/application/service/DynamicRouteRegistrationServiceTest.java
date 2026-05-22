package com.ia.para.devs.mockai.application.service;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.para.devs.mockai.domain.port.in.GetEndpointsBySpecificationIdUseCase;
import com.ia.para.devs.mockai.domain.port.out.DynamicRouteRegistryPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Testes unitários para DynamicRouteRegistrationService.
 * Valida a orquestração de registro e remoção de rotas dinâmicas.
 */
@ExtendWith(MockitoExtension.class)
class DynamicRouteRegistrationServiceTest {

    @Mock
    private GetEndpointsBySpecificationIdUseCase getEndpointsBySpecificationIdUseCase;

    @Mock
    private DynamicRouteRegistryPort dynamicRouteRegistryPort;

    @InjectMocks
    private DynamicRouteRegistrationService service;

    @Test
    @DisplayName("Deve desregistrar todas as rotas e registrar novas rotas")
    void shouldUnregisterAllAndRegisterNewRoutes() {
        UUID specId = UUID.randomUUID();
        EndpointDefinitionEntity endpoint = new EndpointDefinitionEntity();
        endpoint.setPath("/items");
        endpoint.setHttpMethod("GET");
        List<EndpointDefinitionEntity> endpoints = List.of(endpoint);

        when(getEndpointsBySpecificationIdUseCase.findAllBySpecificationId(specId))
                .thenReturn(endpoints);

        service.registerRoutes(specId);

        verify(dynamicRouteRegistryPort).unregisterAll();
        verify(dynamicRouteRegistryPort).registerRoutes(specId, endpoints);
    }

    @Test
    @DisplayName("Deve delegar remoção de rotas ao port")
    void shouldDelegateUnregisterToPort() {
        UUID specId = UUID.randomUUID();

        service.unregisterRoutes(specId);

        verify(dynamicRouteRegistryPort).unregisterRoutes(specId);
    }
}
