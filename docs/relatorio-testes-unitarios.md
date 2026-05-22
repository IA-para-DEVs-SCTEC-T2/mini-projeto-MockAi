# Relatório de Testes Unitários — MockAI

**Data:** 21/05/2026  
**Cobertura total alcançada:** 49% (meta: 40% ✅)  
**Total de testes unitários:** 91  
**Resultado:** 91 passaram, 0 falharam, 0 ignorados

---

## 1. Configuração de Infraestrutura de Testes

### Dependências (já existentes no projeto)
- **JUnit 5** — via `spring-boot-starter-test`
- **Mockito** — via `spring-boot-starter-test`
- **AssertJ** — via `spring-boot-starter-test`
- **Spring MockMvc** — via `spring-boot-starter-test`

### Plugin adicionado
- **JaCoCo 0.8.12** — cobertura de código com regra de mínimo 40% de linhas cobertas

---

## 2. Testes por Camada

### 2.1 Camada Application (Casos de Uso / Services)

| Classe | Arquivo de Teste | Qtd. Testes | Cenários Cobertos |
|--------|-----------------|-------------|-------------------|
| `ValidateSwaggerContentService` | `ValidateSwaggerContentServiceTest.java` | 11 | Spec válida; openapi null/blank; info null; info.title null; info.description null; paths null/vazio; path sem métodos; método sem responses; acúmulo de erros |
| `ImportSwaggerService` | `ImportSwaggerServiceTest.java` | 3 | Orquestração completa (deserializar→validar→persistir→registrar rotas); JSON inválido; propagação de erro de validação |
| `ValidateFileService` | `ValidateFileServiceTest.java` | 5 | Extensão .json aceita; .JSON (case insensitive); extensões inválidas (.xml, .yaml, .txt, .pdf); arquivo sem extensão |
| `GenerateEndpointResponseService` | `GenerateEndpointResponseServiceTest.java` | 8 | Endpoint sem respostas; schema null/vazio; builder retorna null; envio de prompt à IA; remoção de code fences; falha da IA; resposta vazia; prioridade 200 sobre 201 |
| `CheckAiConnectionService` | `CheckAiConnectionServiceTest.java` | 5 | Resposta válida (true); resposta null (false); resposta vazia (false); resposta blank (false); exceção (false) |
| `PersistSwaggerSpecService` | `PersistSwaggerSpecServiceTest.java` | 1 | Delegação ao port de saída e retorno do UUID |
| `GetEndpointsBySpecificationIdService` | `GetEndpointsBySpecificationIdServiceTest.java` | 3 | Retorno de endpoints; specificationId null (NullPointerException); lista vazia |
| `DynamicRouteRegistrationService` | `DynamicRouteRegistrationServiceTest.java` | 2 | Desregistrar todas + registrar novas rotas; delegação de unregister |
| `ListEndpointsService` | `ListEndpointsServiceTest.java` | 3 | Lista vazia; retorno de endpoints; delegação ao port |

**Subtotal:** 41 testes | **Cobertura do pacote:** 85%

---

### 2.2 Camada API (Controllers / Adapters de Entrada)

| Classe | Arquivo de Teste | Qtd. Testes | Cenários Cobertos |
|--------|-----------------|-------------|-------------------|
| `GlobalExceptionHandler` | `GlobalExceptionHandlerTest.java` | 6 | InvalidExtensionException→400; InvalidSwaggerContentException→400; DatabaseConnectionException→503; ReferentialIntegrityException→409; PersistenceDeletionException→500; PersistenceFailureException→500 |
| `AiConnectionController` | `AiConnectionControllerTest.java` | 2 | Conexão funcional (200); conexão indisponível (503) |
| `ImportController` | `ImportControllerTest.java` | 2 | Importação bem-sucedida (201); propagação de InvalidExtensionException |
| `EndpointController` | `EndpointControllerTest.java` | 3 | Lista vazia (200); endpoints mapeados (200); múltiplos endpoints |

**Subtotal:** 13 testes | **Cobertura do pacote:** 96–100%

---

### 2.3 Camada Infrastructure (Adapters de Saída)

| Classe | Arquivo de Teste | Qtd. Testes | Cenários Cobertos |
|--------|-----------------|-------------|-------------------|
| `EndpointDefinitionQueryAdapter` | `EndpointDefinitionQueryAdapterTest.java` | 2 | Retorno de endpoints por specificationId; lista vazia |
| `SwaggerSpecDeletionAdapter` | `SwaggerSpecDeletionAdapterTest.java` | 3 | Ordem correta de deleção (apiSpec→flush→tags→flush); falha no deleteAll; falha no flush |
| `ListEndpointsAdapter` | `ListEndpointsAdapterTest.java` | 2 | Lista vazia; retorno de todos os endpoints |
| `AiGateway` | `AiGatewayTest.java` | 6 | Prompt null; prompt vazio; prompt blank; API key vazia; API key null; erro inesperado na chamada |

**Subtotal:** 13 testes | **Cobertura do pacote:** 9–55%

---

### 2.4 Utilitários e Componentes Auxiliares

| Classe | Arquivo de Teste | Qtd. Testes | Cenários Cobertos |
|--------|-----------------|-------------|-------------------|
| `HttpMethodMapper` | `HttpMethodMapperTest.java` | 4 | Mapeamento de GET/POST/PUT/DELETE/PATCH (case insensitive, com espaços); método null; método vazio; método não suportado (OPTIONS) |
| `DynamicResponseBodyBuilder` | `DynamicResponseBodyBuilderTest.java` | 9 | Schema null; schema vazio; JSON inválido; objeto simples; array (3 itens padrão); resolução de $ref local; boolean; number; enum (primeiro valor); allOf (merge de propriedades) |

**Subtotal:** 13 testes | **Cobertura do pacote:** 42–100%

---

## 3. Cobertura por Pacote (JaCoCo)

| Pacote | Cobertura de Instruções |
|--------|------------------------|
| `application.util` | 100% |
| `adapter.in.web.handler` | 100% |
| `adapter.in.web.dto` | 100% |
| `config` | 100% |
| `infrastructure.config` | 97% |
| `adapter.in.web` | 96% |
| `application.service` | 85% |
| `infrastructure.ai.config` | 73% |
| `domain.exception` | 72% |
| `infrastructure.ai.gateway` | 55% |
| `adapter.in.web.dynamic` | 42% |
| `mockai (main class)` | 10% |
| `infrastructure.persistence.adapter` | 9% |
| **TOTAL** | **49%** |

---

## 4. Padrões de Teste Utilizados

- **Framework:** JUnit 5 (`@Test`, `@DisplayName`, `@ParameterizedTest`)
- **Mocking:** Mockito (`@Mock`, `@InjectMocks`, `@Spy`, `@ExtendWith(MockitoExtension.class)`)
- **Assertions:** AssertJ (`assertThat`, `assertThatThrownBy`, `assertThatCode`)
- **Verificação de ordem:** Mockito `InOrder`
- **Testes parametrizados:** `@ValueSource`, `@CsvSource`

---

## 5. Como Executar

```bash
# Executar todos os testes unitários
./mvnw test

# Executar testes e verificar cobertura mínima (40%)
./mvnw verify

# Gerar relatório de cobertura (HTML)
./mvnw test jacoco:report
# Relatório disponível em: target/site/jacoco/index.html
```

---

## 6. Observações

- Os testes de integração pré-existentes (`ImportSwaggerIntegrationTest`, `ListEndpointsIntegrationTest`) possuem falhas independentes (arquivo `petstore.json` ausente e validação de `info.description`). Esses não foram alterados.
- O `SwaggerSpecPersistenceAdapter` possui lógica complexa de persistência com JPA que requer testes de integração com banco H2 para cobertura completa. Os testes unitários cobrem o adapter de deleção e query.
- O JaCoCo 0.8.12 emite warnings ao instrumentar classes geradas pelo Mockito em JVM 26 (class file version 70), mas isso não impede a execução dos testes nem a geração do relatório.
