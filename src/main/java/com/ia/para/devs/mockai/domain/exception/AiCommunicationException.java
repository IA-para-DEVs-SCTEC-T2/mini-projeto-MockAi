package com.ia.para.devs.mockai.domain.exception;

/**
 * Exceção de domínio lançada quando ocorre falha na comunicação com o serviço de IA (OpenAI API).
 * Encapsula erros HTTP (4xx/5xx), timeout e falhas de autenticação,
 * garantindo que detalhes internos do framework não vazem para as camadas superiores.
 */
public class AiCommunicationException extends RuntimeException {

    /**
     * Cria uma nova exceção com a mensagem descritiva do erro.
     *
     * @param message mensagem em português descrevendo a falha de comunicação
     */
    public AiCommunicationException(String message) {
        super(message);
    }

    /**
     * Cria uma nova exceção com a mensagem descritiva e a causa original do erro.
     *
     * @param message mensagem em português descrevendo a falha de comunicação
     * @param cause   exceção original que causou a falha
     */
    public AiCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
