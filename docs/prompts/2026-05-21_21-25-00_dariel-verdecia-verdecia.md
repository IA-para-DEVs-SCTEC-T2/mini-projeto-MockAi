Prompt: Criar testes unitários para EndpointDefinitionQueryAdapter validando consulta ao repositório
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:25:00

## Prompt original

Implementar testes unitários para a classe EndpointDefinitionQueryAdapter da camada infrastructure.persistence.adapter. Os testes devem validar a delegação correta ao EndpointDefinitionRepository: retorno de endpoints quando o repositório encontra registros para o specificationId fornecido, e retorno de lista vazia quando não há endpoints para a spec. Verificar que o método findAllByApiSpecificationId() do repositório é chamado com o UUID correto. Utilizar Mockito com @Mock para EndpointDefinitionRepository e @InjectMocks para o adapter.
