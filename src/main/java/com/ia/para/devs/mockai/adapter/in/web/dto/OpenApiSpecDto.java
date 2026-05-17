package com.ia.para.devs.mockai.adapter.in.web.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO raiz que representa a estrutura de um documento OpenAPI 3.0 desserializado via Jackson.
 * Pertence à camada adapter/in pois é específico do formato de entrada (JSON OpenAPI).
 *
 * Campos genéricos (Object) garantem compatibilidade com Jackson 2.x e 3.x coexistentes
 * no classpath do Spring Boot 4.x.
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

    /**
     * Bloco "components" da spec OpenAPI preservado como estrutura genérica.
     * Usado para resolver referências $ref em tempo de execução.
     */
    Object components;
}
