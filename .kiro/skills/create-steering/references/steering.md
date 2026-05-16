> ## Índice de Documentação
> Acesse o índice completo da documentação em: https://kiro.dev/llms.txt
> Use este arquivo para descobrir todas as páginas disponíveis antes de explorar mais.

# Steering

> Guie a IA do Kiro com contexto específico do workspace ou global por meio de documentos markdown que definem seus padrões, arquitetura e convenções

## O que é steering?

Steering fornece ao Kiro conhecimento persistente sobre seu workspace por meio de arquivos markdown. Em vez de explicar suas convenções em cada conversa, os arquivos de steering garantem que o Kiro siga consistentemente seus padrões, bibliotecas e convenções estabelecidos.

## Principais benefícios

**Geração de Código Consistente** - Cada componente, endpoint de API ou teste segue os padrões e convenções estabelecidos pela sua equipe.

**Menos Repetição** - Não é necessário explicar os padrões do workspace em cada conversa. O Kiro lembra suas preferências.

**Alinhamento da Equipe** - Todos os desenvolvedores trabalham com os mesmos padrões, sejam novatos ou veteranos no workspace.

**Conhecimento de Projeto Escalável** - Documentação que cresce junto com sua base de código, capturando decisões e padrões à medida que o projeto evolui.

## Escopo dos arquivos de steering
Os arquivos de steering podem ser criados com escopo de workspace ou escopo global.

### Steering de workspace
Os arquivos de steering de workspace ficam na pasta raiz do seu workspace em `.kiro/steering/`, e se aplicam apenas àquele workspace específico. Eles podem ser usados para informar ao Kiro os padrões, bibliotecas e convenções que se aplicam a um workspace individual.

### Steering global
Os arquivos de steering global ficam no seu diretório home em `~/.kiro/steering/`, e se aplicam a todos os workspaces. Eles podem ser usados para informar ao Kiro as convenções que se aplicam a *todos* os seus workspaces.

Em caso de instruções conflitantes entre o steering global e o de workspace, o Kiro priorizará as instruções do steering de workspace. Isso permite especificar diretivas globais que geralmente se aplicam a todos os workspaces, preservando a capacidade de substituí-las para workspaces específicos.

### Steering de equipe
O recurso de steering global pode ser usado para definir arquivos de steering centralizados que se aplicam a equipes inteiras. Os arquivos de steering de equipe podem ser distribuídos aos PCs dos usuários via soluções MDM ou Políticas de Grupo, ou baixados pelos usuários de um repositório central e colocados na pasta `~/.kiro/steering`.

## Arquivos de steering fundamentais

O Kiro fornece arquivos de steering fundamentais para estabelecer o contexto central do projeto. Você pode gerá-los da seguinte forma:

1. Navegue até a seção **Steering** no painel do Kiro
1. Clique no botão **Generate Steering Docs**, ou clique no botão **+** e selecione a opção **Foundation steering files**
1. O Kiro criará três arquivos fundamentais:

**Visão Geral do Produto** (`product.md`) - Define o propósito do seu produto, usuários-alvo, funcionalidades principais e objetivos de negócio. Isso ajuda o Kiro a entender o "porquê" por trás das decisões técnicas e sugerir soluções alinhadas com os objetivos do produto.

**Stack Tecnológica** (`tech.md`) - Documenta os frameworks, bibliotecas, ferramentas de desenvolvimento e restrições técnicas escolhidos. Quando o Kiro sugerir implementações, ele preferirá sua stack estabelecida em vez de alternativas.

**Estrutura do Projeto** (`structure.md`) - Descreve a organização de arquivos, convenções de nomenclatura, padrões de importação e decisões arquiteturais. Isso garante que o código gerado se encaixe perfeitamente na sua base de código existente.

Esses arquivos fundamentais são incluídos em cada interação por padrão, formando a base do entendimento do Kiro sobre o projeto.

## Criando arquivos de steering personalizados

1. Navegue até a seção **Steering** no painel do Kiro
1. Clique no botão **+**
1. Selecione o escopo do arquivo de steering: workspace ou global
1. Escolha um nome de arquivo descritivo (ex: `api-standards.md`)
1. Escreva suas orientações usando a sintaxe padrão de markdown
1. Use linguagem natural para descrever seus requisitos
1. Opcionalmente, para arquivos de steering de workspace, você pode usar o botão **Refine** para que o Kiro refine seus requisitos

Uma vez criados, os arquivos de steering ficam imediatamente disponíveis em todas as interações do Kiro.

## Agents.md

O Kiro suporta o fornecimento de diretivas de steering via o padrão [AGENTS.md](https://agents.md/). Os arquivos AGENTS.md estão no formato markdown, semelhante aos arquivos de steering do Kiro; no entanto, os arquivos AGENTS.md não suportam [modos de inclusão](#modos-de-inclusão) e são sempre incluídos.

Você pode adicionar arquivos AGENTS.md ao local de steering global (`~/.kiro/steering/`), ou à pasta raiz do seu workspace, e eles serão detectados automaticamente pelo Kiro.

## Modos de inclusão

Os arquivos de steering podem ser configurados para carregar em momentos diferentes conforme suas necessidades. Essa flexibilidade ajuda a otimizar o desempenho e garante que o contexto relevante esteja disponível quando necessário.

Configure os modos de inclusão adicionando front matter no topo dos seus arquivos de steering. O front matter usa sintaxe YAML e deve ser colocado no início do arquivo, entre três traços (`---`).

**ℹ️ Info:** A configuração de inclusão deve ser o primeiro conteúdo do arquivo, sem linhas em branco ou conteúdo antes dela.

### Sempre incluído (padrão)
```yaml
---
inclusion: always
---
```

Esses arquivos são carregados automaticamente em cada interação do Kiro. Use este modo para padrões centrais que devem influenciar toda a geração de código e sugestões. Exemplos incluem sua stack tecnológica, convenções de código e princípios arquiteturais fundamentais.

**Ideal para**: Padrões de todo o workspace, preferências tecnológicas, políticas de segurança e convenções de código que se aplicam universalmente.

### Inclusão condicional
```yaml
---
inclusion: fileMatch
fileMatchPattern: "components/**/*.tsx"
---
```

Os arquivos são incluídos automaticamente apenas quando se trabalha com arquivos que correspondem ao padrão especificado. Isso mantém o contexto relevante e reduz o ruído, carregando orientações especializadas apenas quando necessário.

Você também pode especificar múltiplos padrões usando um array:
```yaml
---
inclusion: fileMatch
fileMatchPattern: ["**/*.ts", "**/*.tsx", "**/tsconfig.*.json"]
---
```

**Padrões comuns**:
- `"*.tsx"` - Componentes React e arquivos JSX
- `"app/api/**/*"` - Rotas de API e lógica de backend
- `"**/*.test.*"` - Arquivos de teste e utilitários de teste
- `"src/components/**/*"` - Diretrizes específicas de componentes
- `"*.md"` - Arquivos de documentação
- `["**/*.ts", "**/*.tsx"]` - Todos os arquivos TypeScript
- `["*.js", "*.jsx", "*.ts", "*.tsx"]` - Todos os arquivos JavaScript e TypeScript

**Ideal para**: Padrões específicos de domínio como padrões de componentes, regras de design de API, abordagens de teste ou procedimentos de implantação que se aplicam apenas a certos tipos de arquivo.

### Inclusão manual
```yaml
---
inclusion: manual
---
```

Os arquivos ficam disponíveis sob demanda ao referenciá-los com `#nome-do-arquivo-steering` nas suas mensagens de chat. Isso dá controle preciso sobre quando o contexto especializado é necessário, sem poluir cada interação.

**Uso**: Digite `#troubleshooting-guide` ou `#performance-optimization` no chat para incluir aquele arquivo de steering na conversa atual. Os arquivos de steering manual também aparecem como comandos slash — digite `/` no chat para vê-los e selecioná-los.

**Ideal para**: Fluxos de trabalho especializados, guias de solução de problemas, procedimentos de migração ou documentação com muito contexto que só é necessária ocasionalmente.

### Inclusão automática
```yaml
---
inclusion: auto
name: api-design
description: REST API design patterns and conventions. Use when creating or modifying API endpoints.
---
```

Os arquivos são incluídos automaticamente quando sua solicitação corresponde à descrição. Isso funciona de forma semelhante às [skills](https://kiro.dev/docs/skills.md) — o Kiro usa a descrição para decidir quando o arquivo de steering é relevante.

| Campo | Obrigatório | Descrição |
|-------|-------------|-----------|
| `name` | Sim | Identificador do arquivo de steering. Usado para exibição e correspondência. |
| `description` | Sim | Quando incluir este arquivo. O Kiro compara isso com suas solicitações. |

Os arquivos de steering com inclusão automática também aparecem como comandos slash no chat. Digite `/` seguido do nome do arquivo de steering para incluí-lo explicitamente, além da ativação automática baseada na correspondência de descrição.

**Ideal para**: Orientações com muito contexto que devem carregar apenas quando relevantes — como conhecimento especializado de domínio, fluxos de trabalho complexos ou material de referência detalhado que sobrecarregaria o steering sempre ativo.

## Referências de arquivos

Vincule a arquivos ativos do workspace para manter o steering atualizado:

```markdown
#[[file:<nome_relativo_do_arquivo>]]
```

Exemplos:
- Specs de API: `#[[file:api/openapi.yaml]]`
- Padrões de componentes: `#[[file:components/ui/button.tsx]]`
- Templates de configuração: `#[[file:.env.example]]`

## Boas práticas

**Mantenha os Arquivos Focados**
Um domínio por arquivo - design de API, testes ou procedimentos de implantação.

**Use Nomes Claros**
- `api-rest-conventions.md` - Padrões REST de API
- `testing-unit-patterns.md` - Abordagens de testes unitários
- `components-form-validation.md` - Padrões de componentes de formulário

**Inclua Contexto**
Explique por que as decisões foram tomadas, não apenas quais são os padrões.

**Forneça Exemplos**
Use trechos de código e comparações antes/depois para demonstrar os padrões.

**Segurança em Primeiro Lugar**
Nunca inclua chaves de API, senhas ou dados sensíveis. Os arquivos de steering fazem parte da sua base de código.

**Mantenha Regularmente**
- Revise durante o planejamento de sprint e mudanças de arquitetura
- Teste referências de arquivos após reestruturações
- Trate mudanças de steering como mudanças de código - exija revisões

## Estratégias comuns de arquivos de steering

**Padrões de API** (`api-standards.md`) - Defina convenções REST, formatos de resposta de erro, fluxos de autenticação e estratégias de versionamento. Inclua padrões de nomenclatura de endpoints, uso de códigos de status HTTP e exemplos de requisição/resposta.

**Abordagem de Testes** (`testing-standards.md`) - Estabeleça padrões de testes unitários, estratégias de testes de integração, abordagens de mock e expectativas de cobertura. Documente bibliotecas de teste preferidas, estilos de asserção e organização de arquivos de teste.

**Estilo de Código** (`code-conventions.md`) - Especifique padrões de nomenclatura, organização de arquivos, ordenação de importações e decisões arquiteturais. Inclua exemplos de estruturas de código preferidas, padrões de componentes e anti-padrões a evitar.

**Diretrizes de Segurança** (`security-policies.md`) - Documente requisitos de autenticação, regras de validação de dados, padrões de sanitização de entrada e medidas de prevenção de vulnerabilidades. Inclua práticas de codificação segura específicas para sua aplicação.

**Processo de Implantação** (`deployment-workflow.md`) - Descreva procedimentos de build, configurações de ambiente, etapas de implantação e estratégias de rollback. Inclua detalhes do pipeline de CI/CD e requisitos específicos de ambiente.

## Documentação relacionada

- [Skills](https://kiro.dev/docs/skills.md) - Pacotes de instruções modulares sob demanda para fluxos de trabalho especializados
- [Hooks](https://kiro.dev/docs/hooks.md) - Automatize ações do agente com base em eventos
