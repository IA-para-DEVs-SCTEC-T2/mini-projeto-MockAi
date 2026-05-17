package com.ia.para.devs.mockai.domain.exception;

/**
 * Exceção de domínio lançada quando ocorre falha durante a deleção dos dados
 * anteriores no processo de reimportação de uma especificação OpenAPI.
 * Mapeada para HTTP 500 (Internal Server Error) pelo GlobalExceptionHandler.
 */
public class PersistenceDeletionException extends RuntimeException {

    public PersistenceDeletionException(String message) {
        super(message);
    }

    public PersistenceDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
