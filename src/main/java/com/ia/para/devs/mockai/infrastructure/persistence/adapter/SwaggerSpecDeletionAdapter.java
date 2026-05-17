package com.ia.para.devs.mockai.infrastructure.persistence.adapter;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.ia.para.devs.mockai.application.port.out.DeleteSwaggerSpecPort;
import com.ia.para.devs.mockai.domain.exception.PersistenceDeletionException;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.ApiSpecificationRepository;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.TagRepository;

import jakarta.persistence.EntityManager;

/**
 * Adapter de saída responsável por deletar todos os dados de uma especificação
 * OpenAPI existente no banco de dados.
 *
 * Ordem de deleção (filho → pai), respeitando as constraints de FK:
 *
 *   1. endpoint_tags (tabela de junção) — removida via CascadeType.ALL ao deletar
 *      EndpointDefinitionEntity, que por sua vez é removida via CascadeType.ALL
 *      ao deletar ApiSpecificationEntity.
 *   2. api_specification (com cascade para endpoint_definition, path_parameter
 *      e endpoint_response) — deletada via deleteAllInBatch().
 *   3. tag — deletada após a remoção dos vínculos endpoint_tags, evitando
 *      violação de FK na tabela de junção.
 *   4. flush() explícito após cada etapa — força o Hibernate a emitir os DELETEs
 *      imediatamente, garantindo que as constraints de FK sejam respeitadas
 *      dentro da mesma transação antes dos INSERTs subsequentes.
 *
 * Não possui @Transactional próprio: participa da transação aberta pelo chamador
 * (SwaggerSpecPersistenceAdapter), garantindo que deleção e inserção sejam
 * atômicas — rollback completo se qualquer etapa falhar.
 */
@Component
public class SwaggerSpecDeletionAdapter implements DeleteSwaggerSpecPort {

    private final ApiSpecificationRepository apiSpecificationRepository;
    private final TagRepository tagRepository;
    private final EntityManager entityManager;

    public SwaggerSpecDeletionAdapter(
            ApiSpecificationRepository apiSpecificationRepository,
            TagRepository tagRepository,
            EntityManager entityManager) {
        this.apiSpecificationRepository = apiSpecificationRepository;
        this.tagRepository = tagRepository;
        this.entityManager = entityManager;
    }

    /**
     * Remove todos os dados existentes na ordem correta de dependência de FK.
     *
     * Participa da transação do chamador — não abre transação própria.
     * O rollback é automático via @Transactional do SwaggerSpecPersistenceAdapter.
     *
     * @throws PersistenceDeletionException se qualquer etapa da deleção falhar,
     *         com mensagem descritiva do detalhe do erro
     */
    @Override
    public void deleteAll() {
        try {
            // Passo 1: deleta ApiSpecificationEntity em cascata.
            // O CascadeType.ALL em ApiSpecificationEntity → EndpointDefinitionEntity garante
            // que endpoint_definition, endpoint_tags, path_parameter e endpoint_response
            // sejam removidos automaticamente pelo JPA.
            // IMPORTANT: deleteAllInBatch() ignora cascatas e tenta deletar a tabela pai diretamente,
            // o que causa violação de FK. Por isso usamos deleteAll() para que o JPA execute a
            // remoção em cascata corretamente.
            apiSpecificationRepository.deleteAll();

            // Passo 2: flush explícito — força o Hibernate a emitir os DELETEs para o banco
            // antes de prosseguir. Sem isso, o Hibernate pode acumular operações e emiti-las
            // fora de ordem, causando violação de FK ao tentar deletar tag antes de endpoint_tags.
            entityManager.flush();

            // Passo 3: deleta as tags globais, que são independentes de ApiSpecification.
            // Seguro após o flush, pois os vínculos em endpoint_tags já foram removidos.
            tagRepository.deleteAllInBatch();

            // Passo 4: flush final — garante que todos os DELETEs estejam no banco
            // antes dos INSERTs que virão na sequência dentro da mesma transação.
            entityManager.flush();

        } catch (DataAccessException ex) {
            throw new PersistenceDeletionException(
                    "Falha ao deletar dados anteriores: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }
}
