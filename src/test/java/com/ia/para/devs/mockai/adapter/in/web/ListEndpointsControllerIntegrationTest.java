package com.ia.para.devs.mockai.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.ApiSpecificationEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.ApiSpecificationRepository;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.EndpointDefinitionRepository;

/**
 * Testes de integração para o endpoint GET /endpoints da issue #24.
 * Insere dados diretamente no banco H2 para validar a listagem sem depender do POST /import.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListEndpointsControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EndpointDefinitionRepository endpointDefinitionRepository;

    @Autowired
    private ApiSpecificationRepository apiSpecificationRepository;

    @BeforeEach
    void setUp() {
        endpointDefinitionRepository.deleteAll();
        apiSpecificationRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar HTTP 200 com lista vazia quando não há endpoints cadastrados")
    void shouldReturn200WithEmptyListWhenNoEndpoints() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("/endpoints"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode body = objectMapper.readTree(response.getBody());
        assertThat(body.isArray()).isTrue();
        assertThat(body.size()).isZero();
    }

    @Test
    @DisplayName("Deve retornar HTTP 200 com endpoints persistidos no banco")
    void shouldReturn200WithPersistedEndpoints() throws Exception {
        ApiSpecificationEntity spec = new ApiSpecificationEntity();
        spec.setTitle("Test API");
        spec.setVersion("1.0.0");
        apiSpecificationRepository.save(spec);

        EndpointDefinitionEntity endpoint = new EndpointDefinitionEntity();
        endpoint.setPath("/items");
        endpoint.setHttpMethod("GET");
        endpoint.setDescription("Lista todos os itens");
        endpoint.setApiSpecification(spec);
        endpointDefinitionRepository.save(endpoint);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("/endpoints"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode body = objectMapper.readTree(response.getBody());
        assertThat(body.isArray()).isTrue();
        assertThat(body.size()).isEqualTo(1);
        assertThat(body.get(0).get("path").asText()).isEqualTo("/items");
        assertThat(body.get(0).get("httpMethod").asText()).isEqualTo("GET");
        assertThat(body.get(0).get("description").asText()).isEqualTo("Lista todos os itens");
    }

    @Test
    @DisplayName("Deve retornar todos os campos obrigatórios: path, httpMethod e description")
    void shouldReturnRequiredFields() throws Exception {
        ApiSpecificationEntity spec = new ApiSpecificationEntity();
        spec.setTitle("Test API");
        spec.setVersion("1.0.0");
        apiSpecificationRepository.save(spec);

        EndpointDefinitionEntity endpoint = new EndpointDefinitionEntity();
        endpoint.setPath("/users");
        endpoint.setHttpMethod("POST");
        endpoint.setDescription("Cria um novo usuário");
        endpoint.setApiSpecification(spec);
        endpointDefinitionRepository.save(endpoint);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("/endpoints"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode item = objectMapper.readTree(response.getBody()).get(0);
        assertThat(item.has("path")).isTrue();
        assertThat(item.has("httpMethod")).isTrue();
        assertThat(item.has("description")).isTrue();
    }

    @Test
    @DisplayName("Deve retornar múltiplos endpoints quando existem vários cadastrados")
    void shouldReturnMultipleEndpoints() throws Exception {
        ApiSpecificationEntity spec = new ApiSpecificationEntity();
        spec.setTitle("Test API");
        spec.setVersion("1.0.0");
        apiSpecificationRepository.save(spec);

        EndpointDefinitionEntity e1 = new EndpointDefinitionEntity();
        e1.setPath("/items");
        e1.setHttpMethod("GET");
        e1.setDescription("Lista itens");
        e1.setApiSpecification(spec);

        EndpointDefinitionEntity e2 = new EndpointDefinitionEntity();
        e2.setPath("/items/{id}");
        e2.setHttpMethod("DELETE");
        e2.setDescription("Remove item");
        e2.setApiSpecification(spec);

        endpointDefinitionRepository.save(e1);
        endpointDefinitionRepository.save(e2);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl("/endpoints"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode body = objectMapper.readTree(response.getBody());
        assertThat(body.isArray()).isTrue();
        assertThat(body.size()).isEqualTo(2);
    }

    private String baseUrl(String path) {
        return "http://localhost:" + port + "/mockai" + path;
    }
}
