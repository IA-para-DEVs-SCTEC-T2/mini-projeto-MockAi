# Design Document

## Overview

Implementação da geração de respostas realistas por IA nos endpoints dinâmicos do MockAI. O `DynamicEndpointHandler` passa a delegar a geração do corpo de resposta para um novo use case (`GenerateEndpointResponseUseCase`), que orquestra: seleção da resposta de sucesso, resolução do schema, construção do prompt contextual e envio ao `AiGateway`. O fallback para geração estática via `DynamicResponseBodyBuilder` é mantido em caso de erro.

---

## Architecture

Fluxo de dependências respeitando a arquitetura hexagonal:

```
adapter/in/web                    domain/port/in
DynamicEndpointHandler  →  GenerateEndpointResponseUseCase
                                        ↓ implementado por
                            application/service
                            GenerateEndpointResponseService
                                  ↓              ↓
                          domain/port/out    adapter/in/web/dynamic
                            AiPort          DynamicResponseBodyBuilder
                                ↓
                        infrastructure/ai/gateway
                              AiGateway
```

Diagrama de camadas:

```
┌──────────────────────────────────────────────────────────────────┐
│  adapter/in/web/dynamic                                          │
│  DynamicEndpointHandler                                          │
│    depende de: GenerateEndpointResponseUseCase (port/in)         │
│    depende de: DynamicResponseBodyBuilder (fallback)             │
└────────────────────────────┬─────────────────────────────────────┘
                             │
┌────────────────────────────▼─────────────────────────────────────┐
│  domain/port/in                                                  │
│  GenerateEndpointResponseUseCase (interface)                     │
└────────────────────────────┬─────────────────────────────────────┘
                             │ implementado por
┌────────────────────────────▼─────────────────────────────────────┐
│  application/service                                             │
│  GenerateEndpointResponseService                                 │
│    depende de: AiPort (port/out)                                 │
│    depende de: DynamicResponseBodyBuilder (resolução de schema)  │
│    depende de: ObjectMapper (serialização)                       │
└────────────────────────────┬─────────────────────────────────────┘
                             │
┌────────────────────────────▼─────────────────────────────────────┐
│  domain/port/out                                                 │
│  AiPort (interface — já existente)                               │
└────────────────────────────┬─────────────────────────────────────┘
                             │ implementado por
┌────────────────────────────▼─────────────────────────────────────┐
│  infrastructure/ai/gateway                                       │
│  AiGateway (já existente)                                        │
└──────────────────────────────────────────────────────────────────┘
```

---

## Fluxo de Execução

```
DynamicEndpointHandler.handle(request)
  │
  ├─ resolve endpoint + response (lógica existente)
  │
  ├─ tenta: generateEndpointResponseUseCase.generate(endpoint)
  │     │
  │     ├─ responseSchema nulo/vazio → retorna null
  │     │
  │     ├─ resolve schema via DynamicResponseBodyBuilder → serializa como JSON
  │     ├─ constrói prompt com schema + contexto do endpoint + tags
  │     ├─ aiPort.sendPrompt(prompt) → retorna String JSON da IA
  │     └─ retorna String (ou null se resposta vazia)
  │
  ├─ resultado não nulo → ResponseEntity com body = String da IA (application/json)
  ├─ resultado null (schema ausente) → ResponseEntity sem body, só status
  └─ exceção (AiCommunicationException ou outra) → fallback: DynamicResponseBodyBuilder
```

---

## Components and Interfaces

### 1. `domain/port/in/GenerateEndpointResponseUseCase`

Port de entrada que define o contrato do caso de uso de geração de resposta por IA.

**Pacote:** `com.ia.para.devs.mockai.domain.port.in`

```java
public interface GenerateEndpointResponseUseCase {

    /**
     * Gera o corpo de resposta de um endpoint mockado utilizando IA.
     *
     * <p>Retorna {@code null} quando o endpoint não possui schema de resposta definido,
     * indicando que o handler deve retornar apenas o status HTTP sem corpo.</p>
     *
     * @param endpoint entidade do endpoint cujo corpo de resposta será gerado
     * @return JSON gerado pela IA como String, ou {@code null} se não houver schema
     * @throws AiCommunicationException se ocorrer falha na comunicação com o serviço de IA
     */
    String generate(EndpointDefinitionEntity endpoint);
}
```

**Observação:** A interface recebe `EndpointDefinitionEntity` diretamente pois o handler já possui essa entidade em memória (carregada no registro de rotas). Não há consulta adicional ao banco.

---

### 2. `application/service/GenerateEndpointResponseService`

Implementação do `GenerateEndpointResponseUseCase`. Orquestra a resolução do schema, construção do prompt e envio à IA.

**Pacote:** `com.ia.para.devs.mockai.application.service`

```java
@Service
public class GenerateEndpointResponseService implements GenerateEndpointResponseUseCase {

    private final AiPort aiPort;
    private final DynamicResponseBodyBuilder responseBodyBuilder;
    private final ObjectMapper objectMapper;

    public GenerateEndpointResponseService(
            AiPort aiPort,
            DynamicResponseBodyBuilder responseBodyBuilder,
            ObjectMapper objectMapper) {
        this.aiPort = aiPort;
        this.responseBodyBuilder = responseBodyBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public String generate(EndpointDefinitionEntity endpoint) {
        EndpointResponseEntity selectedResponse = selectSuccessResponse(endpoint);
        if (selectedResponse == null) {
            return null;
        }

        String responseSchema = selectedResponse.getResponseSchema();
        if (responseSchema == null || responseSchema.isBlank()) {
            return null;
        }

        String componentsJson = endpoint.getApiSpecification() != null
                ? endpoint.getApiSpecification().getComponentsJson()
                : null;

        Object resolvedSchema = responseBodyBuilder.buildResponseBody(responseSchema, componentsJson);
        if (resolvedSchema == null) {
            return null;
        }

        String schemaJson;
        try {
            schemaJson = objectMapper.writeValueAsString(resolvedSchema);
        } catch (Exception ex) {
            throw new AiCommunicationException("Erro ao serializar o schema para envio à IA", ex);
        }

        String prompt = buildPrompt(endpoint, schemaJson);

        try {
            String aiResponse = aiPort.sendPrompt(prompt);
            return (aiResponse == null || aiResponse.isBlank()) ? null : aiResponse;
        } catch (Exception ex) {
            throw new AiCommunicationException("Erro ao gerar resposta via IA para o endpoint: " + endpoint.getPath(), ex);
        }
    }

    private EndpointResponseEntity selectSuccessResponse(EndpointDefinitionEntity endpoint) {
        if (endpoint.getResponses() == null || endpoint.getResponses().isEmpty()) {
            return null;
        }
        return endpoint.getResponses().stream()
                .filter(r -> "200".equals(r.getStatusCode()))
                .findFirst()
                .or(() -> endpoint.getResponses().stream()
                        .filter(r -> "201".equals(r.getStatusCode()))
                        .findFirst())
                .or(() -> endpoint.getResponses().stream()
                        .filter(r -> "204".equals(r.getStatusCode()))
                        .findFirst())
                .orElseGet(() -> endpoint.getResponses().iterator().next());
    }

    private String buildPrompt(EndpointDefinitionEntity endpoint, String schemaJson) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você é um gerador de dados de teste para APIs REST.\n\n");
        prompt.append("Gere uma resposta JSON realista e coerente para o seguinte endpoint:\n\n");
        prompt.append("Método HTTP: ").append(endpoint.getHttpMethod()).append("\n");
        prompt.append("Caminho: ").append(endpoint.getPath()).append("\n");

        if (endpoint.getSummary() != null && !endpoint.getSummary().isBlank()) {
            prompt.append("Resumo: ").append(endpoint.getSummary()).append("\n");
        }
        if (endpoint.getDescription() != null && !endpoint.getDescription().isBlank()) {
            prompt.append("Descrição: ").append(endpoint.getDescription()).append("\n");
        }

        if (endpoint.getTags() != null && !endpoint.getTags().isEmpty()) {
            prompt.append("\nTags do endpoint:\n");
            for (TagEntity tag : endpoint.getTags()) {
                prompt.append("- ").append(tag.getName());
                if (tag.getDescription() != null && !tag.getDescription().isBlank()) {
                    prompt.append(": ").append(tag.getDescription());
                }
                prompt.append("\n");
            }
        }

        prompt.append("\nEstrutura esperada da resposta (schema resolvido):\n");
        prompt.append(schemaJson).append("\n\n");
        prompt.append("Instruções:\n");
        prompt.append("- Retorne EXCLUSIVAMENTE um JSON válido, sem texto adicional, sem markdown, sem explicações.\n");
        prompt.append("- Preencha os campos com valores realistas e coerentes com o contexto do endpoint.\n");
        prompt.append("- Respeite os tipos de dados definidos no schema.\n");
        prompt.append("- Não inclua campos extras além dos definidos no schema.\n");

        return prompt.toString();
    }
}
```

---

### 3. `adapter/in/web/dynamic/DynamicEndpointHandler` — modificação

O handler existente é modificado para injetar `GenerateEndpointResponseUseCase` e aplicar a lógica de três caminhos.

**Pacote:** `com.ia.para.devs.mockai.adapter.in.web.dynamic` (já existente — modificar)

```java
@Component
public class DynamicEndpointHandler {

    private final SpringWebDynamicRouteRegistry routeRegistry;
    private final DynamicResponseBodyBuilder responseBodyBuilder;
    private final GenerateEndpointResponseUseCase generateEndpointResponseUseCase;

    public DynamicEndpointHandler(
            SpringWebDynamicRouteRegistry routeRegistry,
            DynamicResponseBodyBuilder responseBodyBuilder,
            GenerateEndpointResponseUseCase generateEndpointResponseUseCase) {
        this.routeRegistry = routeRegistry;
        this.responseBodyBuilder = responseBodyBuilder;
        this.generateEndpointResponseUseCase = generateEndpointResponseUseCase;
    }

    public ResponseEntity<Object> handle(HttpServletRequest request) {
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String httpMethod = request.getMethod();

        EndpointDefinitionEntity endpoint = routeRegistry.getEndpointDefinition(pattern, httpMethod);
        if (endpoint == null) {
            return ResponseEntity.notFound().build();
        }

        EndpointResponseEntity response = selectDefaultResponse(endpoint);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        int statusCode = parseStatusCode(response.getStatusCode());
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(statusCode);

        try {
            String aiBody = generateEndpointResponseUseCase.generate(endpoint);

            if (aiBody == null) {
                // schema ausente → retorna só o status, sem body
                return builder.build();
            }

            // IA gerou resposta → usa como body
            return builder.contentType(MediaType.APPLICATION_JSON).body(aiBody);

        } catch (Exception ex) {
            // fallback: geração estática via DynamicResponseBodyBuilder
            String componentsJson = endpoint.getApiSpecification() != null
                    ? endpoint.getApiSpecification().getComponentsJson()
                    : null;
            Object staticBody = responseBodyBuilder.buildResponseBody(response.getResponseSchema(), componentsJson);
            MediaType mediaType = parseMediaType(response.getContentType());

            if (staticBody == null) {
                return builder.build();
            }
            return builder.contentType(mediaType).body(staticBody);
        }
    }

    // ... métodos privados existentes mantidos
}
```

**Lógica de três caminhos:**

| Resultado de `generate()` | Comportamento |
|---|---|
| String não nula/vazia | Retorna body da IA com `Content-Type: application/json` |
| `null` (schema ausente) | Retorna só o status HTTP, sem body |
| Exceção lançada | Fallback: `DynamicResponseBodyBuilder` com body estático |

---

## Data Models

Nenhuma entidade JPA nova é necessária. A feature utiliza exclusivamente as entidades já existentes:
- `EndpointDefinitionEntity` — path, httpMethod, summary, description, tags, responses, apiSpecification
- `EndpointResponseEntity` — statusCode, contentType, responseSchema
- `ApiSpecificationEntity` — componentsJson
- `TagEntity` — name, description

---

## File Structure

```
src/main/java/com/ia/para/devs/mockai/
├── domain/
│   └── port/
│       └── in/
│           └── GenerateEndpointResponseUseCase.java          ← criar
├── application/
│   └── service/
│       └── GenerateEndpointResponseService.java              ← criar
└── adapter/
    └── in/
        └── web/
            └── dynamic/
                └── DynamicEndpointHandler.java               ← modificar
```

---

## Error Handling

| Situação | Comportamento |
|---|---|
| `responseSchema` nulo/vazio | `generate()` retorna `null` → handler retorna só status |
| `EndpointResponseEntity` ausente | `generate()` retorna `null` → handler retorna só status |
| Falha na serialização do schema | `generate()` lança `AiCommunicationException` → handler usa fallback estático |
| `AiPort.sendPrompt` lança exceção | `generate()` relança como `AiCommunicationException` → handler usa fallback estático |
| Resposta da IA nula/vazia | `generate()` retorna `null` → handler retorna só status |
| Qualquer outra exceção em `generate()` | Handler captura no bloco `catch (Exception)` → fallback estático |

O `DynamicResponseBodyBuilder` existente é preservado integralmente — continua sendo usado tanto na resolução do schema (para construir o prompt) quanto no fallback estático do handler.
