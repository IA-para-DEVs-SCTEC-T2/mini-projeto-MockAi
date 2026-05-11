# MockAI

MockAI é um gerador inteligente de APIs mock que transforma arquivos Swagger/OpenAPI em APIs simuladas locais, permitindo desenvolvimento e testes sem dependência de serviços externos. Suporta geração dinâmica de endpoints e integração opcional com IA para respostas simuladas mais realistas.


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
| SpringDoc OpenAPI | 3.0.2 |
| H2 Database | runtime |

## Pré-requisitos

- Java 17+
- Maven 3.9.7+

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

## Estrutura do Projeto

O projeto segue os princípios de **Clean Architecture** e **Hexagonal Architecture**, organizado em 4 camadas isoladas:

```
src/main/java/com/ia/para/devs/mockai/
├── MockaiApplication.java
├── domain/              # Modelos de domínio puros e ports (interfaces)
│   ├── model/
│   └── port/
├── application/         # Casos de uso e regras de negócio
│   ├── usecase/
│   └── service/
├── infrastructure/      # Adaptadores técnicos: JPA, gateways, mappers
│   ├── persistence/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── mapper/
│   └── gateway/
└── api/                 # Controllers REST, DTOs e tratamento de exceções
    ├── controller/
    ├── dto/
    │   ├── request/
    │   └── response/
    └── exception/
```

### Responsabilidades por camada

- `domain` — Java puro, sem dependências externas. Define os contratos (ports) que outras camadas implementam.
- `application` — Orquestra os casos de uso, depende apenas do domain.
- `infrastructure` — Implementa os ports do domain usando JPA, H2 e gateways externos.
- `api` — Expõe endpoints REST, valida entradas e serializa respostas JSON.

## Ferramentas de Desenvolvimento

### H2 Console

Banco de dados em memória disponível durante o desenvolvimento:

- URL: `http://localhost:8080/mockai/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`

### Swagger UI

Documentação interativa da API:

- Swagger UI: `http://localhost:8080/mockai/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/mockai/v3/api-docs`
