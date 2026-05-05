# MockAI

MockAI é um gerador inteligente de APIs mock que transforma arquivos Swagger/OpenAPI em APIs simuladas locais, permitindo desenvolvimento e testes sem dependência de serviços externos.

Ao fazer o upload de uma spec OpenAPI (JSON ou YAML), o sistema gera automaticamente um **slug** amigável baseado no título da spec (ex: `usuarios`, `fiscalizacao`) e disponibiliza os endpoints simulados em URLs legíveis como `http://localhost:8080/mock/usuarios/usuarios`.

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Maven | 3.9.7+ |
| Spring Boot | 4.0.6 |
| SpringDoc OpenAPI | 3.0.2 |
| H2 Database | runtime |
| Jackson YAML | runtime |

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

---

## Estrutura do Projeto

O projeto segue os princípios de **Clean Architecture** e **Hexagonal Architecture**, organizado em 4 camadas isoladas:

```
src/main/java/com/ia/para/devs/mockai/
├── MockaiApplication.java
├── domain/                               # Java puro, sem dependências externas
│   ├── model/
│   │   ├── MockDefinition.java           # Entidade de domínio: representa um projeto mock
│   │   ├── MockEndpoint.java             # Endpoint com metadados completos da spec
│   │   ├── EndpointParameter.java        # Parâmetro de endpoint (path/query/header/cookie)
│   │   ├── EndpointResponse.java         # Resposta possível de um endpoint
│   │   └── OpenApiSpec.java              # Dados extraídos após parsing da spec
│   └── port/
│       ├── MockDefinitionRepository.java # Port de saída: contrato de persistência
│       └── OpenApiParser.java            # Port de entrada: contrato de parsing
├── application/                          # Casos de uso e regras de negócio
│   ├── usecase/
│   │   ├── CreateMockUseCase.java        # Cria mock a partir de spec inline
│   │   ├── UploadSpecUseCase.java        # Cria mock a partir de arquivo enviado
│   │   ├── ListMocksUseCase.java         # Lista todos os mocks (detalhes completos)
│   │   ├── ListProjectsUseCase.java      # Lista projetos com sumário
│   │   ├── GetProjectEndpointsUseCase.java # Busca endpoints de um projeto pelo slug
│   │   ├── DeleteMockUseCase.java        # Remove mock pelo UUID
│   │   └── DeleteProjectBySlugUseCase.java # Remove projeto pelo slug
│   └── service/
│       ├── MockResolverService.java      # Resolve endpoint por UUID ou slug + path template
│       └── SlugGeneratorService.java     # Gera slugs únicos e incrementais a partir do título
├── infrastructure/                       # Adaptadores técnicos: JPA, gateways, mappers
│   ├── persistence/
│   │   ├── entity/
│   │   │   └── MockDefinitionEntity.java         # Entidade JPA (inclui campo slug)
│   │   ├── repository/
│   │   │   └── MockDefinitionJpaRepository.java  # Spring Data JPA com queries por slug
│   │   └── mapper/
│   │       └── MockDefinitionMapper.java         # Converte Entity ↔ Domain Model
│   └── gateway/
│       ├── OpenApiParserGateway.java              # Parser OpenAPI 3.x (JSON e YAML)
│       └── MockDefinitionRepositoryAdapter.java   # Implementa port de persistência
└── api/                                  # Controllers REST, DTOs e tratamento de exceções
    ├── controller/
    │   ├── MockController.java           # Gerenciamento de mocks: POST, GET, DELETE por UUID
    │   ├── ProjectEndpointsController.java # Projetos por slug: listar, consultar, deletar
    │   └── MockExecutorController.java   # Executor: simula chamadas reais aos endpoints
    ├── dto/
    │   ├── request/
    │   │   └── CreateMockRequest.java    # DTO de entrada com validação
    │   └── response/
    │       ├── MockResponse.java         # DTO de saída com slug e endpointsUrl
    │       ├── MockEndpointResponse.java # Endpoint com metadados completos
    │       ├── EndpointParameterResponse.java
    │       ├── EndpointResponseDetail.java
    │       └── ProjectSummaryResponse.java # Sumário de projeto para listagem
    └── exception/
        └── GlobalExceptionHandler.java   # Handler global de erros HTTP
```

### Responsabilidades por camada

- `domain` — Java puro, sem dependências externas. Define os contratos (ports) que outras camadas implementam.
- `application` — Orquestra os casos de uso, depende apenas do domain.
- `infrastructure` — Implementa os ports do domain usando JPA, H2 e gateways externos.
- `api` — Expõe endpoints REST, valida entradas e serializa respostas JSON.

---

## Endpoints da API

### Gerenciamento de Mocks (`/mocks`)

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/mocks` | Cria um mock a partir de uma spec OpenAPI inline (JSON ou YAML como string) |
| `POST` | `/mocks/upload` | Cria um mock via upload de arquivo `.json`, `.yaml` ou `.yml` |
| `GET` | `/mocks` | Lista todos os mocks com detalhes completos |
| `DELETE` | `/mocks/{id}` | Remove um mock pelo UUID |

### Projetos por Slug (`/projects`, `/{slug}`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/projects` | Lista todos os projetos com slug, URL de acesso e sumário dos endpoints |
| `GET` | `/{slug}/endpoints` | Retorna os endpoints completos de um projeto pelo slug |
| `DELETE` | `/{slug}` | Remove um projeto e todos os seus endpoints pelo slug |

### Executor (`/mock`)

| Método | Endpoint | Descrição |
|---|---|---|
| `*` | `/mock/{slug}/{path}` | Simula uma chamada real ao endpoint mock — use o método HTTP desejado diretamente |

---

## Fluxo de Uso

### 1. Fazer upload de uma spec OpenAPI

```bash
curl -X POST http://localhost:8080/mocks/upload \
  -F "file=@usuarios-api.yaml"
```

A resposta inclui o **slug** gerado e a **URL de acesso**:

```json
{
  "id": "3fa85f64-...",
  "name": "Usuarios API",
  "slug": "usuarios",
  "endpointsUrl": "/usuarios/endpoints"
}
```

Se já existir um projeto com o mesmo slug, um sufixo incremental é adicionado automaticamente:
- `usuarios` → `usuarios-2` → `usuarios-3`

### 2. Consultar os endpoints do projeto

```
GET http://localhost:8080/usuarios/endpoints
GET http://localhost:8080/fiscalizacao/endpoints
```

### 3. Listar todos os projetos

```
GET http://localhost:8080/projects
```

Retorna slug, nome, URL de acesso, URL de deleção e sumário dos endpoints de cada projeto.

### 4. Executar um endpoint mock

Faça a chamada diretamente com o método HTTP desejado — sem parâmetros extras:

```
GET    http://localhost:8080/mock/fiscalizacao/fiscalizacoes
GET    http://localhost:8080/mock/fiscalizacao/fiscalizacoes/1
POST   http://localhost:8080/mock/usuarios/usuarios
PUT    http://localhost:8080/mock/usuarios/usuarios/1
DELETE http://localhost:8080/mock/usuarios/usuarios/1
PATCH  http://localhost:8080/mock/fiscalizacao/fiscalizacoes/1
```

O sistema resolve automaticamente **path templates** — `/fiscalizacoes/{id}` bate com `/fiscalizacoes/42`, `/fiscalizacoes/abc`, etc.

A resposta retorna com o **status HTTP real** definido na spec (200, 201, 204, 404...) e o body JSON simulado.

### 5. Deletar um projeto pelo slug

```bash
curl -X DELETE http://localhost:8080/usuarios-2
curl -X DELETE http://localhost:8080/fiscalizacao
```

---

## Geração de Slugs

O slug é gerado automaticamente a partir do campo `info.title` da spec OpenAPI:

| Título da spec | Slug gerado |
|---|---|
| `Usuarios API` | `usuarios` |
| `Fiscalização API` | `fiscalizacao` |
| `Usuarios API` (2ª vez) | `usuarios-2` |
| `Usuarios API` (3ª vez) | `usuarios-3` |

Regras aplicadas:
- Converte para minúsculas
- Remove acentos e caracteres especiais
- Substitui espaços por hífen
- Adiciona sufixo incremental se o slug já existir

---

## Características Extraídas da Spec OpenAPI

O parser extrai os seguintes dados de cada endpoint:

| Campo | Descrição |
|---|---|
| `path` | Path do endpoint (ex: `/usuarios/{id}`) |
| `httpMethod` | Método HTTP (GET, POST, PUT, PATCH, DELETE...) |
| `summary` | Resumo da operação |
| `description` | Descrição detalhada |
| `operationId` | Identificador único da operação |
| `tags` | Grupos/categorias |
| `requiresAuth` | Se possui `security` definido |
| `parameters` | Parâmetros path/query/header/cookie com tipo e obrigatoriedade |
| `requestBodyExample` | Exemplo do corpo da requisição |
| `requestBodyRequired` | Se o body é obrigatório |
| `responseStatus` | Status HTTP da resposta principal (2xx) |
| `responseBody` | Body da resposta principal |
| `responses` | Todas as respostas possíveis (200, 201, 400, 401, 404...) |

---

## Arquivos de Exemplo

Os arquivos de exemplo estão em `docs/samples/`:

| Arquivo | Descrição |
|---|---|
| `usuarios-api.yaml` | Spec OpenAPI 3.x em YAML — CRUD de usuários (5 endpoints) |
| `usuarios-api.json` | Mesma spec em JSON |
| `fiscalizacao-api.yaml` | Spec OpenAPI 3.x — API de fiscalizações (4 endpoints) |
| `requests.http` | Exemplos de requisições para REST Client (VS Code / IntelliJ) |
| `test-curl.sh` | Script bash com todos os cenários de teste usando curl |

---

## Ferramentas de Desenvolvimento

### Swagger UI

Documentação interativa com todos os endpoints:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

### H2 Console

Banco de dados em memória para inspeção durante o desenvolvimento:

- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** _(vazio)_
