package com.ia.para.devs.mockai.adapter.in.web.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO que representa a definição de uma resposta de endpoint OpenAPI 3.0.
 * Mapeia para EndpointResponseEntity (description, contentType, responseSchema).
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDto {

    String description;

    /**
     * Mapa de content types: chave = media type (ex: "application/json"),
     * valor = definição do media type com schema.
     * Cada entrada gera um registro em EndpointResponseEntity.
     */
    Map<String, MediaTypeDto> content;
}
