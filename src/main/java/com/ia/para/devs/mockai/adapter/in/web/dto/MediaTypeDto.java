package com.ia.para.devs.mockai.adapter.in.web.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO que representa um media type dentro do bloco "content" de uma resposta OpenAPI 3.0.
 *
 * Os campos schema, example e examples são declarados como Object/Map para garantir
 * compatibilidade com ambos os Jacksons presentes no classpath (2.x e 3.x do Spring Boot 4).
 * O ObjectMapper 2.x serializa esses campos corretamente para JSON string na camada de persistência.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MediaTypeDto {

    /**
     * Schema da resposta como estrutura genérica (Map/List/primitivo).
     * Preserva $ref, allOf, oneOf, etc. sem depender de JsonNode de versão específica.
     */
    Object schema;

    /**
     * Exemplo literal (campo `example` do OpenAPI) se presente.
     */
    Object example;

    /**
     * Exemplos nomeados (campo `examples` do OpenAPI) se presente.
     * Cada valor é um objeto Example do OpenAPI (pode ter campo "value").
     */
    Map<String, Object> examples;
}
