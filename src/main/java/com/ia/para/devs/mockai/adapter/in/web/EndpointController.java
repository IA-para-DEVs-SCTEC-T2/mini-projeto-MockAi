package com.ia.para.devs.mockai.adapter.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ia.para.devs.mockai.adapter.in.web.dto.EndpointResponse;
import com.ia.para.devs.mockai.domain.port.in.ListEndpointsUseCase;

/**
 * Controller REST que expõe o endpoint GET /endpoints para listar
 * todos os endpoints mockados disponíveis persistidos no banco de dados.
 */
@RestController
public class EndpointController {

    private final ListEndpointsUseCase listEndpointsUseCase;

    public EndpointController(ListEndpointsUseCase listEndpointsUseCase) {
        this.listEndpointsUseCase = listEndpointsUseCase;
    }

    /**
     * Retorna todos os endpoints mockados disponíveis.
     * <p>
     * Consulta os dados persistidos no banco e retorna uma lista com path,
     * método HTTP e descrição de cada endpoint. Retorna lista vazia quando
     * não houver registros.
     * </p>
     *
     * @return HTTP 200 com lista de endpoints; lista vazia se não houver registros
     */
    @GetMapping("/endpoints")
    public ResponseEntity<List<EndpointResponse>> listEndpoints() {
        List<EndpointResponse> endpoints = listEndpointsUseCase.listAll()
                .stream()
                .map(e -> new EndpointResponse(e.getPath(), e.getHttpMethod(), e.getDescription()))
                .toList();
        return ResponseEntity.ok(endpoints);
    }
}
