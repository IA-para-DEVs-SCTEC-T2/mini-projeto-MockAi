---
inclusion: auto
description: Informações sobre regras de negócio, objetivos e funcionalidades principais do sistema MockAI. Utilize quando precisar entender o propósito do sistema, suas funcionalidades, restrições ou glossário de termos do domínio.
---

# MockAI

Este documento descreve as informações essenciais sobre o produto, seu contexto e objetivos.

## Visão Geral

Muitas vezes, os desenvolvedores que precisam implementar uma integração com alguma API Rest, não tem, por qualquer motivo que seja, o acesso imediato a essa API.

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
| Inserir documentação | Fornecer a documentação necessária para que a MockAI consiga criar a simulação de uma API Rest, com funcionameto real dos endpoints. |
| Criar o mock | Implementar os endpoints e payloads, com base na documentação fornecida. |
| Endpoints mockados | Endpoints prontos para uso e implementação de clients consumidores. |

## Regras de Negócio

- Documentação a ser fornecida: a documentação deve seguir o padrão Swagger/OpenAPI 3.0 ou superior, no formato json.
- Para endpoints que contenham payload no body da resposta, o conteúdo do payload será gerado dinamicamente com auxílio de IA.
- A cada inserção de documentação Swagger, todos os endpoints já existentes e rodando serão deletados, e a MockAI criará novos endpoint com base na documentação inserida.

## Restrições e Limitações

- Ao criar o mock de uma API Rest, nenhum método de autenticação será disponibilizado.
- Não será possível recuperar um histórico de APIs mockadas, sendo que o mock rodando será sempre relativo a última documentação Swagger inserida.

## Glossário

| Termo | Definição |
|-------|-----------|
| API Rest | Interface de Programação de Aplicações (API) que segue os princípios de design da arquitetura REST (Representational State Transfer). Permite a comunicação entre sistemas distribuídos pela internet usando métodos HTTP padronizados (GET, POST, PUT, DELETE) para acessar e manipular recursos, com foco em simplicidade, escalabilidade e formato de dados geralmente em JSON. |
| Mock | Objeto ou estrutura simulada usada em programação para imitar o comportamento de componentes reais (como bancos de dados ou APIs). |
| OpenAPI | Formato padronizado, legível por humanos e máquinas, para descrever APIs RESTful. Ela define endpoints, operações (GET, POST), parâmetros e métodos de autenticação em formato JSON, facilitando a documentação, geração de código e testes. |
| Swagger | Conjunto de ferramentas open-source e comerciais (como Swagger UI, Editor, Codegen) que utilizam a especificação OpenAPI para projetar, construir, documentar e consumir APIs. |

