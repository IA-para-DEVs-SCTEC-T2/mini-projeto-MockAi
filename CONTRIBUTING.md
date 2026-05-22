# Guia de Contribuição — MockAI

## Fluxo de contribuição

1. Escolha uma Issue aberta no [board do projeto](https://github.com/orgs/IA-para-DEVs-SCTEC-T2/projects/6/views/1).
2. Atribua a Issue a você mesmo e mova para "In Progress" manualmente no board.
3. Use o hook `/commit-modifications` → informe o número da issue → a branch `feature/task<N>` ou `bugfix/task<N>` é criada automaticamente e são realizados os commits.
4. Use o hook `/push-modifications` → realiza o push e abre o PR via `scripts/open_pr.sh`.
5. Revise o checklist do PR.
6. Faça merge após validação e aprovação.
7. Apague a branch após o merge.

## Como iniciar uma Issue

1. Acesse o [board do projeto](https://github.com/orgs/IA-para-DEVs-SCTEC-T2/projects/6/views/1).
2. Escolha uma Issue disponível na coluna "To Do".
3. Atribua a Issue a você mesmo.
4. Mova a Issue para "In Progress" manualmente.

## Como fazer commit

Use o hook `/commit-modifications`. Ele solicita o número da issue, cria a branch correta e realiza o commit:

```bash
# A branch é criada automaticamente pelo hook com base no tipo da issue:
feature/task<N>   → nova funcionalidade
bugfix/task<N>    → correção de bug
```

## Como abrir Pull Request

```bash
./scripts/open_pr.sh
```

O script valida alterações não commitadas, identifica o número da issue pela branch, busca o título da issue no GitHub e abre o PR automaticamente para `develop`.

## Padrão de branches

```text
feature/task<N>   → nova funcionalidade
bugfix/task<N>    → correção de bug
```

## Responsabilidade de Cada Branch

| Branch | Propósito | Origem | Destino do merge |
|--------|-----------|--------|-----------------|
| `main` | Código em produção. Sempre estável e versionado. | — | — |
| `develop` | Integração contínua de features. Base para novas funcionalidades. | `main` | `main` |
| `feature/task<N>` | Desenvolvimento de novas funcionalidades. | `develop` | `develop` |
| `bugfix/task<N>` | Correção de bugs. | `develop` | `develop` |

### Regras de proteção

- `main` e `develop` são branches **protegidas** — nenhum commit direto é permitido.
- Todo código entra nessas branches exclusivamente via **Pull Request aprovado**.

## Padrão de commits

```text
feat: nova funcionalidade
fix: correção de bug
docs: documentação, steerings, hooks, skills
refactor: refatoração sem mudança de comportamento
test: testes
chore: configuração, dependências, build
```

Regras:
- Descrição em português, no imperativo presente ("adiciona", "corrige", "remove")
- Máximo de 72 caracteres na linha de título
- Referencie issues quando aplicável: `Closes #42`

## Checklist antes do merge

- [ ] A Issue relacionada está correta.
- [ ] O PR contém `Closes #ID`.
- [ ] Todos os itens aplicáveis do checklist técnico da Issue foram concluídos.
- [ ] Os critérios BDD foram considerados, quando aplicável.
- [ ] O código foi testado, quando aplicável.
- [ ] A documentação foi atualizada, quando necessário.
- [ ] O código compila sem erros: `mvn clean compile`.
- [ ] Entidades JPA não estão expostas diretamente na API.
- [ ] Endpoints documentados com anotações Swagger (`@Operation`, `@ApiResponse`).
- [ ] Sem `System.out.println` no código.

## Padrões de Código

O projeto segue Clean Architecture com Hexagonal Architecture:

```
Adapter/in/web (Controllers, DTOs, Dynamic Routes)
    ↓
Application (Use Cases, Services)
    ↓
Domain (Models, Ports/Interfaces, Exceptions)
    ↑
Infrastructure (JPA Entities, Repositories, Adapters, AI Gateway)
```

- **Domain:** Java puro, sem frameworks. Define ports (in/out) e exceções de negócio.
- **Application:** Depende apenas de abstrações do Domain.
- **Infrastructure:** Implementações concretas (JPA, H2, Spring AI/Groq).
- **Adapter:** Controllers, DTOs, roteamento dinâmico, tratamento de exceções.

Boas práticas obrigatórias:
- Sem `System.out.println` — use o logger do projeto
- Sem código comentado — use git para histórico
- Injeção de dependência via construtor
- Javadoc obrigatório em interfaces públicas e métodos de domínio complexos