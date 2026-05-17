package com.ia.para.devs.mockai.domain.port.out;

import java.util.List;
import java.util.UUID;

import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;

/**
 * Port de saída que define o contrato técnico para registrar, remover e
 * resolver rotas dinâmicas no framework web utilizado.
 */
public interface DynamicRouteRegistryPort {

    /**
     * Registra rotas dinâmicas no runtime, associando cada endpoint à implementação
     * do handler genérico.
     *
     * @param specificationId identificador da especificação
     * @param endpoints lista de endpoints persistidos
     */
    void registerRoutes(UUID specificationId, List<EndpointDefinitionEntity> endpoints);

    /**
     * Remove qualquer rota dinâmica previamente registrada para a especificação.
     *
     * @param specificationId identificador da especificação
     */
    void unregisterRoutes(UUID specificationId);

    /**
     * Remove todas as rotas dinâmicas registradas, independente da especificação.
     * Usado antes de uma nova importação para garantir limpeza completa.
     */
    void unregisterAll();

    /**
     * Resolve uma definição de endpoint a partir do padrão de rota e método HTTP.
     *
     * @param pathPattern padrão da rota (por exemplo, /pet/{petId})
     * @param httpMethod método HTTP (GET, POST, etc.)
     * @return definição do endpoint ou null se não existir
     */
    EndpointDefinitionEntity getEndpointDefinition(String pathPattern, String httpMethod);
}
