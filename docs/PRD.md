# Product Requirements Document (PRD) — MockAI

**Versão:** 0.1.0-SNAPSHOT  
**Data:** 2026-05-13  
**Status:** Em desenvolvimento  
**Equipe:** Grupo 3 — IA para DEVs SCTEC T2

---

## 1. Visão Geral do Produto

O **MockAI** é um gerador inteligente de APIs mock que transforma arquivos Swagger/OpenAPI em APIs simuladas locais. A partir de uma documentação no padrão OpenAPI (JSON), o sistema processa o contrato, persiste as definições de endpoints e disponibiliza rotas HTTP prontas para consumo imediato — sem dependência de serviços externos reais.

A integração opcional com um serviço de IA externo (ex.: OpenAI/ChatGPT) permite que os payloads de resposta sejam gerados dinamicamente, tornando as simulações mais realistas e úteis para desenvolvimento e testes.

---

## 2. Problema Resolvido

Desenvolvedores que precisam implementar o consumo de uma API REST frequentemente enfrentam bloqueios quando o serviço real ainda não está disponível — seja por estar em desenvolvimento, em ambiente restrito ou simplesmente indisponível no momento.

Sem uma alternativa, o trabalho de implementação fica parado, gerando atrasos e dependências desnecessárias entre times.

O MockAI resolve esse problema criando um ambiente mock funcional da API, com endpoints e payloads prontos, permitindo que o desenvolvimento e os testes de integração avancem de forma independente.

---

## 3. Objetivos

- Simular o funcionamento real de uma API REST a partir de sua documentação Swagger/OpenAPI.
- Disponibilizar de forma rápida e dinâmica os endpoints e payloads de uma API REST.
- Auxiliar o trabalho de implementação e testes no consumo de APIs REST.
- Reduzir o tempo de espera e as dependências entre times de desenvolvimento.

---

## 4. Público-Alvo

**Desenvolvedor de software** que precisa implementar o consumo de uma API REST e não tem acesso imediato ao serviço real.

Perfil típico:
- Trabalha em projetos com múltiplos times ou serviços
- Precisa desenvolver e testar integrações antes do ambiente real estar disponível
- Familiarizado com o padrão Swagger/OpenAPI

---

## 5. Funcionalidades Principais

| # | Funcionalidade | Descrição |
|---|----------------|-----------|
| F1 | **Importação de documentação Swagger/OpenAPI** | Receber um arquivo Swagger/OpenAPI (JSON) via endpoint `POST /import` e processar seu conteúdo |
| F2 | **Criação automática do mock** | Extrair e persistir todos os endpoints, parâmetros, schemas e respostas definidos no contrato |
| F3 | **Endpoints mockados dinâmicos** | Disponibilizar automaticamente as rotas HTTP correspondentes ao contrato importado, prontas para consumo |
| F4 | **Geração de payloads com IA** | Integração com serviço de IA externo para gerar respostas dinâmicas e realistas nos endpoints mockados |
| F5 | **Consulta de status do mock** | Endpoint `GET /status` para verificar se uma API Mock está ativa na base de dados |
| F6 | **Documentação interativa** | Swagger UI disponível para explorar e testar os endpoints da própria API MockAI |

---

## 6. Regras de Negócio

| ID | Regra |
|----|-------|
| RN01 | A documentação fornecida deve seguir o padrão Swagger/OpenAPI 3.0 ou superior, no formato JSON |
| RN02 | Para endpoints que contenham payload no body da resposta, o conteúdo será gerado dinamicamente com auxílio de IA |
| RN03 | A cada nova importação de documentação Swagger, todos os endpoints existentes são deletados e recriados com base na nova documentação |
| RN04 | Nenhum método de autenticação é disponibilizado nos endpoints mockados |
| RN05 | Não é possível recuperar um histórico de APIs mockadas — o mock ativo sempre reflete a última documentação importada |
| RN06 | Arquivos inválidos (não JSON, sem campos obrigatórios do contrato, ou vazios) devem ser rejeitados com erro HTTP 400 e mensagem descritiva |
| RN07 | Os endpoints mockados devem estar disponíveis imediatamente após o processamento do contrato, sem necessidade de reinicialização |

---

## 7. Requisitos Funcionais

### RF01 — Recebimento de arquivo Swagger
- O sistema deve expor o endpoint `POST /import` que recebe um arquivo Swagger/OpenAPI via `multipart/form-data`
- O arquivo deve ser processado e todas as rotas (path + método HTTP), schemas e informações relevantes devem ser extraídas e persistidas no banco de dados

### RF02 — Validação do arquivo Swagger
- O sistema deve validar se o arquivo recebido está no formato JSON
- Deve verificar a presença dos campos obrigatórios do contrato (`openapi`/`swagger`, `info`, `paths`)
- Arquivos inválidos, vazios ou com estrutura incorreta devem ser rejeitados com HTTP 400 e mensagem descritiva
- A lógica de validação deve estar isolada em um componente dedicado

### RF03 — Criação de endpoints dinâmicos
- Após o processamento do contrato, o sistema deve registrar dinamicamente as rotas mock correspondentes
- Deve suportar os métodos HTTP: GET, POST, PUT, PATCH e DELETE
- Os endpoints devem estar disponíveis imediatamente após o processamento, sem reinicialização

### RF04 — Geração de retornos com IA
- O sistema deve integrar com um serviço de IA externo (ex.: OpenAI/ChatGPT) para gerar payloads de resposta
- O client de IA deve receber o schema do endpoint e retornar uma resposta gerada no formato esperado
- O token de autenticação da API de IA deve ser configurado via variável de ambiente ou `application.properties`, sem exposição no código-fonte
- Erros de comunicação com a API de IA devem ser tratados e propagados adequadamente

### RF05 — Consulta de status do mock
- O sistema deve expor o endpoint `GET /status` que recebe um identificador de API Mock
- Deve consultar o banco de dados e retornar o status correspondente
- Deve retornar erro adequado caso o identificador não seja encontrado

### RF06 — Consulta de endpoints disponíveis
- O sistema deve permitir listar os endpoints disponíveis no mock ativo

---

## 8. Requisitos Não Funcionais

| ID | Categoria | Requisito |
|----|-----------|-----------|
| RNF01 | Arquitetura | O sistema deve seguir os princípios de Clean Architecture e Hexagonal Architecture, com separação clara entre as camadas: `domain`, `application`, `infrastructure` e `api` |
| RNF02 | Arquitetura | As dependências entre camadas devem sempre apontar para dentro (domain não depende de nenhuma outra camada) |
| RNF03 | Persistência | O banco de dados utilizado é H2 in-memory (`jdbc:h2:mem:testdb`), adequado para desenvolvimento e testes |
| RNF04 | Disponibilidade | Os endpoints mockados devem ser registrados e disponibilizados sem necessidade de reinicialização da aplicação |
| RNF05 | Segurança | Tokens e credenciais de APIs externas não devem ser expostos no código-fonte; devem ser configurados via variáveis de ambiente ou propriedades de configuração |
| RNF06 | Documentação | A API do MockAI deve ser documentada e acessível via Swagger UI em `/mockai/swagger-ui.html` |
| RNF07 | Manutenibilidade | O código deve seguir os princípios SOLID e boas práticas de desenvolvimento Java/Spring Boot |
| RNF08 | Portabilidade | O sistema deve ser executável com Java 17+ e Maven 3.9.7+, sem dependências de infraestrutura externa além da JVM |

---

## 9. Fluxo Principal do Usuário

```
1. Desenvolvedor obtém a documentação Swagger/OpenAPI (JSON) da API que precisa consumir

2. Desenvolvedor envia o arquivo via POST /mockai/import (multipart/form-data)

3. MockAI valida o arquivo:
   ├── Inválido → retorna HTTP 400 com mensagem de erro
   └── Válido → prossegue

4. MockAI processa o contrato:
   ├── Extrai title, version, description e base_url da spec
   ├── Extrai todos os paths e métodos HTTP
   ├── Extrai parâmetros de path, schemas de request e response
   └── Persiste tudo no banco H2

5. MockAI registra dinamicamente as rotas mock correspondentes

6. Desenvolvedor consome os endpoints mockados:
   ├── Faz requisições HTTP para os endpoints registrados
   ├── MockAI consulta o schema do endpoint no banco
   ├── MockAI solicita ao serviço de IA a geração do payload de resposta
   └── MockAI retorna a resposta gerada ao consumidor

7. Desenvolvedor implementa e testa seu client consumidor usando o mock
```

---

## 10. Arquitetura de Alto Nível

O MockAI segue o modelo **C4** e é estruturado em **Clean Architecture** com 4 camadas:

```
┌─────────────────────────────────────────────────────────┐
│                    MockAI Application                    │
│                                                         │
│  ┌──────────┐  ┌─────────────┐  ┌──────────────────┐   │
│  │  Camada  │  │   Camada    │  │     Camada       │   │
│  │   API    │→ │ Application │→ │     Domain       │   │
│  │          │  │             │  │  (modelos+ports) │   │
│  └──────────┘  └─────────────┘  └──────────────────┘   │
│       ↑                                  ↑              │
│  ┌────┴─────────────────────────────────┴────────────┐  │
│  │              Camada Infrastructure                 │  │
│  │  ┌──────────────┐  ┌──────────┐  ┌─────────────┐ │  │
│  │  │ Repositórios │  │ Gateway  │  │   Mappers   │ │  │
│  │  │     JPA      │  │   IA     │  │             │ │  │
│  │  └──────┬───────┘  └────┬─────┘  └─────────────┘ │  │
│  └─────────┼───────────────┼───────────────────────┘  │
└────────────┼───────────────┼────────────────────────────┘
             ↓               ↓
        ┌─────────┐    ┌──────────────┐
        │H2 in-   │    │ Serviço de   │
        │ memory  │    │ IA Externo   │
        └─────────┘    └──────────────┘
```

**Atores externos:**
- **Desenvolvedor** — importa a documentação Swagger e consome os endpoints mockados
- **Consumidor do Mock** — testa e valida a integração com os endpoints mockados
- **Serviço de IA Externo** — gera payloads de resposta dinamicamente

**Modelo de dados (6 entidades JPA):**
- `api_specification` — representa a documentação Swagger importada
- `endpoint_definition` — cada endpoint (path + método HTTP) do contrato
- `tag` — agrupadores de endpoints definidos no Swagger
- `endpoint_tags` — tabela de junção N:N entre endpoints e tags
- `path_parameter` — parâmetros de path de cada endpoint
- `endpoint_response` — definições de resposta possíveis por endpoint

---

## 11. Stack Tecnológica

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| Java | 17 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework base da aplicação |
| Spring Web MVC | (via Spring Boot) | Exposição de endpoints REST |
| Spring Data JPA | (via Spring Boot) | Persistência de dados |
| H2 Database | runtime | Banco de dados in-memory |
| SpringDoc OpenAPI | 3.0.2 | Documentação Swagger UI da própria API |
| Lombok | (via Spring Boot) | Redução de boilerplate Java |
| Maven | 3.9.7+ | Gerenciamento de build e dependências |
| Jakarta Persistence | (via Spring Boot) | Mapeamento ORM das entidades |

**Ferramentas de desenvolvimento:**
- H2 Console: `http://localhost:8080/mockai/h2-console`
- Swagger UI: `http://localhost:8080/mockai/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/mockai/v3/api-docs`

---

## 12. Critérios de Sucesso

| # | Critério | Como medir |
|---|----------|------------|
| CS01 | Importação de Swagger funcional | `POST /import` processa um arquivo OpenAPI válido e persiste todos os endpoints no banco |
| CS02 | Endpoints mockados disponíveis | Após importação, as rotas definidas no contrato respondem a requisições HTTP sem reinicialização |
| CS03 | Validação de entrada | Arquivos inválidos são rejeitados com HTTP 400 e mensagem descritiva |
| CS04 | Geração de payload com IA | Endpoints com schema de resposta retornam payload gerado dinamicamente pelo serviço de IA |
| CS05 | Consulta de status | `GET /status` retorna o status correto de uma API Mock cadastrada |
| CS06 | Documentação acessível | Swagger UI da própria API está disponível e funcional |
| CS07 | Arquitetura limpa | Nenhuma dependência da camada `domain` para camadas externas; camadas isoladas e testáveis |

---

## 13. Limitações Atuais

| # | Limitação |
|---|-----------|
| L01 | **Sem autenticação nos mocks** — os endpoints mockados não implementam nenhum mecanismo de autenticação ou autorização |
| L02 | **Sem histórico de mocks** — apenas o mock da última documentação importada está disponível; não há versionamento ou recuperação de specs anteriores |
| L03 | **Banco in-memory** — os dados são perdidos ao reiniciar a aplicação (H2 in-memory); não há persistência entre sessões |
| L04 | **Formato de entrada restrito** — apenas arquivos no formato JSON são suportados; YAML não está contemplado na especificação atual |
| L05 | **Sem suporte a autenticação na spec** — campos de `securitySchemes` e `security` do OpenAPI são ignorados no mock |
| L06 | **Camadas application, domain e api ainda não implementadas** — o código atual contém apenas as entidades JPA da camada `infrastructure/persistence`; controllers, use cases, domain models e gateways ainda estão pendentes |

---

## 14. Próximos Passos

Com base no backlog e nas issues abertas do projeto:

| Prioridade | Item | Issue(s) |
|------------|------|----------|
| Alta | Criar endpoint `POST /import` para recebimento do arquivo Swagger | #18, #58, #12 |
| Alta | Implementar validação do arquivo Swagger/OpenAPI | #22 |
| Alta | Implementar criação de endpoints dinâmicos | #19 |
| Alta | Criar client HTTP para integração com IA (OpenAI/ChatGPT) | #21 |
| Alta | Implementar geração de retornos com IA | #23 |
| Média | Criar endpoint `GET /status` para consulta de status do mock | #10 |
| Média | Criar endpoint para consulta de endpoints disponíveis | #24 |
| Baixa | Criar arquivo CONTRIBUTING.md | #50 |
| Baixa | Criar arquivo de exemplo YAML para entrada de dados | #40 |
| Baixa | Criar steering para princípios SOLID e Clean Architecture | #39 |

**Roadmap resumido:**
1. Implementar as camadas `domain`, `application` e `api` seguindo a Clean Architecture definida
2. Entregar o fluxo completo de importação → persistência → disponibilização dos endpoints mock
3. Integrar com serviço de IA para geração dinâmica de payloads
4. Adicionar validações e tratamento de erros
5. Documentar e preparar para apresentação

---

*Documento gerado com base em: `.kiro/steering/product.md`, `README.md`, `docs/architecture-diagram.md`, `docs/database-schema.md`, entidades JPA implementadas em `src/` e issues do projeto no GitHub.*
