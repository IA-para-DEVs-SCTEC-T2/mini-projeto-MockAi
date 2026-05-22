package com.ia.para.devs.mockai.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ia.para.devs.mockai.domain.port.in.CheckAiConnectionUseCase;

/**
 * Testes unitários para AiConnectionController.
 * Valida os cenários de conexão com IA funcional e indisponível.
 */
@ExtendWith(MockitoExtension.class)
class AiConnectionControllerTest {

    @Mock
    private CheckAiConnectionUseCase checkAiConnectionUseCase;

    @InjectMocks
    private AiConnectionController controller;

    @Test
    @DisplayName("Deve retornar HTTP 200 quando conexão com IA está funcional")
    void shouldReturn200WhenConnectionIsUp() {
        when(checkAiConnectionUseCase.checkConnection()).thenReturn(true);

        ResponseEntity<String> response = controller.testAiConnection();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("funcional");
    }

    @Test
    @DisplayName("Deve retornar HTTP 503 quando conexão com IA está indisponível")
    void shouldReturn503WhenConnectionIsDown() {
        when(checkAiConnectionUseCase.checkConnection()).thenReturn(false);

        ResponseEntity<String> response = controller.testAiConnection();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(response.getBody()).contains("indisponível");
    }
}
