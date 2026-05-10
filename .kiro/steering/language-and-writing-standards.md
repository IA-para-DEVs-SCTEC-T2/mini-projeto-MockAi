---
inclusion: auto
description: Define os padrões de idioma e escrita do projeto. Deve ser consultado sempre que for necessário escrever código, comentários, documentação, mensagens de log ou qualquer outro texto no projeto.
---

# Padrões de Linguagem e Escrita

Este documento define as regras de uso de idioma no projeto MockAI, garantindo consistência, legibilidade e facilidade de manutenção em todo o código-fonte e documentação.

---

## 1. Introdução

Projetos de software colaborativos exigem clareza tanto no código quanto na documentação. Para o MockAI, adotamos uma separação deliberada de idiomas:

- **Inglês** para todo o código-fonte, pois é a língua universal da programação, compatível com ferramentas, IDEs, linters e bibliotecas externas.
- **Português (pt-BR)** para toda a documentação (JavaDoc) e textos explicativos, pois o time é brasileiro e a comunicação técnica interna deve ser acessível a todos os membros sem barreiras de idioma.

Essa separação não é arbitrária — ela reduz ambiguidade, facilita o onboarding de novos membros e mantém o código alinhado com as convenções da indústria.

---

## 2. Diretriz Geral

| Contexto                          | Idioma         |
|-----------------------------------|----------------|
| Nomes de classes                  | Inglês         |
| Nomes de interfaces               | Inglês         |
| Nomes de métodos                  | Inglês         |
| Nomes de variáveis e parâmetros   | Inglês         |
| Nomes de constantes               | Inglês         |
| Nomes de pacotes                  | Inglês         |
| JavaDoc (`/** */`)                | Português      |
| Mensagens de exceção              | Português      |
| Mensagens de log                  | Português      |
| Arquivos `.md` e documentação     | Português      |
| Paths de endpoints REST           | Inglês         |
| Arquivos de configuração (chaves) | Inglês         |

---

## 3. Regras para Código-Fonte

Todo identificador no código-fonte deve estar em **inglês**, seguindo as convenções padrão do Java (camelCase para variáveis e métodos, PascalCase para classes e interfaces, UPPER_SNAKE_CASE para constantes).

### 3.1 Classes e Interfaces

```java
// ✅ Correto
public class MockDefinition { }
public interface MockDefinitionRepository { }

// ❌ Incorreto
public class DefinicaoMock { }
public interface RepositorioDeDefinicaoMock { }
```

### 3.2 Métodos

```java
// ✅ Correto
public MockDefinition findById(String id) { }
public void deleteMock(String id) { }
public List<MockDefinition> listAll() { }

// ❌ Incorreto
public MockDefinition buscarPorId(String id) { }
public void deletarMock(String id) { }
public List<MockDefinition> listarTodos() { }
```

### 3.3 Variáveis e Parâmetros

```java
// ✅ Correto
String requestPath = request.getPath();
int statusCode = 200;
MockDefinition mockDefinition = repository.findById(id);

// ❌ Incorreto
String caminhoDaRequisicao = request.getPath();
int codigoDeStatus = 200;
MockDefinition definicaoDeMock = repository.findById(id);
```

### 3.4 Constantes

```java
// ✅ Correto
public static final String DEFAULT_CONTENT_TYPE = "application/json";
public static final int MAX_RETRY_ATTEMPTS = 3;

// ❌ Incorreto
public static final String TIPO_CONTEUDO_PADRAO = "application/json";
public static final int MAX_TENTATIVAS = 3;
```

### 3.5 Pacotes

```
// ✅ Correto
com.ia.para.devs.mockai.domain.model
com.ia.para.devs.mockai.application.usecase

// ❌ Incorreto
com.ia.para.devs.mockai.dominio.modelo
com.ia.para.devs.mockai.aplicacao.casodeuso
```

---

## 4. Regras para Documentação e Comentários

O código **não deve conter comentários** (`//` ou `/* */`). A única forma de documentação no código-fonte é o **JavaDoc** (`/** */`), que deve estar em **português (pt-BR)**. Qualquer explicação sobre o funcionamento de um código deve estar em português — inclusive anotações como `@DisplayName` em testes.

### 4.1 JavaDoc em Classes

```java
/**
 * Representa a definição de um mock, contendo as informações necessárias
 * para simular um endpoint HTTP, como método, caminho, status de resposta
 * e corpo da resposta.
 */
public class MockDefinition {
    // ...
}
```

### 4.2 JavaDoc em Métodos

```java
/**
 * Busca uma definição de mock pelo seu identificador único.
 *
 * @param id identificador único do mock
 * @return a definição do mock encontrada
 * @throws MockNotFoundException caso nenhum mock seja encontrado com o id informado
 */
public MockDefinition findById(String id) {
    // ...
}
```

### 4.3 JavaDoc em Interfaces (Ports)

```java
/**
 * Contrato para persistência e recuperação de definições de mock.
 * Implementações desta interface devem garantir isolamento entre mocks
 * de diferentes contextos.
 */
public interface MockDefinitionRepository {

    /**
     * Persiste uma nova definição de mock.
     *
     * @param mockDefinition definição a ser salva
     * @return a definição salva com o identificador gerado
     */
    MockDefinition save(MockDefinition mockDefinition);
}
```

### 4.4 Mensagens de Exceção e Log

```java
// ✅ Correto
throw new MockNotFoundException("Mock não encontrado para o id: " + id);
log.warn("Nenhum endpoint correspondente encontrado para o caminho: {}", path);
log.error("Erro ao processar a requisição para o mock {}: {}", id, e.getMessage());

// ❌ Incorreto
throw new MockNotFoundException("Mock not found for id: " + id);
log.warn("No matching endpoint found for path: {}", path);
```

---

## 5. Exceções às Regras

Algumas situações exigem o uso de inglês mesmo em contextos de documentação, ou impõem restrições técnicas que não podem ser contornadas.

### 5.1 Bibliotecas e Frameworks Externos

Anotações, propriedades e configurações de bibliotecas externas devem seguir a convenção da própria biblioteca:

```java
// ✅ Correto — anotações do Spring e SpringDoc em inglês (padrão da biblioteca)
@Operation(summary = "Cria um novo mock endpoint")
@ApiResponse(responseCode = "201", description = "Mock criado com sucesso")
@PostMapping("/mocks")
public ResponseEntity<MockResponse> createMock(@RequestBody CreateMockRequest request) {
    // ...
}
```

### 5.2 Chaves de Configuração

Chaves em `application.properties` ou `application.yml` devem seguir o padrão do Spring (inglês com kebab-case):

```properties
# ✅ Correto
spring.datasource.url=jdbc:h2:mem:testdb
server.port=8080
mockai.max-endpoints-per-mock=10
```

### 5.3 Paths de Endpoints REST

Os paths dos endpoints devem estar em **inglês**, seguindo as convenções REST (kebab-case para recursos compostos):

```java
// ✅ Correto
@PostMapping("/mock-definitions")
@GetMapping("/mock-definitions/{id}")
@DeleteMapping("/mock-definitions/{id}")

// ❌ Incorreto
@PostMapping("/definicoes-mock")
@GetMapping("/definicoes-mock/{id}")
```

### 5.4 Nomes de Testes

Métodos de teste devem ter nomes descritivos. Prefira descrições em português usando `@DisplayName`, que é a forma recomendada para explicar o comportamento esperado:

```java
// ✅ Correto — @DisplayName em português
@Test
@DisplayName("Deve lançar exceção quando o mock não for encontrado")
void shouldThrowExceptionWhenMockNotFound() { }

@Test
@DisplayName("Deve retornar o mock criado com status 201")
void shouldReturnCreatedMockWithStatus201() { }
```

---

## 6. Boas Práticas

### 6.1 Evite Mistura de Idiomas

Nunca misture inglês e português no mesmo identificador.

```java
// ❌ Incorreto — mistura de idiomas no nome do método
public MockDefinition buscarMockById(String id) { }

// ✅ Correto
public MockDefinition findById(String id) { }
```

### 6.2 Clareza Antes de Tradução Literal

Prefira nomes que expressem a intenção do código em inglês, não traduções literais do português.

```java
// ❌ Tradução literal — não idiomático em inglês
public void executeMockSimulation() { }

// ✅ Nome idiomático em inglês
public void resolveMock() { }
public void executeMock() { }
```

### 6.3 JavaDoc é Obrigatório em APIs Públicas

Todo método, classe ou interface pública que faça parte da API do sistema (controllers, use cases, ports) deve ter JavaDoc completo em português.
