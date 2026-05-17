package com.ia.para.devs.mockai.application.port.out;

import java.util.List;
import java.util.UUID;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Port de saída que define o contrato para obter endpoints persistidos
 * de uma especificação de API pelo seu identificador.
 */
public interface GetEndpointsBySpecificationIdPort {

    /**
     * Retorna todos os endpoints persistidos de uma especificação existente.
     *
     * @param specificationId UUID da especificação
     * @return lista de endpoints persistidos
     */
    List<EndpointDefinitionEntity> findAllBySpecificationId(UUID specificationId);
}
