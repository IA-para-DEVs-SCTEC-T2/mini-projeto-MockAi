# Implementation Tasks

## Task 1: Criar entidades independentes

- [x] 1.1 Criar `ApiSpecificationEntity` com campos `id`, `title`, `version`, `description`, `baseUrl` e relacionamento `endpoints`
- [x] 1.2 Criar `TagEntity` com campos `id`, `name`, `description` e relacionamento `endpoints`

## Task 2: Criar entidade EndpointDefinitionEntity

- [x] 2.1 Criar `EndpointDefinitionEntity` com campos `id`, `path`, `httpMethod`, `summary`, `description` e relacionamentos `apiSpecification`, `tags`, `pathParameters` e `responses`

## Task 3: Criar entidades dependentes de EndpointDefinitionEntity

- [x] 3.1 Criar `PathParameterEntity` com campos `id`, `name`, `type`, `required` e relacionamento `endpointDefinition`
- [x] 3.2 Criar `EndpointResponseEntity` com campos `id`, `statusCode`, `contentType`, `description`, `responseSchema` e relacionamento `endpointDefinition`

## Task 4: Verificar compilação

- [x] 4.1 Executar `mvn clean compile` e confirmar ausência de erros
- [x] 4.2 Iniciar a aplicação e confirmar que o Hibernate gera o schema sem erros nos logs
