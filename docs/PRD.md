# PRD — MockAI

> Versão: 1.0 | Última atualização: 2026-05-11

---

## 1. Visão Geral do Projeto

**MockAI** é um gerador de APIs mock que transforma arquivos Swagger/OpenAPI em endpoints simulados locais. O objetivo é permitir que times de desenvolvimento e QA trabalhem de forma independente de serviços externos, acelerando ciclos de desenvolvimento e testes.

---

## 2. Objetivo Principal

Permitir que desenvolvedores importem uma especificação OpenAPI e obtenham imediatamente uma API mock funcional, com respostas configuráveis por endpoint, sem necessidade de subir serviços reais.

---

## 3. Problema que o Sistema Resolve

Durante o desenvolvimento, equipes frequentemente dependem de APIs externas ou de outros times para avançar. Essa dependência gera bloqueios, atrasos e dificuldades para testar cenários de erro. O MockAI elimina essa dependência ao simular as APIs localmente a partir da própria documentação OpenAPI.

---

## 4. Funcionalidades Principais

| # | Funcionalidade | Descrição |
|---|---|---|
| F1 | Importação de spec OpenAPI | Receber e persistir uma especificação Swagger/OpenAPI |
| F2 | Extração de endpoints | Parsear e armazenar todos os endpoints definidos na spec |
| F3 | Configuração de respostas mock | Associar respostas simuladas (status code, content-type, schema) a cada endpoint |
| F4 | Execução de endpoints mock | Responder requisições HTTP simulando o comportamento definido na spec |
| F5 | Listagem de specs e endpoints | Consultar specs importadas e seus endpoints via API REST |
| F6 | Documentação interativa | Expor Swagger UI para exploração e teste dos endpoints do próprio MockAI |

---

## 5. Regras de Negócio Essenciais

- Uma `ApiSpecification` deve ter `title`, `version` e `baseUrl` obrigatórios.
- Um `EndpointDefinition` deve ter `path` e `httpMethod` obrigatórios e estar vinculado a uma spec.
- Um `EndpointResponse` deve ter `statusCode` e `contentType` obrigatórios.
- Um `PathParameter` deve ter `name`, `type` e `required` obrigatórios.
- A exclusão de uma spec remove em cascata todos os endpoints, parâmetros e respostas associados.
- A exclusão de um endpoint remove em cascata seus parâmetros e respostas.
- Tags são entidades independentes; a remoção de um endpoint não exclui as tags.
- Não é permitido cadastrar dois endpoints com o mesmo `path` + `httpMethod` dentro da mesma spec.

---

## 6. Fluxo Básico de Funcionamento

```
1. Desenvolvedor envia arquivo OpenAPI (YAML/JSON) via API REST
2. Sistema parseia a spec e persiste ApiSpecification + EndpointDefinitions + Tags + PathParameters + EndpointResponses
3. Desenvolvedor consulta os endpoints disponíveis
4. Desenvolvedor (ou sistema de testes) faz requisições aos endpoints mock
5. MockAI retorna a resposta configurada para aquele endpoint (status code + body simulado)
```

**Fluxo de erro:** Se a spec enviada for inválida ou estiver mal formatada, o sistema retorna `400 Bad Request` com descrição do erro.

---

## 7. Stack / Tecnologias Utilizadas

| Camada | Tecnologia | Versão |
|---|---|---|
| Linguagem | Java | 17 |
| Framework | Spring Boot | 4.0.6 |
| Persistência | Spring Data JPA + Hibernate | — |
| Banco de dados | H2 (in-memory) | runtime |
| Documentação API | SpringDoc OpenAPI (Swagger UI) | 3.0.2 |
| Build | Maven | 3.9.7+ |
| Utilitários | Lombok | — |
| Testes | Spring Boot Test + JPA Test | — |

---

## 8. Estrutura Inicial da Arquitetura

O projeto segue **Clean Architecture** com separação em 4 camadas:

```
com.ia.para.devs.mockai/
├── domain/           # Modelos de domínio puros e interfaces (ports)
├── application/      # Casos de uso e orquestração de regras de negócio
├── infrastructure/   # Adaptadores: JPA entities, repositories, mappers, gateways
│   └── persistence/
│       ├── entity/
│       ├── repository/
│       └── mapper/
└── api/              # Controllers REST, DTOs (request/response), tratamento de exceções
    ├── controller/
    ├── dto/
    └── exception/
```

**Endpoints base:** `http://localhost:8080/mockai`  
**Swagger UI:** `http://localhost:8080/mockai/swagger-ui.html`  
**H2 Console:** `http://localhost:8080/mockai/h2-console`

---

## 9. User Stories Principais

| ID | User Story | Prioridade |
|---|---|---|
| US-01 | Como desenvolvedor, quero importar um arquivo OpenAPI para que o sistema gere os endpoints mock automaticamente. | Alta |
| US-02 | Como desenvolvedor, quero listar todas as specs importadas para gerenciar as APIs disponíveis. | Alta |
| US-03 | Como desenvolvedor, quero consultar os endpoints de uma spec para saber quais rotas estão disponíveis. | Alta |
| US-04 | Como desenvolvedor, quero fazer uma requisição a um endpoint mock para receber a resposta simulada configurada. | Alta |
| US-05 | Como QA, quero configurar diferentes respostas (200, 404, 500) para um endpoint para testar cenários de erro. | Média |
| US-06 | Como desenvolvedor, quero deletar uma spec importada para remover APIs que não são mais necessárias. | Média |
| US-07 | Como desenvolvedor, quero acessar o Swagger UI do MockAI para explorar e testar a API de gerenciamento. | Baixa |

---

## 10. Próximos Passos

- [ ] Implementar parser de arquivos OpenAPI (YAML/JSON) na camada de aplicação
- [ ] Implementar controllers REST para CRUD de specs e endpoints
- [ ] Implementar o motor de execução de respostas mock dinâmicas
- [ ] Adicionar suporte a query parameters e headers nas definições de endpoint
- [ ] Integração opcional com IA para geração de respostas mock mais realistas
- [ ] Adicionar suporte a banco de dados persistente (PostgreSQL) para ambientes não-dev
- [ ] Implementar autenticação básica para proteger a API de gerenciamento
- [ ] Adicionar testes de integração e cobertura mínima de 80%
