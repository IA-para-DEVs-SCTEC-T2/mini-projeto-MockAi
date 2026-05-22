package com.ia.para.devs.mockai.domain.port.in;

import java.util.List;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Port de entrada que define o contrato para listar todos os endpoints mockados
 * disponíveis persistidos no banco de dados.
 */
public interface ListEndpointsUseCase {

    /**
     * Retorna todos os endpoints mockados persistidos.
     *
     * @return lista de endpoints; lista vazia se não houver registros
     */
    List<EndpointDefinitionEntity> listAll();
}
