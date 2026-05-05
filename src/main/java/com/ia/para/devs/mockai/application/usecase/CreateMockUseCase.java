package com.ia.para.devs.mockai.application.usecase;

import com.ia.para.devs.mockai.application.service.SlugGeneratorService;
import com.ia.para.devs.mockai.domain.model.MockDefinition;
import com.ia.para.devs.mockai.domain.model.OpenApiSpec;
import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import com.ia.para.devs.mockai.domain.port.OpenApiParser;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Caso de uso: cria um mock a partir de uma especificação OpenAPI.
 * Orquestra o parsing da spec, geração do slug e persistência do MockDefinition.
 */
@Component
public class CreateMockUseCase {

    private final OpenApiParser openApiParser;
    private final MockDefinitionRepository repository;
    private final SlugGeneratorService slugGeneratorService;

    public CreateMockUseCase(OpenApiParser openApiParser,
                             MockDefinitionRepository repository,
                             SlugGeneratorService slugGeneratorService) {
        this.openApiParser = openApiParser;
        this.repository = repository;
        this.slugGeneratorService = slugGeneratorService;
    }

    /**
     * Executa o caso de uso de criação de mock.
     *
     * @param specContent conteúdo da especificação OpenAPI em JSON ou YAML
     * @return MockDefinition persistido com id e slug gerados
     */
    public MockDefinition execute(String specContent) {
        OpenApiSpec spec = openApiParser.parse(specContent);
        String slug = slugGeneratorService.generateUniqueSlug(spec.getTitle());

        MockDefinition mockDefinition = new MockDefinition(
                UUID.randomUUID(),
                spec.getTitle(),
                "Mock gerado a partir da spec: " + spec.getTitle() + " v" + spec.getVersion(),
                slug,
                spec.getEndpoints()
        );

        return repository.save(mockDefinition);
    }
}
