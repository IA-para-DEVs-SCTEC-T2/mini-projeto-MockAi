# Requirements Document

## Introduction

Este documento especifica os requisitos para o inicializador de uma aplicação Java gerenciada pelo Maven, seguindo os princípios de Clean Architecture. O sistema deve criar a estrutura completa do projeto com todas as dependências necessárias, configurações do Spring Boot e organização de camadas (domain, application, infrastructure, api).

## Glossary

- **Maven_Project**: Estrutura de projeto Java gerenciada pelo Maven com arquivo pom.xml
- **Clean_Architecture_Structure**: Organização de código em 4 camadas isoladas (domain, application, infrastructure, api)
- **Spring_Boot_Application**: Aplicação Java configurada com Spring Boot como framework base
- **Domain_Layer**: Camada contendo modelos de domínio puros e abstrações (ports), sem dependências externas
- **Application_Layer**: Camada contendo regras de negócio e casos de uso, dependendo apenas do Domain
- **Infrastructure_Layer**: Camada contendo adaptadores técnicos (JPA, gateways, mappers)
- **API_Layer**: Camada de apresentação contendo endpoints REST e DTOs
- **POM_File**: Arquivo pom.xml contendo configurações Maven e dependências do projeto
- **Application_Properties**: Arquivo de configuração do Spring Boot (application.properties ou application.yml)
- **Main_Class**: Classe principal anotada com @SpringBootApplication que inicializa a aplicação

## Requirements

### Requirement 1: Estrutura Maven do Projeto

**User Story:** Como desenvolvedor, eu quero um projeto Maven configurado corretamente, para que eu possa gerenciar dependências e construir a aplicação.

#### Acceptance Criteria

1. THE Maven_Project SHALL contain a valid pom.xml file with groupId "com.ia.para.devs"
2. THE Maven_Project SHALL contain a valid pom.xml file with artifactId "mockai"
3. THE Maven_Project SHALL use Java version 17 as source and target compatibility
4. THE Maven_Project SHALL use spring-boot-starter-parent version 4.0.6 as parent POM
5. THE POM_File SHALL declare spring-boot-maven-plugin for packaging the application

### Requirement 2: Dependências do Spring Boot

**User Story:** Como desenvolvedor, eu quero todas as dependências do Spring Boot configuradas, para que eu possa utilizar os recursos do framework.

#### Acceptance Criteria

1. THE POM_File SHALL include spring-boot-starter-web dependency for REST API support
2. THE POM_File SHALL include spring-boot-starter-data-jpa dependency for persistence
3. THE POM_File SHALL include h2 database dependency with runtime scope
4. THE POM_File SHALL include spring-boot-starter-validation dependency for Bean Validation
5. THE POM_File SHALL include springdoc-openapi-starter-webmvc-ui version 3.0.2 for API documentation
6. THE POM_File SHALL include spring-boot-starter-test dependency with test scope

### Requirement 3: Estrutura de Camadas Clean Architecture

**User Story:** Como desenvolvedor, eu quero a estrutura de pastas seguindo Clean Architecture, para que o código seja organizado e manutenível.

#### Acceptance Criteria

1. THE Clean_Architecture_Structure SHALL contain a domain package at "src/main/java/com/ia/para/devs/mockai/domain"
2. THE Clean_Architecture_Structure SHALL contain an application package at "src/main/java/com/ia/para/devs/mockai/application"
3. THE Clean_Architecture_Structure SHALL contain an infrastructure package at "src/main/java/com/ia/para/devs/mockai/infrastructure"
4. THE Clean_Architecture_Structure SHALL contain an api package at "src/main/java/com/ia/para/devs/mockai/api"
5. THE Clean_Architecture_Structure SHALL contain a test directory structure mirroring the main source structure

### Requirement 4: Configuração do Spring Boot

**User Story:** Como desenvolvedor, eu quero a aplicação Spring Boot configurada, para que ela possa ser executada corretamente.

#### Acceptance Criteria

1. THE Spring_Boot_Application SHALL contain a Main_Class at "src/main/java/com/ia/para/devs/mockai/MockaiApplication.java"
2. THE Main_Class SHALL be annotated with @SpringBootApplication
3. THE Main_Class SHALL contain a main method that calls SpringApplication.run()
4. THE Application_Properties SHALL be created at "src/main/resources/application.properties"
5. THE Application_Properties SHALL configure H2 console to be enabled
6. THE Application_Properties SHALL configure H2 database URL as "jdbc:h2:mem:testdb"
7. THE Application_Properties SHALL configure application server port (default 8080)

### Requirement 5: Configuração do Banco de Dados H2

**User Story:** Como desenvolvedor, eu quero o banco H2 configurado para desenvolvimento, para que eu possa testar a persistência de dados.

#### Acceptance Criteria

1. WHEN the application starts, THE Spring_Boot_Application SHALL initialize H2 database in memory
2. THE Application_Properties SHALL enable H2 console web interface
3. THE Application_Properties SHALL configure H2 console path as "/h2-console"
4. THE Application_Properties SHALL configure datasource username as "sa"
5. THE Application_Properties SHALL configure datasource with empty password

### Requirement 6: Documentação OpenAPI

**User Story:** Como desenvolvedor, eu quero documentação OpenAPI configurada, para que eu possa visualizar e testar os endpoints da API.

#### Acceptance Criteria

1. WHEN the application starts, THE Spring_Boot_Application SHALL enable Swagger UI at "/swagger-ui.html"
2. THE Spring_Boot_Application SHALL expose OpenAPI JSON specification at "/v3/api-docs"
3. THE Application_Properties SHALL configure springdoc api-docs path
4. THE Application_Properties SHALL configure springdoc swagger-ui path

### Requirement 7: Estrutura de Recursos

**User Story:** Como desenvolvedor, eu quero a estrutura de recursos configurada, para que arquivos de configuração sejam carregados corretamente.

#### Acceptance Criteria

1. THE Maven_Project SHALL contain a resources directory at "src/main/resources"
2. THE Maven_Project SHALL contain a test resources directory at "src/test/resources"
3. THE Maven_Project SHALL include application.properties in main resources
4. WHERE test-specific configurations are needed, THE Maven_Project SHALL support application-test.properties in test resources

### Requirement 8: Arquivos de Documentação

**User Story:** Como desenvolvedor, eu quero documentação do projeto, para que eu entenda a estrutura e como executar a aplicação.

#### Acceptance Criteria

1. THE Maven_Project SHALL contain a README.md file at project root
2. THE README.md SHALL document the project structure with Clean Architecture layers
3. THE README.md SHALL document Maven commands for building and running the application
4. THE README.md SHALL document how to access H2 console and Swagger UI
5. THE README.md SHALL document the technology stack (Java 17, Maven 3.9.7+, Spring Boot 4.0.6)

### Requirement 9: Configuração Git

**User Story:** Como desenvolvedor, eu quero arquivos Git configurados, para que artefatos de build não sejam versionados.

#### Acceptance Criteria

1. THE Maven_Project SHALL contain a .gitignore file at project root
2. THE .gitignore SHALL exclude Maven target directory
3. THE .gitignore SHALL exclude IDE-specific files (.idea, .vscode, .eclipse, *.iml)
4. THE .gitignore SHALL exclude OS-specific files (.DS_Store, Thumbs.db)
5. THE .gitignore SHALL exclude log files (*.log)

### Requirement 10: Validação da Estrutura

**User Story:** Como desenvolvedor, eu quero validar que a estrutura foi criada corretamente, para que eu possa começar a desenvolver com confiança.

#### Acceptance Criteria

1. FOR ALL required directories in Clean_Architecture_Structure, the directories SHALL exist in the file system
2. THE Maven_Project SHALL contain all required configuration files (pom.xml, application.properties, .gitignore, README.md)
3. THE Main_Class MockaiApplication.java SHALL exist at the correct location
4. THE directory structure SHALL follow the Clean Architecture pattern with 4 layers

## Constraints

### Prohibited Actions

1. **NO creation of DTOs, Models, Entities, POJOs, VOs or any data transfer/storage objects**
2. **NO creation of test classes, unit tests, or execution of build/compile/run tests**
3. **NO execution of terminal commands**
4. **NO addition of dependencies beyond those declared in the project's stacks file**
5. **NO creation of Java classes except MockaiApplication.java**
6. **NO creation of Java methods except main(String[] args)**

### Allowed Actions

1. **CREATE** directory structure following Clean Architecture
2. **CREATE** configuration files (pom.xml, application.properties, .gitignore, README.md)
3. **CREATE** MockaiApplication.java class only
4. **CREATE** empty package directories for future development

**Note:** MockaiApplication.java will contain ONLY the main(String[] args) method. No other methods will be created.
