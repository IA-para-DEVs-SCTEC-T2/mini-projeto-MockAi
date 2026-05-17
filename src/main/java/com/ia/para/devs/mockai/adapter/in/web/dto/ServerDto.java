package com.ia.para.devs.mockai.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO que representa uma entrada do bloco "servers" de um documento OpenAPI 3.0.
 * O primeiro servidor (servers[0].url) é mapeado para ApiSpecificationEntity.baseUrl.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServerDto {

    String url;
}
