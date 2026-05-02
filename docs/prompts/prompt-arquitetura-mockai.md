# PROMPT TÉCNICO ESTRUTURADO — MockAI

## 1. Contexto do Projeto

O projeto **MockAI** é uma aplicação Java desenvolvida como atividade do curso "IA para DEV" do programa SCTEC. O repositório já possui estrutura base criada, com esqueleto Maven, configurações Spring Boot e organização de pacotes seguindo Clean Architecture e Hexagonal Architecture.

O código-fonte está na branch `feature/inicializar-esqueleto-projeto`, criada a partir da `main`. Os arquivos de steering em `.kiro/steering/` definem as regras de stack, gitflow e estrutura de projeto que devem ser respeitadas durante todo o desenvolvimento.

---

## 2. Objetivo da Aplicação

Desenvolver um **gerador inteligente de APIs mock** que:

- Recebe um arquivo Swagger/OpenAPI (JSON ou YAML) via upload ou URL
- Interpreta os endpoints, métodos HTTP, parâmetros e schemas definidos na especificação
- Gera e registra dinamicamente endpoints mock correspondentes
- Retorna respostas simuladas baseadas nos schemas da especificação
- Persiste as definições de mock para reutilização entre sessões
- Expõe uma API REST para gerenciamento dos mocks criados

---

## 3. Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 | Linguagem principal |
| Maven | 3.9.7+ | Build e dependências |
| Spring Boot | 4.0.6 | Framework base |
| Spring Web MVC | (via Boot) | Endpoints REST |
| Spring Data JPA | (via Boot) | Persistência |
| H2 Database | runtime | Banco em memória |
| Bean Validation | (via Boot) | Validação de entrada |
| SpringDoc OpenAPI | 3.0.2 | Documentação Swagger UI |

Nenhuma dependência adicional deve ser introduzida sem revisão explícita.

---

## 4. Requisitos Técnicos

### Funcionais
- Receber e parsear especificações OpenAPI 3.x (JSON/YAML)
- Extrair endpoints, métodos HTTP, parâmetros de path/query e schemas de response
- Persistir as definições de mock no banco H2
- Expor endpoints mock dinamicamente com base nas definições persistidas
- Permitir listagem, consulta e remoção de mocks cadastrados

### Não Funcionais
- Arquitetura limpa com separação estrita entre camadas
- Princípios SOLID aplicados em todas as classes
- Código testável com injeção de dependência via Spring
- Documentação automática via SpringDoc
- Sem acoplamento entre camadas (domain não conhece Spring, JPA ou HTTP)

---

## 5. Estrutura Esperada do Projeto

A estrutura de pacotes já existe no repositório e deve ser respeitada:

```
src/main/java/com/ia/para/devs/mockai/
├── MockaiApplication.java
│
├── domain/
│   ├── model/
│   │   ├── MockDefinition.java         # Entidade de domínio: representa um mock cadastrado
│   │   ├── MockEndpoint.java           # Representa um endpoint extraído da spec
│   │   └── OpenApiSpec.java            # Representa a especificação OpenAPI carregada
│   └── port/
│       ├── MockDefinitionRepository.java   # Port de saída: contrato de persistência
│       └── OpenApiParser.java              # Port de entrada: contrato de parsing
│
├── application/
│   ├── usecase/
│   │   ├── CreateMockUseCase.java      # Caso de uso: criar mock a partir de spec
│   │   ├── ListMocksUseCase.java       # Caso de uso: listar mocks cadastrados
│   │   └── DeleteMockUseCase.java      # Caso de uso: remover mock
│   └── service/
│       └── MockResolverService.java    # Serviço: resolve qual resposta retornar para um mock
│
├── infrastructure/
│   ├── persistence/
│   │   ├── entity/
│   │   │   └── MockDefinitionEntity.java   # Entidade JPA mapeada para o banco
│   │   ├── repository/
│   │   │   └── MockDefinitionJpaRepository.java  # Interface Spring Data JPA
│   │   └── mapper/
│   │       └── MockDefinitionMapper.java   # Converte entre Entity e Domain Model
│   └── gateway/
│       └── OpenApiParserGateway.java       # Implementa OpenApiParser do domain
│
└── api/
    ├── controller/
    │   ├── MockController.java         # CRUD de mocks cadastrados
    │   └── MockExecutorController.java # Executa chamadas aos endpoints mock
    ├── dto/
    │   ├── request/
    │   │   └── CreateMockRequest.java  # DTO de entrada para criação de mock
    │   └── response/
    │       ├── MockResponse.java       # DTO de saída com dados do mock
    │       └── MockEndpointResponse.java
    └── exception/
        └── GlobalExceptionHandler.java # Handler global com @RestControllerAdvice
```

---

## 6. Responsabilidades das Classes

### Domain

- `MockDefinition` — modelo de domínio puro representando um mock registrado. Sem anotações de framework. Contém id, nome, lista de endpoints e metadados.
- `MockEndpoint` — representa um endpoint individual: path, método HTTP, parâmetros e schema de resposta simulada.
- `OpenApiSpec` — encapsula os dados brutos extraídos de uma especificação OpenAPI após parsing.
- `MockDefinitionRepository` — interface (port de saída) que define os contratos de persistência: salvar, buscar por id, listar todos, deletar.
- `OpenApiParser` — interface (port de entrada) que define o contrato de parsing de uma especificação OpenAPI a partir de string ou URL.

### Application

- `CreateMockUseCase` — orquestra o fluxo: recebe a spec, delega o parsing ao `OpenApiParser`, constrói o `MockDefinition` e persiste via `MockDefinitionRepository`.
- `ListMocksUseCase` — consulta todos os mocks persistidos via repositório e os retorna como modelos de domínio.
- `DeleteMockUseCase` — valida existência e remove um mock pelo id.
- `MockResolverService` — dado um path e método HTTP, localiza o mock correspondente e retorna a resposta simulada definida no schema.

### Infrastructure

- `MockDefinitionEntity` — classe anotada com `@Entity` mapeando `MockDefinition` para o banco H2. Não deve vazar para fora da camada.
- `MockDefinitionJpaRepository` — interface que estende `JpaRepository`, usada internamente pelo adapter de persistência.
- `MockDefinitionMapper` — converte `MockDefinitionEntity` ↔ `MockDefinition`. Responsabilidade única e testável isoladamente.
- `OpenApiParserGateway` — implementa `OpenApiParser` do domain. Faz o parsing real da spec OpenAPI.

### API

- `MockController` — expõe endpoints REST para `POST /mocks`, `GET /mocks`, `GET /mocks/{id}`, `DELETE /mocks/{id}`. Delega para os use cases.
- `MockExecutorController` — endpoint dinâmico que recebe chamadas no padrão `/mock/{mockId}/{path}` e delega ao `MockResolverService`.
- `CreateMockRequest` — DTO de entrada com validações Bean Validation (`@NotBlank`, `@NotNull`).
- `MockResponse` / `MockEndpointResponse` — DTOs de saída serializados como JSON.
- `GlobalExceptionHandler` — captura exceções de negócio e de validação, retornando respostas HTTP padronizadas com código e mensagem.

---

## 7. Boas Práticas Esperadas

- Aplicar **Single Responsibility Principle**: cada classe tem uma única razão para mudar.
- Aplicar **Dependency Inversion**: use cases dependem de interfaces (ports), não de implementações concretas.
- Aplicar **Open/Closed**: novos parsers ou repositórios devem ser adicionados por extensão, não modificação.
- Injeção de dependência exclusivamente via construtor (não usar `@Autowired` em campo).
- DTOs nunca devem cruzar a fronteira da camada `api` — use cases recebem e retornam modelos de domínio.
- Entidades JPA (`@Entity`) nunca devem ser expostas fora da camada `infrastructure`.
- Métodos de use case devem ser pequenos, focados e sem lógica de infraestrutura.
- Usar `Optional` para retornos que podem ser nulos em repositórios.
- Nomes em inglês para classes, métodos e variáveis; comentários e documentação em português.

---

## 8. Orientações sobre Testes Automatizados

### Estratégia por camada

- **Domain** — sem testes de framework. Testar modelos e regras de negócio com JUnit puro.
- **Application** — testar use cases com mocks das interfaces (ports) usando Mockito. Verificar fluxos de sucesso e exceções.
- **Infrastructure** — testar mappers com JUnit. Testar repositórios com `@DataJpaTest` e banco H2.
- **API** — testar controllers com `@WebMvcTest`, verificando status HTTP, serialização JSON e validações de entrada.

### Convenções

- Nomenclatura: `NomeDaClasse + Test` (ex: `CreateMockUseCaseTest`)
- Estrutura de teste: padrão **Arrange / Act / Assert**
- Cobertura mínima esperada: casos de sucesso e pelo menos um caso de falha por use case
- Não testar detalhes de implementação, testar comportamento

---

## 9. Orientações sobre Documentação Técnica

- Todos os endpoints REST devem ser anotados com `@Operation`, `@ApiResponse` e `@Tag` do SpringDoc.
- DTOs de request e response devem usar `@Schema` para descrever campos.
- Javadoc obrigatório em interfaces de port e classes de use case.
- O `README.md` deve ser atualizado ao final com as URLs reais do H2 Console e Swagger UI.
- Manter o `prompts.md` atualizado com cada solicitação feita ao Kiro.

---

## 10. Limitações do Escopo Inicial (MVP)

- Não implementar integração real com IA nesta fase — respostas simuladas são baseadas apenas nos schemas da spec.
- Não implementar autenticação ou autorização.
- Não implementar suporte a Swagger 2.x (apenas OpenAPI 3.x).
- Não implementar banco de dados relacional externo — usar H2 em memória.
- Não implementar upload de arquivo — aceitar spec como string JSON/YAML no body da requisição.
- Não implementar paginação na listagem de mocks nesta fase.

---

## 11. Resultado Esperado ao Final do Desenvolvimento

Ao concluir o desenvolvimento do MVP, a aplicação deve:

1. Iniciar com `mvn spring-boot:run` sem erros
2. Expor Swagger UI em `http://localhost:8080/swagger-ui.html` com todos os endpoints documentados
3. Permitir cadastrar um mock via `POST /mocks` enviando uma spec OpenAPI como JSON/YAML
4. Listar mocks cadastrados via `GET /mocks`
5. Executar chamadas simuladas via `GET|POST /mock/{mockId}/{path}` retornando a resposta definida na spec
6. Persistir dados no H2 acessível em `http://localhost:8080/h2-console`
7. Ter testes automatizados cobrindo use cases e controllers
8. Manter coerência total com a arquitetura Clean Architecture definida no projeto
