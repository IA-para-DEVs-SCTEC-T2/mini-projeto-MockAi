---
inclusion: manual
---

# Gitflow e Padrões de Nomenclatura

Este projeto segue convenções específicas de gitflow e nomenclatura de commits.

## Branches

### Padrões de Nomenclatura

- **feature/** - Para novas funcionalidades
  - Exemplo: `feature/adicionar-autenticacao`, `feature/dashboard-usuario`
  
- **bugfix/** - Para correções de bugs
  - Exemplo: `bugfix/corrigir-login`, `bugfix/validacao-formulario`

### Branches Não Utilizadas

Este projeto **NÃO** utiliza:
- `release/` - Não trabalhamos com releases
- `hotfix/` - Não trabalhamos com hotfixes

**Motivo:** O intuito do projeto não é a liberação do produto para mercado.

## Commits

### Padrões de Nomenclatura

Todos os commits devem seguir o padrão de conventional commits:

- **feat:** - Para novas funcionalidades
  - Exemplo: `feat: adicionar sistema de login`
  
- **fix:** - Para correções de bugs
  - Exemplo: `fix: corrigir validação de email`
  
- **refactor:** - Para refatorações de código
  - Exemplo: `refactor: reorganizar estrutura de pastas`
  
- **docs:** - Para adição ou atualização de documentações
  - Exemplo: `docs: atualizar README com instruções de instalação`

### Formato do Commit

```
<tipo>: <descrição curta>

[corpo opcional com mais detalhes]
```

### Exemplos Completos

```
feat: adicionar página de perfil do usuário

Implementa a visualização e edição de dados do perfil
```

```
fix: corrigir erro ao salvar formulário

O formulário não estava validando campos obrigatórios corretamente
```

## Workflow

1. Criar branch a partir da `main` usando o padrão apropriado (`feature/` ou `bugfix/`)
2. Desenvolver a funcionalidade ou correção
3. Fazer commits seguindo os padrões de nomenclatura
4. Criar pull request para merge na `main`
5. Após aprovação, fazer merge e deletar a branch

## Regras Importantes

- Nunca fazer commit diretamente na `main`
- Sempre criar uma branch para cada tarefa
- Usar nomes descritivos e em português para branches e commits
- Manter commits atômicos e com mensagens claras
