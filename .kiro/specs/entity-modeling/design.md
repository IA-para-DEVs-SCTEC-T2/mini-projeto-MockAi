# Design Document

## Overview

Este documento descreve o design técnico para a modelagem das entidades JPA do sistema MockAI. O escopo é exclusivamente a criação das entidades de banco de dados na camada de infraestrutura, sem implementação de repositórios, serviços, validações ou qualquer outra funcionalidade.

As entidades serão criadas no pacote `infrastructure` seguindo a Clean Architecture adotada pelo projeto, utilizando Spring Data JPA com H2 Database em memória.

## Architecture

O projeto segue Clean Architecture com 4 camadas. As entidades JPA pertencem exclusivamente à camada `infrastructure`, pois são detalhes de persistência e não devem vazar para as demais camadas.

```
com.ia.para.devs.mockai
├── domain/
├── application/
├── infrastructure/
│   └── persistence/
│       └── entity/          ← entidades JPA criadas nesta entrega
│           ├── ApiSpecificationEntity.java
│           ├── EndpointDefinitionEntity.java
│           ├── EndpointResponseEntity.java
│           ├── PathParameterEntity.java
│           └── TagEntity.java
└── api/
```

## Entity Model

### Diagrama de Relacionamentos

```
ApiSpecificationEntity (1) ──────────── (N) EndpointDefinitionEntity
                                                    │
                                    ┌───────────────┼───────────────┐
                                    │               │               │
                                   (N)             (N)             (N)
                                    │               │               │
                                  TagEntity  PathParameterEntity  EndpointResponseEntity
                                   (N)
                                    │
                              EndpointDefinitionEntity
```

---

### ApiSpecificationEntity

Representa a documentação Swagger/OpenAPI inserida no sistema.

| Campo       | Tipo   | Obrigatório | Descrição                                        |
|-------------|--------|-------------|--------------------------------------------------|
| id          | UUID   | sim         | Identificador único (PK, gerado pela aplicação)  |
| title       | String | sim         | Título da API                                    |
| version     | String | sim         | Versão da API                                    |
| description | String | não         | Descrição da API                                 |
| baseUrl     | String | sim         | URL base da API                                  |

**Relacionamentos:**
- `endpoints`: `@OneToMany(mappedBy = "apiSpecification", cascade = CascadeType.ALL, orphanRemoval = true)`

---

### TagEntity

Representa um agrupador de endpoints definido na documentação Swagger.

| Campo       | Tipo   | Obrigatório | Descrição                                        |
|-------------|--------|-------------|--------------------------------------------------|
| id          | UUID   | sim         | Identificador único (PK, gerado pela aplicação)  |
| name        | String | sim         | Nome da tag                                      |
| description | String | não         | Descrição da tag                                 |

**Relacionamentos:**
- `endpoints`: `@ManyToMany(mappedBy = "tags")`

---

### EndpointDefinitionEntity

Representa a definição de um endpoint individual extraído da documentação Swagger.

| Campo       | Tipo   | Obrigatório | Descrição                                         |
|-------------|--------|-------------|---------------------------------------------------|
| id          | UUID   | sim         | Identificador único (PK, gerado pela aplicação)   |
| path        | String | sim         | Caminho URL do endpoint (ex: /users/{id})         |
| httpMethod  | String | sim         | Método HTTP (GET, POST, PUT, DELETE, PATCH, etc.) |
| summary     | String | não         | Resumo do endpoint                                |
| description | String | não         | Descrição detalhada do endpoint                   |

**Relacionamentos:**
- `apiSpecification`: `@ManyToOne(optional = false)` — FK para `ApiSpecificationEntity`
- `tags`: `@ManyToMany` — tabela de junção `endpoint_tags`
- `pathParameters`: `@OneToMany(mappedBy = "endpointDefinition", cascade = CascadeType.ALL, orphanRemoval = true)`
- `responses`: `@OneToMany(mappedBy = "endpointDefinition", cascade = CascadeType.ALL, orphanRemoval = true)`

---

### PathParameterEntity

Representa um parâmetro de path de um endpoint (ex: `{id}` em `/users/{id}`). É uma entidade própria com relacionamento para `EndpointDefinitionEntity`.

| Campo    | Tipo    | Obrigatório | Descrição                                       |
|----------|---------|-------------|-------------------------------------------------|
| id       | UUID    | sim         | Identificador único (PK, gerado pela aplicação) |
| name     | String  | sim         | Nome do parâmetro (ex: id, slug)                |
| type     | String  | sim         | Tipo do parâmetro (ex: integer, string)         |
| required | Boolean | sim         | Indica se o parâmetro é obrigatório             |

**Relacionamentos:**
- `endpointDefinition`: `@ManyToOne(optional = false)` — FK para `EndpointDefinitionEntity`

---

### EndpointResponseEntity

Representa uma definição de resposta possível para um endpoint.

| Campo          | Tipo   | Obrigatório | Descrição                                       |
|----------------|--------|-------------|-------------------------------------------------|
| id             | UUID   | sim         | Identificador único (PK, gerado pela aplicação) |
| statusCode     | String | sim         | Código de status HTTP (ex: 200, 404)            |
| contentType    | String | sim         | Tipo de conteúdo (ex: application/json)         |
| description    | String | não         | Descrição da resposta                           |
| responseSchema | String | não         | Schema da resposta serializado em JSON          |

**Relacionamentos:**
- `endpointDefinition`: `@ManyToOne(optional = false)` — FK para `EndpointDefinitionEntity`

**Observação sobre `responseSchema`:** Armazenado como `@Column(columnDefinition = "TEXT")` para suportar schemas JSON de tamanho variável. O H2 não possui tipo nativo JSON, portanto o conteúdo é tratado como texto simples.

---

## Database Schema

```sql
CREATE TABLE api_specification (
    id          UUID         PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    version     VARCHAR(255) NOT NULL,
    description TEXT,
    base_url    VARCHAR(255) NOT NULL
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
    type                   VARCHAR(100) NOT NULL,
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

## Mandatory Annotations

Todas as entidades JPA **devem obrigatoriamente** conter as seguintes anotações de classe, sem exceção:

| Anotação                                  | Origem        | Finalidade                                                                 |
|-------------------------------------------|---------------|----------------------------------------------------------------------------|
| `@Entity`                                 | Jakarta JPA   | Marca a classe como entidade JPA gerenciada pelo Hibernate                 |
| `@Table(name = "<nome_da_tabela>")`       | Jakarta JPA   | Define o nome da tabela no banco de dados (snake_case)                     |
| `@Data`                                   | Lombok        | Gera getters, setters, `toString`, `equals` e `hashCode` automaticamente   |
| `@FieldDefaults(level = AccessLevel.PRIVATE)` | Lombok    | Define todos os campos como `private` sem precisar declarar explicitamente |
| `@EqualsAndHashCode(of = "id")`           | Lombok        | Restringe `equals`/`hashCode` ao campo `id`, evitando loops em coleções    |

> **Atenção:** `@EqualsAndHashCode(of = "id")` sobrescreve o `equals`/`hashCode` gerado pelo `@Data`. As duas anotações devem coexistir — o `@Data` cuida dos demais métodos e o `@EqualsAndHashCode(of = "id")` especializa a comparação de identidade.

### Exemplo de estrutura obrigatória

```java
@Entity
@Data
@Table(name = "nome_da_tabela")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class MinhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    // demais campos...
}
```

---

## Entity Implementation

Esta seção apresenta o código completo de cada entidade conforme o modelo definido.

### ApiSpecificationEntity

```java
@Entity
@Data
@Table(name = "api_specification")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class ApiSpecificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String version;

    @Column(columnDefinition = "TEXT")
    String description;

    String baseUrl;

    @OneToMany(mappedBy = "apiSpecification", cascade = CascadeType.ALL, orphanRemoval = true)
    List<EndpointDefinitionEntity> endpoints;
}
```

---

### TagEntity

```java
@Entity
@Data
@Table(name = "tag")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    @ManyToMany(mappedBy = "tags")
    List<EndpointDefinitionEntity> endpoints;
}
```

---

### EndpointDefinitionEntity

```java
@Entity
@Data
@Table(name = "endpoint_definition")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class EndpointDefinitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String path;

    @Column(nullable = false, length = 20)
    String httpMethod;

    String summary;

    @Column(columnDefinition = "TEXT")
    String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "api_specification_id", nullable = false)
    ApiSpecificationEntity apiSpecification;

    @ManyToMany
    @JoinTable(
        name = "endpoint_tags",
        joinColumns = @JoinColumn(name = "endpoint_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<TagEntity> tags;

    @OneToMany(mappedBy = "endpointDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PathParameterEntity> pathParameters;

    @OneToMany(mappedBy = "endpointDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    List<EndpointResponseEntity> responses;
}
```

---

### PathParameterEntity

```java
@Entity
@Data
@Table(name = "path_parameter")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class PathParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, length = 100)
    String type;

    @Column(nullable = false)
    Boolean required;

    @ManyToOne(optional = false)
    @JoinColumn(name = "endpoint_definition_id", nullable = false)
    EndpointDefinitionEntity endpointDefinition;
}
```

---

### EndpointResponseEntity

```java
@Entity
@Data
@Table(name = "endpoint_response")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class EndpointResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, length = 10)
    String statusCode;

    @Column(nullable = false)
    String contentType;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(columnDefinition = "TEXT")
    String responseSchema;

    @ManyToOne(optional = false)
    @JoinColumn(name = "endpoint_definition_id", nullable = false)
    EndpointDefinitionEntity endpointDefinition;
}
```

---

## Implementation Notes

- Todas as entidades usam `UUID` como tipo do campo `id`, anotado com `@Id` e `@GeneratedValue(strategy = GenerationType.UUID)` — disponível a partir do Hibernate 6 (incluso no Spring Boot 3+)
- O UUID é gerado pela aplicação antes da persistência, não pelo banco
- O H2 suporta o tipo `UUID` nativamente como coluna
- Campos de texto longo (`description`, `responseSchema`) usam `@Column(columnDefinition = "TEXT")` para evitar limitação de 255 caracteres
- O relacionamento `@ManyToMany` entre `EndpointDefinitionEntity` e `TagEntity` é gerenciado pelo lado `EndpointDefinitionEntity` via `@JoinTable(name = "endpoint_tags")`
- Cascata `CascadeType.ALL` com `orphanRemoval = true` garante que ao deletar uma `ApiSpecificationEntity`, todos os `EndpointDefinitionEntity` associados sejam removidos; ao deletar um `EndpointDefinitionEntity`, todos os `PathParameterEntity` e `EndpointResponseEntity` associados sejam removidos
- Nenhuma anotação de validação (`@NotNull`, `@NotBlank`, etc.) deve ser adicionada nesta entrega
- Nenhum campo de auditoria (`createdAt`, `updatedAt`) deve ser adicionado
- `@FieldDefaults(level = AccessLevel.PRIVATE)` elimina a necessidade de declarar `private` em cada campo individualmente
- `@Data` do Lombok elimina a necessidade de escrever getters, setters e `toString` manualmente
