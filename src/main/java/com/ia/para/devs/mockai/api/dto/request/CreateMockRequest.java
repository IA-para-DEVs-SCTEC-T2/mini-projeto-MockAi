package com.ia.para.devs.mockai.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para criação de um mock a partir de uma especificação OpenAPI.
 */
@Schema(description = "Requisição para criação de um mock a partir de uma especificação OpenAPI")
public class CreateMockRequest {

    @NotBlank(message = "O conteúdo da especificação OpenAPI é obrigatório")
    @Schema(description = "Conteúdo da especificação OpenAPI em formato JSON ou YAML", required = true)
    private String specContent;

    public CreateMockRequest() {}

    public CreateMockRequest(String specContent) {
        this.specContent = specContent;
    }

    public String getSpecContent() { return specContent; }
    public void setSpecContent(String specContent) { this.specContent = specContent; }
}
