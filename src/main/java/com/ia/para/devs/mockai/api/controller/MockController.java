package com.ia.para.devs.mockai.api.controller;

import com.ia.para.devs.mockai.api.dto.request.CreateMockRequest;
import com.ia.para.devs.mockai.api.dto.response.MockEndpointResponse;
import com.ia.para.devs.mockai.api.dto.response.MockResponse;
import com.ia.para.devs.mockai.application.usecase.CreateMockUseCase;
import com.ia.para.devs.mockai.application.usecase.DeleteMockUseCase;
import com.ia.para.devs.mockai.application.usecase.ListMocksUseCase;
import com.ia.para.devs.mockai.domain.model.MockDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciamento de mocks cadastrados.
 * Expõe endpoints para criação, listagem e remoção de mocks.
 */
@RestController
@RequestMapping("/mocks")
@Tag(name = "Mocks", description = "Gerenciamento de APIs mock")
public class MockController {

    private final CreateMockUseCase createMockUseCase;
    private final ListMocksUseCase listMocksUseCase;
    private final DeleteMockUseCase deleteMockUseCase;

    public MockController(CreateMockUseCase createMockUseCase,
                          ListMocksUseCase listMocksUseCase,
                          DeleteMockUseCase deleteMockUseCase) {
        this.createMockUseCase = createMockUseCase;
        this.listMocksUseCase = listMocksUseCase;
        this.deleteMockUseCase = deleteMockUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar mock", description = "Cria um mock a partir de uma especificação OpenAPI em JSON ou YAML")
    @ApiResponse(responseCode = "201", description = "Mock criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Especificação inválida")
    public ResponseEntity<MockResponse> create(@Valid @RequestBody CreateMockRequest request) {
        MockDefinition created = createMockUseCase.execute(request.getSpecContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Listar mocks", description = "Retorna todos os mocks cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de mocks")
    public ResponseEntity<List<MockResponse>> listAll() {
        List<MockResponse> mocks = listMocksUseCase.execute().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mocks);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover mock", description = "Remove um mock pelo id")
    @ApiResponse(responseCode = "204", description = "Mock removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Mock não encontrado")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteMockUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    private MockResponse toResponse(MockDefinition domain) {
        List<MockEndpointResponse> endpoints = domain.getEndpoints().stream()
                .map(e -> new MockEndpointResponse(e.getPath(), e.getHttpMethod(), e.getResponseStatus(), e.getResponseBody()))
                .collect(Collectors.toList());
        return new MockResponse(domain.getId(), domain.getName(), domain.getDescription(), endpoints);
    }
}
