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

- URL: <adicionar informações>
- JDBC URL: <adicionar informações>

### Swagger UI

Documentação interativa da API:

- Swagger UI: <adicionar informações>
- OpenAPI JSON: <adicionar informações>
