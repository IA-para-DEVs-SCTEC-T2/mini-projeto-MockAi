# Requirements Document

## Introduction

Criação de um cliente HTTP configurado para integração com a API do ChatGPT (OpenAI), utilizando a biblioteca `spring-ai-starter-model-openai`. O cliente deve encapsular a configuração de autenticação via API Key, lida a partir de variáveis de ambiente, e expor um método padrão de envio de solicitações à IA. Nenhuma lógica de negócio adicional deve ser implementada nesta demanda — apenas a criação e configuração do cliente.

A implementação segue a Clean Architecture / Hexagonal Architecture do projeto: o contrato do cliente é definido como um port no domínio, e a implementação concreta reside na camada de infraestrutura como um adapter de saída.

---

## Glossary

- **ChatGPT_Client**: Componente responsável por encapsular a comunicação com a API do ChatGPT (OpenAI), expondo métodos para envio de solicitações à IA.
- **OpenAI_API**: Serviço externo da OpenAI que fornece modelos de linguagem via HTTP, autenticado por API Key.
- **API_Key**: Token de autenticação fornecido pela OpenAI, utilizado no header `Authorization` das requisições HTTP.
- **AI_Port**: Interface (port de saída) definida na camada de domínio que estabelece o contrato de comunicação com serviços de IA externos.
- **AI_Gateway**: Implementação concreta do `AI_Port` na camada de infraestrutura, responsável por realizar as chamadas à OpenAI_API via `spring-ai-starter-model-openai`.
- **AiCommunicationException**: Exceção de domínio lançada pelo `AI_Gateway` quando ocorre falha na comunicação com a OpenAI_API (erros HTTP, timeout ou autenticação inválida).
- **AiConnectionController**: Controller REST responsável por expor o endpoint de verificação de conectividade com a IA.
- **CheckAiConnectionUseCase**: Interface (port de entrada) definida na camada de domínio que representa o caso de uso de verificação de conectividade com a IA.
- **CheckAiConnectionService**: Implementação do `CheckAiConnectionUseCase` na camada de aplicação, responsável por orquestrar a verificação via `AI_Port`.
- **Environment_Variable**: Variável de ambiente do sistema operacional ou arquivo `.env` utilizada para armazenar configurações sensíveis fora do código-fonte.
- **Spring_AI**: Biblioteca `spring-ai-starter-model-openai` que abstrai a integração com modelos OpenAI no ecossistema Spring Boot.
- **Prompt**: Texto de entrada enviado ao modelo de linguagem da OpenAI para geração de resposta.
- **AI_Response**: Objeto de retorno contendo o texto gerado pelo modelo de linguagem da OpenAI.

---

## Restrições de Implementação

- **É estritamente proibido executar qualquer comando de terminal durante a implementação desta feature.** Toda a implementação deve ser realizada exclusivamente por meio de criação e edição de arquivos de código-fonte, configuração e documentação.
- **É estritamente proibido criar qualquer tipo de teste** (unitário, integração, e2e ou outro) para esta implementação.
- **É estritamente proibido executar qualquer tipo de teste** durante a implementação ou execução das tasks desta feature.

---

## Requirements

### Requirement 1: Dependência Maven do Spring AI

**User Story:** Como desenvolvedor, quero adicionar a dependência `spring-ai-starter-model-openai` ao projeto, para que o Spring AI gerencie automaticamente a configuração do cliente OpenAI.

#### Acceptance Criteria

1. THE `pom.xml` SHALL declarar o `spring-ai-bom` em `<dependencyManagement>` com versão compatível com Spring Boot 4.0.6, garantindo gerenciamento centralizado das versões do Spring AI.
2. THE `pom.xml` SHALL declarar a dependência `org.springframework.ai:spring-ai-starter-model-openai` sem especificar versão diretamente, delegando o controle de versão ao BOM declarado.
3. WHEN o projeto é compilado com `mvn clean compile`, THE Maven_Build SHALL produzir `BUILD SUCCESS` na saída do console sem erros de resolução de dependência.

---

### Requirement 2: Configuração da API Key via Variável de Ambiente

**User Story:** Como operador do sistema, quero que a API Key da OpenAI seja lida de uma variável de ambiente, para que credenciais não sejam expostas no código-fonte ou versionadas no repositório.

#### Acceptance Criteria

1. WHEN a aplicação é iniciada com a variável de ambiente `OPENAI_API_KEY` definida, THE MockAI_Application SHALL ler o valor dessa variável e utilizá-lo como API Key para autenticação com a OpenAI_API.
2. THE `application.properties` SHALL mapear a propriedade `spring.ai.openai.api-key` para a variável de ambiente `OPENAI_API_KEY` usando a sintaxe `${OPENAI_API_KEY}`.
3. IF a variável de ambiente `OPENAI_API_KEY` não estiver definida no ambiente de execução, THEN THE MockAI_Application SHALL falhar na inicialização com mensagem de erro indicando que a variável obrigatória não foi configurada.

---

### Requirement 3: Definição do Contrato do Cliente de IA (Port de Saída)

**User Story:** Como desenvolvedor, quero uma interface que defina o contrato de comunicação com serviços de IA, para que a camada de domínio permaneça independente de frameworks e implementações externas.

#### Acceptance Criteria

1. THE AI_Port SHALL ser definido como uma interface Java no pacote `com.ia.para.devs.mockai.domain.port`.
2. THE AI_Port SHALL declarar o método `sendPrompt(String prompt)` que retorna `String` não nula com a resposta gerada pela IA.
3. THE AI_Port SHALL ser livre de dependências de frameworks externos (sem anotações Spring, Spring AI ou Jakarta).
4. IF o parâmetro `prompt` fornecido ao método `sendPrompt` for nulo, vazio (`""`) ou contiver apenas caracteres whitespace, THEN THE AI_Gateway SHALL lançar `IllegalArgumentException` com mensagem em português indicando que o prompt não pode ser nulo ou vazio.

---

### Requirement 4: Implementação do Gateway de IA (Adapter de Saída)

**User Story:** Como desenvolvedor, quero uma implementação concreta do contrato de IA que utilize o Spring AI para comunicação com a OpenAI, para que a integração seja gerenciada pelo framework sem acoplamento no domínio.

#### Acceptance Criteria

1. THE AI_Gateway SHALL implementar a interface `AI_Port` no pacote `com.ia.para.devs.mockai.infrastructure.gateway`.
2. THE AI_Gateway SHALL utilizar o `ChatClient` do Spring AI (provido por `spring-ai-starter-model-openai`) para enviar o `Prompt` à OpenAI_API.
3. WHEN o método `sendPrompt` é invocado com um `prompt` válido, THE AI_Gateway SHALL retornar uma `String` não nula e não vazia contendo o texto da primeira escolha de resposta gerada pelo modelo.
4. IF a OpenAI_API retornar um erro HTTP (4xx ou 5xx), THEN THE AI_Gateway SHALL lançar `AiCommunicationException` com mensagem descritiva em português que não contenha nomes de classes internas do framework.
5. IF a OpenAI_API não responder dentro de 30 segundos (configurável via `spring.ai.openai.chat.options.timeout`), THEN THE AI_Gateway SHALL lançar `AiCommunicationException` com mensagem em português indicando timeout na comunicação com o serviço de IA.
6. THE AI_Gateway SHALL ser registrado como um bean Spring (`@Component`) para permitir injeção de dependência via construtor.
7. IF o AI_Gateway receber uma exceção não coberta pelos critérios 4 e 5, THEN THE AI_Gateway SHALL capturar a exceção e lançar `AiCommunicationException` com mensagem genérica em português, sem propagar a exceção original não tratada.

---

### Requirement 5: Configuração do Modelo e Parâmetros do Spring AI

**User Story:** Como desenvolvedor, quero que o modelo e os parâmetros de comunicação com a OpenAI sejam configuráveis via `application.properties`, para que ajustes possam ser feitos sem alteração de código.

#### Acceptance Criteria

1. WHEN a aplicação é iniciada, THE `application.properties` SHALL definir a propriedade `spring.ai.openai.chat.options.model` com o valor `gpt-4o-mini` como modelo padrão.
2. WHEN a propriedade `spring.ai.openai.chat.options.temperature` estiver configurada com um valor entre `0.0` e `2.0` (inclusive), THE Spring_AI SHALL utilizar esse valor como temperatura de geração das respostas.
3. WHEN uma variável de ambiente correspondente a uma propriedade Spring AI (ex.: `SPRING_AI_OPENAI_CHAT_OPTIONS_MODEL`) estiver definida no ambiente de execução, IF o valor for válido para a propriedade, THEN THE MockAI_Application SHALL utilizar o valor da variável de ambiente em vez do valor definido em `application.properties`.
4. IF a propriedade `spring.ai.openai.chat.options.temperature` for configurada com um valor fora do intervalo `0.0`–`2.0`, THEN THE MockAI_Application SHALL falhar na inicialização ou lançar exceção com mensagem indicando que o valor de temperatura é inválido.

---

### Requirement 6: Proteção de Credenciais no Repositório

**User Story:** Como membro do time, quero garantir que nenhuma credencial ou arquivo `.env` seja versionado no repositório Git, para que a segurança das contas não seja comprometida.

#### Acceptance Criteria

1. THE `.gitignore` SHALL conter entradas para `.env`, `.env.*`, `**/.env` e `**/.env.*` para cobrir arquivos de ambiente na raiz e em subdiretórios.
2. THE repositório SHALL fornecer um arquivo `.env.example` com as variáveis de ambiente necessárias listadas no formato `NOME=` ou `NOME=<descrição>`, sem valores reais, servindo como documentação para novos membros do time.
3. THE `.env.example` SHALL conter ao menos a entrada `OPENAI_API_KEY=` como referência obrigatória de configuração.
4. IF um arquivo `.env` já rastreado pelo Git for adicionado ao `.gitignore`, THEN THE repositório SHALL garantir que o arquivo seja removido do índice Git (`git rm --cached`) para que não seja mais versionado.

---

### Requirement 7: Validação de Conectividade e Autenticação

**User Story:** Como desenvolvedor, quero poder validar que o cliente está corretamente configurado e consegue se comunicar com a API da OpenAI, para que problemas de configuração sejam detectados antes da integração com a lógica de negócio.

#### Acceptance Criteria

1. WHEN o método `sendPrompt` é invocado com um `prompt` não nulo e não vazio em ambiente com `OPENAI_API_KEY` válida configurada, THE AI_Gateway SHALL retornar uma `String` contendo ao menos 1 caractere não-whitespace proveniente da OpenAI_API.
2. IF a `OPENAI_API_KEY` configurada for inválida ou expirada (HTTP 401 retornado pela OpenAI_API), THEN THE AI_Gateway SHALL lançar `AiCommunicationException` com mensagem em português indicando falha de autenticação com o serviço de IA.
3. IF a variável de ambiente `OPENAI_API_KEY` não estiver definida no momento da invocação do método `sendPrompt`, THEN THE MockAI_Application SHALL ter falhado na inicialização antes de qualquer invocação, conforme Requirement 2 criterion 3.

---

### Requirement 8: Endpoint de Verificação de Conectividade com a IA

**User Story:** Como desenvolvedor, quero um endpoint HTTP que verifique se a integração e a conexão com a IA estão funcionais, para que eu possa validar a configuração do ambiente sem precisar acionar a lógica de negócio.

#### Acceptance Criteria

1. THE AiConnectionController SHALL expor o endpoint `GET /test-ai-connection` no pacote `com.ia.para.devs.mockai.api.controller`.
2. THE CheckAiConnectionUseCase SHALL ser definido como uma interface no pacote `com.ia.para.devs.mockai.domain.port`, declarando o método `checkConnection()` que retorna `boolean`.
3. THE CheckAiConnectionService SHALL implementar o `CheckAiConnectionUseCase` no pacote `com.ia.para.devs.mockai.application.usecase`, dependendo exclusivamente do `AI_Port` (interface de domínio) via injeção de construtor.
4. WHEN o método `checkConnection()` é invocado, THE CheckAiConnectionService SHALL invocar o `AI_Port` com um prompt de teste fixo e retornar `true` se a resposta for recebida com sucesso.
5. IF o `AI_Port` lançar qualquer exceção durante a verificação, THEN THE CheckAiConnectionService SHALL retornar `false` sem propagar a exceção.
6. WHEN o endpoint `GET /test-ai-connection` é chamado e o `CheckAiConnectionUseCase` retornar `true`, THE AiConnectionController SHALL retornar HTTP 200 com corpo em português indicando que a conexão está funcional.
7. WHEN o endpoint `GET /test-ai-connection` é chamado e o `CheckAiConnectionUseCase` retornar `false`, THE AiConnectionController SHALL retornar HTTP 503 com corpo em português indicando que a conexão com o serviço de IA está indisponível.
8. THE AiConnectionController SHALL depender exclusivamente do `CheckAiConnectionUseCase` (interface), sem referência direta ao `CheckAiConnectionService`, ao `AI_Gateway` ou a qualquer classe do Spring AI.
