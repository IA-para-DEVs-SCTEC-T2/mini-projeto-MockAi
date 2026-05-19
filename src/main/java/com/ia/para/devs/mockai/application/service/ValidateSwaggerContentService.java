package com.ia.para.devs.mockai.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.PathItemDto;
import com.ia.para.devs.mockai.domain.exception.InvalidSwaggerContentException;
import com.ia.para.devs.mockai.domain.port.in.ValidateSwaggerContentUseCase;

/**
 * Serviço de aplicação responsável por validar os campos mínimos obrigatórios
 * de uma especificação OpenAPI para que seja possível gerar endpoints dinâmicos.
 *
 * Campos validados:
 * <ul>
 *   <li>{@code openapi} — versão da especificação (obrigatório)</li>
 *   <li>{@code info.title} — título da API (obrigatório)</li>
 *   <li>{@code info.description} — descrição da API (obrigatório)</li>
 *   <li>{@code paths} — mapa de endpoints (obrigatório e não vazio)</li>
 *   <li>Cada path deve ter ao menos um método com ao menos uma resposta com status code</li>
 * </ul>
 *
 * Todos os erros encontrados são acumulados e retornados em uma única exceção,
 * evitando múltiplas chamadas para descobrir todos os campos ausentes.
 */
@Service
public class ValidateSwaggerContentService implements ValidateSwaggerContentUseCase {

    @Override
    public void validate(OpenApiSpecDto spec) {
        List<String> missingFields = new ArrayList<>();

        validateOpenApiVersion(spec, missingFields);
        validateInfo(spec, missingFields);
        validatePaths(spec, missingFields);

        if (!missingFields.isEmpty()) {
            throw new InvalidSwaggerContentException(
                    "Especificação OpenAPI inválida. Campos ausentes ou inválidos: " + missingFields);
        }
    }

    /**
     * Valida a presença do campo {@code openapi} com versão da especificação.
     */
    private void validateOpenApiVersion(OpenApiSpecDto spec, List<String> missingFields) {
        if (isBlank(spec.getOpenapi())) {
            missingFields.add("openapi");
        }
    }

    /**
     * Valida a presença e preenchimento dos campos obrigatórios do bloco {@code info}.
     */
    private void validateInfo(OpenApiSpecDto spec, List<String> missingFields) {
        if (spec.getInfo() == null) {
            missingFields.add("info");
            missingFields.add("info.title");
            missingFields.add("info.description");
            return;
        }

        if (isBlank(spec.getInfo().getTitle())) {
            missingFields.add("info.title");
        }

        if (isBlank(spec.getInfo().getDescription())) {
            missingFields.add("info.description");
        }
    }

    /**
     * Valida a presença e preenchimento do bloco {@code paths}.
     * Cada path deve ter ao menos um método HTTP com ao menos uma resposta definida.
     */
    private void validatePaths(OpenApiSpecDto spec, List<String> missingFields) {
        if (spec.getPaths() == null || spec.getPaths().isEmpty()) {
            missingFields.add("paths");
            return;
        }

        for (Map.Entry<String, Map<String, PathItemDto>> pathEntry : spec.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            Map<String, PathItemDto> methods = pathEntry.getValue();

            if (methods == null || methods.isEmpty()) {
                missingFields.add("paths[" + path + "] (nenhum método HTTP definido)");
                continue;
            }

            for (Map.Entry<String, PathItemDto> methodEntry : methods.entrySet()) {
                String method = methodEntry.getKey();
                PathItemDto pathItem = methodEntry.getValue();

                if (pathItem == null || pathItem.getResponses() == null || pathItem.getResponses().isEmpty()) {
                    missingFields.add("paths[" + path + "]." + method + ".responses");
                }
            }
        }
    }

    /**
     * Verifica se uma string é nula ou contém apenas espaços em branco.
     */
    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
