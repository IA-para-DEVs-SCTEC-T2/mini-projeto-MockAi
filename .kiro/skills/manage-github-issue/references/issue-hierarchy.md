# Hierarquia de Issues no GitHub

Este documento define as regras de hierarquia e vínculo entre issues no projeto MockAI.

## Estrutura hierárquica

```
Epic
 └── Story
      └── Tech (quando vinculada a uma Story)
```

- **Epic** → agrupa Stories relacionadas a um objetivo macro.
- **Story** → entrega funcional implementável, sempre vinculada a uma Epic.
- **Tech** → tarefa técnica que pode ser vinculada a uma Story ou diretamente a uma Epic.

## Vínculo no corpo da issue

Quando o usuário informar a issue pai, inclua no **final do corpo** da issue filha a referência textual:

| Tipo da issue filha | Tipo da issue pai | Texto no body                  |
|---------------------|-------------------|--------------------------------|
| Story               | Epic              | `Parent Epic: #ID_DA_EPIC`     |
| Tech                | Epic              | `Parent Epic: #ID_DA_EPIC`     |
| Tech                | Story             | `Parent Story: #ID_DA_STORY`   |

Essa linha deve ser adicionada como última seção do body, separada por uma linha em branco.

## Vínculo via Sub-issues (GitHub GraphQL API)

Além do texto no body, **sempre** crie o vínculo nativo de sub-issue no GitHub usando a API GraphQL. Isso garante que a hierarquia apareça na UI do GitHub.

### Passo 1 — Obter os node IDs das issues

```bash
# ID da issue pai
gh issue view <ISSUE_PAI_NUMBER> --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi --json id --jq .id

# ID da issue filha
gh issue view <ISSUE_FILHA_NUMBER> --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi --json id --jq .id
```

### Passo 2 — Criar o vínculo de sub-issue

```bash
gh api graphql -f query="mutation { addSubIssue(input: { issueId: \"<NODE_ID_ISSUE_PAI>\", subIssueId: \"<NODE_ID_ISSUE_FILHA>\" }) { issue { id title } subIssue { id title } } }"
```

Substitua:
- `<NODE_ID_ISSUE_PAI>` pelo ID GraphQL da issue pai (Epic ou Story)
- `<NODE_ID_ISSUE_FILHA>` pelo ID GraphQL da issue filha (Story ou Tech)

### Exemplos

**Story vinculada a uma Epic:**
```bash
EPIC_ID=$(gh issue view 60 --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi --json id --jq .id)
STORY_ID=$(gh issue view 62 --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi --json id --jq .id)
gh api graphql -f query="mutation { addSubIssue(input: { issueId: \"$EPIC_ID\", subIssueId: \"$STORY_ID\" }) { issue { id title } subIssue { id title } } }"
```

**Tech vinculada a uma Story:**
```bash
STORY_ID=$(gh issue view 62 --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi --json id --jq .id)
TECH_ID=$(gh issue view 99 --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi --json id --jq .id)
gh api graphql -f query="mutation { addSubIssue(input: { issueId: \"$STORY_ID\", subIssueId: \"$TECH_ID\" }) { issue { id title } subIssue { id title } } }"
```

## Regras

1. **Sempre** adicione o texto de referência no body (`Parent Epic:` ou `Parent Story:`).
2. **Sempre** execute o vínculo via GraphQL `addSubIssue` para garantir a hierarquia nativa no GitHub.
3. Execute os dois passos na ordem: primeiro o body, depois o vínculo GraphQL.
4. Se o vínculo GraphQL falhar (feature não disponível), mantenha ao menos o texto no body como fallback.
