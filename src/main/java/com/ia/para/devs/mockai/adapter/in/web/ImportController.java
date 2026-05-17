package com.ia.para.devs.mockai.adapter.in.web;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ia.para.devs.mockai.adapter.in.web.dto.ImportResponse;
import com.ia.para.devs.mockai.domain.port.in.ImportSwaggerUseCase;
import com.ia.para.devs.mockai.domain.port.in.ValidateFileUseCase;
import com.ia.para.devs.mockai.domain.model.FileData;

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
    @PostMapping(value = "/import", consumes = "multipart/form-data")
    public ResponseEntity<ImportResponse> importFile(@RequestPart("file") MultipartFile file) throws IOException {
        FileData fileData = new FileData(file.getOriginalFilename(), file.getBytes());

        // Etapa 1: valida extensão do arquivo (task #58 — comportamento preservado)
        validateFileUseCase.validate(fileData);

        // Etapa 2: desserializa e persiste a especificação OpenAPI
        importSwaggerUseCase.importSpec(fileData);

        return ResponseEntity.status(201).body(new ImportResponse("Arquivo importado com sucesso"));
    }
}
