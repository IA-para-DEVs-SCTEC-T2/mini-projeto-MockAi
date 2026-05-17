package com.ia.para.devs.mockai.domain.exception;

/**
 * Exceção de domínio lançada quando ocorre falha de conexão com o banco de dados.
 * Mapeada para HTTP 503 (Service Unavailable) pelo GlobalExceptionHandler.
 */
public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
