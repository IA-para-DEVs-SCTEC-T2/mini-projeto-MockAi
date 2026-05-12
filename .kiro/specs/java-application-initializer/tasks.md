# Implementation Plan: Inicializador de Aplicação Java Maven

## Overview

Este plano de implementação cria a estrutura completa de um projeto Java Maven seguindo Clean Architecture com 4 camadas (domain, application, infrastructure, api). O foco é estabelecer a base do projeto com todas as configurações necessárias, estrutura de diretórios e arquivos de documentação, sem implementar funcionalidades de negócio.

## Tasks

- [ ] 1. Criar estrutura de diretórios Maven completa
  - Criar diretório raiz `mockai/`
  - Criar estrutura `src/main/java/com/ia/para/devs/mockai/`
  - Criar estrutura `src/main/resources/`
  - Criar estrutura `src/test/java/com/ia/para/devs/mockai/`
  - Criar estrutura `src/test/resources/`
  - _Requirements: 1.1, 1.2, 7.1, 7.2_

- [ ] 2. Criar estrutura de camadas Clean Architecture
  - [ ] 2.1 Criar pacotes da camada Domain
    - Criar diretório `src/main/java/com/ia/para/devs/mockai/domain/`
    - Criar subdiretório `domain/model/` (vazio)
    - Criar subdiretório `domain/port/` (vazio)
    - _Requirements: 3.1_
  
  - [ ] 2.2 Criar pacotes da camada Application
    - Criar diretório `src/main/java/com/ia/para/devs/mockai/application/`
    - Criar subdiretório `application/usecase/` (vazio)
    - Criar subdiretório `application/service/` (vazio)
    - _Requirements: 3.2_
  
  - [ ] 2.3 Criar pacotes da camada Infrastructure
    - Criar diretório `src/main/java/com/ia/para/devs/mockai/infrastructure/`
    - Criar subdiretório `infrastructure/persistence/entity/` (vazio)
    - Criar subdiretório `infrastructure/persistence/repository/` (vazio)
    - Criar subdiretório `infrastructure/persistence/mapper/` (vazio)
    - Criar subdiretório `infrastructure/gateway/` (vazio)
    - _Requirements: 3.3_
  
  - [ ] 2.4 Criar pacotes da camada API
    - Criar diretório `src/main/java/com/ia/para/devs/mockai/api/`
    - Criar subdiretório `api/controller/` (vazio)
    - Criar subdiretório `api/dto/request/` (vazio)
    - Criar subdiretório `api/dto/response/` (vazio)
    - Criar subdiretório `api/exception/` (vazio)
    - _Requirements: 3.4_

- [ ] 3. Criar estrutura de testes espelhando a estrutura principal
  - Criar diretório `src/test/java/com/ia/para/devs/mockai/domain/` (vazio)
  - Criar diretório `src/test/java/com/ia/para/devs/mockai/application/` (vazio)
  - Criar diretório `src/test/java/com/ia/para/devs/mockai/infrastructure/` (vazio)
  - Criar diretório `src/test/java/com/ia/para/devs/mockai/api/` (vazio)
  - _Requirements: 3.5_

- [ ] 4. Criar arquivo pom.xml com configurações Maven
  - Definir parent como spring-boot-starter-parent versão 4.0.6
  - Configurar groupId como "com.ia.para.devs"
  - Configurar artifactId como "mockai"
  - Configurar Java version 17
  - Adicionar dependência spring-boot-starter-web
  - Adicionar dependência spring-boot-starter-data-jpa
  - Adicionar dependência h2 com scope runtime
  - Adicionar dependência spring-boot-starter-validation
  - Adicionar dependência springdoc-openapi-starter-webmvc-ui versão 3.0.2
  - Adicionar dependência spring-boot-starter-test com scope test
  - Configurar spring-boot-maven-plugin
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6_

- [ ] 5. Criar classe principal MockaiApplication.java
  - Criar arquivo em `src/main/java/com/ia/para/devs/mockai/MockaiApplication.java`
  - Adicionar package declaration: `package com.ia.para.devs.mockai;`
  - Adicionar imports necessários (SpringApplication, SpringBootApplication)
  - Adicionar anotação @SpringBootApplication
  - Criar classe pública MockaiApplication
  - Implementar APENAS método main(String[] args) que chama SpringApplication.run()
  - _Requirements: 4.1, 4.2, 4.3_

- [ ] 6. Criar arquivo application.properties com configurações
  - Criar arquivo em `src/main/resources/application.properties`
  - Configurar server.port=8080
  - Configurar spring.datasource.url=jdbc:h2:mem:testdb
  - Configurar spring.datasource.driverClassName=org.h2.Driver
  - Configurar spring.datasource.username=sa
  - Configurar spring.datasource.password (vazio)
  - Habilitar H2 console: spring.h2.console.enabled=true
  - Configurar H2 console path: spring.h2.console.path=/h2-console
  - Configurar JPA: spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  - Configurar JPA: spring.jpa.hibernate.ddl-auto=update
  - Configurar JPA: spring.jpa.show-sql=true
  - Configurar SpringDoc: springdoc.api-docs.path=/v3/api-docs
  - Configurar SpringDoc: springdoc.swagger-ui.path=/swagger-ui.html
  - _Requirements: 4.4, 4.5, 4.6, 4.7, 5.1, 5.2, 5.3, 5.4, 5.5, 6.1, 6.2, 6.3, 6.4_

- [ ] 7. Checkpoint - Verificar estrutura básica criada
  - Ensure all tests pass, ask the user if questions arise.

- [ ] 8. Criar arquivo README.md com documentação do projeto
  - Criar arquivo README.md na raiz do projeto
  - Documentar título e descrição do projeto
  - Documentar stack de tecnologias (Java 17, Maven 3.9.7+, Spring Boot 4.0.6)
  - Documentar estrutura do projeto com as 4 camadas Clean Architecture
  - Documentar comandos Maven (compile, test, package, run)
  - Documentar como acessar H2 console (URL, credenciais)
  - Documentar como acessar Swagger UI (URL)
  - Adicionar seção de pré-requisitos
  - Adicionar seção de como executar a aplicação
  - _Requirements: 8.1, 8.2, 8.3, 8.4, 8.5_

- [ ] 9. Criar arquivo .gitignore
  - Criar arquivo .gitignore na raiz do projeto
  - Adicionar exclusão do diretório target/
  - Adicionar exclusão de arquivos IDE (.idea/, .vscode/, .eclipse/, *.iml)
  - Adicionar exclusão de arquivos do sistema operacional (.DS_Store, Thumbs.db)
  - Adicionar exclusão de arquivos de log (*.log)
  - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5_

- [ ] 10. Criar diretório static em resources
  - Criar diretório `src/main/resources/static/` (vazio para arquivos estáticos futuros)
  - _Requirements: 7.1_

- [ ] 11. Final checkpoint - Validar estrutura completa
  - Ensure all tests pass, ask the user if questions arise.

## Notes

- Este projeto foca EXCLUSIVAMENTE na criação da estrutura e configurações base
- Nenhuma funcionalidade de negócio será implementada nesta fase
- Todos os pacotes de camadas (domain, application, infrastructure, api) ficarão vazios
- A única classe Java criada será MockaiApplication.java contendo APENAS o método main
- Não serão criados DTOs, Models, Entities, POJOs, VOs ou qualquer objeto de dados
- Não serão criadas classes de teste ou executados comandos de build/compile/run
- A estrutura está pronta para desenvolvimento futuro seguindo Clean Architecture
- Cada tarefa referencia os requisitos específicos para rastreabilidade
- Checkpoints garantem validação incremental da estrutura criada
