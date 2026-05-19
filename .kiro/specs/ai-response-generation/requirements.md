# Requirements Document

## Introduction

Implementação da geração de respostas realistas por IA nos endpoints dinâmicos do MockAI (issue #23). Atualmente, o `DynamicResponseBodyBuilder` gera payloads de exemplo estáticos a partir do schema OpenAPI. Esta feature substitui esse comportamento por respostas geradas dinamicamente pelo Groq via `AiGateway`, utilizando o schema do endpoint e as descrições contextuais (endpoint, tags) para produzir um JSON realista de sucesso.

A implementação segue a Clean Architecture / Hexagonal Architecture do projeto: o caso de uso é definido como port de entrada no domínio, a lógica de orquestração reside na camada de aplicação, e o `DynamicEndpointHandler` (adapter de entrada) delega para o use case.

---

## Glossário

- **AI_Response_Generation**: Feature responsável por gerar o corpo de resposta dos endpoints mockados usando IA, em substituição à geração estática de exemplos.
- **EndpointContext**: Conjunto de informações contextuais de um endpoint (path, método HTTP, summary, description, tags) usadas para compor o prompt enviado à IA.
- **ResponseSchema**: JSON do schema de resposta persistido em `EndpointResponseEntity.responseSchema`, referenciando tipos definidos em `ApiSpecificationEntity.componentsJson`.
- **ResolvedSchema**: Schema de resposta com todos os `$ref` resolvidos usando o bloco `componentsJson`, representando a estrutura real do objeto de retorno.
- **AiResponsePrompt**: Prompt construído com o `ResolvedSchema` e o `EndpointContext`, enviado ao `AiGateway` para geração de resposta realista.
- **GenerateEndpointResponseUseCase**: Port de entrada (interface) que define o contrato do caso de uso de geração de resposta por IA.
- **GenerateEndpointResponseService**: Implementação do `GenerateEndpointResponseUseCase` na camada de aplicação.

---

## Restrições de Implementação

- **É estritamente proibido criar ou executar qualquer tipo de teste** (unitário, integração, e2e ou outro) nesta implementação.
- **É estritamente proibido executar comandos de build, compile ou run** para verificar a implementação.
- Toda a implementação deve ser realizada exclusivamente por meio de criação e edição de arquivos de código-fonte.
- Considerar apenas casos de sucesso: se a IA falhar, o comportamento de fallback é retornar o body estático gerado pelo `DynamicResponseBodyBuilder` existente.

---

## Requirements

### Requirement 1: Port de Entrada para Geração de Resposta por IA

**User Story:** Como desenvolvedor, quero uma interface que defina o contrato do caso de uso de geração de resposta por IA, para que a camada de adapter não dependa diretamente da implementação.

#### Acceptance Criteria

1. THE `GenerateEndpointResponseUseCase` SHALL ser definido como uma interface Java no pacote `com.ia.para.devs.mockai.domain.port.in`.
2. THE interface SHALL declarar o método `String generate(EndpointDefinitionEntity endpoint)` que retorna o JSON gerado pela IA.
3. THE interface SHALL ser livre de dependências de frameworks externos (sem anotações Spring, JPA ou Jakarta).
4. THE interface SHALL ter JavaDoc completo em português descrevendo o contrato.

---

### Requirement 2: Resolução do Schema de Resposta

**User Story:** Como desenvolvedor, quero que o schema de resposta do endpoint seja resolvido com os `$ref` expandidos antes de ser enviado à IA, para que o modelo receba a estrutura completa do objeto esperado.

#### Acceptance Criteria

1. WHEN o caso de uso é invocado com um `EndpointDefinitionEntity`, THE `GenerateEndpointResponseService` SHALL selecionar a resposta de sucesso do endpoint usando a mesma lógica de prioridade já existente no `DynamicEndpointHandler` (200 → 201 → 204 → primeira disponível).
2. THE service SHALL obter o `responseSchema` da `EndpointResponseEntity` selecionada.
3. THE service SHALL obter o `componentsJson` da `ApiSpecificationEntity` associada ao endpoint.
4. IF `responseSchema` for nulo ou vazio, THEN THE `GenerateEndpointResponseUseCase` SHALL retornar um resultado indicando ausência de schema, e THE `DynamicEndpointHandler` SHALL retornar apenas o status de sucesso sem corpo na resposta HTTP.
5. THE service SHALL utilizar o `DynamicResponseBodyBuilder` existente para resolver o schema (expandir `$ref`) e obter a estrutura do objeto de retorno como `Object`.

---

### Requirement 3: Construção do Prompt para a IA

**User Story:** Como desenvolvedor, quero que o prompt enviado à IA contenha o schema resolvido e o contexto descritivo do endpoint, para que a resposta gerada seja realista e coerente com o domínio da API mockada.

#### Acceptance Criteria

1. THE prompt SHALL incluir o schema resolvido serializado como JSON (estrutura do objeto de retorno).
2. THE prompt SHALL incluir o path e o método HTTP do endpoint.
3. THE prompt SHALL incluir o `summary` e a `description` do endpoint, quando disponíveis.
4. THE prompt SHALL incluir os nomes e descrições das tags associadas ao endpoint, quando disponíveis.
5. THE prompt SHALL instruir a IA a retornar **exclusivamente** um JSON válido, sem texto adicional, markdown ou explicações.
6. THE prompt SHALL estar em português, descrevendo claramente o contexto e a instrução para a IA.

---

### Requirement 4: Envio do Prompt e Processamento da Resposta

**User Story:** Como desenvolvedor, quero que o prompt seja enviado ao `AiGateway` e a resposta seja retornada como string JSON, para que o endpoint dinâmico possa utilizá-la como corpo de resposta.

#### Acceptance Criteria

1. THE `GenerateEndpointResponseService` SHALL injetar `AiPort` via construtor (sem referência direta ao `AiGateway`).
2. WHEN o prompt é enviado via `AiPort.sendPrompt(prompt)`, THE service SHALL retornar a string retornada pelo gateway diretamente como resultado do caso de uso.
3. IF `AiPort.sendPrompt` lançar qualquer exceção, THEN THE service SHALL capturar a exceção e lançar uma exceção de domínio (`AiCommunicationException`) para que o handler possa aplicar o fallback adequado.
4. IF o resultado retornado pelo `AiPort` for nulo ou vazio, THEN THE service SHALL retornar `null`.

---

### Requirement 5: Integração no DynamicEndpointHandler

**User Story:** Como usuário do MockAI, quero que ao acessar um endpoint mockado a resposta seja gerada pela IA com dados realistas, para que a simulação seja mais fiel à API real.

#### Acceptance Criteria

1. THE `DynamicEndpointHandler` SHALL injetar `GenerateEndpointResponseUseCase` via construtor.
2. WHEN o handler processa uma requisição, THE handler SHALL invocar `GenerateEndpointResponseUseCase.generate(endpoint)` para obter o corpo de resposta gerado pela IA.
3. IF o resultado do use case for não nulo e não vazio, THEN THE handler SHALL utilizar a string retornada como corpo da resposta HTTP, com `Content-Type: application/json`.
4. IF o use case lançar `AiCommunicationException` ou qualquer outra exceção (fallback por erro de IA), THEN THE handler SHALL utilizar o `DynamicResponseBodyBuilder` existente para gerar o corpo estático, mantendo o comportamento atual.
5. IF o resultado do use case for `null` (schema ausente), THEN THE handler SHALL retornar apenas o status de sucesso sem corpo na resposta HTTP, sem acionar o `DynamicResponseBodyBuilder`.
6. THE `DynamicEndpointHandler` SHALL manter todas as suas responsabilidades atuais (seleção de status code, content type, tratamento de endpoint não encontrado).
