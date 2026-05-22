# Product Requirements Document (PRD) — MockAI

**Versão:** 0.1.0-SNAPSHOT  
**Data:** 2026-05-21  
**Status:** Em desenvolvimento  
**Equipe:** Grupo 3 — IA para DEVs SCTEC T2

---

## 1. Visão Geral do Produto

O **MockAI** é um gerador inteligente de APIs mock que transforma arquivos Swagger/OpenAPI em APIs simuladas locais. A partir de uma documentação no padrão OpenAPI (JSON), o sistema processa o contrato, persiste as definições de endpoints e disponibiliza rotas HTTP prontas para consumo imediato — sem dependência de serviços externos reais.

A integração com o serviço de IA Groq permite que os payloads de resposta sejam gerados dinamicamente, tornando as simulações mais realistas e úteis para desenvolvimento e testes.

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
| F1 | **Importação de documentação Swagger/OpenAPI** | Receber um arquivo Swagger/OpenAPI (JSON) via `POST /import` e processar seu conteúdo |
| F2 | **Endpoints mockados dinâmicos** | Disponibilizar automaticamente as rotas HTTP correspondentes ao contrato importado, prontas para consumo imediato sem reinicialização |
| F3 | **Geração de payloads com IA** | Integração com Groq para gerar respostas dinâmicas e realistas nos endpoints mockados; fallback estático em caso de falha |
| F4 | **Listagem de endpoints disponíveis** | `GET /endpoints` lista todos os endpoints mockados ativos (path, método HTTP e descrição) |
| F5 | **Verificação de conectividade com IA** | `GET /test-ai-connection` verifica se a integração com o Groq está operacional |
| F6 | **Documentação interativa** | Swagger UI disponível para explorar e testar os endpoints da própria API MockAI |

---

## 6. Regras de Negócio

| ID | Regra |
|----|-------|
| RN01 | A documentação fornecida deve seguir o padrão Swagger/OpenAPI 3.0 ou superior, no formato JSON |
| RN02 | Para endpoints com payload no body da resposta, o conteúdo é gerado dinamicamente via Groq. Em caso de falha na IA, aplica-se fallback estático baseado no schema |
| RN03 | A cada nova importação de documentação Swagger, todos os endpoints existentes são deletados e recriados com base na nova documentação |
| RN04 | Nenhum método de autenticação é disponibilizado nos endpoints mockados |
| RN05 | Não é possível recuperar histórico de APIs mockadas — o mock ativo sempre reflete a última documentação importada |
| RN06 | Arquivos inválidos (não JSON, sem campos obrigatórios, ou vazios) devem ser rejeitados com HTTP 400 e mensagem descritiva |
| RN07 | Os endpoints mockados devem estar disponíveis imediatamente após o processamento do contrato, sem necessidade de reinicialização |
| RN08 | Os endpoints mockados retornam exclusivamente respostas de sucesso (2xx). Apenas o primeiro status de sucesso definido na spec é persistido (prioridade: 200 → 201 → 204 → primeiro disponível). Respostas de erro (4xx, 5xx) são ignoradas |
| RN09 | Path parameters com formato `uuid` são registrados com constraint de regex no Spring MVC, garantindo que rotas literais (ex: `/owner/all`) não sejam capturadas por endpoints parametrizados |

---

## 7. Requisitos Funcionais

### RF01 — Recebimento de arquivo Swagger
- O sistema deve expor `POST /import` recebendo um arquivo Swagger/OpenAPI via `multipart/form-data`
- O arquivo deve ser processado e todas as rotas (path + método HTTP), schemas e informações relevantes devem ser extraídas e persistidas no banco de dados

### RF02 — Validação do arquivo Swagger
- Validar se o arquivo recebido está no formato JSON
- Verificar presença dos campos obrigatórios (`openapi`/`swagger`, `info`, `paths`)
- Arquivos inválidos, vazios ou com estrutura incorreta devem ser rejeitados com HTTP 400 e mensagem descritiva
- A lógica de validação deve estar isolada em componente dedicado

### RF03 — Criação de endpoints dinâmicos
- Após o processamento do contrato, registrar dinamicamente as rotas mock correspondentes
- Suportar os métodos HTTP: GET, POST, PUT, PATCH e DELETE
- Os endpoints devem estar disponíveis imediatamente após o processamento, sem reinicialização

### RF04 — Geração de retornos com IA
- Integrar com o Groq para gerar payloads de resposta
- O client de IA recebe o schema do endpoint e retorna resposta gerada no formato esperado
- Token de autenticação configurado via variável de ambiente, sem exposição no código-fonte
- Erros de comunicação com a IA devem ser tratados com fallback estático

### RF05 — Listagem de endpoints disponíveis
- `GET /endpoints` retorna todos os endpoints mockados ativos
- A resposta inclui path, método HTTP e descrição de cada endpoint
- Retorna lista vazia quando não houver registros (sem erro)

### RF06 — Verificação de conectividade com IA
- `GET /test-ai-connection` verifica se a integração com o Groq está operacional
- Retorna HTTP 200 quando funcional e HTTP 503 quando indisponível

---

## 8. Requisitos Não Funcionais

| ID | Categoria | Requisito |
|----|-----------|-----------|
| RNF01 | Arquitetura | O sistema deve seguir Clean Architecture e Hexagonal Architecture, com separação clara entre `domain`, `application`, `infrastructure` e `adapter` |
| RNF02 | Arquitetura | As dependências entre camadas devem sempre apontar para dentro (domain não depende de nenhuma outra camada) |
| RNF03 | Persistência | Banco de dados H2 in-memory (`jdbc:h2:mem:testdb`), adequado para desenvolvimento e testes |
| RNF04 | Disponibilidade | Endpoints mockados registrados e disponibilizados sem necessidade de reinicialização da aplicação |
| RNF05 | Segurança | Tokens e credenciais de APIs externas configurados via variáveis de ambiente; nunca expostos no código-fonte |
| RNF06 | Documentação | API do MockAI documentada e acessível via Swagger UI em `/mockai/swagger-ui.html` |
| RNF07 | Manutenibilidade | Código deve seguir os princípios SOLID e boas práticas Java/Spring Boot |
| RNF08 | Portabilidade | Executável com Java 17+ e Maven 3.9.7+, sem dependências de infraestrutura externa além da JVM |

---

## 9. Fluxo Principal do Usuário

```
1. Desenvolvedor obtém a documentação Swagger/OpenAPI (JSON) da API que precisa consumir

2. Desenvolvedor envia o arquivo via POST /mockai/import (multipart/form-data)

3. MockAI valida o arquivo:
   ├── Extensão inválida → HTTP 400 "Arquivo com extensão inválida, deve ser .json"
   ├── JSON inválido ou não reconhecido como OpenAPI → HTTP 400 com mensagem descritiva
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

7. Desenvolvedor consulta os endpoints disponíveis via GET /mockai/endpoints

8. Desenvolvedor verifica a conectividade com a IA via GET /mockai/test-ai-connection
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
│  │ Adapter  │→ │ Application │→ │     Domain       │   │
│  │  (web)   │  │             │  │  (modelos+ports) │   │
│  └──────────┘  └─────────────┘  └──────────────────┘   │
│       ↑                                  ↑              │
│  ┌────┴─────────────────────────────────┴────────────┐  │
│  │              Camada Infrastructure                 │  │
│  │  ┌──────────────┐  ┌──────────┐  ┌─────────────┐ │  │
│  │  │ Repositórios │  │ Gateway  │  │   Adapters  │ │  │
│  │  │     JPA      │  │   IA     │  │ Persistência│ │  │
│  │  └──────┬───────┘  └────┬─────┘  └─────────────┘ │  │
│  └─────────┼───────────────┼───────────────────────┘  │
└────────────┼───────────────┼────────────────────────────┘
             ↓               ↓
        ┌─────────┐    ┌──────────────┐
        │H2 in-   │    │ Groq (IA)    │
        │ memory  │    │ api.groq.com │
        └─────────┘    └──────────────┘
```

**Atores externos:**
- **Desenvolvedor** — importa a documentação Swagger e consome os endpoints mockados
- **Consumidor do Mock** — testa e valida a integração com os endpoints mockados
- **Groq** — serviço externo de IA que gera payloads de resposta dinamicamente

**Modelo de dados (6 entidades JPA):**

| Entidade | Descrição |
|----------|-----------|
| `api_specification` | Representa a documentação Swagger importada |
| `endpoint_definition` | Cada endpoint (path + método HTTP) do contrato |
| `tag` | Agrupadores de endpoints definidos no Swagger |
| `endpoint_tags` | Tabela de junção N:N entre endpoints e tags |
| `path_parameter` | Parâmetros de path de cada endpoint (com `param_in`, `type`, `format`) |
| `endpoint_response` | Primeiro status de sucesso (2xx) por endpoint, com schema serializado |

**Fluxo de dados:**
```
Adapter → Application → Domain ← Infrastructure
```

---

## 11. Stack Tecnológica

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| Java | 17 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework base da aplicação |
| Spring Web MVC | (via Spring Boot) | Exposição de endpoints REST e roteamento dinâmico |
| Spring Data JPA | (via Spring Boot) | Persistência de dados |
| Spring AI | 2.0.0-M6 (BOM) | Abstração sobre cliente de IA (Groq via endpoint OpenAI-compatível) |
| H2 Database | runtime | Banco de dados in-memory |
| SpringDoc OpenAPI | 3.0.2 | Documentação Swagger UI da própria API |
| Lombok | (via Spring Boot) | Redução de boilerplate Java |
| Maven | 3.9.7+ | Gerenciamento de build e dependências |
| Groq | serviço externo | Motor de inferência de IA — modelo `llama-3.1-8b-instant` |

**URLs de desenvolvimento:**

| Recurso | URL |
|---------|-----|
| Base URL | `http://localhost:8080/mockai` |
| Swagger UI | `http://localhost:8080/mockai/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/mockai/v3/api-docs` |
| H2 Console | `http://localhost:8080/mockai/h2-console` |

---

## 12. Critérios de Sucesso

| # | Critério | Como medir |
|---|----------|------------|
| CS01 | Importação de Swagger funcional | `POST /import` processa arquivo OpenAPI válido e persiste todos os endpoints no banco |
| CS02 | Endpoints mockados disponíveis | Após importação, as rotas definidas no contrato respondem a requisições HTTP sem reinicialização |
| CS03 | Validação de entrada | Arquivos inválidos são rejeitados com HTTP 400 e mensagem descritiva |
| CS04 | Geração de payload com IA | Endpoints com schema de resposta retornam payload gerado pelo Groq; fallback estático em caso de falha |
| CS05 | Listagem de endpoints | `GET /endpoints` retorna lista de endpoints mockados ativos com path, método e descrição |
| CS06 | Verificação de IA | `GET /test-ai-connection` retorna 200 quando o Groq está acessível e 503 quando indisponível |
| CS07 | Documentação acessível | Swagger UI da própria API está disponível e funcional |

---

## 13. Limitações Atuais

| # | Limitação |
|---|-----------|
| L01 | **Sem autenticação nos mocks** — endpoints mockados não implementam nenhum mecanismo de autenticação ou autorização |
| L02 | **Sem histórico de mocks** — apenas o mock da última documentação importada está disponível; sem versionamento ou recuperação de specs anteriores |
| L03 | **Banco in-memory** — dados são perdidos ao reiniciar a aplicação (H2 in-memory); sem persistência entre sessões |
| L04 | **Formato de entrada restrito** — apenas arquivos JSON são suportados; YAML não está contemplado |
| L05 | **Sem suporte a autenticação na spec** — campos `securitySchemes` e `security` do OpenAPI são ignorados no mock |
| L06 | **Apenas respostas de sucesso** — endpoints mockados retornam exclusivamente respostas 2xx; cenários de erro (4xx, 5xx) não são simulados |
| L07 | **Dependência de conectividade com Groq** — geração de payloads realistas requer acesso à API do Groq; sem conectividade, aplica-se fallback estático |


---

## 14. User Stories

User stories concluídas, extraídas das issues do projeto no GitHub:

---

### US-62 — Importar especificação Swagger e persistir dados no banco de dados

**Como** desenvolvedor integrador,  
**quero** importar um arquivo de especificação Swagger via endpoint `POST /import`,  
**para** que o sistema extraia as informações da especificação e persista os dados no banco de dados.

**Critérios de aceitação:**

- Dado que o endpoint `POST /import` está disponível, quando envio uma requisição com arquivo Swagger válido, o sistema deve deserializar o conteúdo, extrair endpoints/parâmetros/respostas, persistir no banco e retornar confirmação de sucesso
- Os dados persistidos devem respeitar os relacionamentos e a modelagem do banco de dados
- O endpoint deve estar acessível e processar a requisição corretamente

**Status:** ✅ Concluída

---

### US-22 — Validar arquivo Swagger recebido no endpoint de importação

**Como** desenvolvedor integrador,  
**quero** que o sistema valide o arquivo recebido pelo endpoint de importação,  
**para** garantir que apenas especificações Swagger válidas em formato JSON sejam processadas.

**Critérios de aceitação:**

- Dado que o endpoint `POST /import` recebeu um arquivo JSON válido com todos os parâmetros obrigatórios de uma especificação Swagger, o sistema deve aceitar o arquivo para processamento
- Dado que o arquivo não é um JSON válido, o sistema deve rejeitar e retornar mensagem de erro informando que o formato é inválido
- Dado que o arquivo é JSON válido mas não contém os parâmetros obrigatórios do Swagger (`openapi`, `info`, `paths`), o sistema deve rejeitar e retornar mensagem de erro informando quais parâmetros estão ausentes

**Status:** ✅ Concluída

---

### US-24 — Listar endpoints mockados disponíveis

**Como** desenvolvedor consumidor de API,  
**quero** listar todos os endpoints mockados disponíveis,  
**para** saber quais rotas posso utilizar durante o desenvolvimento e testes de integração.

**Critérios de aceitação:**

- Dado que uma especificação Swagger foi importada, quando faço uma requisição para listar os endpoints, o sistema deve retornar todos os endpoints salvos com seus respectivos métodos HTTP e paths
- Dado que nenhuma especificação foi importada, o sistema deve retornar lista vazia ou mensagem informativa

**Status:** ✅ Concluída

---

### US-64 — Disponibilizar endpoints mockados com respostas realistas geradas por IA

**Como** desenvolvedor consumidor de API,  
**quero** acessar os endpoints mockados e obter respostas realistas geradas por IA,  
**para** simular o comportamento real da API durante o desenvolvimento e testes de integração.

**Critérios de aceitação:**

- Dado que uma especificação Swagger foi importada, quando acesso um endpoint mockado, o sistema deve obter o schema de resposta, enviar para a IA gerar um exemplo realista e retornar a resposta gerada
- Múltiplas requisições ao mesmo endpoint devem gerar respostas dinâmicas diferentes, respeitando o schema definido
- Todos os endpoints definidos na especificação devem estar acessíveis e responder com o método HTTP correto (GET, POST, PUT, DELETE)

**Status:** ✅ Concluída

---

## 15. Próximos Passos

Novas funcionalidades planejadas para evolução do produto:

| # | Funcionalidade | Descrição |
|---|----------------|-----------|
| P1 | **Suporte a arquivo YAML** | Permitir importação de especificações Swagger/OpenAPI no formato YAML, além do JSON já suportado |
| P2 | **Banco de dados PostgreSQL** | Substituir o H2 in-memory por PostgreSQL, eliminando a perda de dados ao reiniciar a aplicação e habilitando persistência entre sessões |
| P3 | **Suporte a múltiplas especificações** | Permitir que múltiplas especificações de API coexistam simultaneamente, possibilitando o acesso a endpoints de diferentes APIs sem a necessidade de reimportar o arquivo Swagger a cada troca |


