# Fluxo de Gerenciamento de Issues no GitHub

## Repositório e projeto fixos

- Repositório: `IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi`
- Projeto: `mini-projeto-mockAI` (project number: 6, owner: `IA-para-DEVs-SCTEC-T2`)
- Sempre use `--repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi` em todos os comandos `gh`

---

## Passo 1 — Identificar a operação (OBRIGATÓRIO)

**Antes de qualquer ação**, pergunte ao usuário:

> "Você deseja **criar uma nova issue** ou **alterar uma issue existente**?"

Aguarde a resposta antes de prosseguir.

---

## Passo 2A — Fluxo: Criar nova issue

### 2A.1 — Coletar informações

- Solicite o título, descrição e demais informações necessárias caso não tenham sido fornecidas.
- O título deve ser claro, objetivo e em português.
- O tipo (`--type`) é obrigatório. O valor é sempre `Feature`.

### 2A.2 — Montar o corpo da issue

O corpo da issue deve seguir o template correspondente ao tipo de issue sendo criada. Os templates estão em `.kiro/skills/github-issue-management/assets/`:

| Tipo de issue | Template a utilizar       |
|---------------|---------------------------|
| Epic          | `epic_template.yml`       |
| Story         | `user_story_template.yml` |
| Tech          | `tech_template.yml`       |
| Docs          | `docs_template.yml`       |

Leia o template correspondente antes de montar o corpo e respeite as seções e campos definidos nele.

Regras de formatação:
- Use Markdown real com quebras de linha reais.
- Não use `\n` escapado.
- Não use `--body $"texto\ntexto"`.
- Para criar issues via terminal, use `--body-file` com um arquivo temporário.

### 2A.3 — Montar o arquivo de corpo

Crie um arquivo markdown temporário com o corpo da issue antes de executar o comando. Use o seguinte padrão com heredoc:

```bash
cat > /tmp/issue_body.md << 'ENDOFFILE'
## Seção 1

Conteúdo da seção...

## Seção 2

Conteúdo da seção...
ENDOFFILE
```

Regras do heredoc:
- Use aspas simples no delimitador (`'ENDOFFILE'`) para evitar interpolação de variáveis.
- O conteúdo entre os delimitadores é escrito literalmente no arquivo.
- O arquivo gerado em `/tmp/issue_body.md` será usado com `--body-file`.

### 2A.4 — Solicitar permissão do usuário

Apresente o comando que será executado e aguarde confirmação antes de prosseguir.

### 2A.5 — Executar a criação

```bash
gh issue create \
  --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi \
  --title "[TIPO] Título da issue" \
  --type Feature \
  --body-file <arquivo-temporario>.md
```

Regras do comando:
- Não use `--json` nem `--jq` com `gh issue create`.
- O comando deve retornar a URL da issue criada.
- Para capturar o número da issue, salve a URL em uma variável e extraia o número final com `sed`.

### 2A.6 — Adicionar ao projeto

Após a criação, adicione a issue ao projeto:

```bash
gh project item-add 6 --owner IA-para-DEVs-SCTEC-T2 --url <URL_DA_ISSUE_CRIADA>
```

### 2A.7 — Definir o tipo via API

O `gh issue create` nem sempre aplica o campo `type` corretamente. Execute sempre após a criação:

```bash
gh api repos/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi/issues/<ISSUE_NUMBER> \
  --method PATCH -f type="Feature" 2>&1 | head -5
```

---

## Passo 2B — Fluxo: Alterar issue existente

### 2B.1 — Solicitar identificação da issue (OBRIGATÓRIO)

Pergunte ao usuário:

> "Qual o **número** ou **link** da issue que deseja alterar?"

Aguarde a resposta antes de prosseguir.

### 2B.2 — Buscar dados atuais da issue (OBRIGATÓRIO)

Ao receber o número ou URL, execute imediatamente:

```bash
gh issue view <ISSUE_NUMBER> --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi
```

Use as informações retornadas (título, descrição, labels, assignees, type, status) como contexto base para a alteração. Nunca altere uma issue sem antes buscar seus dados atuais.

### 2B.3 — Mesclar informações

Combine os dados atuais da issue com as alterações solicitadas pelo usuário, preservando tudo que não foi explicitamente modificado.

### 2B.4 — Solicitar permissão do usuário

Apresente as alterações que serão aplicadas e aguarde confirmação antes de prosseguir.

### 2B.5 — Aplicar a alteração

```bash
gh issue edit <ISSUE_NUMBER> \
  --repo IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi \
  --title "Novo título" \
  --body-file <arquivo-temporario>.md
```

---

## Regras gerais

- Sempre usar `gh` CLI.
- Sempre operar no repositório `IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi`.
- Títulos sempre em português, claros e objetivos.
- Labels e assignees somente quando explicitamente informados.
- Proibido executar comandos sem solicitar permissão do usuário antes.
