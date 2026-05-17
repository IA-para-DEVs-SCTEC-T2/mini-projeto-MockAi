package com.ia.para.devs.mockai.domain.exception;

/**
 * Exceção de domínio lançada quando ocorre violação de integridade referencial
 * durante a persistência de dados.
 * Mapeada para HTTP 409 (Conflict) pelo GlobalExceptionHandler.
 */
public class ReferentialIntegrityException extends RuntimeException {

    public ReferentialIntegrityException(String message) {
        super(message);
    }

    public ReferentialIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
