# Diagrama C4 — MockAI

O modelo C4 descreve a arquitetura em 4 níveis de detalhe progressivo:
- **Nível 1 — Contexto:** O sistema e quem interage com ele
- **Nível 2 — Container:** Os processos e armazenamentos que compõem o sistema
- **Nível 3 — Componente:** Os módulos internos de cada container
- **Nível 4 — Código:** As classes e entidades principais

---

## Nível 1 — Contexto do Sistema

> Quem usa o MockAI e como ele se encaixa no mundo externo.

```mermaid
---
config:
  layout: dagre
  theme: neutral
---
flowchart TD
  dev["👤 Desenvolvedor
  ─────────────────
  Precisa implementar o consumo
  de uma API REST que ainda não
  está disponível"]

  consumer["👤 Consumidor do Mock
  ─────────────────
  Testa e valida a integração
  com os endpoints mockados"]

  mockai["🖥️ MockAI
  ─────────────────
  Gera APIs mock a partir de
  documentações Swagger/OpenAPI.
  Disponibiliza endpoints prontos
  para consumo imediato."]

  ai_ext["☁️ Groq
  ─────────────────
  Gera payloads de resposta
  dinamicamente para os
  endpoints mockados"]

  dev -- "Envia especificação\nSwagger/OpenAPI [JSON]\nvia HTTP POST" --> mockai
  consumer -- "Consome endpoints\nmockados via HTTP" --> mockai
  mockai -- "Solicita geração\nde payload mockado" --> ai_ext
  ai_ext -- "Retorna payload\ngerado" --> mockai

  dev:::person
  consumer:::person
  mockai:::system
  ai_ext:::external

  classDef person   fill:#E3F2FD,stroke:#1565C0,stroke-width:2px,color:#0D47A1
  classDef system   fill:#E8F5E9,stroke:#2E7D32,stroke-width:2px,color:#1B5E20
  classDef external fill:#F5F5F5,stroke:#616161,stroke-width:2px,color:#212121
```

---

## Nível 2 — Containers

> Os processos e armazenamentos que compõem o MockAI.

```mermaid
---
config:
  layout: dagre
  theme: neutral
---
flowchart LR
  dev["👤 Desenvolvedor"]
  consumer["👤 Consumidor do Mock"]

  subgraph mockai["Sistema MockAI"]
    direction TB

    api_app["🟩 MockAI Application
    ─────────────────
    Spring Boot 4.0.6 / Java 17
    Porta 8080 / context-path: /mockai
    ─────────────────
    Processa a spec Swagger,
    orquestra a criação dos mocks
    e serve os endpoints mockados"]

    db[("🟧 H2 Database
    ─────────────────
    H2 in-memory
    JDBC: jdbc:h2:mem:testdb
    ─────────────────
    Armazena especificações,
    endpoints, parâmetros
    e schemas de resposta")]
  end

  ai_ext["☁️ Groq
  ─────────────────
  API externa (api.groq.com)
  ─────────────────
  Gera payloads JSON
  dinamicamente"]

  dev      -- "POST /mockai/mocks\n[JSON Swagger/OpenAPI]" --> api_app
  consumer -- "GET /mockai/mock/{id}/{path}\n[HTTP]"        --> api_app
  api_app  -- "Spring Data JPA / JDBC"                      --> db
  db       -- "Consulta specs e endpoints"                   --> api_app
  api_app  -- "HTTP — solicita payload mockado"              --> ai_ext
  ai_ext   -- "Retorna JSON gerado"                          --> api_app

  dev:::person
  consumer:::person
  api_app:::container
  db:::database
  ai_ext:::external

  classDef person    fill:#E3F2FD,stroke:#1565C0,stroke-width:2px,color:#0D47A1
  classDef container fill:#E8F5E9,stroke:#2E7D32,stroke-width:2px,color:#1B5E20
  classDef database  fill:#FFF3E0,stroke:#EF6C00,stroke-width:2px,color:#E65100
  classDef external  fill:#F5F5F5,stroke:#616161,stroke-width:2px,color:#212121
```

---

## Nível 3 — Componentes

> Os módulos internos da MockAI Application, seguindo Clean Architecture.

```mermaid
---
config:
  layout: dagre
  theme: neutral
---
flowchart TD
  http_in["HTTP Request\n(Desenvolvedor / Consumidor)"]

  subgraph app["MockAI Application — Clean Architecture"]
    direction TB

    subgraph api_layer["Camada API"]
      ctrl["Controller REST
      ─────────────────
      Spring Web MVC + Bean Validation
      ─────────────────
      Recebe requisições HTTP,
      valida DTOs de entrada,
      serializa respostas JSON"]
    end

    subgraph application_layer["Camada Application"]
      usecase["Use Cases
      ─────────────────
      Java puro
      ─────────────────
      Processar spec Swagger,
      criar mock, servir endpoint"]
    end

    subgraph domain_layer["Camada Domain"]
      model["Modelos de Domínio
      ─────────────────
      Java puro — sem frameworks
      ─────────────────
      ApiSpecification, Endpoint,
      PathParameter, EndpointResponse"]

      port["Ports (Interfaces)
      ─────────────────
      Contratos que definem
      o que a infraestrutura
      deve implementar"]
    end

    subgraph infra_layer["Camada Infrastructure"]
      repo["Repositórios JPA
      ─────────────────
      Spring Data JPA
      ─────────────────
      Implementa os ports do domain.
      Persiste e consulta dados no H2"]

      gateway["Gateway de IA
      ─────────────────
      HTTP Client (Spring AI)
      ─────────────────
      Chama o Groq (api.groq.com)
      para gerar payloads mockados"]

      mapper["Mappers
      ─────────────────
      Converte entre entidades JPA
      e modelos de domínio"]
    end
  end

  db[("H2 Database")]
  ai_ext["Groq (api.groq.com)"]

  http_in --> ctrl
  ctrl    --> usecase
  usecase --> model
  usecase --> port
  port    --> repo
  port    --> gateway
  repo    --> mapper
  mapper  --> model
  repo    --> db
  gateway --> ai_ext

  ctrl:::apiComp
  usecase:::appComp
  model:::domainComp
  port:::domainComp
  repo:::infraComp
  gateway:::infraComp
  mapper:::infraComp
  db:::database
  ai_ext:::external

  classDef apiComp    fill:#E3F2FD,stroke:#1565C0,stroke-width:2px,color:#0D47A1
  classDef appComp    fill:#E8F5E9,stroke:#2E7D32,stroke-width:2px,color:#1B5E20
  classDef domainComp fill:#EDE7F6,stroke:#4527A0,stroke-width:2px,color:#311B92
  classDef infraComp  fill:#FFF3E0,stroke:#EF6C00,stroke-width:2px,color:#E65100
  classDef database   fill:#FFF3E0,stroke:#EF6C00,stroke-width:2px,color:#E65100
  classDef external   fill:#F5F5F5,stroke:#616161,stroke-width:2px,color:#212121
```

---

## Nível 4 — Código (Modelo de Dados)

> As entidades JPA que representam o modelo de dados persistido no H2.

```mermaid
---
config:
  layout: dagre
  theme: neutral
---
erDiagram
  api_specification {
    UUID   id          PK
    string title
    string version
    text   description
    string base_url
  }

  endpoint_definition {
    UUID   id                   PK
    string path
    string http_method
    string summary
    text   description
    UUID   api_specification_id FK
  }

  tag {
    UUID   id          PK
    string name
    text   description
  }

  endpoint_tags {
    UUID endpoint_id PK_FK
    UUID tag_id      PK_FK
  }

  path_parameter {
    UUID    id                     PK
    string  name
    string  param_in
    text    description
    string  type
    string  format
    boolean required
    UUID    endpoint_definition_id FK
  }

  endpoint_response {
    UUID   id                     PK
    string status_code
    string content_type
    text   description
    text   response_schema
    UUID   endpoint_definition_id FK
  }

  api_specification  ||--o{ endpoint_definition : "possui"
  endpoint_definition }o--o{ tag                : "endpoint_tags"
  endpoint_definition ||--o{ path_parameter     : "possui"
  endpoint_definition ||--o{ endpoint_response  : "possui"
```

---

## Resumo das Tecnologias por Camada

| Camada | Tecnologia | Responsabilidade |
|---|---|---|
| API | Spring Web MVC + SpringDoc OpenAPI 3.0.2 | Endpoints REST, validação, documentação Swagger |
| Application | Java 17 puro | Casos de uso, regras de negócio |
| Domain | Java 17 puro | Modelos e contratos (ports) |
| Infrastructure | Spring Data JPA + H2 + HTTP Client | Persistência, gateway de IA, mappers |
