package com.ia.para.devs.mockai.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO que representa o schema de um parâmetro ou resposta OpenAPI 3.0.
 * O campo "type" é mapeado para PathParameterEntity.type.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchemaDto {

    /** Tipo primitivo do schema (ex: "string", "integer", "boolean"). */
    String type;
}
