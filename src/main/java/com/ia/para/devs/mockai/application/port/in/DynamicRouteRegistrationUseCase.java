package com.ia.para.devs.mockai.application.port.in;

import java.util.UUID;

/**
 * Port de entrada responsável por registrar rotas dinâmicas em tempo de execução
 * com base nas definições de endpoint persistidas.
 */
public interface DynamicRouteRegistrationUseCase {

    /**
     * Registra as rotas dinâmicas de uma especificação pelo seu ID.
     *
     * @param specificationId identificador da especificação
     */
    void registerRoutes(UUID specificationId);

    /**
     * Remove rotas previamente registradas de uma especificação.
     *
     * @param specificationId identificador da especificação
     */
    void unregisterRoutes(UUID specificationId);
}
