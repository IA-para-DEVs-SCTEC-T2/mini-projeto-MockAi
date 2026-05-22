package com.ia.para.devs.mockai.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.para.devs.mockai.domain.port.out.AiPort;

/**
 * Testes unitários para CheckAiConnectionService.
 * Valida a lógica de verificação de conectividade com o serviço de IA.
 */
@ExtendWith(MockitoExtension.class)
class CheckAiConnectionServiceTest {

    @Mock
    private AiPort aiPort;

    @InjectMocks
    private CheckAiConnectionService service;

    @Test
    @DisplayName("Deve retornar true quando IA responde com conteúdo válido")
    void shouldReturnTrueWhenAiRespondsWithContent() {
        when(aiPort.sendPrompt("ping")).thenReturn("pong");

        boolean result = service.checkConnection();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando IA responde com null")
    void shouldReturnFalseWhenAiRespondsWithNull() {
        when(aiPort.sendPrompt("ping")).thenReturn(null);

        boolean result = service.checkConnection();

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false quando IA responde com string vazia")
    void shouldReturnFalseWhenAiRespondsWithEmptyString() {
        when(aiPort.sendPrompt("ping")).thenReturn("");

        boolean result = service.checkConnection();

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false quando IA responde com string em branco")
    void shouldReturnFalseWhenAiRespondsWithBlankString() {
        when(aiPort.sendPrompt("ping")).thenReturn("   ");

        boolean result = service.checkConnection();

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false quando IA lança exceção")
    void shouldReturnFalseWhenAiThrowsException() {
        when(aiPort.sendPrompt("ping")).thenThrow(new RuntimeException("Connection refused"));

        boolean result = service.checkConnection();

        assertThat(result).isFalse();
    }
}
