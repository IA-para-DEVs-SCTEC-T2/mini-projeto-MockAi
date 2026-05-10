# Design Document: Inicializador de Aplicação Java Maven

## Overview

Este documento descreve o design técnico para o inicializador de uma aplicação Java gerenciada pelo Maven, seguindo os princípios de Clean Architecture. O sistema cria uma estrutura completa de projeto com todas as dependências necessárias, configurações do Spring Boot e organização em 4 camadas isoladas (domain, application, infrastructure, api).

### Objetivos

- Criar estrutura Maven completa e funcional
- Configurar Spring Boot 4.0.6 com todas as dependências necessárias
- Implementar organização Clean Architecture com 4 camadas
- Configurar banco de dados H2 para desenvolvimento
- Habilitar documentação OpenAPI/Swagger
- Fornecer documentação clara e arquivos de configuração

### Escopo

**Incluído:**
- Estrutura completa de diretórios Maven
- Arquivo pom.xml com todas as dependências
- Classe principal MockaiApplication.java
- Arquivos de configuração (application.properties)
- Estrutura de camadas Clean Architecture (diretórios vazios)
- Documentação (README.md, .gitignore)

**Não Incluído:**
- Implementação de funcionalidades de negócio
- Entidades de domínio específicas
- Casos de uso concretos
- Endpoints REST específicos
- DTOs, Models, Entities, POJOs, VOs ou qualquer objeto de transferência/armazenamento
- Classes de teste ou testes unitários
- Execução de comandos de build, compile ou run
- Qualquer classe Java além de MockaiApplication.java

## Architecture

### Visão Geral da Arquitetura

O projeto segue **Clean Architecture** com **Hexagonal Architecture**, garantindo que as regras de negócio sejam completamente independentes de frameworks, bancos de dados e detalhes de infraestrutura.

```
┌─────────────────────────────────────────────────────────────┐
│                         API Layer                            │
│  (Controllers, DTOs, Request/Response Handlers)             │
│  - Endpoints REST                                            │
│  - Validação de entrada                                      │
│  - Serialização JSON                                         │
└────────────────────┬────────────────────────────────────────┘
                     │ depende de
                     ▼
┌─────────────────────────────────────────────────────────────┐
│                    Application Layer                         │
│  (Use Cases, Business Rules)                                │
│  - Orquestração de casos de uso                             │
│  - Regras de negócio                                         │
│  - Validações de domínio                                     │
└────────────────────┬────────────────────────────────────────┘
                     │ depende de
                     ▼
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                            │
│  (Entities, Value Objects, Ports)                           │
│  - Modelos de domínio puros                                 │
│  - Interfaces (Ports)                                        │
│  - Sem dependências externas                                │
└─────────────────────────────────────────────────────────────┘
                     ▲ implementa
                     │
┌─────────────────────────────────────────────────────────────┐
│                  Infrastructure Layer                        │
│  (Adapters, JPA Entities, Repositories)                     │
│  - Implementação de repositórios                            │
│  - Entidades JPA                                             │
│  - Gateways externos                                         │
│  - Mappers                                                   │
└─────────────────────────────────────────────────────────────┘
```

### Princípios de Dependência

1. **Domain** não depende de nenhuma outra camada (Java puro)
2. **Application** depende apenas de **Domain**
3. **Infrastructure** implementa as interfaces (ports) definidas em **Domain**
4. **API** depende de **Application** e **Domain**, mas não de **Infrastructure** diretamente

### Fluxo de Dados

```
HTTP Request → API (Controller) → Application (Use Case) → Domain (Port)
                                                              ↓
HTTP Response ← API (DTO) ← Application ← Infrastructure (Adapter)
```

## Components and Interfaces

### 1. Estrutura de Diretórios Maven

```
mockai/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ia/
│   │   │           └── para/
│   │   │               └── devs/
│   │   │                   └── mockai/
│   │   │                       ├── MockaiApplication.java
│   │   │                       ├── domain/
│   │   │                       │   ├── model/
│   │   │                       │   └── port/
│   │   │                       ├── application/
│   │   │                       │   ├── usecase/
│   │   │                       │   └── service/
│   │   │                       ├── infrastructure/
│   │   │                       │   ├── persistence/
│   │   │                       │   │   ├── entity/
│   │   │                       │   │   ├── repository/
│   │   │                       │   │   └── mapper/
│   │   │                       │   └── gateway/
│   │   │                       └── api/
│   │   │                           ├── controller/
│   │   │                           ├── dto/
│   │   │                           │   ├── request/
│   │   │                           │   └── response/
│   │   │                           └── exception/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── ia/
│       │           └── para/
│       │               └── devs/
│       │                   └── mockai/
│       │                       ├── domain/
│       │                       ├── application/
│       │                       ├── infrastructure/
│       │                       └── api/
│       └── resources/
│           └── application-test.properties
├── pom.xml
├── README.md
└── .gitignore
```

### 2. Classe Principal (MockaiApplication.java)

**Responsabilidade:** Ponto de entrada da aplicação Spring Boot

**Localização:** `src/main/java/com/ia/para/devs/mockai/MockaiApplication.java`

**Estrutura:**
```java
package com.ia.para.devs.mockai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MockaiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockaiApplication.class, args);
    }
}
```

**Anotações:**
- `@SpringBootApplication`: Combina `@Configuration`, `@EnableAutoConfiguration` e `@ComponentScan`

**Restrições:**
- Esta classe conterá APENAS o método `main(String[] args)`
- Nenhum outro método será criado
- Nenhum campo ou construtor adicional será adicionado

### 3. Camada Domain

**Responsabilidade:** Contém modelos de domínio puros e abstrações (ports)

**Pacotes:**
- `domain.model`: Entidades de domínio, Value Objects (diretório vazio para desenvolvimento futuro)
- `domain.port`: Interfaces (ports) que definem contratos (diretório vazio para desenvolvimento futuro)

**Características:**
- Sem dependências de frameworks
- Java puro
- Regras de negócio fundamentais

**Nota:** Nesta fase de inicialização, apenas a estrutura de diretórios será criada. Nenhuma classe será implementada.

### 4. Camada Application

**Responsabilidade:** Implementa casos de uso e regras de negócio

**Pacotes:**
- `application.usecase`: Casos de uso específicos (diretório vazio para desenvolvimento futuro)
- `application.service`: Serviços de aplicação (diretório vazio para desenvolvimento futuro)

**Características:**
- Depende apenas de Domain
- Orquestra operações de domínio
- Validações de negócio

**Nota:** Nesta fase de inicialização, apenas a estrutura de diretórios será criada. Nenhuma classe será implementada.

### 5. Camada Infrastructure

**Responsabilidade:** Implementa adaptadores técnicos (JPA, gateways, mappers)

**Pacotes:**
- `infrastructure.persistence.entity`: Entidades JPA (diretório vazio para desenvolvimento futuro)
- `infrastructure.persistence.repository`: Implementações de repositórios (diretório vazio para desenvolvimento futuro)
- `infrastructure.persistence.mapper`: Conversão entre entidades JPA e modelos de domínio (diretório vazio para desenvolvimento futuro)
- `infrastructure.gateway`: Gateways para serviços externos (diretório vazio para desenvolvimento futuro)

**Características:**
- Implementa ports do Domain
- Usa Spring Data JPA
- Contém detalhes técnicos

**Nota:** Nesta fase de inicialização, apenas a estrutura de diretórios será criada. Nenhuma classe será implementada.

### 6. Camada API

**Responsabilidade:** Expõe endpoints REST e gerencia DTOs

**Pacotes:**
- `api.controller`: Controllers REST (diretório vazio para desenvolvimento futuro)
- `api.dto.request`: DTOs de entrada (diretório vazio para desenvolvimento futuro)
- `api.dto.response`: DTOs de saída (diretório vazio para desenvolvimento futuro)
- `api.exception`: Tratamento de exceções (diretório vazio para desenvolvimento futuro)

**Características:**
- Usa Spring Web MVC
- Validação com Bean Validation
- Documentação com SpringDoc

**Nota:** Nesta fase de inicialização, apenas a estrutura de diretórios será criada. Nenhuma classe será implementada.

### 7. Configuração Maven (pom.xml)

**Responsabilidade:** Gerenciar dependências e configurações de build

**Estrutura Principal:**
- Parent: `spring-boot-starter-parent` versão 4.0.6
- GroupId: `com.ia.para.devs`
- ArtifactId: `mockai`
- Java Version: 17

**Dependências Principais:**
- `spring-boot-starter-web`: Suporte REST API
- `spring-boot-starter-data-jpa`: Persistência
- `h2`: Banco de dados em memória
- `spring-boot-starter-validation`: Bean Validation
- `springdoc-openapi-starter-webmvc-ui`: Documentação OpenAPI
- `spring-boot-starter-test`: Testes

**Plugin:**
- `spring-boot-maven-plugin`: Empacotamento da aplicação

### 8. Configuração Spring Boot (application.properties)

**Responsabilidade:** Configurar comportamento da aplicação

**Localização:** `src/main/resources/application.properties`

**Configurações Principais:**
- Porta do servidor
- Configuração do H2
- Console H2
- SpringDoc/Swagger

**Estrutura:**
```properties
# Server Configuration
server.port=8080

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# SpringDoc OpenAPI Configuration
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## Data Models

### Estrutura de Dados por Camada

**Nota Importante:** Nesta fase de inicialização, **NENHUM modelo de dados será criado**. Apenas a estrutura de diretórios será estabelecida para desenvolvimento futuro.

#### Domain Layer
- **Diretório:** `domain.model` (vazio)
- **Propósito futuro:** POJOs puros representando conceitos de negócio
- **Características futuras:** Value Objects imutáveis, sem anotações JPA

#### Infrastructure Layer
- **Diretório:** `infrastructure.persistence.entity` (vazio)
- **Propósito futuro:** Classes anotadas com `@Entity`, `@Table`, etc.
- **Características futuras:** Mapeamento bidirecional entre entidades JPA e modelos de domínio

#### API Layer
- **Diretório:** `api.dto.request` e `api.dto.response` (vazios)
- **Propósito futuro:** Records ou classes para entrada/saída de dados
- **Características futuras:** Validações com Bean Validation (`@NotBlank`, `@NotNull`, etc.)

### Fluxo de Transformação de Dados (Referência Futura)

```
HTTP JSON → Request DTO → Domain Model → JPA Entity → Database
                ↓              ↓              ↓
         (Validation)   (Business Logic)  (Persistence)
```

**Esta é apenas uma referência arquitetural. Nenhuma implementação será criada nesta fase.**

## Testing Strategy

### Abordagem de Testes

**IMPORTANTE:** Nesta fase de inicialização, **NENHUM teste será criado ou executado**. Este projeto foca exclusivamente na criação da estrutura base e configurações.

### Justificativa

Este projeto de inicialização é principalmente **Infrastructure as Code (IaC)** e configuração declarativa:
- Cria estrutura de arquivos e configurações
- Não há lógica de negócio ou transformações de dados
- Não há funções com propriedades testáveis
- O foco é estabelecer a base para desenvolvimento futuro

### Validação Manual (Opcional pelo Desenvolvedor)

Após a criação da estrutura, o desenvolvedor pode opcionalmente validar:

1. **Estrutura de Diretórios:** Verificar que todos os pacotes foram criados
2. **Arquivos de Configuração:** Confirmar existência de pom.xml, application.properties, etc.
3. **Classe Principal:** Verificar que MockaiApplication.java existe

**Nota:** Estas validações são opcionais e devem ser feitas manualmente pelo desenvolvedor, não por testes automatizados nesta fase.

### Testes Futuros (Referência)

Quando funcionalidades forem implementadas no futuro, considerar:
- Testes unitários para lógica de negócio
- Testes de integração para APIs
- Testes de repositório para persistência

**Esta é apenas uma referência para desenvolvimento futuro. Nenhum teste será implementado nesta fase.**

## Error Handling

### Estratégia de Tratamento de Erros

#### 1. Erros de Configuração

**Cenário:** Configurações inválidas ou ausentes

**Tratamento:**
- Spring Boot falha rapidamente no startup
- Mensagens de erro claras no console
- Logs detalhados indicando configuração problemática

**Exemplo:**
```
***************************
APPLICATION FAILED TO START
***************************

Description:
Failed to configure a DataSource: 'url' attribute is not specified

Action:
Consider the following:
    - Verify spring.datasource.url in application.properties
```

#### 2. Erros de Dependência Maven

**Cenário:** Dependências não resolvidas ou conflitos

**Tratamento:**
- Maven exibe erro durante build
- Indica dependência problemática
- Sugere resolução de conflitos

**Exemplo:**
```
[ERROR] Failed to execute goal on project mockai: 
Could not resolve dependencies for project com.ia.para.devs:mockai:jar:1.0.0
```

**Resolução:**
- Verificar conectividade com repositórios Maven
- Executar `mvn dependency:tree` para análise
- Atualizar versões conflitantes

#### 3. Erros de Compilação

**Cenário:** Código Java com erros de sintaxe ou tipo

**Tratamento:**
- Compilador Java reporta erros
- Maven falha na fase de compilação
- Mensagens indicam arquivo e linha do erro

**Exemplo:**
```
[ERROR] /path/to/MockaiApplication.java:[10,8] class MockaiApplication is public, 
should be declared in a file named MockaiApplication.java
```

#### 4. Erros de Inicialização do Spring

**Cenário:** Beans não podem ser criados ou injetados

**Tratamento:**
- Spring Boot falha no startup
- Stack trace completo no console
- Indica bean problemático e causa raiz

**Exemplo:**
```
Error creating bean with name 'entityManagerFactory': 
Invocation of init method failed
```

**Resolução:**
- Verificar anotações de componentes
- Confirmar configuração de JPA
- Validar entidades e repositórios

#### 5. Erros de Porta em Uso

**Cenário:** Porta 8080 já está sendo utilizada

**Tratamento:**
- Spring Boot detecta porta ocupada
- Falha no startup com mensagem clara

**Exemplo:**
```
Web server failed to start. Port 8080 was already in use.
```

**Resolução:**
- Alterar porta em application.properties: `server.port=8081`
- Ou encerrar processo usando a porta

#### 6. Erros de Acesso ao Banco H2

**Cenário:** Problemas ao conectar ou inicializar H2

**Tratamento:**
- Spring Boot loga erro de conexão
- Aplicação não inicia

**Resolução:**
- Verificar configuração de datasource
- Confirmar driver H2 no classpath
- Validar URL JDBC

### Logging

**Configuração de Logs:**
```properties
# Logging Configuration
logging.level.root=INFO
logging.level.com.ia.para.devs.mockai=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

**Níveis de Log por Camada:**
- **API:** INFO para requisições, DEBUG para detalhes
- **Application:** DEBUG para fluxo de casos de uso
- **Infrastructure:** DEBUG para queries SQL
- **Domain:** Geralmente sem logs (lógica pura)

### Validação de Entrada

**Na Camada API:**
```java
@PostMapping
public ResponseEntity<ExampleResponse> create(
    @Valid @RequestBody ExampleRequest request
) {
    // Bean Validation automática
}
```

**Tratamento de Erros de Validação:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
        MethodArgumentNotValidException ex
    ) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();
            
        return ResponseEntity
            .badRequest()
            .body(new ErrorResponse(errors));
    }
}
```

### Monitoramento

**Spring Boot Actuator (opcional para produção):**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**Endpoints de Saúde:**
- `/actuator/health`: Status da aplicação
- `/actuator/info`: Informações da aplicação
- `/actuator/metrics`: Métricas de runtime

## Constraints and Limitations

### Prohibited Actions

Esta fase de inicialização tem restrições específicas para manter o escopo focado:

1. **❌ NO creation of DTOs, Models, Entities, POJOs, VOs or any data transfer/storage objects**
   - Apenas estrutura de diretórios será criada
   - Implementações de classes de dados são para fases futuras

2. **❌ NO creation of test classes, unit tests, or execution of build/compile/run tests**
   - Nenhum arquivo de teste será criado
   - Nenhum comando Maven será executado
   - Validação é manual e opcional

3. **❌ NO execution of terminal commands**
   - Nenhum comando bash/shell será executado
   - Nenhuma validação automatizada via CLI

4. **❌ NO addition of dependencies beyond those declared in the project's stacks file**
   - Apenas dependências já especificadas em stacks.md
   - Sem adição de novas bibliotecas

5. **❌ NO creation of Java classes except MockaiApplication.java**
   - Única classe Java permitida: MockaiApplication.java
   - Todos os outros pacotes ficam vazios

6. **❌ NO creation of Java methods except main(String[] args)**
   - MockaiApplication.java conterá APENAS o método main
   - Nenhum método auxiliar, construtor ou campo adicional

### Allowed Actions

1. **✅ CREATE** directory structure following Clean Architecture
2. **✅ CREATE** configuration files (pom.xml, application.properties, .gitignore, README.md)
3. **✅ CREATE** MockaiApplication.java class with ONLY main(String[] args) method
4. **✅ CREATE** empty package directories for future development

### Rationale

Estas restrições garantem que:
- O projeto mantém foco em estrutura e configuração
- Não há complexidade desnecessária na fase inicial
- A base está pronta para desenvolvimento futuro
- O desenvolvedor tem controle total sobre implementações futuras
- MockaiApplication.java é minimalista, contendo apenas o essencial para inicializar Spring Boot

