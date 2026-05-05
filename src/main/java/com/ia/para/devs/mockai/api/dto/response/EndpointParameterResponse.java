package com.ia.para.devs.mockai.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de saída representando um parâmetro de um endpoint mock.
 */
@Schema(description = "Parâmetro de um endpoint (path, query, header ou cookie)")
public class EndpointParameterResponse {

    @Schema(description = "Nome do parâmetro", example = "id")
    private String name;

    @Schema(description = "Localização do parâmetro", example = "path", allowableValues = {"path", "query", "header", "cookie"})
    private String in;

    @Schema(description = "Descrição do parâmetro", example = "Identificador único do usuário")
    private String description;

    @Schema(description = "Indica se o parâmetro é obrigatório", example = "true")
    private boolean required;

    @Schema(description = "Tipo do parâmetro", example = "integer")
    private String type;

    public EndpointParameterResponse() {}

    public EndpointParameterResponse(String name, String in, String description, boolean required, String type) {
        this.name = name;
        this.in = in;
        this.description = description;
        this.required = required;
        this.type = type;
    }

    public String getName() { return name; }
    public String getIn() { return in; }
    public String getDescription() { return description; }
    public boolean isRequired() { return required; }
    public String getType() { return type; }
}
