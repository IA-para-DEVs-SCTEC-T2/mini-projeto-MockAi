package com.ia.para.devs.mockai.domain.port.out;

/**
 * Port de saída que define o contrato de comunicação com serviços de IA externos.
 * Livre de dependências de frameworks — Java puro.
 */
public interface AiPort {

    /**
     * Envia um prompt para o serviço de IA e retorna a resposta gerada.
     *
     * @param prompt texto de entrada não nulo e não vazio
     * @return resposta gerada pelo modelo, nunca nula
     * @throws IllegalArgumentException se o prompt for nulo, vazio ou whitespace
     * @throws com.ia.para.devs.mockai.domain.exception.AiCommunicationException
     *         se ocorrer falha na comunicação com a IA
     */
    String sendPrompt(String prompt);
}
