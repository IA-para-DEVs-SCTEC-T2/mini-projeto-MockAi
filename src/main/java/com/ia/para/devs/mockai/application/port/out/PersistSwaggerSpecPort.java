package com.ia.para.devs.mockai.application.port.out;

import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;

/**
 * Port de saída que define o contrato para persistência de uma especificação OpenAPI.
 * Implementado pela camada de infraestrutura (adapter/out/persistence).
 */
public interface PersistSwaggerSpecPort {

    /**
     * Persiste todos os dados de uma especificação OpenAPI no banco de dados,
     * substituindo qualquer especificação existente (conforme RN03).
     *
     * @param spec DTO com a especificação OpenAPI desserializada
     */
    void persist(OpenApiSpecDto spec);
}
