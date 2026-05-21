# MockAI

MockAI é um gerador inteligente de APIs mock que transforma arquivos Swagger/OpenAPI em APIs simuladas locais, permitindo desenvolvimento e testes sem dependência de serviços externos. Suporta geração dinâmica de endpoints e integração com IA (Groq) para respostas simuladas realistas.

## Grupo 3
- Dariel Verdecia Verdecia
- João Ricardo Tasca Puel
- Welton Sabino
- Daniel Rodrigues da Silva
- Luiz Fernando Amaral

## Kanban
- https://github.com/orgs/IA-para-DEVs-SCTEC-T2/projects/6/views/1

## Arquitetura

O diagrama C4 completo da arquitetura do MockAI está disponível em [`docs/architecture-diagram.md`](docs/architecture-diagram.md), cobrindo os 4 níveis:

- **Nível 1 — Contexto:** sistema, atores externos e serviço de IA
- **Nível 2 — Containers:** MockAI Application (Spring Boot) e H2 Database
- **Nível 3 — Componentes:** as 4 camadas da Clean Architecture
- **Nível 4 — Código:** modelo de dados com as 6 entidades JPA

## Banco de Dados

- [Diagrama do banco de dados (dbdiagram.io)](https://dbdiagram.io/d/6a0034347a923b947269ee88)
- Especificação DBML: [`docs/database-schema.dbml`](docs/database-schema.dbml)
- Documentação das tabelas: [`docs/database-schema.md`](docs/database-schema.md)

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Maven | 3.9.7+ |
| Spring Boot | 4.0.6 |
| Spring AI | 2.0.0-M6 (BOM) |
| SpringDoc OpenAPI | 3.0.2 |
| H2 Database | runtime |
| Groq | llama-3.1-8b-instant |

## Pré-requisitos

- Java 17+
- Maven 3.9.7+
- Chave de API do Groq (obtenha em [console.groq.com/keys](https://console.groq.com/keys))

## Configuração

Copie o arquivo de exemplo e preencha com sua chave do Groq:

```bash
cp .env.example .env
# Edite .env e defina GROQ_API_KEY=sua_chave_aqui
```

## Como executar

```bash
mvn spring-boot:run
```

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

## Endpoints da API MockAI

| Método | Path | Descrição |
|--------|------|-----------|
| `POST` | `/mockai/import` | Importa uma spec Swagger/OpenAPI (multipart/form-data, campo `file`) |
| `GET` | `/mockai/endpoints` | Lista todos os endpoints mockados ativos |
| `GET` | `/mockai/test-ai-connection` | Verifica conectividade com o serviço de IA (Groq) |

Os endpoints mockados são registrados dinamicamente após o import e ficam disponíveis nos paths definidos na spec.

## Estrutura do Projeto

O projeto segue os princípios de **Clean Architecture** e **Hexagonal Architecture**, organizado em 4 camadas isoladas:

```
src/main/java/com/ia/para/devs/mockai/
├── MockaiApplication.java
├── adapter/
│   └── in/
│       └── web/
│           ├── AiConnectionController.java   # GET /test-ai-connection
│           ├── EndpointController.java        # GET /endpoints
│           ├── ImportController.java          # POST /import
│           ├── dto/                           # DTOs de request/response e OpenAPI
│           ├── dynamic/                       # Handler e registry de rotas dinâmicas
│           └── handler/                       # GlobalExceptionHandler
├── application/
│   ├── service/                               # Implementações dos casos de uso
│   └── util/                                  # HttpMethodMapper
├── config/                                    # JacksonConfig
├── domain/
│   ├── exception/                             # Exceções de domínio tipadas
│   ├── model/                                 # FileData (modelo de domínio)
│   └── port/
│       ├── in/                                # Use cases (interfaces de entrada)
│       └── out/                               # Ports de saída (repositórios, gateways)
└── infrastructure/
    ├── ai/
    │   ├── config/                            # GroqApiKeyValidator
    │   └── gateway/                           # AiGateway (Spring AI + Groq)
    ├── config/                                # DotEnvInitializer
    └── persistence/
        ├── adapter/                           # Adapters de persistência e consulta
        ├── entity/                            # Entidades JPA (5 entidades)
        └── repository/                        # Repositórios Spring Data JPA
```

### Responsabilidades por camada

- `domain` — Java puro, sem dependências externas. Define os contratos (ports) e exceções de negócio.
- `application` — Orquestra os casos de uso, depende apenas do domain.
- `infrastructure` — Implementa os ports do domain usando JPA, H2 e Spring AI (Groq).
- `adapter` — Expõe endpoints REST, valida entradas, registra rotas dinâmicas e serializa respostas JSON.

## Ferramentas de Desenvolvimento

### H2 Console

Banco de dados em memória disponível durante o desenvolvimento:

- URL: `http://localhost:8080/mockai/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuário: `sa` | Senha: *(vazio)*

### Swagger UI

Documentação interativa da API:

- Swagger UI: `http://localhost:8080/mockai/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/mockai/v3/api-docs`

## Documentação

- [PRD — Product Requirements Document](docs/PRD.md)
- [Diagrama de Arquitetura C4](docs/architecture-diagram.md)
- [Schema do Banco de Dados](docs/database-schema.md)
- [Guia de Contribuição](CONTRIBUTING.md)
