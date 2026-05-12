# Requirements Document

## Introduction

Este documento define os requisitos para a modelagem de entidades do sistema MockAI. O MockAI é um gerador inteligente de APIs mock que transforma arquivos Swagger/OpenAPI em APIs simuladas locais. A modelagem de entidades deve suportar as funcionalidades principais: inserção de documentação Swagger/OpenAPI, criação de mocks de endpoints e disponibilização de endpoints mockados prontos para uso.

O sistema segue Clean Architecture e Hexagonal Architecture com 4 camadas (domain, application, infrastructure, api) e utiliza Spring Data JPA com H2 Database em memória.

**Escopo desta entrega:** Apenas a criação das entidades JPA do banco de dados. Nenhuma outra classe, serviço, repositório, validação ou funcionalidade deve ser implementada.

## Glossary

- **MockAI_System**: O sistema completo de geração de APIs mock
- **Swagger_Documentation**: Arquivo JSON seguindo o padrão Swagger/OpenAPI 3.0 ou superior que descreve uma API Rest
- **Mock_Endpoint**: Endpoint simulado criado com base na documentação Swagger
- **API_Specification**: Representação interna da documentação Swagger inserida no sistema
- **Endpoint_Definition**: Definição de um endpoint individual extraído da documentação Swagger
- **HTTP_Method**: Método HTTP (GET, POST, PUT, DELETE, PATCH, etc.)
- **Endpoint_Path**: Caminho URL de um endpoint (ex: /users/{id})
- **Status_Code**: Código de status HTTP de resposta (ex: 200, 404, 500)
- **Content_Type**: Tipo de conteúdo da resposta (ex: application/json)
- **Schema_Definition**: Estrutura de dados definida no Swagger para request/response bodies
- **Path_Parameter**: Parâmetro dinâmico presente no Endpoint_Path entre chaves (ex: {id}), com nome, tipo e indicação de obrigatoriedade
- **Tag**: Agrupador de endpoints definido na documentação Swagger
- **Database**: Banco de dados H2 em memória onde as entidades são persistidas

## Requirements

### Requirement 1: Persistir Documentação Swagger

**User Story:** Como desenvolvedor, eu quero que o sistema persista a documentação Swagger inserida, para que os endpoints mockados possam ser criados com base nessa documentação.

#### Acceptance Criteria

1. WHEN uma Swagger_Documentation válida é inserida, THE MockAI_System SHALL persistir a API_Specification no Database
2. THE API_Specification SHALL conter o título da API
3. THE API_Specification SHALL conter a versão da API
4. THE API_Specification SHALL conter a descrição da API
5. THE API_Specification SHALL conter a URL base da API

### Requirement 2: Persistir Definições de Endpoints

**User Story:** Como desenvolvedor, eu quero que o sistema persista as definições de endpoints extraídas da documentação Swagger, para que cada endpoint possa ser mockado individualmente.

#### Acceptance Criteria

1. WHEN uma Swagger_Documentation é processada, THE MockAI_System SHALL extrair todas as Endpoint_Definitions
2. THE Endpoint_Definition SHALL conter o Endpoint_Path
3. THE Endpoint_Definition SHALL conter o HTTP_Method
4. THE Endpoint_Definition SHALL conter uma descrição do endpoint
5. THE Endpoint_Definition SHALL conter um resumo do endpoint
6. THE Endpoint_Definition SHALL estar associada a uma API_Specification
7. THE Endpoint_Definition SHALL poder estar associada a uma ou mais Tags

### Requirement 3: Persistir Definições de Respostas

**User Story:** Como desenvolvedor, eu quero que o sistema persista as definições de respostas possíveis para cada endpoint, para que o mock possa retornar respostas apropriadas baseadas no status code.

#### Acceptance Criteria

1. WHEN uma Endpoint_Definition é criada, THE MockAI_System SHALL extrair todas as definições de resposta do Swagger
2. THE definição de resposta SHALL conter o Status_Code
3. THE definição de resposta SHALL conter o Content_Type
4. THE definição de resposta SHALL conter a descrição da resposta
5. THE definição de resposta SHALL estar associada a uma Endpoint_Definition
6. WHERE o Swagger define um Schema_Definition para a resposta, THE definição de resposta SHALL armazenar o schema em formato JSON

### Requirement 4: Persistir Tags de Agrupamento

**User Story:** Como desenvolvedor, eu quero que o sistema persista as tags definidas na documentação Swagger, para que os endpoints possam ser organizados e agrupados por categoria.

#### Acceptance Criteria

1. THE Tag SHALL conter um nome
2. THE Tag SHALL conter uma descrição
3. THE Tag SHALL ter um identificador único gerado automaticamente
4. THE relacionamento entre Tag e Endpoint_Definition SHALL ser muitos-para-muitos

### Requirement 5: Persistir Parâmetros de Path

**User Story:** Como desenvolvedor, eu quero que o sistema persista os parâmetros de path dos endpoints em uma entidade própria, para que endpoints dinâmicos como /users/{id} possam ser mockados corretamente.

#### Acceptance Criteria

1. THE Path_Parameter SHALL ser persistido como uma entidade separada no Database
2. THE Path_Parameter SHALL conter o nome do parâmetro
3. THE Path_Parameter SHALL conter o tipo do parâmetro
4. THE Path_Parameter SHALL conter uma indicação se o parâmetro é obrigatório
5. THE Path_Parameter SHALL estar associado a uma Endpoint_Definition
6. THE Path_Parameter SHALL ter um identificador único gerado automaticamente

### Requirement 6: Relacionamento entre Entidades

**User Story:** Como desenvolvedor, eu quero que as entidades mantenham relacionamentos consistentes, para que a integridade referencial seja preservada no Database.

#### Acceptance Criteria

1. WHEN uma API_Specification é deletada, THE MockAI_System SHALL deletar todas as Endpoint_Definitions associadas em cascata
2. WHEN uma Endpoint_Definition é deletada, THE MockAI_System SHALL deletar todas as definições de resposta associadas em cascata
3. WHEN uma Endpoint_Definition é deletada, THE MockAI_System SHALL remover as associações com Tags
4. THE relacionamento entre API_Specification e Endpoint_Definition SHALL ser um-para-muitos
5. THE relacionamento entre Endpoint_Definition e definição de resposta SHALL ser um-para-muitos
6. THE relacionamento entre Endpoint_Definition e Tag SHALL ser muitos-para-muitos
7. THE relacionamento entre Endpoint_Definition e Path_Parameter SHALL ser um-para-muitos

### Requirement 7: Identificadores Únicos

**User Story:** Como desenvolvedor, eu quero que cada entidade tenha um identificador único, para que possam ser referenciadas de forma inequívoca.

#### Acceptance Criteria

1. THE API_Specification SHALL ter um identificador único gerado automaticamente
2. THE Endpoint_Definition SHALL ter um identificador único gerado automaticamente
3. THE definição de resposta SHALL ter um identificador único gerado automaticamente
4. THE Tag SHALL ter um identificador único gerado automaticamente
5. THE Path_Parameter SHALL ter um identificador único gerado automaticamente
6. THE identificadores únicos SHALL ser gerados pelo Database
