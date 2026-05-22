package com.ia.para.devs.mockai.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ia.para.devs.mockai.domain.port.in.ListEndpointsUseCase;
import com.ia.para.devs.mockai.domain.port.out.ListEndpointsPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Serviço de aplicação responsável por listar todos os endpoints mockados
 * disponíveis persistidos no banco de dados.
 */
@Service
public class ListEndpointsService implements ListEndpointsUseCase {

    private final ListEndpointsPort listEndpointsPort;

    public ListEndpointsService(ListEndpointsPort listEndpointsPort) {
        this.listEndpointsPort = listEndpointsPort;
    }

    @Override
    public List<EndpointDefinitionEntity> listAll() {
        return listEndpointsPort.findAll();
    }
}
