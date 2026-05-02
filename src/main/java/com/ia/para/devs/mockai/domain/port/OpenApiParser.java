package com.ia.para.devs.mockai.domain.port;

import com.ia.para.devs.mockai.domain.model.OpenApiSpec;

/**
 * Port de entrada: contrato de parsing de especificações OpenAPI.
 * Implementado pela camada de infrastructure (gateway).
 */
public interface OpenApiParser {

    /**
     * Realiza o parsing de uma especificação OpenAPI a partir de uma string JSON ou YAML.
     *
     * @param specContent conteúdo da especificação OpenAPI em formato JSON ou YAML
     * @return OpenApiSpec com os dados extraídos da especificação
     */
    OpenApiSpec parse(String specContent);
}
