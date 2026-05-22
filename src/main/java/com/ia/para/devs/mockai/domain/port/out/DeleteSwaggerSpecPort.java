package com.ia.para.devs.mockai.domain.port.out;

/**
 * Port de saída que define o contrato para deleção de todos os dados de uma
 * especificação OpenAPI existente no banco de dados.
 *
 * Implementado pela camada de infraestrutura (adapter/out/persistence).
 * A deleção deve ocorrer dentro da mesma transação que a inserção dos novos dados,
 * garantindo rollback completo em caso de falha em qualquer etapa (RN03).
 */
public interface DeleteSwaggerSpecPort {

    /**
     * Remove todos os dados persistidos de uma especificação OpenAPI anterior:
     * endpoints, parâmetros, respostas, tags e a própria especificação raiz.
     *
     * A ordem de deleção respeita as dependências de FK (filho → pai):
     * endpoint_tags → endpoint_definition → api_specification → tag
     */
    void deleteAll();
}
