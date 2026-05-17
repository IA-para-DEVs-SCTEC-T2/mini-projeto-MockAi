package com.ia.para.devs.mockai.adapter.in.web.dto;

/**
 * DTO de resposta que representa um endpoint mockado disponível.
 */
public record EndpointResponse(String path, String httpMethod, String description) {
}
