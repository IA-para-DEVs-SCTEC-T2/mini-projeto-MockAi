package com.ia.para.devs.mockai.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO que representa um parâmetro de endpoint OpenAPI 3.0.
 * Apenas parâmetros com "in": "path" são mapeados para PathParameterEntity.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParameterDto {

    String name;

    /** Localização do parâmetro: "path", "query", "header" ou "cookie". */
    String in;

    Boolean required;

    SchemaDto schema;
}
