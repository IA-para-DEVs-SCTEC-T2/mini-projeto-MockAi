package com.ia.para.devs.mockai.domain.port.in;

import java.util.List;
import java.util.UUID;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Port de entrada que define o contrato para buscar todos os endpoints
 * associados a uma especificação de API existente.
 */
public interface GetEndpointsBySpecificationIdUseCase {

    /**
     * Busca todos os endpoints persistidos de uma especificação identificada por ID.
     *
     * @param specificationId UUID da especificação
     * @return lista de endpoints associados à especificação
     */
    List<EndpointDefinitionEntity> findAllBySpecificationId(UUID specificationId);
}
