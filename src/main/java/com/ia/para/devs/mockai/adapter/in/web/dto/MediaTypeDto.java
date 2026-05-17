package com.ia.para.devs.mockai.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO que representa um media type dentro do bloco "content" de uma resposta OpenAPI 3.0.
 * O schema é mantido como JsonNode para preservar a estrutura original e serializar
 * como string em EndpointResponseEntity.responseSchema.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MediaTypeDto {

    /**
     * Schema da resposta mantido como JsonNode para suportar qualquer estrutura
     * (incluindo $ref, allOf, oneOf, etc.) sem perda de informação.
     */
    JsonNode schema;
}
