package com.ia.para.devs.mockai.domain.port.in;

import com.ia.para.devs.mockai.domain.exception.AiCommunicationException;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Contrato do caso de uso de geração de resposta de endpoint mockado por IA.
 *
 * <p>Define a operação de geração do corpo de resposta HTTP para um endpoint
 * mockado, utilizando inteligência artificial para produzir um JSON realista
 * e coerente com o schema e o contexto descritivo do endpoint.</p>
 *
 * <p>Esta interface pertence à camada de domínio e não possui dependências
 * de frameworks externos.</p>
 */
public interface GenerateEndpointResponseUseCase {

    /**
     * Gera o corpo de resposta de um endpoint mockado utilizando IA.
     *
     * <p>O método seleciona a resposta de sucesso do endpoint, resolve o schema
     * de resposta, constrói um prompt contextual e o envia ao serviço de IA,
     * retornando o JSON gerado como string.</p>
     *
     * <p>Retorna {@code null} quando o endpoint não possui schema de resposta
     * definido, indicando que o handler deve retornar apenas o status HTTP
     * sem corpo na resposta.</p>
     *
     * @param endpoint entidade do endpoint cujo corpo de resposta será gerado
     * @return JSON gerado pela IA como {@code String}, ou {@code null} se não
     *         houver schema de resposta definido ou se a resposta da IA for vazia
     * @throws AiCommunicationException se ocorrer falha na comunicação com o
     *                                  serviço de IA ou na serialização do schema
     */
    String generate(EndpointDefinitionEntity endpoint);
}
