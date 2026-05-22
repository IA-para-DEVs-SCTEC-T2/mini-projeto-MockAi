package com.ia.para.devs.mockai.adapter.in.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ia.para.devs.mockai.adapter.in.web.dto.ImportResponse;
import com.ia.para.devs.mockai.domain.exception.DatabaseConnectionException;
import com.ia.para.devs.mockai.domain.exception.InvalidExtensionException;
import com.ia.para.devs.mockai.domain.exception.InvalidSwaggerContentException;
import com.ia.para.devs.mockai.domain.exception.PersistenceDeletionException;
import com.ia.para.devs.mockai.domain.exception.PersistenceFailureException;
import com.ia.para.devs.mockai.domain.exception.ReferentialIntegrityException;

/**
 * Componente @ControllerAdvice que intercepta exceções de domínio e as mapeia
 * para respostas HTTP sem expor detalhes internos (stack trace, nome de classe).
 *
 * Handlers existentes (task #58 — não alterados):
 *   InvalidExtensionException       → HTTP 400
 *
 * Handlers adicionados (etapa 4 e 5):
 *   InvalidSwaggerContentException  → HTTP 400 (conteúdo JSON inválido)
 *   DatabaseConnectionException     → HTTP 503 (banco indisponível)
 *   ReferentialIntegrityException   → HTTP 409 (violação de FK/constraint)
 *   PersistenceDeletionException    → HTTP 500 (falha ao deletar dados anteriores)
 *   PersistenceFailureException     → HTTP 500 (falha genérica de persistência)
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // -------------------------------------------------------------------------
    // Handlers da task #58 — mensagens não alteradas
    // -------------------------------------------------------------------------

    @ExceptionHandler(InvalidExtensionException.class)
    public ResponseEntity<ImportResponse> handleInvalidExtension(InvalidExtensionException ex) {
        return ResponseEntity.badRequest().body(new ImportResponse("Arquivo com extensão inválida, deve ser .json"));
    }

    // -------------------------------------------------------------------------
    // Handlers da etapa 5 — erro de desserialização do conteúdo
    // -------------------------------------------------------------------------

    /**
     * Conteúdo do arquivo JSON inválido ou não reconhecido como OpenAPI.
     * HTTP 400 — erro de entrada do cliente.
     */
    @ExceptionHandler(InvalidSwaggerContentException.class)
    public ResponseEntity<ImportResponse> handleInvalidSwaggerContent(InvalidSwaggerContentException ex) {
        return ResponseEntity.badRequest().body(new ImportResponse(ex.getMessage()));
    }

    // -------------------------------------------------------------------------
    // Handlers da etapa 4 — erros de persistência
    // -------------------------------------------------------------------------

    /**
     * Falha de conexão com o banco de dados.
     * HTTP 503 — serviço de banco temporariamente indisponível.
     */
    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ImportResponse> handleDatabaseConnection(DatabaseConnectionException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ImportResponse("Falha de conexão com o banco de dados"));
    }

    /**
     * Violação de integridade referencial (FK, constraint única, etc.).
     * HTTP 409 — conflito de estado dos dados.
     */
    @ExceptionHandler(ReferentialIntegrityException.class)
    public ResponseEntity<ImportResponse> handleReferentialIntegrity(ReferentialIntegrityException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ImportResponse(ex.getMessage()));
    }

    /**
     * Falha durante a deleção dos dados anteriores.
     * HTTP 500 — erro interno; rollback já foi executado pelo Spring.
     */
    @ExceptionHandler(PersistenceDeletionException.class)
    public ResponseEntity<ImportResponse> handlePersistenceDeletion(PersistenceDeletionException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ImportResponse(ex.getMessage()));
    }

    /**
     * Falha genérica durante a persistência dos novos dados.
     * HTTP 500 — erro interno; rollback já foi executado pelo Spring.
     */
    @ExceptionHandler(PersistenceFailureException.class)
    public ResponseEntity<ImportResponse> handlePersistenceFailure(PersistenceFailureException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ImportResponse(ex.getMessage()));
    }
}
