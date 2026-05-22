package com.ia.para.devs.mockai.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.ia.para.devs.mockai.adapter.in.web.dto.ImportResponse;
import com.ia.para.devs.mockai.domain.exception.InvalidExtensionException;
import com.ia.para.devs.mockai.domain.model.FileData;
import com.ia.para.devs.mockai.domain.port.in.ImportSwaggerUseCase;
import com.ia.para.devs.mockai.domain.port.in.ValidateFileUseCase;

/**
 * Testes unitários para ImportController.
 * Valida o fluxo de importação de arquivo via multipart.
 */
@ExtendWith(MockitoExtension.class)
class ImportControllerTest {

    @Mock
    private ValidateFileUseCase validateFileUseCase;

    @Mock
    private ImportSwaggerUseCase importSwaggerUseCase;

    @InjectMocks
    private ImportController controller;

    @Test
    @DisplayName("Deve retornar HTTP 201 quando importação é bem-sucedida")
    void shouldReturn201WhenImportSucceeds() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "spec.json", "application/json", "{\"openapi\":\"3.0.1\"}".getBytes());

        ResponseEntity<ImportResponse> response = controller.importFile(file);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Arquivo importado com sucesso");
        verify(validateFileUseCase).validate(any(FileData.class));
        verify(importSwaggerUseCase).importSpec(any(FileData.class));
    }

    @Test
    @DisplayName("Deve propagar InvalidExtensionException quando extensão é inválida")
    void shouldPropagateInvalidExtensionException() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "spec.xml", "application/xml", "<xml/>".getBytes());

        doThrow(new InvalidExtensionException("Arquivo com extensão inválida, deve ser .json"))
                .when(validateFileUseCase).validate(any(FileData.class));

        try {
            controller.importFile(file);
        } catch (InvalidExtensionException ex) {
            assertThat(ex.getMessage()).contains(".json");
        }
    }
}
