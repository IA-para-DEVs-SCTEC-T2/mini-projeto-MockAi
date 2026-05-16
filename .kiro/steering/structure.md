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
├── api/                                            # Camada de apresentação (a implementar)
│   ├── controller/                                 # Controllers REST
│   ├── dto/                                        # DTOs de request e response
│   └── mapper/                                     # Mapeadores entre DTOs e modelos de domínio
├── application/                                    # Casos de uso (a implementar)
│   └── usecase/                                    # Implementações dos casos de uso
├── domain/                                         # Regras de negócio e contratos (a implementar)
│   ├── model/                                      # Modelos de domínio puros
│   └── port/                                       # Interfaces (ports) que definem contratos
└── infrastructure/                                 # Adaptadores técnicos
    └── persistence/
        ├── entity/                                 # Entidades JPA
        │   ├── ApiSpecificationEntity.java
        │   ├── EndpointDefinitionEntity.java
        │   ├── EndpointResponseEntity.java
        │   ├── PathParameterEntity.java
        │   └── TagEntity.java
        ├── repository/                             # Repositórios Spring Data JPA (a implementar)
        └── mapper/                                 # Mapeadores entre entidades e modelos de domínio (a implementar)
```

## Camadas

| Camada           | Pacote                                      | Responsabilidade Principal                                                                                      |
|------------------|---------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| `domain`         | `...mockai.domain`                          | Modelos de domínio puros e interfaces (ports) que definem os contratos que outras camadas devem implementar.    |
| `application`    | `...mockai.application`                     | Implementa as regras de negócio e os casos de uso, sem depender de frameworks ou detalhes de infraestrutura.    |
| `infrastructure` | `...mockai.infrastructure`                  | Adaptadores técnicos: persistência JPA, gateways e mapeadores entre entidades e modelos de domínio.             |
| `api`            | `...mockai.api`                             | Camada de apresentação: expõe endpoints REST, recebe requisições HTTP e delega para os casos de uso.            |

## Princípios de Dependência

- **Domain** não depende de nenhuma outra camada
- **Application** depende apenas de **Domain**
- **Infrastructure** implementa as interfaces definidas em **Domain**
- **API** depende de **Application** e **Domain**, mas não de **Infrastructure** diretamente

## Fluxo de Dados

```
API → Application → Domain ← Infrastructure
```

1. **API** recebe requisições HTTP e delega para os casos de uso
2. **Application** processa a lógica de negócio usando os contratos do Domain
3. **Domain** define as regras e os contratos (ports)
4. **Infrastructure** implementa os adaptadores técnicos que cumprem os contratos

## Boas Práticas

- Mantenha o **Domain** livre de dependências externas (Java puro, sem frameworks)
- Use **Ports** (interfaces) no Domain para definir contratos com a infraestrutura
- Implemente **Adapters** na Infrastructure para cumprir os contratos definidos no Domain
- Mantenha os casos de uso na **Application** focados e coesos — um caso de uso por classe
- Use DTOs na **API** para isolar a camada de apresentação dos modelos de domínio
- Nunca exponha entidades JPA fora da camada de **Infrastructure**
