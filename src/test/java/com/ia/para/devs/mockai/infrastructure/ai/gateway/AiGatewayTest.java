package com.ia.para.devs.mockai.infrastructure.ai.gateway;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.ai.chat.client.ChatClient;

import com.ia.para.devs.mockai.domain.exception.AiCommunicationException;

/**
 * Testes unitários para AiGateway.
 * Valida validações de entrada e tratamento de erros.
 */
class AiGatewayTest {

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando prompt é null")
    void shouldThrowWhenPromptIsNull() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class);
        when(builder.build()).thenReturn(chatClient);

        AiGateway gateway = new AiGateway(builder, "valid-key");

        assertThatThrownBy(() -> gateway.sendPrompt(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nulo ou vazio");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando prompt é vazio")
    void shouldThrowWhenPromptIsEmpty() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class);
        when(builder.build()).thenReturn(chatClient);

        AiGateway gateway = new AiGateway(builder, "valid-key");

        assertThatThrownBy(() -> gateway.sendPrompt(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nulo ou vazio");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando prompt é whitespace")
    void shouldThrowWhenPromptIsBlank() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class);
        when(builder.build()).thenReturn(chatClient);

        AiGateway gateway = new AiGateway(builder, "valid-key");

        assertThatThrownBy(() -> gateway.sendPrompt("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nulo ou vazio");
    }

    @Test
    @DisplayName("Deve lançar AiCommunicationException quando API key não está configurada")
    void shouldThrowWhenApiKeyIsNotConfigured() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class);
        when(builder.build()).thenReturn(chatClient);

        AiGateway gateway = new AiGateway(builder, "");

        assertThatThrownBy(() -> gateway.sendPrompt("hello"))
                .isInstanceOf(AiCommunicationException.class)
                .hasMessageContaining("chave de API");
    }

    @Test
    @DisplayName("Deve lançar AiCommunicationException quando API key é null")
    void shouldThrowWhenApiKeyIsNull() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class);
        when(builder.build()).thenReturn(chatClient);

        AiGateway gateway = new AiGateway(builder, null);

        assertThatThrownBy(() -> gateway.sendPrompt("hello"))
                .isInstanceOf(AiCommunicationException.class)
                .hasMessageContaining("chave de API");
    }

    @Test
    @DisplayName("Deve lançar AiCommunicationException quando ocorre erro inesperado na chamada")
    void shouldThrowAiCommunicationExceptionOnUnexpectedError() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class);

        when(builder.build()).thenReturn(chatClient);
        when(chatClient.prompt()).thenThrow(new RuntimeException("network error"));

        AiGateway gateway = new AiGateway(builder, "valid-key");

        assertThatThrownBy(() -> gateway.sendPrompt("hello"))
                .isInstanceOf(AiCommunicationException.class)
                .hasMessageContaining("Erro inesperado");
    }
}
