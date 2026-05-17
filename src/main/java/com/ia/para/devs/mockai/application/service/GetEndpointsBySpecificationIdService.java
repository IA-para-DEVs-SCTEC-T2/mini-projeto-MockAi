package com.ia.para.devs.mockai.application.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ia.para.devs.mockai.application.port.in.GetEndpointsBySpecificationIdUseCase;
import com.ia.para.devs.mockai.application.port.out.GetEndpointsBySpecificationIdPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Serviço de aplicação responsável por buscar todos os endpoints associados
 * a uma especificação de API persistida.
 */
@Service
public class GetEndpointsBySpecificationIdService implements GetEndpointsBySpecificationIdUseCase {

    private final GetEndpointsBySpecificationIdPort getEndpointsBySpecificationIdPort;

    public GetEndpointsBySpecificationIdService(
            GetEndpointsBySpecificationIdPort getEndpointsBySpecificationIdPort) {
        this.getEndpointsBySpecificationIdPort = getEndpointsBySpecificationIdPort;
    }

    @Override
    public List<EndpointDefinitionEntity> findAllBySpecificationId(UUID specificationId) {
        Objects.requireNonNull(specificationId, "specificationId must not be null");
        return getEndpointsBySpecificationIdPort.findAllBySpecificationId(specificationId);
    }
}
