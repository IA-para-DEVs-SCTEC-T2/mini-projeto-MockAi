package com.ia.para.devs.mockai.application.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.adapter.in.web.dynamic.DynamicResponseBodyBuilder;
import com.ia.para.devs.mockai.domain.exception.AiCommunicationException;
import com.ia.para.devs.mockai.domain.port.in.GenerateEndpointResponseUseCase;
import com.ia.para.devs.mockai.domain.port.out.AiPort;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointResponseEntity;
import com.ia.para.devs.mockai.infrastructure.persistence.entity.TagEntity;

/**
 * Serviço responsável por gerar o corpo de resposta de endpoints mockados
 * utilizando IA.
 *
 * <p>
 * Implementa o caso de uso {@link GenerateEndpointResponseUseCase},
 * orquestrando as seguintes etapas:</p>
 * <ol>
 * <li>Seleção da resposta de sucesso do endpoint (prioridade: 200 → 201 → 204 →
 * primeira disponível)</li>
 * <li>Resolução do schema de resposta via
 * {@link DynamicResponseBodyBuilder}</li>
 * <li>Serialização do schema resolvido para JSON</li>
 * <li>Construção do prompt contextual em português</li>
 * <li>Envio do prompt ao serviço de IA via {@link AiPort}</li>
 * </ol>
 *
 * <p>
 * Retorna {@code null} quando o endpoint não possui schema de resposta
 * definido, indicando que o handler deve retornar apenas o status HTTP sem
 * corpo.</p>
 *
 * <p>
 * Lança {@link AiCommunicationException} em caso de falha na serialização do
 * schema ou na comunicação com o serviço de IA.</p>
 */
@Service
public class GenerateEndpointResponseService implements GenerateEndpointResponseUseCase {

    private final AiPort aiPort;
    private final DynamicResponseBodyBuilder responseBodyBuilder;
    private final ObjectMapper objectMapper;

    /**
     * Cria uma nova instância do serviço com as dependências necessárias.
     *
     * @param aiPort port de saída para comunicação com o serviço de IA
     * @param responseBodyBuilder construtor de corpo de resposta a partir de
     * schemas OpenAPI
     * @param objectMapper serializador JSON para converter o schema resolvido
     */
    public GenerateEndpointResponseService(
            AiPort aiPort,
            DynamicResponseBodyBuilder responseBodyBuilder,
            ObjectMapper objectMapper) {
        this.aiPort = aiPort;
        this.responseBodyBuilder = responseBodyBuilder;
        this.objectMapper = objectMapper;
    }

    /**
     * Gera o corpo de resposta de um endpoint mockado utilizando IA.
     *
     * <p>
     * Seleciona a resposta de sucesso do endpoint, resolve o schema de
     * resposta, constrói um prompt contextual e o envia ao serviço de IA,
     * retornando o JSON gerado como string.</p>
     *
     * <p>
     * Retorna {@code null} quando:</p>
     * <ul>
     * <li>O endpoint não possui respostas cadastradas</li>
     * <li>O schema de resposta selecionado é nulo ou vazio</li>
     * <li>O schema resolvido pelo builder é nulo</li>
     * <li>A resposta da IA é nula ou vazia</li>
     * </ul>
     *
     * @param endpoint entidade do endpoint cujo corpo de resposta será gerado
     * @return JSON gerado pela IA como {@code String}, ou {@code null} se não
     * houver schema de resposta definido ou se a resposta da IA for vazia
     * @throws AiCommunicationException se ocorrer falha na serialização do
     * schema ou na comunicação com o serviço de IA
     */
    @Override
    public String generate(EndpointDefinitionEntity endpoint) {
        EndpointResponseEntity selectedResponse = selectSuccessResponse(endpoint);
        if (selectedResponse == null) {
            return null;
        }

        String responseSchema = selectedResponse.getResponseSchema();
        if (responseSchema == null || responseSchema.isBlank()) {
            return null;
        }

        String componentsJson = endpoint.getApiSpecification() != null
                ? endpoint.getApiSpecification().getComponentsJson()
                : null;

        Object resolvedSchema = responseBodyBuilder.buildResponseBody(responseSchema, componentsJson);
        if (resolvedSchema == null) {
            return null;
        }

        String schemaJson;
        try {
            schemaJson = objectMapper.writeValueAsString(resolvedSchema);
        } catch (Exception ex) {
            throw new AiCommunicationException("Erro ao serializar o schema para envio à IA", ex);
        }

        String prompt = buildPrompt(endpoint, schemaJson);

        try {
            String aiResponse = aiPort.sendPrompt(prompt);
            return (aiResponse == null || aiResponse.isBlank()) ? null : aiResponse;
        } catch (Exception ex) {
            throw new AiCommunicationException(
                    "Erro ao gerar resposta via IA para o endpoint: " + endpoint.getPath(), ex);
        }
    }

    /**
     * Seleciona a resposta de sucesso do endpoint com base na prioridade de
     * status HTTP.
     *
     * <p>
     * A prioridade de seleção é: 200 → 201 → 204 → primeira resposta
     * disponível. Retorna {@code null} se o endpoint não possuir respostas
     * cadastradas.</p>
     *
     * @param endpoint entidade do endpoint cujas respostas serão avaliadas
     * @return a {@link EndpointResponseEntity} selecionada, ou {@code null} se
     * não houver respostas
     */
    private EndpointResponseEntity selectSuccessResponse(EndpointDefinitionEntity endpoint) {
        if (endpoint.getResponses() == null || endpoint.getResponses().isEmpty()) {
            return null;
        }
        return endpoint.getResponses().stream()
                .filter(r -> "200".equals(r.getStatusCode()))
                .findFirst()
                .or(() -> endpoint.getResponses().stream()
                .filter(r -> "201".equals(r.getStatusCode()))
                .findFirst())
                .or(() -> endpoint.getResponses().stream()
                .filter(r -> "204".equals(r.getStatusCode()))
                .findFirst())
                .orElseGet(() -> endpoint.getResponses().iterator().next());
    }

    /**
     * Constrói o prompt em português para envio ao serviço de IA.
     *
     * <p>
     * O prompt inclui o método HTTP, path, summary, description, tags (nome e
     * descrição) e o schema resolvido serializado como JSON, além de instruções
     * para que a IA retorne exclusivamente um JSON válido sem texto
     * adicional.</p>
     *
     * @param endpoint entidade do endpoint com as informações contextuais
     * @param schemaJson schema resolvido serializado como string JSON
     * @return prompt completo em português pronto para envio à IA
     */
    private String buildPrompt(EndpointDefinitionEntity endpoint, String schemaJson) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você é um gerador de dados de teste para APIs REST.\n\n");
        prompt.append("Gere uma resposta JSON realista e coerente para o seguinte endpoint:\n\n");
        prompt.append("Método HTTP: ").append(endpoint.getHttpMethod()).append("\n");
        prompt.append("Caminho: ").append(endpoint.getPath()).append("\n");

        if (endpoint.getSummary() != null && !endpoint.getSummary().isBlank()) {
            prompt.append("Resumo: ").append(endpoint.getSummary()).append("\n");
        }
        if (endpoint.getDescription() != null && !endpoint.getDescription().isBlank()) {
            prompt.append("Descrição: ").append(endpoint.getDescription()).append("\n");
        }

        if (endpoint.getTags() != null && !endpoint.getTags().isEmpty()) {
            prompt.append("\nTags do endpoint:\n");
            for (TagEntity tag : endpoint.getTags()) {
                prompt.append("- ").append(tag.getName());
                if (tag.getDescription() != null && !tag.getDescription().isBlank()) {
                    prompt.append(": ").append(tag.getDescription());
                }
                prompt.append("\n");
            }
        }

        prompt.append("\nEstrutura esperada da resposta (schema resolvido):\n");
        prompt.append(schemaJson).append("\n\n");
        prompt.append("Instruções:\n");
        prompt.append("- Retorne EXCLUSIVAMENTE um único JSON válido, sem texto adicional, sem markdown, sem explicações.\n");
        prompt.append("- Preencha os campos com valores realistas e coerentes com o contexto do endpoint.\n");
        prompt.append("- Respeite os tipos de dados definidos no schema.\n");
        prompt.append("- Não inclua campos extras além dos definidos no schema.\n");
        prompt.append("- PROIBIDO retornar mais de um JSON.\n");

        return prompt.toString();
    }
}
