package com.ia.para.devs.mockai.infrastructure.persistence.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.adapter.in.web.dto.MediaTypeDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.ParameterDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.PathItemDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.ResponseDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.TagDto;
import com.ia.para.devs.mockai.domain.exception.DatabaseConnectionException;
import com.ia.para.devs.mockai.domain.exception.PersistenceDeletionException;
import com.ia.para.devs.mockai.domain.exception.PersistenceFailureException;
import com.ia.para.devs.mockai.domain.exception.ReferentialIntegrityException;
import com.ia.para.devs.mockai.domain.port.out.DeleteSwaggerSpecPort;
import com.ia.para.devs.mockai.domain.port.out.PersistSwaggerSpecPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.ApiSpecificationEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointResponseEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.PathParameterEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.TagEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.ApiSpecificationRepository;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.TagRepository;

/**
 * Adapter de saída responsável por persistir uma especificação OpenAPI no banco de dados.
 * Implementa PersistSwaggerSpecPort seguindo os princípios de Clean Architecture:
 * a camada de aplicação depende apenas da interface (port), não desta implementação.
 *
 * Ordem de persistência (pai → filho):
 * 1. ApiSpecificationEntity
 * 2. TagEntity (independente, referenciada pelos endpoints)
 * 3. EndpointDefinitionEntity (depende de ApiSpecificationEntity)
 * 4. endpoint_tags (junção — depende de EndpointDefinitionEntity + TagEntity)
 * 5. PathParameterEntity (depende de EndpointDefinitionEntity)
 * 6. EndpointResponseEntity (depende de EndpointDefinitionEntity)
 */
@Component
public class SwaggerSpecPersistenceAdapter implements PersistSwaggerSpecPort {

    private static final String DEFAULT_CONTENT_TYPE = "*/*";
    private static final String DEFAULT_PARAM_TYPE = "string";

    private final ApiSpecificationRepository apiSpecificationRepository;
    private final TagRepository tagRepository;
    private final ObjectMapper objectMapper;
    private final DeleteSwaggerSpecPort deleteSwaggerSpecPort;

    public SwaggerSpecPersistenceAdapter(
            ApiSpecificationRepository apiSpecificationRepository,
            TagRepository tagRepository,
            ObjectMapper objectMapper,
            DeleteSwaggerSpecPort deleteSwaggerSpecPort) {
        this.apiSpecificationRepository = apiSpecificationRepository;
        this.tagRepository = tagRepository;
        this.objectMapper = objectMapper;
        this.deleteSwaggerSpecPort = deleteSwaggerSpecPort;
    }

    /**
     * Persiste a especificação OpenAPI substituindo todos os dados existentes (RN03).
     * A transação garante atomicidade: ou tudo é persistido, ou nada é.
     *
     * Fluxo transacional:
     *   1. Delega a deleção completa ao DeleteSwaggerSpecPort (dentro desta transação)
     *   2. Persiste os novos dados
     * Se qualquer etapa lançar exceção, o Spring reverte toda a transação automaticamente.
     *
     * Hierarquia de tratamento de erros (mais específico → mais genérico):
     *   PersistenceDeletionException    — relançada diretamente (vem do adapter de deleção)
     *   DataIntegrityViolationException — violação de FK/constraint → ReferentialIntegrityException
     *   DataAccessException             — falha de conexão/driver  → DatabaseConnectionException
     *   Exception genérica              — falha inesperada         → PersistenceFailureException
     */
    @Override
    @Transactional
    public UUID persist(OpenApiSpecDto spec) {
        try {
            // 1. Deleta todos os dados existentes via port dedicado (RN03)
            //    PersistenceDeletionException propagada diretamente se lançada aqui
            deleteSwaggerSpecPort.deleteAll();

            // 2. Persiste ApiSpecificationEntity (entidade raiz)
            ApiSpecificationEntity apiSpec = buildApiSpecification(spec);

            // 3. Persiste TagEntity e monta índice por nome para lookup rápido
            Map<String, TagEntity> tagsByName = persistTags(spec);

            // 4. Constrói e associa EndpointDefinitionEntity com seus filhos
            List<EndpointDefinitionEntity> endpoints = buildEndpoints(spec, apiSpec, tagsByName);
            apiSpec.setEndpoints(endpoints);

            // 5. Persiste a especificação com todos os endpoints em cascata (CascadeType.ALL)
            ApiSpecificationEntity savedApiSpec = apiSpecificationRepository.save(apiSpec);
            return savedApiSpec.getId();

        } catch (PersistenceDeletionException ex) {
            // Relança diretamente — já tem mensagem descritiva do adapter de deleção
            throw ex;

        } catch (DataIntegrityViolationException ex) {
            // Subclasse de DataAccessException — deve vir antes para ser capturada corretamente
            throw new ReferentialIntegrityException(
                    "Violação de integridade referencial: " + ex.getMostSpecificCause().getMessage(), ex);

        } catch (DataAccessException ex) {
            // Cobre falhas de conexão, timeout, driver indisponível, etc.
            throw new DatabaseConnectionException("Falha de conexão com o banco de dados", ex);

        } catch (Exception ex) {
            throw new PersistenceFailureException(
                    "Falha ao persistir os dados: " + ex.getMessage(), ex);
        }
    }

    // -------------------------------------------------------------------------
    // Métodos privados de construção
    // -------------------------------------------------------------------------

    private ApiSpecificationEntity buildApiSpecification(OpenApiSpecDto spec) {
        ApiSpecificationEntity entity = new ApiSpecificationEntity();
        entity.setTitle(spec.getInfo() != null ? spec.getInfo().getTitle() : "");
        entity.setVersion(spec.getInfo() != null ? spec.getInfo().getVersion() : "");
        entity.setDescription(spec.getInfo() != null ? spec.getInfo().getDescription() : null);
        entity.setBaseUrl(extractBaseUrl(spec));
        entity.setComponentsJson(serializeComponents(spec));
        return entity;
    }

    /**
     * Serializa o bloco "components" da spec como JSON string para persistência.
     * Retorna null se não houver components.
     */
    private String serializeComponents(OpenApiSpecDto spec) {
        if (spec.getComponents() == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(spec.getComponents());
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private String extractBaseUrl(OpenApiSpecDto spec) {
        if (spec.getServers() == null || spec.getServers().isEmpty()) {
            return "";
        }
        String url = spec.getServers().get(0).getUrl();
        return url != null ? url : "";
    }

    /**
     * Persiste as tags globais da spec e retorna um mapa nome → TagEntity
     * para uso no vínculo com os endpoints.
     */
    private Map<String, TagEntity> persistTags(OpenApiSpecDto spec) {
        if (spec.getTags() == null || spec.getTags().isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, TagEntity> tagsByName = new HashMap<>();
        for (TagDto tagDto : spec.getTags()) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setName(tagDto.getName());
            tagEntity.setDescription(tagDto.getDescription());
            TagEntity saved = tagRepository.save(tagEntity);
            tagsByName.put(saved.getName(), saved);
        }
        return tagsByName;
    }

    /**
     * Constrói a lista de EndpointDefinitionEntity a partir do mapa de paths da spec.
     * Cada combinação path + httpMethod gera um endpoint.
     */
    private List<EndpointDefinitionEntity> buildEndpoints(
            OpenApiSpecDto spec,
            ApiSpecificationEntity apiSpec,
            Map<String, TagEntity> tagsByName) {

        if (spec.getPaths() == null || spec.getPaths().isEmpty()) {
            return Collections.emptyList();
        }

        List<EndpointDefinitionEntity> endpoints = new ArrayList<>();

        for (Map.Entry<String, Map<String, PathItemDto>> pathEntry : spec.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            Map<String, PathItemDto> methodMap = pathEntry.getValue();

            if (methodMap == null) {
                continue;
            }

            for (Map.Entry<String, PathItemDto> methodEntry : methodMap.entrySet()) {
                String httpMethod = methodEntry.getKey().toUpperCase();
                PathItemDto pathItem = methodEntry.getValue();

                EndpointDefinitionEntity endpoint = buildEndpoint(
                        path, httpMethod, pathItem, apiSpec, tagsByName);
                endpoints.add(endpoint);
            }
        }

        return endpoints;
    }

    private EndpointDefinitionEntity buildEndpoint(
            String path,
            String httpMethod,
            PathItemDto pathItem,
            ApiSpecificationEntity apiSpec,
            Map<String, TagEntity> tagsByName) {

        EndpointDefinitionEntity endpoint = new EndpointDefinitionEntity();
        endpoint.setPath(path);
        endpoint.setHttpMethod(httpMethod);
        endpoint.setSummary(pathItem.getSummary());
        endpoint.setDescription(pathItem.getDescription());
        endpoint.setApiSpecification(apiSpec);

        // Associa as tags ao endpoint via tabela de junção endpoint_tags
        endpoint.setTags(resolveEndpointTags(pathItem, tagsByName));

        // Constrói os parâmetros de path (apenas in=path)
        endpoint.setPathParameters(buildPathParameters(pathItem, endpoint));

        // Constrói as respostas do endpoint
        endpoint.setResponses(buildResponses(pathItem, endpoint));

        return endpoint;
    }

    /**
     * Resolve as tags de um endpoint buscando as TagEntity já persistidas pelo nome.
     * Tags referenciadas no endpoint mas não declaradas globalmente são ignoradas.
     */
    private Set<TagEntity> resolveEndpointTags(
            PathItemDto pathItem,
            Map<String, TagEntity> tagsByName) {

        if (pathItem.getTags() == null || pathItem.getTags().isEmpty()) {
            return Collections.emptySet();
        }

        Set<TagEntity> tags = new LinkedHashSet<>();
        for (String tagName : pathItem.getTags()) {
            TagEntity tagEntity = tagsByName.get(tagName);
            if (tagEntity != null) {
                tags.add(tagEntity);
            }
        }
        return tags;
    }

    /**
     * Constrói PathParameterEntity apenas para parâmetros com "in": "path".
     */
    private Set<PathParameterEntity> buildPathParameters(
            PathItemDto pathItem,
            EndpointDefinitionEntity endpoint) {

        if (pathItem.getParameters() == null || pathItem.getParameters().isEmpty()) {
            return Collections.emptySet();
        }

        Set<PathParameterEntity> parameters = new LinkedHashSet<>();
        for (ParameterDto paramDto : pathItem.getParameters()) {
            if (!"path".equalsIgnoreCase(paramDto.getIn())) {
                continue;
            }

            PathParameterEntity param = new PathParameterEntity();
            param.setName(paramDto.getName());
            param.setRequired(Boolean.TRUE.equals(paramDto.getRequired()));
            param.setType(extractParamType(paramDto));
            param.setEndpointDefinition(endpoint);
            parameters.add(param);
        }
        return parameters;
    }

    private String extractParamType(ParameterDto paramDto) {
        if (paramDto.getSchema() != null && paramDto.getSchema().getType() != null) {
            return paramDto.getSchema().getType();
        }
        return DEFAULT_PARAM_TYPE;
    }

    /**
     * Constrói EndpointResponseEntity para cada combinação statusCode + contentType.
     * Respostas sem bloco "content" (ex: 204 No Content) geram um registro com
     * contentType DEFAULT_CONTENT_TYPE.
     */
    private Set<EndpointResponseEntity> buildResponses(
            PathItemDto pathItem,
            EndpointDefinitionEntity endpoint) {

        if (pathItem.getResponses() == null || pathItem.getResponses().isEmpty()) {
            return Collections.emptySet();
        }

        Set<EndpointResponseEntity> responses = new LinkedHashSet<>();

        for (Map.Entry<String, ResponseDto> responseEntry : pathItem.getResponses().entrySet()) {
            String statusCode = responseEntry.getKey();
            ResponseDto responseDto = responseEntry.getValue();

            if (responseDto.getContent() == null || responseDto.getContent().isEmpty()) {
                // Resposta sem body (ex: 204 No Content)
                EndpointResponseEntity response = buildResponse(
                        statusCode, DEFAULT_CONTENT_TYPE, responseDto.getDescription(), null, endpoint);
                responses.add(response);
            } else {
                // Uma entrada por content type
                for (Map.Entry<String, MediaTypeDto> contentEntry : responseDto.getContent().entrySet()) {
                    String contentType = contentEntry.getKey();
                    MediaTypeDto mediaType = contentEntry.getValue();
                    String schemaJson = serializeSchema(mediaType);

                    EndpointResponseEntity response = buildResponse(
                            statusCode, contentType, responseDto.getDescription(), schemaJson, endpoint);
                    responses.add(response);
                }
            }
        }

        return responses;
    }

    private EndpointResponseEntity buildResponse(
            String statusCode,
            String contentType,
            String description,
            String responseSchema,
            EndpointDefinitionEntity endpoint) {

        EndpointResponseEntity response = new EndpointResponseEntity();
        response.setStatusCode(statusCode);
        response.setContentType(contentType);
        response.setDescription(description);
        response.setResponseSchema(responseSchema);
        response.setEndpointDefinition(endpoint);
        return response;
    }

    /**
     * Serializa o schema de um MediaTypeDto como string JSON.
     * Prioridade: schema > example > primeiro valor de examples.
     * Usa o ObjectMapper 2.x para serializar estruturas Map/List/primitivo.
     */
    private String serializeSchema(MediaTypeDto mediaType) {
        if (mediaType == null) {
            return null;
        }
        try {
            if (mediaType.getSchema() != null) {
                return objectMapper.writeValueAsString(mediaType.getSchema());
            }
            if (mediaType.getExample() != null) {
                return objectMapper.writeValueAsString(mediaType.getExample());
            }
            if (mediaType.getExamples() != null && !mediaType.getExamples().isEmpty()) {
                Object first = mediaType.getExamples().values().iterator().next();
                if (first instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> firstMap = (Map<String, Object>) first;
                    Object value = firstMap.getOrDefault("value", first);
                    return objectMapper.writeValueAsString(value);
                }
                return objectMapper.writeValueAsString(first);
            }
            return null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
