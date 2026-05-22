# Diagramas UML — MockAI

Este documento apresenta os diagramas UML do projeto MockAI, cobrindo os dois fluxos principais:
- **Importação de especificação Swagger/OpenAPI**
- **Obtenção de resposta por IA (endpoint mockado)**

---

## 1. Diagrama de Sequência

### 1.1 Importação de Especificação Swagger

> Sequência de interações desde o recebimento do arquivo até o registro das rotas dinâmicas.

```mermaid
sequenceDiagram
    autonumber
    actor Dev as Desenvolvedor
    participant BE as MockAI Backend
    participant DB as H2 Database

    Dev->>BE: POST /mockai/import (multipart/form-data .json)

    alt Extensão inválida (não .json)
        BE-->>Dev: HTTP 400 — extensão inválida
    end

    alt JSON inválido ou campos obrigatórios ausentes
        BE-->>Dev: HTTP 400 — conteúdo inválido
    end

    BE->>DB: DELETE todos os endpoints existentes
    BE->>DB: INSERT especificação + endpoints + respostas + parâmetros + tags
    DB-->>BE: UUID da especificação persistida

    alt Falha na persistência
        BE-->>Dev: HTTP 500 / 503 / 409 — erro de persistência
    end

    BE->>DB: SELECT endpoints por specificationId
    DB-->>BE: lista de endpoints
    BE->>BE: Registra rotas dinâmicas para cada endpoint

    BE-->>Dev: HTTP 201 — "Arquivo importado com sucesso"
```

---

### 1.2 Obtenção de Resposta por IA (Endpoint Mockado)

> Sequência de interações quando um consumidor chama um endpoint mockado dinamicamente.

```mermaid
sequenceDiagram
    autonumber
    actor Consumer as Consumidor do Mock
    participant BE as MockAI Backend
    participant DB as H2 Database
    participant Groq as Groq API<br/>(api.groq.com)

    Consumer->>BE: HTTP [GET|POST|PUT|PATCH|DELETE] /mockai/{path}

    alt Endpoint não encontrado no registro dinâmico
        BE-->>Consumer: HTTP 404 — endpoint não encontrado
    end

    alt Sem schema de resposta definido
        BE-->>Consumer: HTTP {statusCode} (sem corpo)
    end

    BE->>DB: SELECT schema e metadados do endpoint
    DB-->>BE: EndpointDefinitionEntity + EndpointResponseEntity

    BE->>BE: Resolve schema OpenAPI e constrói prompt contextual

    alt API key ausente ou inválida
        BE-->>Consumer: HTTP {statusCode} + corpo estático (fallback)
    end

    BE->>Groq: POST /openai/v1/chat/completions (llama-3.1-8b-instant)

    alt Timeout ou erro HTTP (4xx/5xx)
        Groq-->>BE: erro
        BE-->>Consumer: HTTP {statusCode} + corpo estático (fallback)
    end

    Groq-->>BE: JSON gerado pelo modelo

    alt Resposta da IA não é JSON válido
        BE-->>Consumer: HTTP {statusCode} + corpo estático (fallback)
    end

    BE-->>Consumer: HTTP {statusCode} + corpo gerado por IA (application/json)
```

---

## 2. Diagrama de Atividades

### 2.1 Processo de Importação de Especificação Swagger

> Fluxo de execução completo, incluindo decisões, desvios e caminhos alternativos.

```mermaid
---
config:
  layout: dagre
  theme: neutral
---
flowchart TD
    Start([▶ Início
POST /mockai/import])

    A[Receber MultipartFile]
    B[Adaptar para FileData
nome + bytes]
    C{Extensão
é .json?}
    D[HTTP 400
Extensão inválida]
    E[Desserializar JSON
Jackson → OpenApiSpecDto]
    F{JSON válido
e mapeável?}
    G[HTTP 400
Conteúdo JSON inválido]
    H[Validar campos obrigatórios
openapi · info.title · info.description · paths]
    I{Campos
obrigatórios
presentes?}
    J[HTTP 400
Campos ausentes listados]
    K[Deletar todos os endpoints
existentes no banco]
    L[Persistir ApiSpecificationEntity]
    M[Persistir EndpointDefinitionEntity
para cada path/método]
    N[Persistir entidades relacionadas
EndpointResponseEntity
PathParameterEntity
TagEntity]
    O{Persistência
bem-sucedida?}
    P[HTTP 500 / 503 / 409
Erro de persistência]
    Q[Desregistrar todas as
rotas dinâmicas existentes]
    R[Buscar endpoints persistidos
por specificationId]
    S[Registrar handler dinâmico
para cada path/método]
    End([⏹ Fim
HTTP 201 — Importado com sucesso])

    Start --> A --> B --> C
    C -- Não --> D
    C -- Sim --> E --> F
    F -- Não --> G
    F -- Sim --> H --> I
    I -- Não --> J
    I -- Sim --> K --> L --> M --> N --> O
    O -- Não --> P
    O -- Sim --> Q --> R --> S --> End

    D:::error
    G:::error
    J:::error
    P:::error

    classDef error fill:#FFEBEE,stroke:#C62828,stroke-width:2px,color:#B71C1C
```

---

### 2.2 Processo de Obtenção de Resposta por IA

> Fluxo de execução ao receber uma requisição em um endpoint mockado, incluindo fallback estático.

```mermaid
---
config:
  layout: dagre
  theme: neutral
---
flowchart TD
    Start([▶ Início
HTTP request no endpoint mockado])

    A[Identificar pattern e método HTTP
a partir da requisição]
    B{Endpoint
encontrado no
registro dinâmico?}
    C[HTTP 404
Endpoint não encontrado]
    D[Selecionar resposta de sucesso
200 → 201 → 204 → primeira disponível]
    E{Resposta
encontrada?}
    F[HTTP 404
Sem resposta definida]
    G[Extrair status code da resposta]
    H{Schema de
resposta definido?}
    I[Retornar HTTP status
sem corpo]
    J[Resolver schema OpenAPI
expandir refs e tipos]
    K{Schema
resolvido com
sucesso?}
    L[Retornar HTTP status
sem corpo]
    M[Serializar schema resolvido
para JSON via Jackson]
    N[Construir prompt contextual
método · path · summary · description · tags · schema]
    O{API key
configurada?}
    P[Fallback estático
DynamicResponseBodyBuilder]
    Q[Enviar prompt ao Groq
POST api.groq.com/openai/v1/chat/completions]
    R{Groq respondeu
com sucesso?}
    S[Remover delimitadores
markdown da resposta]
    T{Resposta
da IA é JSON
válido?}
    U[Retornar resposta gerada pela IA
HTTP status + corpo JSON
application/json]
    End([⏹ Fim])

    Start --> A --> B
    B -- Não --> C --> End
    B -- Sim --> D --> E
    E -- Não --> F --> End
    E -- Sim --> G --> H
    H -- Não --> I --> End
    H -- Sim --> J --> K
    K -- Não --> L --> End
    K -- Sim --> M --> N --> O
    O -- Não --> P
    Q --> R
    O -- Sim --> Q
    R -- Não
timeout ou erro HTTP --> P
    R -- Sim --> S --> T
    T -- Não
parse falhou --> P
    T -- Sim --> U --> End
    P --> End

    C:::error
    F:::error
    I:::neutral
    L:::neutral
    P:::fallback

    classDef error    fill:#FFEBEE,stroke:#C62828,stroke-width:2px,color:#B71C1C
    classDef neutral  fill:#F5F5F5,stroke:#616161,stroke-width:2px,color:#212121
    classDef fallback fill:#FFF3E0,stroke:#EF6C00,stroke-width:2px,color:#E65100
```

> **Nota sobre fallback:** Quando a IA falha (API key ausente, timeout, erro HTTP ou JSON inválido), o sistema aplica fallback estático via `DynamicResponseBodyBuilder`, que constrói um corpo de resposta baseado diretamente no schema OpenAPI sem chamar o Groq.
