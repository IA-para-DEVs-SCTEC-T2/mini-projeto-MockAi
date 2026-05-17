package com.ia.para.devs.mockai.domain.port.in;

import com.ia.para.devs.mockai.domain.model.FileData;

/**
 * Port de entrada que define o contrato do caso de uso de importação completa
 * de uma especificação OpenAPI: desserialização do conteúdo e persistência no banco.
 *
 * Orquestra a sequência: deserializar FileData → persistir OpenApiSpecDto.
 * Separado de ValidateFileUseCase para respeitar o SRP — cada use case tem
 * uma única responsabilidade.
 */
public interface ImportSwaggerUseCase {

    /**
     * Desserializa o conteúdo do arquivo e persiste a especificação OpenAPI.
     *
     * @param file arquivo validado contendo a especificação OpenAPI em JSON
     * @throws com.ia.para.devs.mockai.domain.exception.InvalidSwaggerContentException
     *         se o conteúdo não puder ser desserializado como OpenAPI válido
     */
    void importSpec(FileData file);
}
