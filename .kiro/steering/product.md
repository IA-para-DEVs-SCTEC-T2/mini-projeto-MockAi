---
inclusion: always
---

# MockAI

Este documento descreve as informações essenciais sobre o produto, seu contexto e objetivos.

## Visão Geral

Muitas vezes, os desenvolvedores que precisam implementar uma integração com alguma API Rest não têm, por qualquer motivo que seja, o acesso imediato a essa API.

A MockAI cria um ambiente mock dessa API, disponibilizando os endpoints e payloads necessários para que o trabalho de implementação não fique esperando o ambiente real estar disponível.

Dessa forma, ganha-se agilidade na implementação e testes de consumo de APIs Rest.

## Objetivos

- Simular o funcionamento real de uma API Rest que precisa ser consumida.
- Disponibilizar de forma rápida e dinâmica os endpoints e payloads de uma API Rest.
- Auxiliar o trabalho de implementação e testes no consumo de uma API Rest.

## Público-Alvo

Desenvolvedores que precisam implementar o consumo de alguma API Rest.

## Funcionalidades Principais

| Funcionalidade | Descrição |
|----------------|-----------|
| Inserir documentação | Fornecer a documentação necessária para que a MockAI consiga criar a simulação de uma API Rest, com funcionamento real dos endpoints. |
| Criar o mock | Implementar os endpoints e payloads, com base na documentação fornecida. |
| Endpoints mockados | Endpoints prontos para uso e implementação de clients consumidores. |
| Listar endpoints | Consultar os endpoints mockados ativos via `GET /endpoints`. |
| Verificar IA | Verificar a conectividade com o serviço de IA via `GET /test-ai-connection`. |

## Regras de Negócio

- A documentação a ser fornecida deve seguir o padrão Swagger/OpenAPI 3.0 ou superior, no formato JSON.
- Para endpoints que contenham payload no body da resposta, o conteúdo do payload será gerado dinamicamente com auxílio do Groq (serviço de IA). Em caso de falha na IA, um fallback estático baseado no schema é utilizado.
- A cada inserção de documentação Swagger, todos os endpoints já existentes e rodando serão deletados, e a MockAI criará novos endpoints com base na documentação inserida.
- **Os endpoints mockados retornam exclusivamente respostas de sucesso (2xx).** O sistema seleciona a primeira resposta de sucesso definida na spec (prioridade: 200 → 201 → 204 → primeira disponível). Respostas de erro (4xx, 5xx) definidas na spec são ignoradas e não são simuladas.

## Restrições e Limitações

- Ao criar o mock de uma API Rest, nenhum método de autenticação será disponibilizado.
- Não será possível recuperar um histórico de APIs mockadas; o mock em execução será sempre relativo à última documentação Swagger inserida.

## Glossário

| Termo | Definição |
|-------|-----------|
| API Rest | Interface de Programação de Aplicações que segue os princípios da arquitetura REST. Permite a comunicação entre sistemas usando métodos HTTP padronizados (GET, POST, PUT, DELETE), com dados geralmente em formato JSON. |
| Mock | Objeto ou estrutura simulada usada em programação para imitar o comportamento de componentes reais, como bancos de dados ou APIs. |
| OpenAPI | Formato padronizado para descrever APIs RESTful. Define endpoints, operações, parâmetros e respostas em formato JSON ou YAML, facilitando documentação, geração de código e testes. |
| Swagger | Conjunto de ferramentas open-source (como Swagger UI, Editor, Codegen) que utilizam a especificação OpenAPI para projetar, construir, documentar e consumir APIs. |
| Groq | Serviço de inferência de IA de alta velocidade utilizado pelo MockAI para geração dinâmica de payloads de resposta. Acessado via API compatível com OpenAI em `api.groq.com`. |
