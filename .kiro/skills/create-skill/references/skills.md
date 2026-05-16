> ## Índice de Documentação
> Consulte o índice completo de documentação em: https://kiro.dev/llms.txt
> Use este arquivo para descobrir todas as páginas disponíveis antes de explorar mais.

# Agent Skills

> Estenda o Kiro com pacotes de instruções portáteis usando o padrão aberto Agent Skills

## O que são skills?

Skills são pacotes de instruções portáteis que seguem o padrão aberto [Agent Skills](https://agentskills.io). Eles agrupam instruções, scripts e templates em pacotes reutilizáveis que o Kiro pode ativar quando relevante para sua tarefa.

O Kiro suporta o padrão Agent Skills, então você pode importar skills da comunidade ou de outras ferramentas de IA compatíveis, e compartilhar suas próprias skills em todo o ecossistema.

## Como as skills funcionam

Agentes de IA são cada vez mais capazes, mas frequentemente carecem do contexto específico necessário para o trabalho real. Sem conhecimento do processo de deploy da sua equipe, dos padrões de code review da sua empresa ou do pipeline de análise de dados do seu projeto, os agentes adivinham e iteram — assim como você faria ao aprender algo novo.

Carregar todo esse contexto antecipadamente também não é prático. Informação demais sobrecarrega o agente, tornando as respostas mais lentas e reduzindo a qualidade.

Skills resolvem isso com divulgação progressiva:

1. **Descoberta** - Na inicialização, o Kiro carrega apenas o nome e a descrição de cada skill
2. **Ativação** - Quando sua solicitação corresponde à descrição de uma skill, o Kiro carrega as instruções completas
3. **Execução** - O Kiro segue as instruções, carregando scripts ou arquivos de referência apenas quando necessário

Isso mantém o contexto focado enquanto dá ao Kiro acesso a amplo conhecimento especializado sob demanda.

## Usando skills

O Kiro ativa automaticamente skills quando sua solicitação corresponde à descrição de uma skill. Você também pode invocar uma skill diretamente digitando `/` no campo de entrada do chat para ver as skills disponíveis como comandos slash. Selecionar um comando slash carrega as instruções completas da skill, dando a você controle explícito sobre quando uma skill é ativada.

Visualize e gerencie skills na seção **Agent Steering & Skills** no painel do Kiro.

## Escopo da skill

Skills podem ser criadas com escopo de workspace ou escopo global.

### Skills de workspace

Skills de workspace residem no seu projeto em `.kiro/skills/`, e se aplicam apenas àquele workspace específico. Use skills de workspace para fluxos de trabalho específicos do projeto, como procedimentos de deploy ou convenções da equipe.

### Skills globais

Skills globais residem no seu diretório home em `~/.kiro/skills/`, e estão disponíveis em todos os workspaces. Use skills globais para fluxos de trabalho pessoais que você usa independentemente do projeto — como seu processo de code review ou padrões de documentação.

Em caso de nomes conflitantes entre skills globais e de workspace, o Kiro priorizará a skill de workspace. Isso permite que você defina skills globais que geralmente se aplicam a todos os seus workspaces, preservando a capacidade de sobrescrevê-las para projetos específicos.

## Importando skills

1. Abra a seção **Agent Steering & Skills** no painel do Kiro
2. Clique em **+** e selecione **Import a skill**
3. Escolha sua fonte:
   - **GitHub** - Importe de uma URL de repositório público. Você pode colar uma URL apontando para a pasta da skill ou diretamente para o arquivo `SKILL.md`. A URL deve apontar para um subdiretório no repositório, não para a raiz do repositório.
   - **Pasta local** - Importe do seu sistema de arquivos

Skills importadas são copiadas para o seu diretório de skills e funcionam imediatamente.

## Criando uma skill

Uma skill é uma pasta contendo um arquivo `SKILL.md`:

```
my-skill/
├── SKILL.md           # Obrigatório
├── scripts/           # Código executável opcional
├── references/        # Documentação opcional
└── assets/            # Templates opcionais
```

### Formato do SKILL.md

```markdown
---
name: pr-review
description: Revisa pull requests quanto à qualidade do código, problemas de segurança e cobertura de testes. Use ao revisar PRs ou preparar código para revisão.
---

## Processo de revisão

1. Verificar vulnerabilidades de segurança
2. Verificar tratamento de erros
3. Confirmar cobertura de testes
4. Revisar nomenclatura e estrutura
```

### Campos do frontmatter

| Campo | Obrigatório | Descrição |
|-------|-------------|-----------|
| `name` | Sim | Deve corresponder ao nome da pasta. Apenas letras minúsculas, números e hífens (máx. 64 caracteres). |
| `description` | Sim | Quando usar esta skill. O Kiro compara isso com suas solicitações (máx. 1024 caracteres). |
| `license` | Não | Nome da licença ou referência a um arquivo de licença incluído. |
| `compatibility` | Não | Requisitos de ambiente (ex.: ferramentas necessárias, acesso à rede). |
| `metadata` | Não | Dados adicionais de chave-valor como autor ou versão. |

Consulte a [especificação completa](https://agentskills.io/specification) para restrições detalhadas dos campos.

## Como skills diferem de steering e powers

**Skills** são pacotes portáteis que seguem um padrão aberto. Elas carregam sob demanda e podem incluir scripts. Use para fluxos de trabalho reutilizáveis que você deseja compartilhar ou importar de outros.

**Steering** é contexto específico do Kiro que molda o comportamento do agente. Suporta os modos `always`, `auto`, `fileMatch` e `manual`. Use para padrões e convenções do projeto.

**Powers** agrupam ferramentas MCP com conhecimento e fluxos de trabalho. Eles são ativados dinamicamente com base no contexto. Use para integrações onde você precisa tanto de ferramentas quanto de orientação.

**💡 Dica:** Para integrações MCP, [powers](https://kiro.dev/docs/powers.md) geralmente são uma opção melhor — eles agrupam ferramentas com orientação integrada e são ativados automaticamente com base no que você está trabalhando.

## Boas práticas

**Escreva descrições precisas** - O Kiro usa a descrição para decidir quando ativar. Inclua palavras-chave específicas: "Revisa pull requests quanto à segurança e cobertura de testes" é melhor que "ajuda com code review."

**Mantenha o SKILL.md focado** - Coloque documentação detalhada em arquivos `references/`. O Kiro carrega o SKILL.md completo na ativação.

**Use scripts para tarefas determinísticas** - Validação, geração de arquivos e chamadas de API funcionam melhor como scripts do que como código gerado por LLM.

**Escolha o escopo correto** - Global para fluxos de trabalho pessoais (sua checklist de revisão), workspace para procedimentos da equipe (deploy do projeto).

## Documentação relacionada

- [Steering](https://kiro.dev/docs/steering.md) - Contexto e padrões específicos do projeto
- [Powers](https://kiro.dev/docs/powers.md) - Integrações MCP com conhecimento integrado
- [Especificação Agent Skills](https://agentskills.io/specification) - Detalhes completos do formato
