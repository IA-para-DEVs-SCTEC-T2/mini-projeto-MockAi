package com.ia.para.devs.mockai.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ia.para.devs.mockai.domain.port.in.DynamicRouteRegistrationUseCase;
import com.ia.para.devs.mockai.domain.port.in.GetEndpointsBySpecificationIdUseCase;
import com.ia.para.devs.mockai.domain.port.out.DynamicRouteRegistryPort;

/**
 * Serviço de aplicação responsável por orquestrar o registro de rotas dinâmicas
 * com base nas definições de endpoint persistidas.
 */
@Service
public class DynamicRouteRegistrationService implements DynamicRouteRegistrationUseCase {

    private final GetEndpointsBySpecificationIdUseCase getEndpointsBySpecificationIdUseCase;
    private final DynamicRouteRegistryPort dynamicRouteRegistryPort;

    public DynamicRouteRegistrationService(
            GetEndpointsBySpecificationIdUseCase getEndpointsBySpecificationIdUseCase,
            DynamicRouteRegistryPort dynamicRouteRegistryPort) {
        this.getEndpointsBySpecificationIdUseCase = getEndpointsBySpecificationIdUseCase;
        this.dynamicRouteRegistryPort = dynamicRouteRegistryPort;
    }

    @Override
    public void registerRoutes(UUID specificationId) {
        dynamicRouteRegistryPort.unregisterAll();
        dynamicRouteRegistryPort.registerRoutes(specificationId,
                getEndpointsBySpecificationIdUseCase.findAllBySpecificationId(specificationId));
    }

    @Override
    public void unregisterRoutes(UUID specificationId) {
        dynamicRouteRegistryPort.unregisterRoutes(specificationId);
    }
}
