package com.ia.para.devs.mockai.api.controller;

import com.ia.para.devs.mockai.api.dto.request.CreateMockRequest;
import com.ia.para.devs.mockai.api.dto.response.MockEndpointResponse;
import com.ia.para.devs.mockai.api.dto.response.MockResponse;
import com.ia.para.devs.mockai.application.usecase.CreateMockUseCase;
import com.ia.para.devs.mockai.application.usecase.DeleteMockUseCase;
import com.ia.para.devs.mockai.application.usecase.ListMocksUseCase;
import com.ia.para.devs.mockai.application.usecase.UploadSpecUseCase;
import com.ia.para.devs.mockai.domain.model.MockDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private final UploadSpecUseCase uploadSpecUseCase;

    public MockController(CreateMockUseCase createMockUseCase,
                          ListMocksUseCase listMocksUseCase,
                          DeleteMockUseCase deleteMockUseCase,
                          UploadSpecUseCase uploadSpecUseCase) {
        this.createMockUseCase = createMockUseCase;
        this.listMocksUseCase = listMocksUseCase;
        this.deleteMockUseCase = deleteMockUseCase;
        this.uploadSpecUseCase = uploadSpecUseCase;
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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Criar mock via upload de arquivo",
            description = "Cria um mock a partir de um arquivo de especificação OpenAPI enviado em formato JSON (.json), YAML (.yaml) ou YML (.yml)"
    )
    @ApiResponse(responseCode = "201", description = "Mock criado com sucesso a partir do arquivo")
    @ApiResponse(responseCode = "400", description = "Arquivo inválido, formato não suportado ou especificação malformada")
    public ResponseEntity<MockResponse> uploadSpec(
            @Parameter(
                    description = "Arquivo de especificação OpenAPI em formato JSON ou YAML",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo enviado está vazio");
        }

        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "";
        String fileContent;
        try {
            fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Erro ao ler o arquivo enviado: " + e.getMessage(), e);
        }

        MockDefinition created = uploadSpecUseCase.execute(fileContent, fileName);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    private MockResponse toResponse(MockDefinition domain) {
        List<MockEndpointResponse> endpoints = domain.getEndpoints().stream()
                .map(this::toEndpointResponse)
                .collect(Collectors.toList());
        String endpointsUrl = domain.getSlug() != null ? "/" + domain.getSlug() + "/endpoints" : null;
        return new MockResponse(domain.getId(), domain.getName(), domain.getDescription(),
                domain.getSlug(), endpointsUrl, endpoints);
    }

    private MockEndpointResponse toEndpointResponse(com.ia.para.devs.mockai.domain.model.MockEndpoint e) {
        List<com.ia.para.devs.mockai.api.dto.response.EndpointParameterResponse> params =
                e.getParameters() == null ? List.of() :
                e.getParameters().stream()
                        .map(p -> new com.ia.para.devs.mockai.api.dto.response.EndpointParameterResponse(
                                p.getName(), p.getIn(), p.getDescription(), p.isRequired(), p.getType()))
                        .collect(Collectors.toList());

        List<com.ia.para.devs.mockai.api.dto.response.EndpointResponseDetail> responses =
                e.getResponses() == null ? List.of() :
                e.getResponses().stream()
                        .map(r -> new com.ia.para.devs.mockai.api.dto.response.EndpointResponseDetail(
                                r.getStatus(), r.getDescription(), r.getBody()))
                        .collect(Collectors.toList());

        return new MockEndpointResponse(
                e.getPath(), e.getHttpMethod(), e.getSummary(), e.getDescription(),
                e.getOperationId(), e.getTags(), e.isRequiresAuth(),
                params, e.getRequestBodyExample(), e.isRequestBodyRequired(),
                e.getResponseStatus(), e.getResponseBody(), responses
        );
    }
}
