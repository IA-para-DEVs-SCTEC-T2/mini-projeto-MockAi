Prompt: Criar testes unitários para PersistSwaggerSpecService e GetEndpointsBySpecificationIdService
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:00:00

## Prompt original

Implementar testes unitários para as classes PersistSwaggerSpecService e GetEndpointsBySpecificationIdService da camada application. Para PersistSwaggerSpecService: validar que delega a persistência ao PersistSwaggerSpecPort e retorna o UUID gerado. Para GetEndpointsBySpecificationIdService: validar retorno de endpoints quando specificationId é válido, lançamento de NullPointerException quando specificationId é null (via Objects.requireNonNull), e retorno de lista vazia quando não há endpoints. Utilizar Mockito com @Mock e @InjectMocks para ambos os serviços.
