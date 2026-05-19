# Implementation Plan: ai-response-generation

## Overview

Implementação da geração de respostas realistas por IA nos endpoints dinâmicos do MockAI. Cria o port de entrada `GenerateEndpointResponseUseCase`, o serviço `GenerateEndpointResponseService` e modifica o `DynamicEndpointHandler` para delegar a geração do body à IA, com fallback estático em caso de erro.

---

## Task Dependency Graph

```json
{
  "waves": [
    { "wave": 1, "tasks": ["1"] },
    { "wave": 2, "tasks": ["2"] },
    { "wave": 3, "tasks": ["3"] }
  ]
}
```

---

## Tasks

- [x] 1. Criar o port de entrada `GenerateEndpointResponseUseCase`
  - Criar interface `GenerateEndpointResponseUseCase` no pacote `com.ia.para.devs.mockai.domain.port.in`
  - Declarar método `String generate(EndpointDefinitionEntity endpoint)`
  - Sem anotações de framework (Java puro)
  - Adicionar JavaDoc completo em português descrevendo:
    - O contrato do método
    - Que retorna `null` quando não há schema de resposta definido
    - Que lança `AiCommunicationException` em caso de falha na IA
  - _Requirements: 1.1, 1.2, 1.3, 1.4_

- [x] 2. Criar o serviço `GenerateEndpointResponseService`
  - Criar classe `GenerateEndpointResponseService implements GenerateEndpointResponseUseCase` no pacote `com.ia.para.devs.mockai.application.service`
  - Anotar com `@Service`
  - Injetar via construtor: `AiPort`, `DynamicResponseBodyBuilder`, `ObjectMapper`
  - Implementar método `generate(EndpointDefinitionEntity endpoint)`:
    1. Selecionar a `EndpointResponseEntity` de sucesso com prioridade 200 → 201 → 204 → primeira disponível; retornar `null` se nenhuma existir
    2. Obter `responseSchema` da resposta selecionada; retornar `null` se nulo ou vazio
    3. Obter `componentsJson` de `endpoint.getApiSpecification()` (pode ser null)
    4. Chamar `responseBodyBuilder.buildResponseBody(responseSchema, componentsJson)` para resolver o schema; retornar `null` se resultado for null
    5. Serializar o schema resolvido com `objectMapper.writeValueAsString(resolvedSchema)`; lançar `AiCommunicationException` se falhar
    6. Construir o prompt em português com: método HTTP, path, summary, description, tags (nome + descrição), schema serializado e instrução para retornar exclusivamente JSON válido sem texto adicional
    7. Chamar `aiPort.sendPrompt(prompt)`; capturar qualquer exceção e relançar como `AiCommunicationException` com mensagem descritiva
    8. Retornar a resposta da IA, ou `null` se nula/vazia
  - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 4.1, 4.2, 4.3, 4.4_

- [x] 3. Modificar `DynamicEndpointHandler` para integrar geração por IA
  - Adicionar `GenerateEndpointResponseUseCase` como dependência via construtor (manter as dependências existentes)
  - No método `handle(HttpServletRequest request)`, substituir a chamada direta ao `responseBodyBuilder` pela seguinte lógica:
    1. Envolver a chamada `generateEndpointResponseUseCase.generate(endpoint)` em bloco `try`
    2. Se resultado não nulo e não vazio → retornar `ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(aiBody)`
    3. Se resultado `null` (schema ausente) → retornar `ResponseEntity.status(statusCode).build()` sem body
    4. Se qualquer exceção for lançada → executar fallback: chamar `responseBodyBuilder.buildResponseBody(response.getResponseSchema(), componentsJson)` e retornar com `contentType` e `body` estáticos (comportamento atual), ou sem body se resultado do builder for null
  - Manter todos os métodos privados existentes (`selectDefaultResponse`, `parseMediaType`, `parseStatusCode`) sem alteração
  - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5, 5.6_

---

## Notes

- Nenhum teste deve ser criado ou executado durante a implementação desta feature
- Nenhum comando de build, compile ou run deve ser executado
- `DynamicResponseBodyBuilder` é reutilizado em dois contextos: resolução do schema (no service) e fallback estático (no handler)
- A interface `AiPort` e o `AiGateway` já existem e não devem ser modificados
- A exceção `AiCommunicationException` já existe em `com.ia.para.devs.mockai.domain.exception`
- O `ObjectMapper` já é um bean Spring disponível via `JacksonConfig`
