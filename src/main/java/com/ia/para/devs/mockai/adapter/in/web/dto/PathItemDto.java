package com.ia.para.devs.mockai.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

/**
 * DTO que representa a definição de um endpoint dentro de um path e método HTTP.
 * Mapeia para EndpointDefinitionEntity (summary, description, tags),
 * PathParameterEntity (parameters com in=path) e EndpointResponseEntity (responses).
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PathItemDto {

    String summary;

    String description;

    /** Nomes das tags globais associadas a este endpoint. */
    List<String> tags;

    /** Parâmetros do endpoint — apenas os com "in": "path" serão persistidos. */
    List<ParameterDto> parameters;

    /**
     * Mapa de respostas: chave = código de status HTTP (ex: "200", "404"),
     * valor = definição da resposta.
     */
    Map<String, ResponseDto> responses;
}
