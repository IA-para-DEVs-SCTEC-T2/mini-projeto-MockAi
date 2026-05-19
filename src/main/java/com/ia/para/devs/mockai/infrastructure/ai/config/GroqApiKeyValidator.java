package com.ia.para.devs.mockai.infrastructure.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * Validador de inicialização responsável por garantir que a chave de API do Groq
 * esteja configurada antes que a aplicação suba completamente.
 *
 * <p>Se a variável de ambiente {@code GROQ_API_KEY} não estiver definida ou estiver
 * vazia, a aplicação será abortada com uma mensagem de erro clara.</p>
 */
@Component
public class GroqApiKeyValidator {

    private final String apiKey;

    public GroqApiKeyValidator(@Value("${spring.ai.openai.api-key:}") String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Valida a presença da chave de API do Groq durante a inicialização do contexto Spring.
     *
     * @throws IllegalStateException se a chave de API estiver ausente ou vazia
     */
    @PostConstruct
    public void validate() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                "A variável de ambiente GROQ_API_KEY não está configurada. " +
                "A aplicação não pode ser iniciada sem a chave de API do serviço de IA."
            );
        }
    }
}
