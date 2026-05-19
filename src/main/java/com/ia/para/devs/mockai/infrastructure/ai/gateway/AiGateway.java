package com.ia.para.devs.mockai.infrastructure.ai.gateway;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.ia.para.devs.mockai.domain.exception.AiCommunicationException;
import com.ia.para.devs.mockai.domain.port.out.AiPort;

/**
 * Gateway de infraestrutura responsável pela comunicação com o serviço de IA (Groq API).
 * Implementa o port de saída {@link AiPort} utilizando o {@code ChatClient} do Spring AI.
 *
 * <p>Encapsula todos os detalhes de comunicação HTTP com o Groq, garantindo que
 * nenhuma exceção interna do framework vaze para as camadas superiores — todas as
 * falhas são convertidas em {@link AiCommunicationException}.</p>
 */
@Component
public class AiGateway implements AiPort {

    private final ChatClient chatClient;
    private final String apiKey;

    /**
     * Cria uma nova instância do gateway, construindo o {@code ChatClient} a partir do builder fornecido.
     *
     * @param chatClientBuilder builder do Spring AI para construção do cliente de chat
     * @param apiKey            chave de API do Groq, lida da propriedade {@code spring.ai.openai.api-key}
     */
    public AiGateway(ChatClient.Builder chatClientBuilder,
                     @Value("${spring.ai.openai.api-key:}") String apiKey) {
        this.chatClient = chatClientBuilder.build();
        this.apiKey = apiKey;
    }

    /**
     * Envia um prompt para o serviço de IA e retorna a resposta gerada pelo modelo.
     *
     * <p>Validações realizadas antes do envio:</p>
     * <ul>
     *   <li>Prompt nulo, vazio ou somente espaços → {@link IllegalArgumentException}</li>
     *   <li>Chave de API ausente ou vazia → {@link AiCommunicationException}</li>
     * </ul>
     *
     * <p>Tratamento de erros durante a chamada:</p>
     * <ul>
     *   <li>HTTP 401 → falha de autenticação</li>
     *   <li>HTTP 4xx/5xx → erro HTTP descritivo</li>
     *   <li>Timeout → falha por tempo esgotado</li>
     *   <li>Qualquer outra exceção → erro genérico de comunicação</li>
     * </ul>
     *
     * @param prompt texto de entrada não nulo e não vazio
     * @return resposta gerada pelo modelo de IA
     * @throws IllegalArgumentException  se o prompt for nulo, vazio ou whitespace
     * @throws AiCommunicationException  se ocorrer qualquer falha na comunicação com a IA
     */
    @Override
    public String sendPrompt(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("O prompt não pode ser nulo ou vazio");
        }
        if (apiKey == null || apiKey.isBlank()) {
            throw new AiCommunicationException("A chave de API do serviço de IA não foi configurada");
        }
        try {
            return chatClient.prompt().user(prompt).call().content();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().value() == 401) {
                throw new AiCommunicationException(
                        "Falha de autenticação com o serviço de IA: chave de API inválida ou expirada", ex);
            }
            throw new AiCommunicationException(
                    "Erro HTTP " + ex.getStatusCode().value() + " ao comunicar com o serviço de IA: " + ex.getMessage(), ex);
        } catch (HttpStatusCodeException ex) {
            throw new AiCommunicationException(
                    "Erro HTTP " + ex.getStatusCode().value() + " ao comunicar com o serviço de IA: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            if (isTimeoutException(ex)) {
                throw new AiCommunicationException(
                        "Tempo limite esgotado ao aguardar resposta do serviço de IA", ex);
            }
            if (contains401(ex)) {
                throw new AiCommunicationException(
                        "Falha de autenticação com o serviço de IA: chave de API inválida ou expirada", ex);
            }
            throw new AiCommunicationException(
                    "Erro inesperado ao comunicar com o serviço de IA: " + ex.getMessage(), ex);
        }
    }

    private boolean isTimeoutException(Throwable ex) {
        if (ex instanceof TimeoutException || ex instanceof SocketTimeoutException) {
            return true;
        }
        Throwable cause = ex.getCause();
        return cause != null && isTimeoutException(cause);
    }

    private boolean contains401(Throwable ex) {
        if (ex == null) {
            return false;
        }
        String message = ex.getMessage();
        if (message != null && message.contains("401")) {
            return true;
        }
        return contains401(ex.getCause());
    }
}
