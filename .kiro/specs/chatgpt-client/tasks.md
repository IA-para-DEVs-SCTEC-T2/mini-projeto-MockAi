# Implementation Plan

## Overview

Implementação do cliente HTTP para integração com a API do ChatGPT (OpenAI) via `spring-ai-starter-model-openai`, seguindo a arquitetura hexagonal do projeto. As tasks cobrem desde a configuração de dependências Maven até a exposição do endpoint de verificação de conectividade.

## Task Dependency Graph

```json
{
  "waves": [
    { "wave": 1, "tasks": [1, 2, 3, 4, 10] },
    { "wave": 2, "tasks": [5] },
    { "wave": 3, "tasks": [6] },
    { "wave": 4, "tasks": [7, 8] },
    { "wave": 5, "tasks": [9] }
  ]
}
```

## Tasks

- [x] 1. Adicionar dependências do Spring AI ao `pom.xml`
  - Declarar `spring-ai-bom` versão `2.0.0` em `<dependencyManagement>`
  - Declarar `spring-ai-starter-model-openai` em `<dependencies>` sem versão explícita
  - _Requirement: 1_

- [x] 2. Criar a exceção de domínio `AiCommunicationException`
  - Criar classe `AiCommunicationException extends RuntimeException` no pacote `com.ia.para.devs.mockai.domain.exception`
  - Implementar construtor com `String message` e construtor com `String message, Throwable cause`
  - Sem anotações de framework
  - _Requirement: 4_

- [x] 3. Criar o port de saída `AiPort`
  - Criar interface `AiPort` no pacote `com.ia.para.devs.mockai.application.port.out`
  - Declarar método `String sendPrompt(String prompt)`
  - Sem dependências de frameworks externos
  - Adicionar JavaDoc em português
  - _Requirement: 3_

- [x] 4. Criar o port de entrada `CheckAiConnectionUseCase`
  - Criar interface `CheckAiConnectionUseCase` no pacote `com.ia.para.devs.mockai.application.port.in`
  - Declarar método `boolean checkConnection()`
  - Sem dependências de frameworks externos
  - Adicionar JavaDoc em português
  - _Requirement: 8_

- [x] 5. Configurar `application.properties` com propriedades do Spring AI
  - Adicionar `spring.ai.openai.api-key=${OPENAI_API_KEY:}`
  - Adicionar `spring.ai.openai.chat.options.model=gpt-4o-mini`
  - Adicionar `spring.ai.openai.chat.options.temperature=0.7`
  - _Requirement: 2, 5_

- [x] 6. Criar o validador de startup `OpenAiApiKeyValidator`
  - Criar classe `OpenAiApiKeyValidator` no pacote `com.ia.para.devs.mockai.infrastructure.ai.config`
  - Anotar com `@Component`
  - Injetar `spring.ai.openai.api-key` via `@Value("${spring.ai.openai.api-key:}")` no construtor
  - Implementar método `@PostConstruct validate()` que lança `IllegalStateException` com mensagem em português se a chave estiver ausente ou vazia
  - _Requirement: 2_

- [x] 7. Criar o gateway `AiGateway`
  - Criar classe `AiGateway` no pacote `com.ia.para.devs.mockai.infrastructure.ai.gateway`
  - Anotar com `@Component`
  - Implementar interface `AiPort`
  - Injetar `ChatClient.Builder` e `@Value("${spring.ai.openai.api-key:}")` via construtor; construir `ChatClient` a partir do builder
  - No método `sendPrompt`: validar prompt nulo/vazio/whitespace lançando `IllegalArgumentException` com mensagem em português
  - No método `sendPrompt`: validar `apiKey` ausente/vazia lançando `AiCommunicationException` com mensagem em português
  - Chamar `chatClient.prompt().user(prompt).call().content()` e retornar o resultado
  - Capturar erros HTTP 401 → `AiCommunicationException` indicando falha de autenticação
  - Capturar erros HTTP 4xx/5xx → `AiCommunicationException` com mensagem descritiva em português
  - Capturar timeout → `AiCommunicationException` indicando timeout em português
  - Capturar qualquer outra exceção → `AiCommunicationException` genérica em português (fallback)
  - _Requirement: 4, 7_

- [x] 8. Criar o serviço `CheckAiConnectionService`
  - Criar classe `CheckAiConnectionService` no pacote `com.ia.para.devs.mockai.application.service`
  - Anotar com `@Service`
  - Implementar interface `CheckAiConnectionUseCase`
  - Injetar `AiPort` via construtor
  - Definir constante `TEST_PROMPT = "ping"`
  - No método `checkConnection()`: invocar `aiPort.sendPrompt(TEST_PROMPT)`, retornar `true` se resposta não nula e não vazia, retornar `false` em qualquer exceção
  - _Requirement: 8_

- [x] 9. Criar o controller `AiConnectionController`
  - Criar classe `AiConnectionController` no pacote `com.ia.para.devs.mockai.adapter.in.web`
  - Anotar com `@RestController`
  - Injetar `CheckAiConnectionUseCase` via construtor (sem referência a `CheckAiConnectionService`, `AiGateway` ou classes do Spring AI)
  - Implementar `GET /test-ai-connection`: retornar HTTP 200 com mensagem em português se `checkConnection()` retornar `true`; retornar HTTP 503 com mensagem em português se retornar `false`
  - _Requirement: 8_

- [x] 10. Proteger credenciais no repositório
  - Adicionar entradas `.env`, `.env.*`, `**/.env`, `**/.env.*` ao `.gitignore`
  - Criar arquivo `.env.example` na raiz do projeto com a entrada `OPENAI_API_KEY=<sua-chave-da-api-openai>`
  - _Requirement: 6_

## Notes

- Tasks 2, 3 e 4 são independentes entre si e podem ser executadas em paralelo
- Task 7 depende de 1, 2, 3, 5 e 6 estar concluídas
- Task 9 depende de 4 e 8 estar concluídas
- Task 10 é independente e pode ser executada a qualquer momento
- Nenhum teste deve ser criado ou executado durante a implementação desta feature (restrição do requirements)
- Toda implementação deve ser feita exclusivamente via criação/edição de arquivos, sem execução de comandos de terminal
