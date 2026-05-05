---
inclusion: manual
---

# Gerenciamento de Issues no GitHub

Este documento define como criar e atualizar issues/tasks/tarefas/demandas no GitHub utilizando o **GitHub CLI (`gh`)**.

## Regras Gerais

- Sempre usar o `gh` CLI para criar ou atualizar issues
- O título deve ser claro, objetivo e em português
- A descrição deve seguir obrigatoriamente o template definido abaixo
- Labels, assignees e type devem ser incluídos **somente quando explicitamente informados**
- **Quando um número de issue ou URL for informado, buscar os dados atuais da issue antes de qualquer alteração**, usando essas informações como contexto para a atualização

---

## Busca de Contexto da Issue

Sempre que o usuário informar um **número de issue** (ex: `#42`, `42`) ou uma **URL** (ex: `https://github.com/org/repo/issues/42`), executar obrigatoriamente o comando abaixo **antes de qualquer edição**:

```bash
# Por número
gh issue view <ISSUE_NUMBER>

# Por URL
gh issue view <URL>
```

As informações retornadas (título, descrição atual, labels, assignees, type, status) devem ser lidas e usadas como contexto para:

- Preservar informações existentes que não foram solicitadas para alteração
- Complementar a descrição com o que já estava preenchido
- Evitar sobrescrever dados relevantes acidentalmente
- Entender o estado atual da issue antes de propor ou aplicar mudanças

---

## Template de Descrição

Toda issue criada ou atualizada deve conter a descrição no seguinte formato:

```markdown
## Descrição
Descrição da demanda (O que deve ser feito?/qual problema deve ser resolvido?)

## Orientações técnicas
- Utilize a anotação @XPTO da dependência xpto
- Utilize a função xpto
- Adicione a validação aqui

## Critérios de Aceite
- [ ] Critério 1
- [ ] Critério 2
```

---

## Comandos gh CLI

### Criar uma nova issue

Montar o comando incluindo **apenas os campos que foram informados**. Omitir `--label`, `--assignee` e `--type` quando não fornecidos.

```bash
# Campos obrigatórios (sempre presentes)
gh issue create \
  --title "Título da issue" \
  --body "## Descrição
Descrição da demanda (O que deve ser feito?/qual problema deve ser resolvido?)

## Orientações técnicas
- Orientação técnica 1
- Orientação técnica 2

## Critérios de Aceite
- [ ] Critério 1
- [ ] Critério 2"

# Adicionar --label somente se labels foram informadas
# --label "label1,label2"

# Adicionar --assignee somente se o responsável foi informado
# --assignee "username"

# Adicionar --type somente se o tipo foi informado
# --type "bug|feature|task|..."
```

### Atualizar o corpo (descrição) de uma issue existente

Montar o comando incluindo **apenas os campos que foram informados**. Omitir `--add-label`, `--add-assignee` e `--type` quando não fornecidos.

```bash
gh issue edit <ISSUE_NUMBER> \
  --body "## Descrição
Descrição atualizada da demanda

## Orientações técnicas
- Orientação técnica atualizada

## Critérios de Aceite
- [ ] Critério 1
- [ ] Critério 2"

# Adicionar --add-label somente se labels foram informadas
# --add-label "label1,label2"

# Adicionar --add-assignee somente se o responsável foi informado
# --add-assignee "username"

# Adicionar --type somente se o tipo foi informado
# --type "bug|feature|task|..."
```

### Atualizar o título de uma issue

```bash
gh issue edit <ISSUE_NUMBER> --title "Novo título da issue"
```

### Adicionar labels a uma issue

```bash
gh issue edit <ISSUE_NUMBER> --add-label "label1,label2"
```

### Atribuir responsável a uma issue

```bash
gh issue edit <ISSUE_NUMBER> --add-assignee "username"
```

### Fechar uma issue

```bash
gh issue close <ISSUE_NUMBER>
```

### Reabrir uma issue

```bash
gh issue reopen <ISSUE_NUMBER>
```

### Listar issues abertas

```bash
gh issue list
```

### Visualizar detalhes de uma issue

```bash
# Por número
gh issue view <ISSUE_NUMBER>

# Por URL
gh issue view <URL>
```

> **Obrigatório** executar este comando antes de qualquer edição quando um número ou URL for fornecido.

---

## Fluxo de Atualização de Issue

Ao receber uma solicitação de atualização com número ou URL da issue, seguir obrigatoriamente este fluxo:

1. **Buscar** os dados atuais com `gh issue view <ISSUE_NUMBER|URL>`
2. **Analisar** o conteúdo retornado (título, descrição, labels, assignees, type, status)
3. **Mesclar** as informações existentes com as novas solicitadas pelo usuário
4. **Aplicar** a atualização com `gh issue edit`, preservando o que não foi alterado

---

## Boas Práticas

- **Descrição**: Sempre preencher com contexto suficiente para que qualquer desenvolvedor entenda o que precisa ser feito
- **Orientações técnicas**: Incluir referências a classes, anotações, métodos ou padrões específicos do projeto que devem ser utilizados
- **Critérios de Aceite**: Definir critérios mensuráveis e verificáveis que indiquem quando a tarefa está concluída
- **Labels**: Incluir `--label` (criar) ou `--add-label` (editar) **somente se labels forem informadas**
- **Assignee**: Incluir `--assignee` (criar) ou `--add-assignee` (editar) **somente se o responsável for informado**
- **Type**: Incluir `--type` **somente se o tipo for informado**
- **Título**: Usar nomes descritivos e em português, alinhados com os padrões de commit do projeto
