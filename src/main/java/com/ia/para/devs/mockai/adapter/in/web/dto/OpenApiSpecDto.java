package com.ia.para.devs.mockai.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

/**
 * DTO raiz que representa a estrutura de um documento OpenAPI 3.0 desserializado via Jackson.
 * Pertence à camada adapter/in pois é específico do formato de entrada (JSON OpenAPI).
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpenApiSpecDto {

    InfoDto info;

    List<ServerDto> servers;

    List<TagDto> tags;

    /**
     * Mapa de paths: chave = caminho do endpoint (ex: "/pet/{petId}"),
     * valor = mapa de métodos HTTP para definição do endpoint.
     */
    Map<String, Map<String, PathItemDto>> paths;
}
