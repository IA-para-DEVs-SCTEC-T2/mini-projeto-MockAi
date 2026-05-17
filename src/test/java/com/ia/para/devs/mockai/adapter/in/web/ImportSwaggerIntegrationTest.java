package com.ia.para.devs.mockai.adapter.in.web;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
class ImportSwaggerIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    private static final String SPEC_V1 = "{\n" +
            "  \"openapi\": \"3.0.1\",\n" +
            "  \"info\": {\n" +
            "    \"title\": \"Pets API\",\n" +
            "    \"version\": \"1.0.0\"\n" +
            "  },\n" +
            "  \"paths\": {\n" +
            "    \"/pets\": {\n" +
            "      \"get\": {\n" +
            "        \"summary\": \"List pets\",\n" +
            "        \"responses\": {\n" +
            "          \"200\": {\n" +
            "            \"description\": \"Successful response\",\n" +
            "            \"content\": {\n" +
            "              \"application/json\": {\n" +
            "                \"schema\": {\n" +
            "                  \"type\": \"object\",\n" +
            "                  \"properties\": {\n" +
            "                    \"name\": { \"type\": \"string\" },\n" +
            "                    \"age\": { \"type\": \"integer\" }\n" +
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

    private static final String SPEC_V2 = "{\n" +
            "  \"openapi\": \"3.0.1\",\n" +
            "  \"info\": {\n" +
            "    \"title\": \"Pets API\",\n" +
            "    \"version\": \"2.0.0\"\n" +
            "  },\n" +
            "  \"paths\": {\n" +
            "    \"/pets\": {\n" +
            "      \"get\": {\n" +
            "        \"summary\": \"List pets with breed\",\n" +
            "        \"responses\": {\n" +
            "          \"200\": {\n" +
            "            \"description\": \"Successful response\",\n" +
            "            \"content\": {\n" +
            "              \"application/json\": {\n" +
            "                \"schema\": {\n" +
            "                  \"type\": \"object\",\n" +
            "                  \"properties\": {\n" +
            "                    \"breed\": { \"type\": \"string\" }\n" +
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
    void whenImportingSwaggerSpec_thenDynamicEndpointBecomesAvailable() throws Exception {
        ResponseEntity<String> importResponse = uploadSpec(SPEC_V1);

        assertThat(importResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(importResponse.getBody()).contains("Arquivo importado com sucesso");

        JsonNode firstResponse = requestPets();
        assertThat(firstResponse.has("name")).isTrue();
        assertThat(firstResponse.has("age")).isTrue();

        ResponseEntity<String> reimportResponse = uploadSpec(SPEC_V2);
        assertThat(reimportResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        JsonNode updatedResponse = requestPets();
        assertThat(updatedResponse.has("breed")).isTrue();
        assertThat(updatedResponse.has("name")).isFalse();
    }

    private ResponseEntity<String> uploadSpec(String content) {
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

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(baseUrl("/import"), request, String.class);
    }

    private JsonNode requestPets() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("/pets"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return objectMapper.readTree(response.getBody());
    }

    private String baseUrl(String path) {
        return "http://localhost:" + port + "/mockai" + path;
    }

    private String loadSpecFromFile(String relativePath) throws Exception {
        return Files.readString(Path.of(relativePath), StandardCharsets.UTF_8);
    }

    @Test
    void whenImportingPetstoreJson_thenPetEndpointReturnsDynamicResponse() throws Exception {
        String petstoreSpec = loadSpecFromFile("docs/petstore.json");

        ResponseEntity<String> importResponse = uploadSpec(petstoreSpec);
        assertThat(importResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("/pet"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode body = objectMapper.readTree(response.getBody());
        assertThat(body.isArray()).isTrue();
        assertThat(body).isNotEmpty();

        // Após a resolução de $ref, o body deve conter um objeto Pet com as propriedades reais
        // e não mais o $ref bruto { "$ref": "#/components/schemas/Pet" }
        JsonNode firstItem = body.get(0);
        assertThat(firstItem.isObject()).isTrue();
        assertThat(firstItem.has("$ref")).isFalse();
        // O schema Pet do petstore.json tem: id (integer), name (string), status (enum), photoUrls (array)
        assertThat(firstItem.has("name")).isTrue();
        assertThat(firstItem.has("photoUrls")).isTrue();
    }
}
