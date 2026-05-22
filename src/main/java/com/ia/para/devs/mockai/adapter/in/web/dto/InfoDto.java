package com.ia.para.devs.mockai.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO que representa o bloco "info" de um documento OpenAPI 3.0.
 * Mapeia para os campos title, version e description de ApiSpecificationEntity.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoDto {

    String title;

    String version;

    String description;
}
