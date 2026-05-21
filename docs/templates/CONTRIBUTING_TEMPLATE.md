# Guia de contribuição

## Fluxo de contribuição

1. Escolha uma Issue aberta.
2. Inicie uma branch usando `scripts/start_issue.sh`.
3. Implemente a alteração.
4. Faça commit seguindo a convenção.
5. Abra um Pull Request usando `scripts/open_pr.sh`.
6. Revise o checklist.
7. Faça merge após validação.
8. Apague a branch após o merge.

## Como iniciar uma Issue

```bash
./scripts/start_issue.sh
```

## Como fazer commit

```bash
git add .
git commit -m "feat: descricao da alteracao"
```

## Como abrir Pull Request

```bash
./scripts/open_pr.sh
```

## Padrão de branches

```text
feature/<issue-id>-descricao
docs/<issue-id>-descricao
fix/<issue-id>-descricao
```

## Responsabilidade de Cada Branch
| Branch | Propósito | Origem | Destino do merge |
|--------|-----------|--------|-----------------|
| `main` | Código em produção. Sempre estável e versionado. | — | — |
| `develop` | Integração contínua de features. Base para novas funcionalidades. | `main` | `release/*` |

### Regras de proteção
- `main` e `develop` são branches **protegidas** — nenhum commit direto é permitido.
- Todo código entra nessas branches exclusivamente via **Pull Request aprovado**.

## Padrão de commits

```text
feat: nova funcionalidade
fix: correção
docs: documentação
chore: configuração
test: testes
refactor: refatoração
```

## Checklist antes do merge

- [ ] A Issue relacionada está correta.
- [ ] O PR contém `Closes #ID`.
- [ ] A User Story foi considerada.
- [ ] Os critérios BDD foram atendidos.
- [ ] O código foi testado.
- [ ] A documentação foi atualizada, quando necessário.
