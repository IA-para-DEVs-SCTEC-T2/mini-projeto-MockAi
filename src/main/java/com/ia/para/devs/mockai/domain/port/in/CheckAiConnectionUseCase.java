package com.ia.para.devs.mockai.domain.port.in;

/**
 * Contrato do caso de uso de verificação de conectividade com o serviço de IA.
 * Implementações desta interface devem verificar se a integração com a IA está
 * operacional, sem propagar exceções ao chamador.
 */
public interface CheckAiConnectionUseCase {

    /**
     * Verifica se a integração com o serviço de IA está funcional.
     *
     * @return true se a conexão estiver operacional, false caso contrário
     */
    boolean checkConnection();
}
