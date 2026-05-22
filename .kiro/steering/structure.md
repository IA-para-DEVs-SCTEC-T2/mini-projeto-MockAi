---
inclusion: always
---

# Estrutura do Projeto

Este projeto adota os princípios da **Clean Architecture** e **Hexagonal Architecture**, garantindo que as regras de negócio sejam completamente independentes de frameworks, bancos de dados e detalhes de infraestrutura.

O código está organizado em 4 camadas principais: `api`, `application`, `domain` e `infrastructure`, cada uma com responsabilidades bem definidas e isoladas.

## Pacote Base

```
com.ia.para.devs.mockai
```

## Estrutura de Diretórios

```
src/main/java/com/ia/para/devs/mockai/
├── MockaiApplication.java                          # Classe principal Spring Boot
├── adapter/                                        # Camada de apresentação (adaptadores de entrada)
│   └── in/
│       └── web/
│           ├── AiConnectionController.java         # GET /test-ai-connection
│           ├── EndpointController.java             # GET /endpoints
│           ├── ImportController.java               # POST /import
│           ├── dto/                                # DTOs de request/response e OpenAPI
│           │   ├── EndpointResponse.java
│           │   ├── ImportResponse.java
│           │   ├── InfoDto.java
│           │   ├── MediaTypeDto.java
│           │   ├── OpenApiSpecDto.java
│           │   ├── ParameterDto.java
│           │   ├── PathItemDto.java
│           │   ├── ResponseDto.java
│           │   ├── SchemaDto.java
│           │   ├── ServerDto.java
│           │   └── TagDto.java
│           ├── dynamic/                            # Roteamento dinâmico de endpoints mockados
│           │   ├── DynamicEndpointHandler.java
│           │   ├── DynamicResponseBodyBuilder.java
│           │   └── SpringWebDynamicRouteRegistry.java
│           └── handler/
│               └── GlobalExceptionHandler.java
├── application/                                    # Casos de uso
│   ├── service/                                    # Implementações dos casos de uso
│   │   ├── CheckAiConnectionService.java
│   │   ├── DynamicRouteRegistrationService.java
│   │   ├── GenerateEndpointResponseService.java
│   │   ├── GetEndpointsBySpecificationIdService.java
│   │   ├── ImportSwaggerService.java
│   │   ├── ListEndpointsService.java
│   │   ├── PersistSwaggerSpecService.java
│   │   ├── ValidateFileService.java
│   │   └── ValidateSwaggerContentService.java
│   └── util/
│       └── HttpMethodMapper.java
├── config/
│   └── JacksonConfig.java
├── domain/                                         # Regras de negócio e contratos
│   ├── exception/                                  # Exceções de domínio tipadas
│   │   ├── AiCommunicationException.java
│   │   ├── DatabaseConnectionException.java
│   │   ├── InvalidExtensionException.java
│   │   ├── InvalidSwaggerContentException.java
│   │   ├── PersistenceDeletionException.java
│   │   ├── PersistenceFailureException.java
│   │   └── ReferentialIntegrityException.java
│   ├── model/                                      # Modelos de domínio puros
│   │   └── FileData.java
│   └── port/
│       ├── in/                                     # Interfaces de entrada (use cases)
│       │   ├── CheckAiConnectionUseCase.java
│       │   ├── DynamicRouteRegistrationUseCase.java
│       │   ├── GenerateEndpointResponseUseCase.java
│       │   ├── GetEndpointsBySpecificationIdUseCase.java
│       │   ├── ImportSwaggerUseCase.java
│       │   ├── ListEndpointsUseCase.java
│       │   ├── PersistSwaggerSpecUseCase.java
│       │   ├── ValidateFileUseCase.java
│       │   └── ValidateSwaggerContentUseCase.java
│       └── out/                                    # Interfaces de saída (repositórios, gateways)
│           ├── AiPort.java
│           ├── DeleteSwaggerSpecPort.java
│           ├── DynamicRouteRegistryPort.java
│           ├── GetEndpointsBySpecificationIdPort.java
│           ├── ListEndpointsPort.java
│           └── PersistSwaggerSpecPort.java
└── infrastructure/                                 # Adaptadores técnicos
    ├── ai/
    │   ├── config/
    │   │   └── GroqApiKeyValidator.java
    │   └── gateway/
    │       └── AiGateway.java                      # Spring AI + Groq
    ├── config/
    │   └── DotEnvInitializer.java
    └── persistence/
        ├── adapter/                                # Adapters de persistência e consulta
        │   ├── EndpointDefinitionQueryAdapter.java
        │   ├── ListEndpointsAdapter.java
        │   ├── SwaggerSpecDeletionAdapter.java
        │   └── SwaggerSpecPersistenceAdapter.java
        ├── entity/                                 # Entidades JPA
        │   ├── ApiSpecificationEntity.java
        │   ├── EndpointDefinitionEntity.java
        │   ├── EndpointResponseEntity.java
        │   ├── PathParameterEntity.java
        │   └── TagEntity.java
        └── repository/                             # Repositórios Spring Data JPA
            ├── ApiSpecificationRepository.java
            ├── EndpointDefinitionRepository.java
            ├── EndpointResponseRepository.java
            ├── PathParameterRepository.java
            └── TagRepository.java
```

## Camadas

| Camada           | Pacote                                      | Responsabilidade Principal                                                                                      |
|------------------|---------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| `domain`         | `...mockai.domain`                          | Modelos de domínio puros, interfaces (ports in/out) e exceções de negócio tipadas.                              |
| `application`    | `...mockai.application`                     | Implementa as regras de negócio e os casos de uso, sem depender de frameworks ou detalhes de infraestrutura.    |
| `infrastructure` | `...mockai.infrastructure`                  | Adaptadores técnicos: persistência JPA, gateway de IA (Groq via Spring AI) e adapters de consulta.             |
| `adapter`        | `...mockai.adapter`                         | Camada de apresentação: expõe endpoints REST, registra rotas dinâmicas e trata exceções globalmente.            |

## Princípios de Dependência

- **Domain** não depende de nenhuma outra camada
- **Application** depende apenas de **Domain**
- **Infrastructure** implementa as interfaces de saída definidas em **Domain** (`port/out`)
- **Adapter** depende de **Application** (via interfaces `port/in`) e **Domain**, mas não de **Infrastructure** diretamente

## Fluxo de Dados

```
Adapter → Application → Domain ← Infrastructure
```

1. **Adapter** recebe requisições HTTP e delega para os casos de uso via ports de entrada
2. **Application** processa a lógica de negócio usando os contratos do Domain
3. **Domain** define as regras, os contratos (ports) e as exceções de negócio
4. **Infrastructure** implementa os adapters técnicos que cumprem os contratos de saída

## Boas Práticas

- Mantenha o **Domain** livre de dependências externas (Java puro, sem frameworks)
- Use **Ports** (interfaces) no Domain para definir contratos com a infraestrutura
- Implemente **Adapters** na Infrastructure para cumprir os contratos definidos no Domain
- Mantenha os casos de uso na **Application** focados e coesos — um caso de uso por classe
- Use DTOs na **API** para isolar a camada de apresentação dos modelos de domínio
- Nunca exponha entidades JPA fora da camada de **Infrastructure**
