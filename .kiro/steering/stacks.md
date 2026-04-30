# Stacks

Este documento descreve as tecnologias, frameworks e ferramentas utilizadas no projeto.

## Versões Principais

| Tecnologia    | Versão          | Descrição                                    |
|---------------|-----------------|----------------------------------------------|
| Java          | 17              | Linguagem de programação principal           |
| Maven         | 3.9.7+          | Gerenciador de dependências e build          |
| Spring Boot   | 4.0.6           | Framework principal para aplicações Java     |

## Stack Completa

### Core Framework

- **Spring Boot 4.0.6**
  - Framework base que simplifica o desenvolvimento de aplicações Java
  - Configuração automática e convenções sobre configuração
  - Servidor embutido para execução standalone

### Persistência de Dados

- **Spring Data JPA**
  - Abstração sobre JPA para acesso a dados
  - Repositórios com queries automáticas
  - Suporte a transações declarativas

- **H2 Database**
  - Banco de dados em memória para desenvolvimento e testes
  - Console web integrado para visualização de dados
  - Configuração zero para ambientes de desenvolvimento

### Web e API

- **Spring Web MVC**
  - Framework para construção de APIs REST
  - Suporte a controllers, request/response handling
  - Serialização/deserialização JSON automática

- **SpringDoc OpenAPI 3.0.2**
  - Geração automática de documentação OpenAPI/Swagger
  - Interface Swagger UI para testar endpoints
  - Integração nativa com Spring Boot

### Validação

- **Spring Boot Starter Validation (Bean Validation)**
  - Validação declarativa usando anotações
  - Implementação de JSR-380 (Bean Validation 2.0)
  - Validação automática em controllers e DTOs

### Testes

- **Spring Boot Starter Data JPA Test**
  - Ferramentas para testes de repositórios
  - Suporte a testes com banco de dados em memória

- **Spring Boot Starter Validation Test**
  - Ferramentas para testes de validação
  - Verificação de constraints e regras de negócio

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

### H2 Console

O console H2 está disponível em desenvolvimento para inspeção do banco de dados:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (vazio)

### Swagger UI

A documentação interativa da API está disponível em:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Boas Práticas

### Maven

- Sempre especificar versões explícitas de dependências quando necessário
- Usar o `spring-boot-starter-parent` como parent POM
- Manter as dependências atualizadas e compatíveis

### Spring Boot

- Usar anotações do Spring para injeção de dependência
- Aproveitar a configuração automática do Spring Boot
- Externalizar configurações em `application.properties` ou `application.yml`

### JPA e Persistência

- Usar entidades JPA apenas na camada de infraestrutura
- Mapear entidades para modelos de domínio usando mappers
- Evitar expor entidades JPA diretamente na API

### Validação

- Aplicar validações em DTOs na camada de API
- Usar Bean Validation para validações simples
- Implementar validações complexas nos casos de uso (application)

### Documentação

- Documentar endpoints com anotações do SpringDoc
- Incluir exemplos de request/response
- Descrever códigos de status HTTP retornados

## Estrutura de Dependências por Camada

### Domain
- Sem dependências de frameworks
- Java puro

### Application
- Depende apenas de abstrações do Domain
- Pode usar anotações do Spring para injeção de dependência

### Infrastructure
- Spring Data JPA
- H2 Database
- Implementações de repositórios

### API
- Spring Web MVC
- Bean Validation
- SpringDoc OpenAPI
- DTOs e Controllers
