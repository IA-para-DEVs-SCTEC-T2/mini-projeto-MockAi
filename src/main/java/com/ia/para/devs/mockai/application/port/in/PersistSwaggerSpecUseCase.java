package com.ia.para.devs.mockai.application.port.in;

import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;

/**
 * Port de entrada que define o contrato do caso de uso de persistência
 * de uma especificação OpenAPI no banco de dados.
 */
public interface PersistSwaggerSpecUseCase {

    /**
     * Persiste a especificação OpenAPI desserializada, substituindo
     * todos os dados existentes (conforme RN03).
     *
     * @param spec DTO com a especificação OpenAPI desserializada
     */
    void persist(OpenApiSpecDto spec);
}
