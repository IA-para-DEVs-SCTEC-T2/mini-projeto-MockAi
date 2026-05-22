package com.ia.para.devs.mockai.application.service;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.adapter.in.web.dynamic.DynamicResponseBodyBuilder;
import com.ia.para.devs.mockai.domain.exception.AiCommunicationException;
import com.ia.para.devs.mockai.domain.port.out.AiPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointResponseEntity;

/**
 * Testes unitários para GenerateEndpointResponseService.
 * Valida a geração de respostas mockadas via IA.
 */
@ExtendWith(MockitoExtension.class)
class GenerateEndpointResponseServiceTest {

    @Mock
    private AiPort aiPort;

    @Mock
    private DynamicResponseBodyBuilder responseBodyBuilder;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private GenerateEndpointResponseService service;

    @Test
    @DisplayName("Deve retornar null quando endpoint não tem respostas")
    void shouldReturnNullWhenNoResponses() {
        EndpointDefinitionEntity endpoint = buildEndpoint(null);

        String result = service.generate(endpoint);

        assertThat(result).isNull();
        verify(aiPort, never()).sendPrompt(anyString());
    }

    @Test
    @DisplayName("Deve retornar null quando responseSchema é null")
    void shouldReturnNullWhenSchemaIsNull() {
        EndpointResponseEntity response = buildResponse("200", null);
        EndpointDefinitionEntity endpoint = buildEndpoint(Set.of(response));

        String result = service.generate(endpoint);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve retornar null quando responseSchema é vazio")
    void shouldReturnNullWhenSchemaIsBlank() {
        EndpointResponseEntity response = buildResponse("200", "   ");
        EndpointDefinitionEntity endpoint = buildEndpoint(Set.of(response));

        String result = service.generate(endpoint);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve retornar null quando builder resolve schema como null")
    void shouldReturnNullWhenBuilderReturnsNull() {
        EndpointResponseEntity response = buildResponse("200", "{\"type\":\"object\"}");
        EndpointDefinitionEntity endpoint = buildEndpoint(Set.of(response));

        when(responseBodyBuilder.buildResponseBody(eq("{\"type\":\"object\"}"), isNull()))
                .thenReturn(null);

        String result = service.generate(endpoint);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve enviar prompt à IA e retornar resposta")
    void shouldSendPromptAndReturnResponse() {
        EndpointResponseEntity response = buildResponse("200", "{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"}}}");
        EndpointDefinitionEntity endpoint = buildEndpoint(Set.of(response));
        endpoint.setHttpMethod("GET");
        endpoint.setPath("/pets");

        Map<String, Object> resolvedSchema = new LinkedHashMap<>();
        resolvedSchema.put("name", "string");

        when(responseBodyBuilder.buildResponseBody(anyString(), isNull()))
                .thenReturn(resolvedSchema);
        when(aiPort.sendPrompt(anyString())).thenReturn("{\"name\":\"Rex\"}");

        String result = service.generate(endpoint);

        assertThat(result).isEqualTo("{\"name\":\"Rex\"}");
    }

    @Test
    @DisplayName("Deve remover code fences da resposta da IA")
    void shouldStripCodeFencesFromAiResponse() {
        EndpointResponseEntity response = buildResponse("200", "{\"type\":\"object\"}");
        EndpointDefinitionEntity endpoint = buildEndpoint(Set.of(response));
        endpoint.setHttpMethod("GET");
        endpoint.setPath("/items");

        when(responseBodyBuilder.buildResponseBody(anyString(), isNull()))
                .thenReturn(Map.of("id", 1));
        when(aiPort.sendPrompt(anyString())).thenReturn("```json\n{\"id\":1}\n```");

        String result = service.generate(endpoint);

        assertThat(result).isEqualTo("{\"id\":1}");
    }

    @Test
    @DisplayName("Deve lançar AiCommunicationException quando IA falha")
    void shouldThrowWhenAiFails() {
        EndpointResponseEntity response = buildResponse("200", "{\"type\":\"object\"}");
        EndpointDefinitionEntity endpoint = buildEndpoint(Set.of(response));
        endpoint.setHttpMethod("GET");
        endpoint.setPath("/items");

        when(responseBodyBuilder.buildResponseBody(anyString(), isNull()))
                .thenReturn(Map.of("id", 1));
        when(aiPort.sendPrompt(anyString())).thenThrow(new RuntimeException("timeout"));

        assertThatThrownBy(() -> service.generate(endpoint))
                .isInstanceOf(AiCommunicationException.class)
                .hasMessageContaining("/items");
    }

    @Test
    @DisplayName("Deve retornar null quando IA responde com string vazia")
    void shouldReturnNullWhenAiRespondsEmpty() {
        EndpointResponseEntity response = buildResponse("200", "{\"type\":\"object\"}");
        EndpointDefinitionEntity endpoint = buildEndpoint(Set.of(response));
        endpoint.setHttpMethod("GET");
        endpoint.setPath("/items");

        when(responseBodyBuilder.buildResponseBody(anyString(), isNull()))
                .thenReturn(Map.of("id", 1));
        when(aiPort.sendPrompt(anyString())).thenReturn("");

        String result = service.generate(endpoint);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve priorizar resposta 200 sobre 201")
    void shouldPrioritize200Over201() {
        EndpointResponseEntity response200 = buildResponse("200", "{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\"}}}");
        EndpointResponseEntity response201 = buildResponse("201", "{\"type\":\"string\"}");
        Set<EndpointResponseEntity> responses = new LinkedHashSet<>();
        responses.add(response201);
        responses.add(response200);
        EndpointDefinitionEntity endpoint = buildEndpoint(responses);
        endpoint.setHttpMethod("POST");
        endpoint.setPath("/items");

        when(responseBodyBuilder.buildResponseBody(anyString(), isNull()))
                .thenReturn(Map.of("id", 0));
        when(aiPort.sendPrompt(anyString())).thenReturn("{\"id\":42}");

        String result = service.generate(endpoint);

        assertThat(result).isEqualTo("{\"id\":42}");
    }

    private EndpointDefinitionEntity buildEndpoint(Set<EndpointResponseEntity> responses) {
        EndpointDefinitionEntity endpoint = new EndpointDefinitionEntity();
        endpoint.setPath("/test");
        endpoint.setHttpMethod("GET");
        endpoint.setResponses(responses != null ? responses : new LinkedHashSet<>());
        return endpoint;
    }

    private EndpointResponseEntity buildResponse(String statusCode, String schema) {
        EndpointResponseEntity response = new EndpointResponseEntity();
        response.setStatusCode(statusCode);
        response.setContentType("application/json");
        response.setResponseSchema(schema);
        return response;
    }
}
