package com.ia.para.devs.mockai.domain.exception;

/**
 * Exceção de domínio lançada quando o conteúdo do arquivo JSON não pode ser
 * desserializado como uma especificação OpenAPI válida.
 * Mapeada para HTTP 400 (Bad Request) pelo GlobalExceptionHandler.
 */
public class InvalidSwaggerContentException extends RuntimeException {

    public InvalidSwaggerContentException(String message) {
        super(message);
    }

    public InvalidSwaggerContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
