package com.ia.para.devs.mockai.domain.exception;

/**
 * Exceção de domínio lançada quando ocorre falha genérica durante a persistência
 * de dados de uma especificação OpenAPI.
 * Mapeada para HTTP 500 (Internal Server Error) pelo GlobalExceptionHandler.
 */
public class PersistenceFailureException extends RuntimeException {

    public PersistenceFailureException(String message) {
        super(message);
    }

    public PersistenceFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
