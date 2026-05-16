---
name: create-steering
description: Cria arquivos de steering no workspace. Use quando o usuário pedir para criar, adicionar ou configurar um arquivo de steering, definir padrões persistentes para o Kiro, ou documentar convenções, arquitetura e regras do projeto.
---

# Criar Arquivo de Steering

Steering fornece ao Kiro conhecimento persistente sobre o workspace por meio de arquivos markdown. Esta skill guia a criação correta de arquivos de steering, garantindo estrutura, frontmatter e modo de inclusão adequados.

Consulte a [documentação completa de steering](references/steering.md) para detalhes sobre todos os conceitos.

---

## Processo de criação

### 1. Entender o propósito do steering

Antes de criar qualquer arquivo, identifique:

- **Conteúdo**: qual padrão, convenção ou contexto será documentado
- **Escopo**: workspace (`.kiro/steering/`) ou global (`~/.kiro/steering/`)
- **Modo de inclusão**: quando o arquivo deve ser carregado (veja seção abaixo)
- **Nome do arquivo**: descritivo, em kebab-case, com extensão `.md` — **sempre em inglês**

Exemplos de nomes válidos: `api-standards.md`, `testing-patterns.md`, `code-conventions.md`

> **Regra de idioma**: o nome do arquivo (e o campo `name` no frontmatter `auto`) deve estar sempre em **inglês**, pois é usado como identificador e comando slash. A `description` do frontmatter e todo o corpo do arquivo devem estar em **português (pt-BR)**.

### 2. Escolher o modo de inclusão

| Modo | Frontmatter | Quando usar |
|------|-------------|-------------|
| **always** | `inclusion: always` | Padrões universais que se aplicam a toda interação (stack, convenções gerais) |
| **fileMatch** | `inclusion: fileMatch` + `fileMatchPattern` | Padrões específicos de domínio que só fazem sentido para certos arquivos |
| **manual** | `inclusion: manual` | Guias especializados, pesados em contexto, usados ocasionalmente |
| **auto** | `inclusion: auto` + `name` + `description` | Contexto relevante que deve carregar automaticamente quando o assunto bater |

### 3. Estrutura obrigatória do arquivo

Todo arquivo de steering deve começar com o frontmatter YAML, seguido do conteúdo em markdown:

```markdown
---
inclusion: always
---

# Título do Steering

Conteúdo das diretrizes...
```

Para `fileMatch`:
```markdown
---
inclusion: fileMatch
fileMatchPattern: "src/**/*.java"
---
```

Para múltiplos padrões:
```markdown
---
inclusion: fileMatch
fileMatchPattern: ["**/*.ts", "**/*.tsx"]
---
```

Para `auto`:
```markdown
---
inclusion: auto
name: nome-do-steering
description: Descrição em português de quando este arquivo deve ser incluído automaticamente.
---
```

> **Atenção**: o frontmatter deve ser o primeiro conteúdo do arquivo, sem linhas em branco antes dele.

### 4. Escrever o conteúdo

O **nome do arquivo** e o campo `name` (usado no modo `auto`) devem estar sempre em **inglês**. A `description` do frontmatter e todo o corpo do arquivo devem estar em **português (pt-BR)**.

Siga estas diretrizes ao escrever o conteúdo:

- **Seja específico**: explique o "porquê" das decisões, não apenas o "o quê"
- **Use exemplos**: trechos de código e comparações antes/depois são mais eficazes que descrições abstratas
- **Um domínio por arquivo**: não misture padrões de API com padrões de testes no mesmo arquivo
- **Nunca inclua segredos**: chaves de API, senhas ou dados sensíveis não pertencem a arquivos de steering

### 5. Referenciar arquivos do workspace (opcional)

Para manter o steering sincronizado com arquivos reais do projeto, use referências:

```markdown
#[[file:caminho/relativo/do/arquivo]]
```

Exemplos:
- `#[[file:docs/petstore.yml]]` — referencia a spec OpenAPI do projeto
- `#[[file:pom.xml]]` — referencia o arquivo de dependências Maven

### 6. Criar o arquivo

Use a ferramenta de escrita de arquivos para criar o steering em `.kiro/steering/<nome>.md`.

Após criar, confirme que:
- [ ] O frontmatter é o primeiro conteúdo do arquivo (sem linhas antes)
- [ ] O campo `inclusion` está presente e com valor válido
- [ ] Para `fileMatch`, o campo `fileMatchPattern` está preenchido
- [ ] Para `auto`, os campos `name` e `description` estão presentes
- [ ] O nome do arquivo está em inglês (kebab-case)
- [ ] O campo `name` (modo `auto`) está em inglês
- [ ] A `description` do frontmatter e o corpo do arquivo estão em português (pt-BR)
- [ ] O arquivo está em `.kiro/steering/` (workspace) ou `~/.kiro/steering/` (global)

---

## Exemplos de steering por categoria

### Padrões de código (`code-conventions.md`)
Nomenclatura, organização de arquivos, ordenação de imports, anti-padrões a evitar.

### Padrões de API (`api-standards.md`)
Convenções REST, formatos de erro, autenticação, versionamento, exemplos de request/response.

### Abordagem de testes (`testing-standards.md`)
Padrões de testes unitários e de integração, mocking, cobertura esperada, bibliotecas preferidas.

### Diretrizes de segurança (`security-policies.md`)
Autenticação, validação de dados, sanitização de entrada, prevenção de vulnerabilidades.

### Processo de implantação (`deployment-workflow.md`)
Build, configurações de ambiente, etapas de deploy, rollback, CI/CD.
