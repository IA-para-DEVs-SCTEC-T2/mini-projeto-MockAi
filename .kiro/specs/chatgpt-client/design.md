# Design Document

## Overview

Implementação de um client de integração com a API do ChatGPT (OpenAI) utilizando `spring-ai-starter-model-openai`, seguindo a arquitetura hexagonal já adotada no projeto. A feature adiciona um port de saída no domínio, um gateway na infraestrutura, um use case de verificação de conectividade na camada de aplicação e um controller REST na camada de adapter.

---

## Architecture

O fluxo respeita estritamente as regras de dependência da arquitetura hexagonal do projeto:

```
adapter/in/web                application/port/in         application/port/out
AiConnectionController  →  CheckAiConnectionUseCase  →  AiPort
        ↓                           ↓
application/service         infrastructure/ai/gateway
CheckAiConnectionService  →  AiGateway
```

Diagrama de dependências por camada:

```
┌─────────────────────────────────────────────────────────────┐
│  adapter/in/web                                             │
│  AiConnectionController                                     │
│    depende de: CheckAiConnectionUseCase (port/in)           │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│  application/port/in                                        │
│  CheckAiConnectionUseCase (interface)                       │
└──────────────────────────┬──────────────────────────────────┘
                           │ implementado por
┌──────────────────────────▼──────────────────────────────────┐
│  application/service                                        │
│  CheckAiConnectionService                                   │
│    depende de: AiPort (port/out)                            │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│  application/port/out                                       │
│  AiPort (interface)                                         │
└──────────────────────────┬──────────────────────────────────┘
                           │ implementado por
┌──────────────────────────▼──────────────────────────────────┐
│  infrastructure/ai/gateway                                  │
│  AiGateway                                                  │
│    depende de: ChatClient (Spring AI)                       │
└─────────────────────────────────────────────────────────────┘
```

Exceções de domínio:

```
domain/exception
└── AiCommunicationException  (RuntimeException)
```

Validação de startup:

```
infrastructure/ai/config
└── OpenAiApiKeyValidator  (@Component + @PostConstruct)
    └── falha com IllegalStateException se OPENAI_API_KEY ausente
```

---

## Components and Interfaces

### 1. `pom.xml` — Dependência Maven

Adicionar o BOM do Spring AI em `<dependencyManagement>` e a dependência do starter:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>2.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- Spring AI OpenAI starter -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-starter-model-openai</artifactId>
    </dependency>
</dependencies>
```

---

### 2. `domain/exception/AiCommunicationException`

Exceção de domínio para falhas de comunicação com a OpenAI API.

**Pacote:** `com.ia.para.devs.mockai.domain.exception`

```java
public class AiCommunicationException extends RuntimeException {
    public AiCommunicationException(String message) { ... }
    public AiCommunicationException(String message, Throwable cause) { ... }
}
```

---

### 3. `application/port/out/AiPort`

Port de saída que define o contrato de comunicação com serviços de IA externos. Livre de dependências de frameworks.

**Pacote:** `com.ia.para.devs.mockai.application.port.out`

```java
public interface AiPort {
    /**
     * Envia um prompt para o serviço de IA e retorna a resposta gerada.
     *
     * @param prompt texto de entrada não nulo e não vazio
     * @return resposta gerada pelo modelo, nunca nula
     * @throws IllegalArgumentException se o prompt for nulo, vazio ou whitespace
     * @throws AiCommunicationException se ocorrer falha na comunicação com a IA
     */
    String sendPrompt(String prompt);
}
```

---

### 4. `application/port/in/CheckAiConnectionUseCase`

Port de entrada que define o contrato do caso de uso de verificação de conectividade.

**Pacote:** `com.ia.para.devs.mockai.application.port.in`

```java
public interface CheckAiConnectionUseCase {
    /**
     * Verifica se a integração com o serviço de IA está funcional.
     *
     * @return true se a conexão estiver operacional, false caso contrário
     */
    boolean checkConnection();
}
```

---

### 5. `infrastructure/ai/gateway/AiGateway`

Adapter de saída que implementa `AiPort` utilizando o `ChatClient` do Spring AI.

**Pacote:** `com.ia.para.devs.mockai.infrastructure.ai.gateway`

```java
@Component
public class AiGateway implements AiPort {

    private final ChatClient chatClient;
    private final String apiKey;  // lido via @Value("${spring.ai.openai.api-key:}")

    public AiGateway(ChatClient.Builder chatClientBuilder,
                     @Value("${spring.ai.openai.api-key:}") String apiKey) {
        this.chatClient = chatClientBuilder.build();
        this.apiKey = apiKey;
    }

    @Override
    public String sendPrompt(String prompt) {
        // 1. Valida prompt
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("O prompt não pode ser nulo ou vazio");
        }
        // 2. Valida API Key
        if (apiKey == null || apiKey.isBlank()) {
            throw new AiCommunicationException("A chave de API do serviço de IA não foi configurada");
        }
        // 3. Envia e trata erros
        try {
            return chatClient.prompt().user(prompt).call().content();
        } catch (/* erros HTTP 4xx/5xx */ Exception ex) {
            // mapeamento para AiCommunicationException com mensagem em português
        }
    }
}
```

**Tratamento de erros no `sendPrompt`:**

| Condição | Exceção lançada |
|---|---|
| `prompt` nulo / vazio / whitespace | `IllegalArgumentException` |
| `apiKey` ausente ou vazia | `AiCommunicationException` |
| HTTP 401 (autenticação inválida) | `AiCommunicationException` |
| HTTP 4xx / 5xx | `AiCommunicationException` |
| Timeout (> 30s) | `AiCommunicationException` |
| Qualquer outra exceção | `AiCommunicationException` (fallback) |

---

### 6. `application/service/CheckAiConnectionService`

Implementação do `CheckAiConnectionUseCase`. Depende exclusivamente do `AiPort`.

**Pacote:** `com.ia.para.devs.mockai.application.service`

```java
@Service
public class CheckAiConnectionService implements CheckAiConnectionUseCase {

    private static final String TEST_PROMPT = "ping";

    private final AiPort aiPort;

    public CheckAiConnectionService(AiPort aiPort) {
        this.aiPort = aiPort;
    }

    @Override
    public boolean checkConnection() {
        try {
            String response = aiPort.sendPrompt(TEST_PROMPT);
            return response != null && !response.isBlank();
        } catch (Exception ex) {
            return false;
        }
    }
}
```

---

### 7. `adapter/in/web/AiConnectionController`

Controller REST que expõe `GET /test-ai-connection`. Depende exclusivamente do `CheckAiConnectionUseCase`.

**Pacote:** `com.ia.para.devs.mockai.adapter.in.web`

```java
@RestController
public class AiConnectionController {

    private final CheckAiConnectionUseCase checkAiConnectionUseCase;

    public AiConnectionController(CheckAiConnectionUseCase checkAiConnectionUseCase) {
        this.checkAiConnectionUseCase = checkAiConnectionUseCase;
    }

    @GetMapping("/test-ai-connection")
    public ResponseEntity<String> testAiConnection() {
        boolean connected = checkAiConnectionUseCase.checkConnection();
        if (connected) {
            return ResponseEntity.ok("Conexão com o serviço de IA está funcional");
        }
        return ResponseEntity.status(503).body("Conexão com o serviço de IA está indisponível");
    }
}
```

---

### 8. `infrastructure/ai/config/OpenAiApiKeyValidator`

Componente Spring responsável por validar a presença da API Key na inicialização da aplicação. Falha imediatamente com mensagem clara caso a variável de ambiente `OPENAI_API_KEY` não esteja configurada.

**Pacote:** `com.ia.para.devs.mockai.infrastructure.ai.config`

```java
@Component
public class OpenAiApiKeyValidator {

    private final String apiKey;

    public OpenAiApiKeyValidator(@Value("${spring.ai.openai.api-key:}") String apiKey) {
        this.apiKey = apiKey;
    }

    @PostConstruct
    public void validate() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                "A variável de ambiente OPENAI_API_KEY não está configurada. " +
                "A aplicação não pode ser iniciada sem a chave de API do serviço de IA."
            );
        }
    }
}
```

**Comportamento:**
- Executado automaticamente pelo Spring durante o contexto de inicialização (`@PostConstruct`)
- Se `OPENAI_API_KEY` não estiver definida → `IllegalStateException` → Spring aborta o startup com log de erro
- Se `OPENAI_API_KEY` estiver definida → validação passa silenciosamente, aplicação sobe normalmente

**Impacto no `AiGateway`:**
Com o `OpenAiApiKeyValidator` garantindo a presença da chave na startup, a validação de `apiKey` dentro do método `sendPrompt` do `AiGateway` torna-se uma defesa secundária (defensive programming), não o mecanismo principal de falha.

---

### 9. `application.properties` — Configurações Spring AI

```properties
spring.ai.openai.api-key=${OPENAI_API_KEY:}
spring.ai.openai.chat.options.model=gpt-4o-mini
spring.ai.openai.chat.options.temperature=0.7
```

---

### 9. `.env.example`

```
OPENAI_API_KEY=<sua-chave-da-api-openai>
```

---

### 10. `.gitignore` — Entradas adicionais

```
.env
.env.*
**/.env
**/.env.*
```

---

## Data Models

Nenhuma entidade JPA nova é necessária. A feature não persiste dados — apenas realiza chamadas HTTP à OpenAI API.

---

## File Structure

Arquivos a criar ou modificar:

```
pom.xml                                                          ← modificar
src/main/resources/application.properties                       ← modificar
.gitignore                                                       ← modificar
.env.example                                                     ← criar

src/main/java/com/ia/para/devs/mockai/
├── domain/
│   └── exception/
│       └── AiCommunicationException.java                       ← criar
├── application/
│   ├── port/
│   │   ├── in/
│   │   │   └── CheckAiConnectionUseCase.java                   ← criar
│   │   └── out/
│   │       └── AiPort.java                                     ← criar
│   └── service/
│       └── CheckAiConnectionService.java                       ← criar
├── infrastructure/
│   └── ai/
│       ├── config/
│       │   └── OpenAiApiKeyValidator.java                      ← criar
│       └── gateway/
│           └── AiGateway.java                                  ← criar
└── adapter/
    └── in/
        └── web/
            └── AiConnectionController.java                     ← criar
```

---

## Error Handling

Todas as exceções de comunicação com a IA são encapsuladas em `AiCommunicationException` (domínio), garantindo que nenhum detalhe interno do Spring AI vaze para as camadas superiores.

O `CheckAiConnectionService` captura qualquer exceção do `AiPort` e retorna `false`, evitando que erros de conectividade propaguem como HTTP 500 no endpoint de verificação.

O `GlobalExceptionHandler` existente no projeto pode ser estendido para tratar `AiCommunicationException` caso outros use cases futuros precisem propagar o erro ao invés de absorvê-lo.
