package com.ia.para.devs.mockai.domain.port.out;

import java.util.List;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Port de saída que define o contrato para recuperar todos os endpoints
 * mockados persistidos no banco de dados.
 */
public interface ListEndpointsPort {

    /**
     * Retorna todos os endpoints persistidos.
     *
     * @return lista de endpoints; lista vazia se não houver registros
     */
    List<EndpointDefinitionEntity> findAll();
}
