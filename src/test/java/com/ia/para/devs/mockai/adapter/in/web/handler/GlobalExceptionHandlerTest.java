package com.ia.para.devs.mockai.adapter.in.web.handler;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ia.para.devs.mockai.adapter.in.web.dto.ImportResponse;
import com.ia.para.devs.mockai.domain.exception.DatabaseConnectionException;
import com.ia.para.devs.mockai.domain.exception.InvalidExtensionException;
import com.ia.para.devs.mockai.domain.exception.InvalidSwaggerContentException;
import com.ia.para.devs.mockai.domain.exception.PersistenceDeletionException;
import com.ia.para.devs.mockai.domain.exception.PersistenceFailureException;
import com.ia.para.devs.mockai.domain.exception.ReferentialIntegrityException;

/**
 * Testes unitários para GlobalExceptionHandler.
 * Valida o mapeamento de exceções de domínio para respostas HTTP.
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Deve mapear InvalidExtensionException para HTTP 400")
    void shouldMapInvalidExtensionTo400() {
        InvalidExtensionException ex = new InvalidExtensionException("extensão inválida");

        ResponseEntity<ImportResponse> response = handler.handleInvalidExtension(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().message()).contains(".json");
    }

    @Test
    @DisplayName("Deve mapear InvalidSwaggerContentException para HTTP 400")
    void shouldMapInvalidSwaggerContentTo400() {
        InvalidSwaggerContentException ex = new InvalidSwaggerContentException("JSON inválido");

        ResponseEntity<ImportResponse> response = handler.handleInvalidSwaggerContent(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().message()).isEqualTo("JSON inválido");
    }

    @Test
    @DisplayName("Deve mapear DatabaseConnectionException para HTTP 503")
    void shouldMapDatabaseConnectionTo503() {
        DatabaseConnectionException ex = new DatabaseConnectionException("falha de conexão", new RuntimeException());

        ResponseEntity<ImportResponse> response = handler.handleDatabaseConnection(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(response.getBody().message()).contains("banco de dados");
    }

    @Test
    @DisplayName("Deve mapear ReferentialIntegrityException para HTTP 409")
    void shouldMapReferentialIntegrityTo409() {
        ReferentialIntegrityException ex = new ReferentialIntegrityException("violação FK", new RuntimeException());

        ResponseEntity<ImportResponse> response = handler.handleReferentialIntegrity(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().message()).contains("violação FK");
    }

    @Test
    @DisplayName("Deve mapear PersistenceDeletionException para HTTP 500")
    void shouldMapPersistenceDeletionTo500() {
        PersistenceDeletionException ex = new PersistenceDeletionException("falha ao deletar", new RuntimeException());

        ResponseEntity<ImportResponse> response = handler.handlePersistenceDeletion(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().message()).contains("falha ao deletar");
    }

    @Test
    @DisplayName("Deve mapear PersistenceFailureException para HTTP 500")
    void shouldMapPersistenceFailureTo500() {
        PersistenceFailureException ex = new PersistenceFailureException("falha genérica", new RuntimeException());

        ResponseEntity<ImportResponse> response = handler.handlePersistenceFailure(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().message()).contains("falha genérica");
    }
}
