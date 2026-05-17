---
inclusion: always
---

# Stack Tecnológica

Este documento descreve as tecnologias, frameworks e ferramentas utilizadas no projeto MockAI.

## Versões Principais

| Tecnologia  | Versão  | Descrição                                |
|-------------|---------|------------------------------------------|
| Java        | 17      | Linguagem de programação principal       |
| Maven       | 3.9.7+  | Gerenciador de dependências e build      |
| Spring Boot | 4.0.6   | Framework principal para aplicações Java |

## Stack Completa

### Core Framework

- **Spring Boot 4.0.6**
  - Framework base que simplifica o desenvolvimento de aplicações Java
  - Configuração automática e convenções sobre configuração
  - Servidor embutido para execução standalone

### Persistência de Dados

- **Spring Data JPA** (`spring-boot-starter-data-jpa`)
  - Abstração sobre JPA para acesso a dados
  - Repositórios com queries automáticas
  - Suporte a transações declarativas

- **H2 Database** (`spring-boot-h2console` + `com.h2database:h2`)
  - Banco de dados em memória para desenvolvimento e testes
  - Console web integrado para visualização de dados
  - Configuração zero para ambientes de desenvolvimento

### Web e API

- **Spring Web MVC** (`spring-boot-starter-webmvc`)
  - Framework para construção de APIs REST
  - Suporte a controllers, request/response handling
  - Serialização/deserialização JSON automática

- **SpringDoc OpenAPI 3.0.2** (`springdoc-openapi-starter-webmvc-ui`)
  - Geração automática de documentação OpenAPI/Swagger
  - Interface Swagger UI para testar endpoints
  - Integração nativa com Spring Boot

### Utilitários

- **Lombok**
  - Redução de boilerplate com geração de código em tempo de compilação
  - Anotações como `@Getter`, `@Setter`, `@Builder`, `@RequiredArgsConstructor`
  - Configurado como `optional` no Maven e excluído do JAR final

### Testes

- **Spring Boot Starter Data JPA Test** (`spring-boot-starter-data-jpa-test`)
  - Ferramentas para testes de repositórios
  - Suporte a testes com banco de dados em memória

## Comandos Maven Úteis

```bash
# Compilar o projeto
mvn clean compile

# Executar testes
mvn test

# Gerar o pacote (JAR)
mvn clean package

# Executar a aplicação
mvn spring-boot:run

# Limpar artefatos de build
mvn clean
```

## Configurações Importantes

### Contexto da Aplicação

A aplicação sobe com context path `/mockai` na porta `8080`:
- Base URL: `http://localhost:8080/mockai`

### H2 Console

O console H2 está disponível em desenvolvimento para inspeção do banco de dados:
- URL: `http://localhost:8080/mockai/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (vazio)

### Swagger UI

A documentação interativa da API está disponível em:
- Swagger UI: `http://localhost:8080/mockai/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/mockai/v3/api-docs`

## Boas Práticas

### Maven

- Sempre especificar versões explícitas de dependências quando não gerenciadas pelo parent POM
- Usar o `spring-boot-starter-parent` como parent POM
- Manter as dependências compatíveis com a versão do Spring Boot em uso

### Spring Boot

- Usar anotações do Spring para injeção de dependência
- Aproveitar a configuração automática do Spring Boot
- Externalizar configurações em `application.properties`

### JPA e Persistência

- Usar entidades JPA apenas na camada de infraestrutura
- Mapear entidades para modelos de domínio usando mappers
- Evitar expor entidades JPA diretamente na API

### Lombok

- Preferir `@RequiredArgsConstructor` para injeção de dependência via construtor
- Usar `@Builder` em DTOs e modelos de domínio para facilitar a construção de objetos
- Não usar Lombok em interfaces ou classes abstratas do domínio

### Documentação

- Documentar endpoints com anotações do SpringDoc (`@Operation`, `@ApiResponse`)
- Incluir exemplos de request/response
- Descrever códigos de status HTTP retornados

## Estrutura de Dependências por Camada

### Domain
- Sem dependências de frameworks
- Java puro (pode usar Lombok)

### Application
- Depende apenas de abstrações do Domain
- Pode usar anotações do Spring para injeção de dependência

### Infrastructure
- Spring Data JPA
- H2 Database
- Implementações de repositórios e gateways

### API
- Spring Web MVC
- SpringDoc OpenAPI
- DTOs e Controllers

#[[file:pom.xml]]
