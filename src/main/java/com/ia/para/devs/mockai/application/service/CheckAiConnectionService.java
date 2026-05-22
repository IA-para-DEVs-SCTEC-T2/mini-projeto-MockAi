package com.ia.para.devs.mockai.application.service;

import com.ia.para.devs.mockai.domain.port.in.CheckAiConnectionUseCase;
import com.ia.para.devs.mockai.domain.port.out.AiPort;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por verificar a conectividade com o serviço de IA externo.
 * Implementa o caso de uso {@link CheckAiConnectionUseCase}, absorvendo qualquer
 * exceção e retornando {@code false} em caso de falha, sem propagar erros ao chamador.
 */
@Service
public class CheckAiConnectionService implements CheckAiConnectionUseCase {

    private static final String TEST_PROMPT = "ping";

    private final AiPort aiPort;

    public CheckAiConnectionService(AiPort aiPort) {
        this.aiPort = aiPort;
    }

    /**
     * Verifica se a integração com o serviço de IA está funcional enviando um
     * prompt de teste e validando se a resposta é não nula e não vazia.
     *
     * @return {@code true} se a conexão estiver operacional e a resposta for válida,
     *         {@code false} em caso de falha ou exceção
     */
    @Override
    public boolean checkConnection() {
        try {
            String response = aiPort.sendPrompt(TEST_PROMPT);
            return response != null && !response.isBlank();
        } catch (Exception ex) {
            return false;
        }
    }
}
