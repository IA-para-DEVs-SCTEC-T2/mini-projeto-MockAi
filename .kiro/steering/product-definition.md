---
inclusion: auto
name: product-definition
description: Definição do produto MockAi e suas regras de negócio. Use quando a implementação envolver lógicas e regras de negócio da aplicação, e ao criar ou atualizar issues, tasks, tarefas, demandas e atividades no GitHub.
---

# MockAi — Definição do Produto

## O que é o MockAi

MockAi é um servidor mock inteligente que permite simular completamente APIs backend antes da implementação real. A partir de um arquivo Swagger/OpenAPI, o sistema gera automaticamente endpoints funcionais com respostas realistas e contextualizadas, eliminando bloqueios no desenvolvimento e viabilizando testes de integração robustos sem depender de serviços externos.

## Objetivos

- Permitir simulação completa de APIs backend antes da implementação real
- Facilitar testes de integração robustos sem depender de serviços externos
- Habilitar desenvolvimento paralelo onde equipes trabalham simultaneamente sem bloqueios
- Eliminar gargalos ao fornecer mocks funcionais instantaneamente

## Como Funciona

1. **Upload do contrato**: O desenvolvedor faz upload de um arquivo Swagger ou OpenAPI através de uma interface simples
2. **Análise do contrato**: O sistema analisa endpoints, métodos HTTP e schemas de resposta definidos no arquivo
3. **Criação do servidor mock**: MockAi cria automaticamente a estrutura do servidor com todas as rotas definidas no Swagger recebido
4. **Geração de respostas com IA**: A integração com IA gera dados realistas e contextualizados para cada endpoint
5. **Disponibilização imediata**: Os endpoints do Swagger ficam acessíveis e retornam respostas realistas prontas para uso

## Uso de Inteligência Artificial

A IA é o núcleo da qualidade das respostas geradas pelo MockAi:

- **Respeito a tipos e validações**: A IA interpreta os schemas para gerar dados respeitando tipos, formatos e constraints definidos no contrato
- **Consistência entre endpoints**: Respostas coerentes com dados relacionados, mantendo consistência entre diferentes endpoints do mesmo contrato
- **Múltiplos cenários**: Geração de variações realistas de dados para simular diferentes cenários de uso
- **Evolução contínua**: A integração com IA permite evolução contínua na qualidade e realismo das respostas geradas

## Regras de Negócio

### Processamento do Contrato
- O sistema deve aceitar arquivos nos formatos Swagger 2.0 e OpenAPI 3.x
- Todos os endpoints definidos no contrato devem ser registrados como rotas mock
- Métodos HTTP (GET, POST, PUT, PATCH, DELETE) devem ser respeitados conforme definido no contrato
- Os schemas de request e response devem ser analisados para guiar a geração de dados

### Geração de Respostas
- As respostas devem ser geradas respeitando os tipos de dados definidos nos schemas (string, integer, boolean, array, object, etc.)
- Campos com validações (minLength, maxLength, pattern, enum, etc.) devem ter seus valores gerados dentro das restrições
- Dados relacionados entre endpoints devem ser consistentes (ex: um ID retornado em um endpoint deve ser válido em outro)
- O status HTTP retornado deve corresponder ao definido no contrato para o cenário de sucesso

### Disponibilização dos Endpoints
- Os endpoints mockados devem estar disponíveis imediatamente após o processamento do contrato
- Cada endpoint deve retornar uma resposta no formato definido no contrato (JSON, XML, etc.)
- O servidor mock deve ser isolado por contrato, permitindo múltiplos contratos ativos simultaneamente
