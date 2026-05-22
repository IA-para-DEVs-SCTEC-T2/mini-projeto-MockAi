package com.ia.para.devs.mockai.application.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ia.para.devs.mockai.domain.exception.InvalidExtensionException;
import com.ia.para.devs.mockai.domain.model.FileData;

/**
 * Testes unitários para ValidateFileService.
 * Valida a lógica de verificação de extensão de arquivo (.json).
 */
class ValidateFileServiceTest {

    private final ValidateFileService service = new ValidateFileService();

    @Test
    @DisplayName("Deve aceitar arquivo com extensão .json")
    void shouldAcceptJsonExtension() {
        FileData file = new FileData("swagger.json", new byte[]{});

        assertThatCode(() -> service.validate(file)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve aceitar arquivo com extensão .JSON (case insensitive)")
    void shouldAcceptJsonExtensionCaseInsensitive() {
        FileData file = new FileData("swagger.JSON", new byte[]{});

        assertThatCode(() -> service.validate(file)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"swagger.xml", "swagger.yaml", "swagger.txt", "swagger.pdf"})
    @DisplayName("Deve rejeitar arquivo com extensão diferente de .json")
    void shouldRejectNonJsonExtension(String filename) {
        FileData file = new FileData(filename, new byte[]{});

        assertThatThrownBy(() -> service.validate(file))
                .isInstanceOf(InvalidExtensionException.class)
                .hasMessageContaining(".json");
    }

    @Test
    @DisplayName("Deve rejeitar arquivo sem extensão")
    void shouldRejectFileWithoutExtension() {
        FileData file = new FileData("swagger", new byte[]{});

        assertThatThrownBy(() -> service.validate(file))
                .isInstanceOf(InvalidExtensionException.class)
                .hasMessageContaining(".json");
    }
}
