package com.ia.para.devs.mockai.adapter.in.web;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ia.para.devs.mockai.adapter.in.web.dto.ImportResponse;
import com.ia.para.devs.mockai.domain.model.FileData;
import com.ia.para.devs.mockai.domain.port.in.ImportSwaggerUseCase;
import com.ia.para.devs.mockai.domain.port.in.ValidateFileUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller REST que expõe o endpoint POST /import.
 *
 * Responsabilidades (SRP):
 *   1. Receber o MultipartFile e adaptá-lo para FileData (tipo de domínio)
 *   2. Delegar a validação de extensão ao ValidateFileUseCase (task #58 — sem alteração)
 *   3. Delegar a desserialização e persistência ao ImportSwaggerUseCase (etapa 5)
 *   4. Retornar HTTP 201 com mensagem de sucesso
 *
 * Não captura exceções — todo tratamento é delegado ao GlobalExceptionHandler.
 * Não contém lógica de negócio — apenas adaptação e orquestração de chamadas.
 */
@Tag(name = "Import", description = "Importação de especificação Swagger/OpenAPI")
@RestController
public class ImportController {

    private final ValidateFileUseCase validateFileUseCase;
    private final ImportSwaggerUseCase importSwaggerUseCase;

    public ImportController(
            ValidateFileUseCase validateFileUseCase,
            ImportSwaggerUseCase importSwaggerUseCase) {
        this.validateFileUseCase = validateFileUseCase;
        this.importSwaggerUseCase = importSwaggerUseCase;
    }

    /**
     * Recebe um arquivo Swagger/OpenAPI via multipart/form-data, valida a extensão,
     * desserializa o conteúdo e persiste as definições no banco de dados.
     *
     * Fluxo:
     *   MultipartFile → FileData
     *   → validateFileUseCase.validate()   [lança InvalidExtensionException se inválido]
     *   → importSwaggerUseCase.importSpec() [lança InvalidSwaggerContentException ou erros de persistência]
     *   → HTTP 201 "Arquivo importado com sucesso"
     *
     * @param file arquivo enviado via campo "file" do multipart
     * @return HTTP 201 com mensagem de sucesso
     * @throws IOException se a leitura dos bytes do MultipartFile falhar
     */
    @Operation(summary = "Importa especificação Swagger/OpenAPI",
               description = "Recebe um arquivo .json no formato OpenAPI 3.0+, valida, desserializa e persiste os endpoints mockados. Substitui todos os endpoints existentes.")
    @RequestBody(required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                 schema = @Schema(implementation = ImportController.ImportFileRequest.class)))
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Arquivo importado com sucesso",
                     content = @Content(schema = @Schema(implementation = ImportResponse.class))),
        @ApiResponse(responseCode = "400", description = "Extensão inválida ou conteúdo JSON não reconhecido como OpenAPI",
                     content = @Content(schema = @Schema(implementation = ImportResponse.class))),
        @ApiResponse(responseCode = "409", description = "Violação de integridade referencial nos dados",
                     content = @Content(schema = @Schema(implementation = ImportResponse.class))),
        @ApiResponse(responseCode = "500", description = "Falha interna ao persistir os dados",
                     content = @Content(schema = @Schema(implementation = ImportResponse.class))),
        @ApiResponse(responseCode = "503", description = "Banco de dados indisponível",
                     content = @Content(schema = @Schema(implementation = ImportResponse.class)))
    })
    @PostMapping(value = "/import", consumes = "multipart/form-data")
    public ResponseEntity<ImportResponse> importFile(@RequestPart("file") MultipartFile file) throws IOException {
        FileData fileData = new FileData(file.getOriginalFilename(), file.getBytes());

        // Etapa 1: valida extensão do arquivo (task #58 — comportamento preservado)
        validateFileUseCase.validate(fileData);

        // Etapa 2: desserializa e persiste a especificação OpenAPI
        importSwaggerUseCase.importSpec(fileData);

        return ResponseEntity.status(201).body(new ImportResponse("Arquivo importado com sucesso"));
    }

    /**
     * DTO interno usado exclusivamente para descrever o schema multipart no Swagger UI.
     * Não é instanciado em runtime — serve apenas como referência para geração da documentação OpenAPI.
     */
    static class ImportFileRequest {
        @Schema(type = "string", format = "binary", description = "Arquivo JSON no formato OpenAPI 3.0+")
        public MultipartFile file;
    }
}
