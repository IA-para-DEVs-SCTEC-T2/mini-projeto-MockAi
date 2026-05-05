---
inclusion: auto
description: Utilizado em operações que envolvam a estrutura do projeto, como criação, movimentação ou remoção de arquivos e diretórios.
---

# Estrutura do Projeto

Este projeto adota os princípios da **Clean Architecture** e **Hexagonal Architecture**, garantindo que as regras de negócio sejam completamente independentes de frameworks, bancos de dados e detalhes de infraestrutura.

Em termos de organização, o código está estruturado em 4 camadas principais: api, application, domain e infrastructure, cada uma com responsabilidades bem definidas e isoladas.

## Camadas

| Camada           | Responsabilidade Principal                                                                                                                                              |
|------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `domain`         | Contém os modelos de domínio puros e as abstrações (ports) que definem os contratos que outras camadas devem implementar.                                              |
| `application`    | Implementa as regras de negócio e validações específicas dos casos de uso, mas sem depender de frameworks ou detalhes de infraestrutura.                               |
| `infrastructure` | Implementa os **adaptadores técnicos**: persistência com JPA, gateways que se conectam ao banco de dados e mapeadores entre entidades e modelos de domínio.            |
| `api`            | Camada de **apresentação** que expõe endpoints REST para o mundo externo. Recebe requisições HTTP, delega para os casos de uso e retorna respostas padronizadas.      |

## Princípios de Dependência

- **Domain** não depende de nenhuma outra camada
- **Application** depende apenas de **Domain**
- **Infrastructure** implementa as interfaces definidas em **Domain**
- **API** depende de **Application** e **Domain**, mas não de **Infrastructure** diretamente

## Fluxo de Dados

```
API → Application → Domain ← Infrastructure
```

1. **API** recebe requisições HTTP
2. **Application** processa a lógica de negócio
3. **Domain** define as regras e contratos
4. **Infrastructure** implementa os adaptadores técnicos

## Boas Práticas

- Mantenha o **Domain** livre de dependências externas
- Use **Ports** (interfaces) no Domain para definir contratos
- Implemente **Adapters** na Infrastructure para cumprir os contratos
- Mantenha os casos de uso na **Application** focados e coesos
- Use DTOs na **API** para isolar a camada de apresentação
