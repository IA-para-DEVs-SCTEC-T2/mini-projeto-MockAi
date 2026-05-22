# Database Schema — MockAI

## Visão Geral

O banco de dados do MockAI é composto por 6 tabelas que representam a estrutura de uma documentação Swagger/OpenAPI e seus endpoints. O banco utilizado é o **H2 in-memory** com suporte nativo ao tipo `UUID`.

---

## Diagrama de Relacionamentos

```
api_specification (1) ──────────── (N) endpoint_definition
                                               │
                               ┌───────────────┼───────────────┐
                               │               │               │
                              (N)             (N)             (N)
                               │               │               │
                          endpoint_tags   path_parameter   endpoint_response
                               │
                              (N)
                               │
                             tag
```

---

## Tabelas

### `api_specification`

Representa a documentação Swagger/OpenAPI inserida no sistema.

| Coluna            | Tipo         | Restrições  | Descrição                                                        |
|-------------------|--------------|-------------|------------------------------------------------------------------|
| `id`              | UUID         | PK NOT NULL | Identificador único                                              |
| `title`           | VARCHAR(255) | NOT NULL    | Título da API                                                    |
| `version`         | VARCHAR(255) | NOT NULL    | Versão da API                                                    |
| `description`     | TEXT         | —           | Descrição da API                                                 |
| `base_url`        | VARCHAR(255) | NOT NULL    | URL base da API                                                  |
| `components_json` | TEXT         | —           | Bloco `components` da spec serializado como JSON (usado para resolução de `$ref` na geração de payloads) |

---

### `tag`

Representa um agrupador de endpoints definido na documentação Swagger.

| Coluna        | Tipo         | Restrições  | Descrição           |
|---------------|--------------|-------------|---------------------|
| `id`          | UUID         | PK NOT NULL | Identificador único |
| `name`        | VARCHAR(255) | NOT NULL    | Nome da tag         |
| `description` | TEXT         | —           | Descrição da tag    |

---

### `endpoint_definition`

Representa a definição de um endpoint individual extraído da documentação Swagger.

| Coluna                 | Tipo         | Restrições       | Descrição                                         |
|------------------------|--------------|------------------|---------------------------------------------------|
| `id`                   | UUID         | PK NOT NULL      | Identificador único                               |
| `path`                 | VARCHAR(255) | NOT NULL         | Caminho URL do endpoint (ex: `/users/{id}`)       |
| `http_method`          | VARCHAR(20)  | NOT NULL         | Método HTTP (GET, POST, PUT, DELETE, PATCH, etc.) |
| `summary`              | VARCHAR(255) | —                | Resumo do endpoint                                |
| `description`          | TEXT         | —                | Descrição detalhada do endpoint                   |
| `api_specification_id` | UUID         | FK NOT NULL      | Referência para `api_specification(id)`           |

---

### `endpoint_tags`

Tabela de junção do relacionamento N:N entre `endpoint_definition` e `tag`.

| Coluna        | Tipo | Restrições       | Descrição                               |
|---------------|------|------------------|-----------------------------------------|
| `endpoint_id` | UUID | PK FK NOT NULL   | Referência para `endpoint_definition(id)` |
| `tag_id`      | UUID | PK FK NOT NULL   | Referência para `tag(id)`               |

---

### `path_parameter`

Representa um parâmetro de path de um endpoint (ex: `{id}` em `/users/{id}`).

| Coluna                  | Tipo         | Restrições  | Descrição                                                              |
|-------------------------|--------------|-------------|------------------------------------------------------------------------|
| `id`                    | UUID         | PK NOT NULL | Identificador único                                                    |
| `name`                  | VARCHAR(255) | NOT NULL    | Nome do parâmetro (ex: `id`, `slug`)                                   |
| `param_in`              | VARCHAR(20)  | NOT NULL    | Localização do parâmetro conforme OpenAPI (`path`, `query`, `header`)  |
| `description`           | TEXT         | —           | Descrição do parâmetro                                                 |
| `type`                  | VARCHAR(100) | NOT NULL    | Tipo do parâmetro conforme OpenAPI (ex: `string`, `integer`)           |
| `format`                | VARCHAR(100) | —           | Formato do schema conforme OpenAPI (ex: `uuid`, `int64`, `date-time`)  |
| `required`              | BOOLEAN      | NOT NULL    | Indica se o parâmetro é obrigatório                                    |
| `endpoint_definition_id`| UUID         | FK NOT NULL | Referência para `endpoint_definition(id)`                              |

---

### `endpoint_response`

Representa uma definição de resposta possível para um endpoint.

| Coluna                  | Tipo         | Restrições  | Descrição                                       |
|-------------------------|--------------|-------------|-------------------------------------------------|
| `id`                    | UUID         | PK NOT NULL | Identificador único                             |
| `status_code`           | VARCHAR(10)  | NOT NULL    | Código de status HTTP (ex: `200`, `404`)        |
| `content_type`          | VARCHAR(255) | NOT NULL    | Tipo de conteúdo (ex: `application/json`)       |
| `description`           | TEXT         | —           | Descrição da resposta                           |
| `response_schema`       | TEXT         | —           | Schema da resposta serializado em JSON          |
| `endpoint_definition_id`| UUID         | FK NOT NULL | Referência para `endpoint_definition(id)`       |

---

## SQL — DDL Completo

```sql
CREATE TABLE api_specification (
    id              UUID         PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    version         VARCHAR(255) NOT NULL,
    description     TEXT,
    base_url        VARCHAR(255) NOT NULL,
    components_json TEXT
);

CREATE TABLE tag (
    id          UUID         PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE endpoint_definition (
    id                   UUID         PRIMARY KEY,
    path                 VARCHAR(255) NOT NULL,
    http_method          VARCHAR(20)  NOT NULL,
    summary              VARCHAR(255),
    description          TEXT,
    api_specification_id UUID NOT NULL,
    FOREIGN KEY (api_specification_id) REFERENCES api_specification(id)
);

CREATE TABLE endpoint_tags (
    endpoint_id UUID NOT NULL,
    tag_id      UUID NOT NULL,
    PRIMARY KEY (endpoint_id, tag_id),
    FOREIGN KEY (endpoint_id) REFERENCES endpoint_definition(id),
    FOREIGN KEY (tag_id)      REFERENCES tag(id)
);

CREATE TABLE path_parameter (
    id                     UUID         PRIMARY KEY,
    name                   VARCHAR(255) NOT NULL,
    param_in               VARCHAR(20)  NOT NULL,
    description            TEXT,
    type                   VARCHAR(100) NOT NULL,
    format                 VARCHAR(100),
    required               BOOLEAN      NOT NULL,
    endpoint_definition_id UUID NOT NULL,
    FOREIGN KEY (endpoint_definition_id) REFERENCES endpoint_definition(id)
);

CREATE TABLE endpoint_response (
    id                     UUID         PRIMARY KEY,
    status_code            VARCHAR(10)  NOT NULL,
    content_type           VARCHAR(255) NOT NULL,
    description            TEXT,
    response_schema        TEXT,
    endpoint_definition_id UUID NOT NULL,
    FOREIGN KEY (endpoint_definition_id) REFERENCES endpoint_definition(id)
);
```

---

## Notas Técnicas

- Todos os identificadores são do tipo `UUID`, gerados pela aplicação antes da persistência (não pelo banco).
- O H2 suporta o tipo `UUID` nativamente como coluna.
- Campos de texto longo (`description`, `response_schema`, `components_json`) utilizam o tipo `TEXT` para evitar a limitação de 255 caracteres do `VARCHAR`.
- O relacionamento N:N entre `endpoint_definition` e `tag` é gerenciado pela tabela de junção `endpoint_tags`.
- A exclusão em cascata é gerenciada pela aplicação via JPA (`CascadeType.ALL` + `orphanRemoval = true`):
  - Ao deletar uma `api_specification`, todos os `endpoint_definition` associados são removidos.
  - Ao deletar um `endpoint_definition`, todos os `path_parameter` e `endpoint_response` associados são removidos.
- A tabela `path_parameter` armazena todas as propriedades do parâmetro conforme a spec OpenAPI: `param_in` (localização), `description`, `type` e `format`. Isso permite distinguir endpoints com path parameters de formatos diferentes (ex: `uuid` vs string simples) durante o roteamento dinâmico. Path parameters com `format: uuid` recebem constraint de regex no Spring MVC.
- A tabela `endpoint_response` persiste **apenas o primeiro status de sucesso (2xx)** encontrado na spec. Respostas de erro (4xx, 5xx) são ignoradas. Se não houver nenhum status de sucesso, persiste um registro com `status_code = 200` e `response_schema = NULL`.
- O campo `components_json` em `api_specification` armazena o bloco `components` da spec OpenAPI serializado como JSON. É utilizado pelo `DynamicResponseBodyBuilder` para resolver referências `$ref` ao construir o schema de resposta enviado à IA.
