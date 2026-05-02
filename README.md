# MockAI

MockAI é um gerador inteligente de APIs mock que transforma arquivos Swagger/OpenAPI em APIs simuladas locais, permitindo desenvolvimento e testes sem dependência de serviços externos. Suporta geração dinâmica de endpoints e integração opcional com IA para respostas simuladas mais realistas.

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Maven | 3.9.7+ |
| Spring Boot | 4.0.6 |
| SpringDoc OpenAPI | 3.0.2 |
| H2 Database | runtime |

## Pré-requisitos

- Java 17+
- Maven 3.9.7+

## Como executar

```bash
mvn spring-boot:run
```

A aplicação sobe na porta `8080` por padrão.

## Comandos Maven

```bash
# Compilar
mvn clean compile

# Executar testes
mvn test

# Gerar JAR
mvn clean package

# Limpar artefatos
mvn clean
```

## Estrutura do Projeto

O projeto segue os princípios de **Clean Architecture** e **Hexagonal Architecture**, organizado em 4 camadas isoladas:

```
src/main/java/com/ia/para/devs/mockai/
├── MockaiApplication.java
├── domain/                          # Java puro, sem dependências externas
│   ├── model/
│   │   ├── MockDefinition.java      # Entidade de domínio: representa um mock cadastrado
│   │   ├── MockEndpoint.java        # Endpoint extraído da spec OpenAPI
│   │   └── OpenApiSpec.java         # Dados extraídos após parsing da spec
│   └── port/
│       ├── MockDefinitionRepository.java  # Port de saída: contrato de persistência
│       └── OpenApiParser.java             # Port de entrada: contrato de parsing
├── application/                     # Casos de uso e regras de negócio
│   ├── usecase/
│   │   ├── CreateMockUseCase.java   # Cria mock a partir de uma spec OpenAPI
│   │   ├── ListMocksUseCase.java    # Lista todos os mocks cadastrados
│   │   └── DeleteMockUseCase.java   # Remove um mock pelo id
│   └── service/
│       └── MockResolverService.java # Resolve a resposta simulada para um endpoint
├── infrastructure/                  # Adaptadores técnicos: JPA, gateways, mappers
│   ├── persistence/
│   │   ├── entity/
│   │   │   └── MockDefinitionEntity.java        # Entidade JPA
│   │   ├── repository/
│   │   │   └── MockDefinitionJpaRepository.java # Spring Data JPA
│   │   └── mapper/
│   │       └── MockDefinitionMapper.java        # Converte Entity ↔ Domain Model
│   └── gateway/
│       ├── OpenApiParserGateway.java             # Implementa parsing JSON/YAML
│       └── MockDefinitionRepositoryAdapter.java  # Implementa port de persistência
└── api/                             # Controllers REST, DTOs e tratamento de exceções
    ├── controller/
    │   ├── MockController.java          # CRUD de mocks: POST, GET, DELETE
    │   └── MockExecutorController.java  # Executa chamadas aos endpoints mock
    ├── dto/
    │   ├── request/
    │   │   └── CreateMockRequest.java   # DTO de entrada com validação
    │   └── response/
    │       ├── MockResponse.java        # DTO de saída do mock
    │       └── MockEndpointResponse.java
    └── exception/
        └── GlobalExceptionHandler.java  # Handler global de erros HTTP
```

### Responsabilidades por camada

- `domain` — Java puro, sem dependências externas. Define os contratos (ports) que outras camadas implementam.
- `application` — Orquestra os casos de uso, depende apenas do domain.
- `infrastructure` — Implementa os ports do domain usando JPA, H2 e gateways externos.
- `api` — Expõe endpoints REST, valida entradas e serializa respostas JSON.

## Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/mocks` | Cria um mock a partir de uma spec OpenAPI (JSON ou YAML) |
| `GET` | `/mocks` | Lista todos os mocks cadastrados |
| `DELETE` | `/mocks/{id}` | Remove um mock pelo id |
| `GET/POST/PUT/DELETE/PATCH` | `/mock/{id}/{path}` | Executa uma chamada ao endpoint mock simulado |

## Como testar

### Swagger UI

Acesse a documentação interativa com todos os endpoints disponíveis:

```
http://localhost:8080/swagger-ui.html
```

### Exemplo de criação de mock

`POST /mocks` com o body:

```json
{
  "specContent": "{\"openapi\":\"3.0.0\",\"info\":{\"title\":\"Minha API\",\"version\":\"1.0.0\"},\"paths\":{\"/usuarios\":{\"get\":{\"summary\":\"Lista usuários\",\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"application/json\":{\"example\":{\"id\":1,\"nome\":\"João\"}}}}}}}}}"
}
```

### Executar um endpoint mock

Com o `id` retornado na criação:

```
GET http://localhost:8080/mock/{id}/usuarios
```

Retorna a resposta simulada definida na spec: `{"id":1,"nome":"João"}`

## Ferramentas de Desenvolvimento

### H2 Console

Banco de dados em memória disponível durante o desenvolvimento:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: _(vazio)_

### Swagger UI

Documentação interativa da API:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
