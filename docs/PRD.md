# Product Requirements Document (PRD) — MockAI

**Versão:** 0.1.0-SNAPSHOT  
**Data:** 2026-05-13  
**Status:** Em desenvolvimento  
**Equipe:** Grupo 3 — IA para DEVs SCTEC T2

---

## 1. Visão Geral do Produto

O **MockAI** é um gerador inteligente de APIs mock que transforma arquivos Swagger/OpenAPI em APIs simuladas locais. A partir de uma documentação no padrão OpenAPI (JSON), o sistema processa o contrato, persiste as definições de endpoints e disponibiliza rotas HTTP prontas para consumo imediato — sem dependência de serviços externos reais.

A integração opcional com um serviço de IA externo (Groq) permite que os payloads de resposta sejam gerados dinamicamente, tornando as simulações mais realistas e úteis para desenvolvimento e testes.

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
| F4 | **Geração de payloads com IA** | Integração com serviço de IA externo (Groq) para gerar respostas dinâmicas e realistas nos endpoints mockados |
| F5 | **Listagem de endpoints disponíveis** | Endpoint `GET /endpoints` para listar todos os endpoints mockados ativos (path, método HTTP e descrição) |
| F6 | **Verificação de conectividade com IA** | Endpoint `GET /test-ai-connection` para verificar se a integração com o Groq está operacional |
| F7 | **Documentação interativa** | Swagger UI disponível para explorar e testar os endpoints da própria API MockAI |

---

## 6. Regras de Negócio

| ID | Regra |
|----|-------|
| RN01 | A documentação fornecida deve seguir o padrão Swagger/OpenAPI 3.0 ou superior, no formato JSON |
| RN02 | Para endpoints que contenham payload no body da resposta, o conteúdo será gerado dinamicamente com auxílio de IA (Groq). Em caso de falha na IA, um fallback estático baseado no schema é utilizado |
| RN03 | A cada nova importação de documentação Swagger, todos os endpoints existentes são deletados e recriados com base na nova documentação |
| RN04 | Nenhum método de autenticação é disponibilizado nos endpoints mockados |
| RN05 | Não é possível recuperar um histórico de APIs mockadas — o mock ativo sempre reflete a última documentação importada |
| RN06 | Arquivos inválidos (não JSON, sem campos obrigatórios do contrato, ou vazios) devem ser rejeitados com erro HTTP 400 e mensagem descritiva |
| RN07 | Os endpoints mockados devem estar disponíveis imediatamente após o processamento do contrato, sem necessidade de reinicialização |
| RN08 | **Os endpoints mockados retornam exclusivamente respostas de sucesso (2xx).** Apenas o primeiro status de sucesso definido na spec é persistido e utilizado (prioridade: 200 → 201 → 204 → primeiro disponível). Respostas de erro (4xx, 5xx) são ignoradas |
| RN09 | Path parameters com formato `uuid` são registrados com constraint de regex no Spring MVC, garantindo que rotas literais (ex: `/owner/all`) não sejam capturadas por endpoints parametrizados |

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
- O sistema deve integrar com o serviço de IA Groq para gerar payloads de resposta
- O client de IA deve receber o schema do endpoint e retornar uma resposta gerada no formato esperado
- O token de autenticação da API de IA deve ser configurado via variável de ambiente ou `application.properties`, sem exposição no código-fonte
- Erros de comunicação com a API de IA devem ser tratados e propagados adequadamente

### RF05 — Listagem de endpoints disponíveis
- O sistema deve expor o endpoint `GET /endpoints` que retorna todos os endpoints mockados ativos
- A resposta deve incluir path, método HTTP e descrição de cada endpoint
- Deve retornar lista vazia quando não houver registros (sem erro)

### RF06 — Verificação de conectividade com IA
- O sistema deve expor o endpoint `GET /test-ai-connection` para verificar se a integração com o Groq está operacional
- Retorna HTTP 200 quando a conexão está funcional e HTTP 503 quando indisponível

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
   ├── Extensão inválida → retorna HTTP 400 "Arquivo com extensão inválida, deve ser .json"
   ├── JSON inválido ou não reconhecido como OpenAPI → retorna HTTP 400 com mensagem descritiva
   └── Válido → prossegue

4. MockAI processa o contrato:
   ├── Deleta todos os endpoints e specs existentes (RN03)
   ├── Extrai title, version, description, base_url e components da spec
   ├── Extrai tags globais
   ├── Extrai todos os paths e métodos HTTP
   ├── Extrai parâmetros de path (com tipo e formato para roteamento correto)
   ├── Persiste apenas o primeiro status de sucesso (2xx) de cada endpoint
   └── Persiste tudo no banco H2 em uma única transação

5. MockAI registra dinamicamente as rotas mock no Spring MVC:
   ├── Path parameters com formato uuid recebem constraint de regex
   └── Rotas ficam disponíveis imediatamente, sem reinicialização

6. Desenvolvedor consome os endpoints mockados:
   ├── Faz requisições HTTP para os endpoints registrados
   ├── MockAI consulta o schema do endpoint no banco
   ├── MockAI solicita ao Groq a geração do payload de resposta via IA
   ├── Em caso de falha na IA → aplica fallback estático baseado no schema
   └── MockAI retorna a resposta com o status de sucesso definido na spec (2xx)
   ⚠️  Apenas respostas de sucesso são retornadas — cenários de erro não são simulados

7. Desenvolvedor pode consultar os endpoints disponíveis via GET /mockai/endpoints

8. Desenvolvedor pode verificar a conectividade com a IA via GET /mockai/test-ai-connection
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
| CS04 | Geração de payload com IA | Endpoints com schema de resposta retornam payload gerado dinamicamente pelo Groq; fallback estático em caso de falha |
| CS05 | Listagem de endpoints | `GET /endpoints` retorna a lista de endpoints mockados ativos com path, método e descrição |
| CS06 | Verificação de IA | `GET /test-ai-connection` retorna 200 quando o Groq está acessível e 503 quando indisponível |
| CS07 | Documentação acessível | Swagger UI da própria API está disponível e funcional |
| CS08 | Arquitetura limpa | Nenhuma dependência da camada `domain` para camadas externas; camadas isoladas e testáveis |

---

## 13. Limitações Atuais

| # | Limitação |
|---|-----------|
| L01 | **Sem autenticação nos mocks** — os endpoints mockados não implementam nenhum mecanismo de autenticação ou autorização |
| L02 | **Sem histórico de mocks** — apenas o mock da última documentação importada está disponível; não há versionamento ou recuperação de specs anteriores |
| L03 | **Banco in-memory** — os dados são perdidos ao reiniciar a aplicação (H2 in-memory); não há persistência entre sessões |
| L04 | **Formato de entrada restrito** — apenas arquivos no formato JSON são suportados; YAML não está contemplado |
| L05 | **Sem suporte a autenticação na spec** — campos de `securitySchemes` e `security` do OpenAPI são ignorados no mock |
| L06 | **Apenas respostas de sucesso** — os endpoints mockados retornam exclusivamente respostas 2xx. Cenários de erro (4xx, 5xx) definidos na spec não são simulados |
| L07 | **Dependência de conectividade com Groq** — a geração de payloads realistas requer acesso à API do Groq. Sem conectividade, o sistema aplica fallback estático baseado no schema |

---

## 14. Próximos Passos

Com base no backlog e nas issues abertas do projeto:

| Prioridade | Item | Status |
|------------|------|--------|
| ✅ Concluído | Criar endpoint `POST /import` para recebimento do arquivo Swagger | Implementado |
| ✅ Concluído | Implementar validação do arquivo Swagger/OpenAPI | Implementado |
| ✅ Concluído | Implementar criação de endpoints dinâmicos | Implementado |
| ✅ Concluído | Criar client HTTP para integração com IA (Groq) | Implementado |
| ✅ Concluído | Implementar geração de retornos com IA | Implementado |
| ✅ Concluído | Criar endpoint `GET /endpoints` para listagem dos endpoints mockados | Implementado |
| ✅ Concluído | Criar endpoint `GET /test-ai-connection` para verificação de conectividade com IA | Implementado |

**Estado atual do sistema:**
- Fluxo completo de importação → persistência → disponibilização dos endpoints mock está funcional
- Integração com Groq para geração dinâmica de payloads está ativa, com fallback estático em caso de falha
- Persistência de `path_parameter` inclui todos os campos OpenAPI (`param_in`, `description`, `type`, `format`) para roteamento correto de endpoints com parâmetros de formatos distintos (ex: `uuid` vs string simples)
- Persistência de `endpoint_response` salva apenas o primeiro status de sucesso (2xx); respostas de erro são ignoradas
- Respostas da IA são retornadas como JSON puro (delimitadores Markdown removidos automaticamente)
- `api_specification` persiste o bloco `components` serializado como JSON para resolução de `$ref` durante a geração de payloads
- Endpoints mockados retornam **exclusivamente respostas de sucesso (2xx)** — não há simulação de cenários de erro

---

*Documento gerado com base em: `.kiro/steering/product.md`, `README.md`, `docs/architecture-diagram.md`, `docs/database-schema.md`, entidades JPA implementadas em `src/` e issues do projeto no GitHub.*
