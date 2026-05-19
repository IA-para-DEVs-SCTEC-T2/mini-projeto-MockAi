package com.ia.para.devs.mockai.domain.port.in;

import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;

/**
 * Contrato para validação do conteúdo de uma especificação OpenAPI desserializada.
 * Garante que os campos mínimos necessários para geração de endpoints dinâmicos
 * estejam presentes e não vazios antes de qualquer persistência.
 */
public interface ValidateSwaggerContentUseCase {

    /**
     * Valida os campos obrigatórios da especificação OpenAPI.
     *
     * @param spec especificação desserializada a ser validada
     * @throws com.ia.para.devs.mockai.domain.exception.InvalidSwaggerContentException
     *         se algum campo obrigatório estiver ausente ou vazio
     */
    void validate(OpenApiSpecDto spec);
}
