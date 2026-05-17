package com.ia.para.devs.mockai.infrastructure.persistence.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ia.para.devs.mockai.application.port.out.GetEndpointsBySpecificationIdPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.EndpointDefinitionRepository;

/**
 * Adapter de saída responsável por consultar endpoints persistidos por
 * especificação de API usando Spring Data JPA.
 */
@Component
public class EndpointDefinitionQueryAdapter implements GetEndpointsBySpecificationIdPort {

    private final EndpointDefinitionRepository endpointDefinitionRepository;

    public EndpointDefinitionQueryAdapter(EndpointDefinitionRepository endpointDefinitionRepository) {
        this.endpointDefinitionRepository = endpointDefinitionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EndpointDefinitionEntity> findAllBySpecificationId(UUID specificationId) {
        return endpointDefinitionRepository.findAllByApiSpecificationId(specificationId);
    }
}
