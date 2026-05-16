---
name: manage-github-issue
description: Cria ou atualiza issues no GitHub seguindo o template padrão do projeto, usando o GitHub CLI (gh). Use quando o usuário pedir para criar, abrir, registrar, alterar ou editar uma issue no GitHub, ou quando precisar gerenciar o backlog do projeto no repositório IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi.
metadata:
  author: joaopuel
  version: "1.1"
allowed-tools:
  - shell
---

## Gerenciamento de Issues no GitHub

Skill para criar e alterar issues no repositório `IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi` usando o `gh` CLI.

Consulte o [fluxo detalhado](references/workflow.md) para o passo a passo completo de criação e alteração de issues.

## Regras obrigatórias

- Sempre perguntar ao usuário se deseja **criar** ou **alterar** uma issue antes de qualquer ação.
- Nunca executar comandos sem solicitar permissão do usuário antes.
- Nunca alterar uma issue sem antes buscar seus dados atuais com `gh issue view`.
- Sempre usar `--repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi` em todos os comandos `gh`.
- Títulos sempre em português, claros e objetivos.
- Labels e assignees somente quando explicitamente informados pelo usuário.

## Classificação das issues

Consulte as referências para detalhes completos:

- [Padrão de títulos](references/issue-title.md) — prefixos `[EPIC]`, `[STORY]`, `[TECH]`, `[DOCS]`
- [Labels disponíveis](references/issue-labels.md) — tipos, área técnica e prioridade
- [Hierarquia de issues](references/issue-hierarchy.md) — vínculos entre Epic, Story e Tech

### Resumo dos tipos

| Tipo  | Prefixo   | Descrição                                        |
|-------|-----------|--------------------------------------------------|
| Epic  | `[EPIC]`  | Objetivo macro de um conjunto de funcionalidades |
| Story | `[STORY]` | Entrega funcional implementável                  |
| Tech  | `[TECH]`  | Tarefa técnica (infra, config, refactoring)      |
| Docs  | `[DOCS]`  | Documentação do projeto                          |

- O campo `--type` é **sempre `Feature`** em todos os casos.
- Tarefas técnicas pequenas ficam como checklist dentro da Story, não como issues separadas.

## Templates disponíveis

Os templates de corpo das issues estão em `assets/`:

| Tipo  | Template                  |
|-------|---------------------------|
| Epic  | `epic_template.yml`       |
| Story | `user_story_template.yml` |
| Tech  | `tech_template.yml`       |
| Docs  | `docs_template.yml`       |

Leia o template correspondente antes de montar o corpo da issue.
