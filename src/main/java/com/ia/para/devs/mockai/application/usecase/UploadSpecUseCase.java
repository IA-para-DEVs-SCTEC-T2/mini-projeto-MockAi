package com.ia.para.devs.mockai.application.usecase;

import com.ia.para.devs.mockai.application.service.SlugGeneratorService;
import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.model.OpenApiSpec;
import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import com.ia.para.devs.mockai.domain.port.OpenApiParser;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso: cria um mock a partir de um arquivo de especificação OpenAPI (JSON ou YAML)
 * enviado via upload multipart.
 * Gera automaticamente um slug único baseado no título da spec para compor a URL de acesso.
 */
@Component
public class UploadSpecUseCase {

    private final OpenApiParser openApiParser;
    private final MockDefinitionRepository repository;
    private final SlugGeneratorService slugGeneratorService;

    public UploadSpecUseCase(OpenApiParser openApiParser,
                             MockDefinitionRepository repository,
                             SlugGeneratorService slugGeneratorService) {
        this.openApiParser = openApiParser;
        this.repository = repository;
        this.slugGeneratorService = slugGeneratorService;
    }

    /**
     * Executa o caso de uso de criação de mock a partir do conteúdo de um arquivo.
     *
     * @param fileContent conteúdo do arquivo de especificação OpenAPI em JSON ou YAML
     * @param fileName    nome original do arquivo enviado (usado para validação de extensão)
     * @return MockDefinition persistido com id e slug gerados
     * @throws IllegalArgumentException se o arquivo não for JSON ou YAML, ou se o conteúdo for inválido
     */
    public MockDefinition execute(String fileContent, String fileName) {
        validateFileExtension(fileName);

        OpenApiSpec spec = openApiParser.parse(fileContent);
        String slug = slugGeneratorService.generateUniqueSlug(spec.getTitle());

        MockDefinition mockDefinition = new MockDefinition(
                UUID.randomUUID(),
                spec.getTitle(),
                "Mock gerado via upload do arquivo: " + fileName + " (spec: " + spec.getTitle() + " v" + spec.getVersion() + ")",
                slug,
                spec.getEndpoints()
        );

        return repository.save(mockDefinition);
    }

    private void validateFileExtension(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Nome do arquivo não pode ser vazio");
        }
        String lower = fileName.toLowerCase();
        if (!lower.endsWith(".json") && !lower.endsWith(".yaml") && !lower.endsWith(".yml")) {
            throw new IllegalArgumentException(
                    "Formato de arquivo não suportado: '" + fileName + "'. Apenas arquivos .json, .yaml e .yml são aceitos."
            );
        }
    }
}
