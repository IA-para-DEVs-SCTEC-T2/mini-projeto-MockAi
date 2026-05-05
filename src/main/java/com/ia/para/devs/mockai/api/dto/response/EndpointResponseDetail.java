package com.ia.para.devs.mockai.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de saída representando uma resposta possível de um endpoint mock.
 */
@Schema(description = "Resposta possível de um endpoint mock")
public class EndpointResponseDetail {

    @Schema(description = "Código de status HTTP", example = "201")
    private int status;

    @Schema(description = "Descrição da resposta", example = "Usuário criado com sucesso")
    private String description;

    @Schema(description = "Corpo de exemplo da resposta em JSON")
    private String body;

    public EndpointResponseDetail() {}

    public EndpointResponseDetail(int status, String description, String body) {
        this.status = status;
        this.description = description;
        this.body = body;
    }

    public int getStatus() { return status; }
    public String getDescription() { return description; }
    public String getBody() { return body; }
}
