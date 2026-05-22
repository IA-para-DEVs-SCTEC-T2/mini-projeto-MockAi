package com.ia.para.devs.mockai.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;
import com.ia.para.devs.mockai.domain.port.in.PersistSwaggerSpecUseCase;
import com.ia.para.devs.mockai.domain.port.out.PersistSwaggerSpecPort;

/**
 * Serviço de aplicação responsável por orquestrar a persistência de uma
 * especificação OpenAPI no banco de dados.
 *
 * Segue o Princípio da Responsabilidade Única (SRP): delega a persistência
 * ao port de saída, sem conhecer detalhes de JPA ou banco de dados.
 *
 * Segue o Princípio da Inversão de Dependência (DIP): depende exclusivamente
 * da abstração PersistSwaggerSpecPort, não de implementações concretas.
 */
@Service
public class PersistSwaggerSpecService implements PersistSwaggerSpecUseCase {

    private final PersistSwaggerSpecPort persistSwaggerSpecPort;

    public PersistSwaggerSpecService(PersistSwaggerSpecPort persistSwaggerSpecPort) {
        this.persistSwaggerSpecPort = persistSwaggerSpecPort;
    }

    /**
     * Persiste a especificação OpenAPI desserializada, substituindo todos os
     * dados existentes conforme a regra de negócio RN03.
     *
     * @param spec DTO com a especificação OpenAPI desserializada
     * @return UUID da especificação persistida
     */
    @Override
    public UUID persist(OpenApiSpecDto spec) {
        return persistSwaggerSpecPort.persist(spec);
    }
}
