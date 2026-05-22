package com.ia.para.devs.mockai.adapter.in.web;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListEndpointsIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SPEC = "{\n" +
            "  \"openapi\": \"3.0.1\",\n" +
            "  \"info\": {\n" +
            "    \"title\": \"Test API\",\n" +
            "    \"description\": \"API de teste para listagem de endpoints\",\n" +
            "    \"version\": \"1.0.0\"\n" +
            "  },\n" +
            "  \"paths\": {\n" +
            "    \"/items\": {\n" +
            "      \"get\": {\n" +
            "        \"summary\": \"List items\",\n" +
            "        \"description\": \"Retorna lista de itens\",\n" +
            "        \"responses\": {\n" +
            "          \"200\": {\n" +
            "            \"description\": \"OK\",\n" +
            "            \"content\": {\n" +
            "              \"application/json\": {\n" +
            "                \"schema\": {\n" +
            "                  \"type\": \"object\",\n" +
            "                  \"properties\": {\n" +
            "                    \"id\": { \"type\": \"integer\" }\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    @DisplayName("Deve retornar lista vazia quando não há endpoints persistidos")
    void shouldReturnEmptyListWhenNoEndpointsPersisted() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("/endpoints"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode body = objectMapper.readTree(response.getBody());
        assertThat(body.isArray()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar endpoints com path, método HTTP e descrição após importação")
    void shouldReturnEndpointsAfterImport() throws Exception {
        uploadSpec(SPEC);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("/endpoints"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode body = objectMapper.readTree(response.getBody());
        assertThat(body.isArray()).isTrue();
        assertThat(body.size()).isGreaterThan(0);

        JsonNode first = body.get(0);
        assertThat(first.has("path")).isTrue();
        assertThat(first.has("httpMethod")).isTrue();
        assertThat(first.has("description")).isTrue();
    }

    private void uploadSpec(String content) {
        ByteArrayResource payload = new ByteArrayResource(content.getBytes(StandardCharsets.UTF_8)) {
            @Override
            public String getFilename() {
                return "spec.json";
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", payload);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        restTemplate.postForEntity(baseUrl("/import"), new HttpEntity<>(body, headers), String.class);
    }

    private String baseUrl(String path) {
        return "http://localhost:" + port + "/mockai" + path;
    }
}
