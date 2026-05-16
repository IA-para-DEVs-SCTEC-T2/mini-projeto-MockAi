> ## Índice de Documentação
> Consulte o índice completo de documentação em: https://agentskills.io/llms.txt
> Use este arquivo para descobrir todas as páginas disponíveis antes de explorar mais.

# Especificação

> A especificação completa do formato para Agent Skills.

## Estrutura de diretórios

Uma skill é um diretório contendo, no mínimo, um arquivo `SKILL.md`:

```
skill-name/
├── SKILL.md          # Obrigatório: metadados + instruções
├── scripts/          # Opcional: código executável
├── references/       # Opcional: documentação
├── assets/           # Opcional: templates, recursos
└── ...               # Quaisquer arquivos ou diretórios adicionais
```

## Formato do `SKILL.md`

O arquivo `SKILL.md` deve conter frontmatter YAML seguido de conteúdo Markdown.

### Frontmatter

| Campo           | Obrigatório | Restrições                                                                                                       |
| --------------- | ----------- | ---------------------------------------------------------------------------------------------------------------- |
| `name`          | Sim         | Máximo 64 caracteres. Apenas letras minúsculas, números e hífens. Não deve começar ou terminar com hífen.        |
| `description`   | Sim         | Máximo 1024 caracteres. Não vazio. Descreve o que a skill faz e quando usá-la.                                   |
| `license`       | Não         | Nome da licença ou referência a um arquivo de licença incluído.                                                   |
| `compatibility` | Não         | Máximo 500 caracteres. Indica requisitos de ambiente (produto pretendido, pacotes do sistema, acesso à rede, etc.). |
| `metadata`      | Não         | Mapeamento arbitrário de chave-valor para metadados adicionais.                                                   |
| `allowed-tools` | Não         | String separada por espaços de ferramentas pré-aprovadas que a skill pode usar. (Experimental)                    |

<Card>
  **Exemplo mínimo:**

  ```markdown SKILL.md theme={null}
  ---
  name: skill-name
  description: Uma descrição do que esta skill faz e quando usá-la.
  ---
  ```

  **Exemplo com campos opcionais:**

  ```markdown SKILL.md theme={null}
  ---
  name: pdf-processing
  description: Extrai texto de PDFs, preenche formulários, mescla arquivos. Use ao lidar com PDFs.
  license: Apache-2.0
  metadata:
    author: example-org
    version: "1.0"
  ---
  ```
</Card>

#### Campo `name`

O campo obrigatório `name`:

* Deve ter de 1 a 64 caracteres
* Pode conter apenas caracteres alfanuméricos unicode em minúsculas (`a-z`) e hífens (`-`)
* Não deve começar ou terminar com hífen (`-`)
* Não deve conter hífens consecutivos (`--`)
* Deve corresponder ao nome do diretório pai

<Card>
  **Exemplos válidos:**

  ```yaml theme={null}
  name: pdf-processing
  ```

  ```yaml theme={null}
  name: data-analysis
  ```

  ```yaml theme={null}
  name: code-review
  ```

  **Exemplos inválidos:**

  ```yaml theme={null}
  name: PDF-Processing  # maiúsculas não permitidas
  ```

  ```yaml theme={null}
  name: -pdf  # não pode começar com hífen
  ```

  ```yaml theme={null}
  name: pdf--processing  # hífens consecutivos não permitidos
  ```
</Card>

#### Campo `description`

O campo obrigatório `description`:

* Deve ter de 1 a 1024 caracteres
* Deve descrever tanto o que a skill faz quanto quando usá-la
* Deve incluir palavras-chave específicas que ajudem os agentes a identificar tarefas relevantes

<Card>
  **Bom exemplo:**

  ```yaml theme={null}
  description: Extrai texto e tabelas de arquivos PDF, preenche formulários PDF e mescla múltiplos PDFs. Use ao trabalhar com documentos PDF ou quando o usuário mencionar PDFs, formulários ou extração de documentos.
  ```

  **Exemplo ruim:**

  ```yaml theme={null}
  description: Ajuda com PDFs.
  ```
</Card>

#### Campo `license`

O campo opcional `license`:

* Especifica a licença aplicada à skill
* Recomendamos mantê-lo curto (seja o nome de uma licença ou o nome de um arquivo de licença incluído)

<Card>
  **Exemplo:**

  ```yaml theme={null}
  license: Proprietário. LICENSE.txt contém os termos completos
  ```
</Card>

#### Campo `compatibility`

O campo opcional `compatibility`:

* Deve ter de 1 a 500 caracteres se fornecido
* Deve ser incluído apenas se sua skill tiver requisitos específicos de ambiente
* Pode indicar produto pretendido, pacotes do sistema necessários, necessidades de acesso à rede, etc.

<Card>
  **Exemplos:**

  ```yaml theme={null}
  compatibility: Projetado para Claude Code (ou produtos similares)
  ```

  ```yaml theme={null}
  compatibility: Requer git, docker, jq e acesso à internet
  ```

  ```yaml theme={null}
  compatibility: Requer Python 3.14+ e uv
  ```
</Card>

<Note>
  A maioria das skills não precisa do campo `compatibility`.
</Note>

#### Campo `metadata`

O campo opcional `metadata`:

* Um mapa de chaves string para valores string
* Clientes podem usar isso para armazenar propriedades adicionais não definidas pela especificação Agent Skills
* Recomendamos tornar seus nomes de chave razoavelmente únicos para evitar conflitos acidentais

<Card>
  **Exemplo:**

  ```yaml theme={null}
  metadata:
    author: example-org
    version: "1.0"
  ```
</Card>

#### Campo `allowed-tools`

O campo opcional `allowed-tools`:

* Uma string separada por espaços de ferramentas que são pré-aprovadas para execução
* Experimental. O suporte para este campo pode variar entre implementações de agentes

<Card>
  **Exemplo:**

  ```yaml theme={null}
  allowed-tools: Bash(git:*) Bash(jq:*) Read
  ```
</Card>

### Conteúdo do corpo

O corpo Markdown após o frontmatter contém as instruções da skill. Não há restrições de formato. Escreva o que ajudar os agentes a executar a tarefa de forma eficaz.

Seções recomendadas:

* Instruções passo a passo
* Exemplos de entradas e saídas
* Casos extremos comuns

Note que o agente carregará este arquivo inteiro assim que decidir ativar uma skill. Considere dividir conteúdo mais longo do `SKILL.md` em arquivos referenciados.

## Diretórios opcionais

### `scripts/`

Contém código executável que os agentes podem executar. Scripts devem:

* Ser autocontidos ou documentar claramente as dependências
* Incluir mensagens de erro úteis
* Tratar casos extremos de forma elegante

Linguagens suportadas dependem da implementação do agente. Opções comuns incluem Python, Bash e JavaScript.

### `references/`

Contém documentação adicional que os agentes podem ler quando necessário:

* `REFERENCE.md` - Referência técnica detalhada
* `FORMS.md` - Templates de formulários ou formatos de dados estruturados
* Arquivos específicos de domínio (`finance.md`, `legal.md`, etc.)

Mantenha [arquivos de referência](#file-references) individuais focados. Agentes os carregam sob demanda, então arquivos menores significam menos uso de contexto.

### `assets/`

Contém recursos estáticos:

* Templates (templates de documentos, templates de configuração)
* Imagens (diagramas, exemplos)
* Arquivos de dados (tabelas de consulta, schemas)

## Divulgação progressiva

Agentes carregam skills *progressivamente*, buscando mais detalhes apenas quando uma tarefa exige. Skills devem ser estruturadas para aproveitar isso:

1. **Metadados** (\~100 tokens): Os campos `name` e `description` são carregados na inicialização para todas as skills
2. **Instruções** (\< 5000 tokens recomendado): O corpo completo do `SKILL.md` é carregado quando a skill é ativada
3. **Recursos** (conforme necessário): Arquivos (ex.: em `scripts/`, `references/` ou `assets/`) são carregados apenas quando necessário

Mantenha seu `SKILL.md` principal com menos de 500 linhas. Mova material de referência detalhado para arquivos separados.

## Referências de arquivos

Ao referenciar outros arquivos na sua skill, use caminhos relativos a partir da raiz da skill:

```markdown SKILL.md theme={null}
Consulte [o guia de referência](references/REFERENCE.md) para detalhes.

Execute o script de extração:
scripts/extract.py
```

Mantenha referências de arquivos com um nível de profundidade a partir do `SKILL.md`. Evite cadeias de referência profundamente aninhadas.

## Validação

Use a biblioteca de referência [skills-ref](https://github.com/agentskills/agentskills/tree/main/skills-ref) para validar suas skills:

```bash theme={null}
skills-ref validate ./my-skill
```

Isso verifica se o frontmatter do seu `SKILL.md` é válido e segue todas as convenções de nomenclatura.
