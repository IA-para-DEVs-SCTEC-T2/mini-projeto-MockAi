package com.ia.para.devs.mockai.application.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;
import com.ia.para.devs.mockai.application.port.in.DynamicRouteRegistrationUseCase;
import com.ia.para.devs.mockai.application.port.in.ImportSwaggerUseCase;
import com.ia.para.devs.mockai.application.port.in.PersistSwaggerSpecUseCase;
import com.ia.para.devs.mockai.domain.exception.InvalidSwaggerContentException;
import com.ia.para.devs.mockai.domain.model.FileData;

/**
 * Serviço de aplicação responsável por orquestrar a importação de uma
 * especificação OpenAPI: desserializa o conteúdo do arquivo e delega
 * a persistência ao PersistSwaggerSpecUseCase.
 *
 * Segue o SRP: responsável exclusivamente pela orquestração do fluxo de importação.
 * Segue o DIP: depende apenas de interfaces (ports), não de implementações concretas.
 *
 * A desserialização Jackson ocorre aqui (camada application) e não no controller
 * (camada adapter), mantendo o controller livre de lógica de negócio.
 */
@Service
public class ImportSwaggerService implements ImportSwaggerUseCase {

    private final ObjectMapper objectMapper;
    private final PersistSwaggerSpecUseCase persistSwaggerSpecUseCase;
    private final DynamicRouteRegistrationUseCase dynamicRouteRegistrationUseCase;

    public ImportSwaggerService(
            ObjectMapper objectMapper,
            PersistSwaggerSpecUseCase persistSwaggerSpecUseCase,
            DynamicRouteRegistrationUseCase dynamicRouteRegistrationUseCase) {
        this.objectMapper = objectMapper;
        this.persistSwaggerSpecUseCase = persistSwaggerSpecUseCase;
        this.dynamicRouteRegistrationUseCase = dynamicRouteRegistrationUseCase;
    }

    /**
     * Desserializa o conteúdo do arquivo como OpenApiSpecDto e persiste no banco.
     *
     * @param file arquivo validado contendo a especificação OpenAPI em JSON
     * @throws InvalidSwaggerContentException se o JSON não puder ser desserializado
     *         como OpenApiSpecDto (estrutura inválida, JSON malformado, etc.)
     */
    @Override
    public void importSpec(FileData file) {
        OpenApiSpecDto spec = deserialize(file);
        UUID specificationId = persistSwaggerSpecUseCase.persist(spec);
        dynamicRouteRegistrationUseCase.registerRoutes(specificationId);
    }

    /**
     * Desserializa os bytes do arquivo para OpenApiSpecDto via Jackson.
     * Campos desconhecidos são ignorados (@JsonIgnoreProperties na classe DTO).
     *
     * @throws InvalidSwaggerContentException se o conteúdo não for JSON válido
     *         ou não puder ser mapeado para OpenApiSpecDto
     */
    private OpenApiSpecDto deserialize(FileData file) {
        try {
            return objectMapper.readValue(file.getContent(), OpenApiSpecDto.class);
        } catch (IOException ex) {
            throw new InvalidSwaggerContentException(
                    "Conteúdo do arquivo inválido: não é um JSON OpenAPI válido", ex);
        }
    }
}
