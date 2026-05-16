# Labels para Issues no GitHub

Este documento define as labels disponíveis para categorização de issues no projeto MockAI.

> **Referência**: Este arquivo documenta as labels utilizadas na seção `labels` dos seguintes templates:
> - `labels: ["story"]` → [`user_story_template.yml`](../assets/user_story_template.yml)
> - `labels: ["epic"]` → [`epic_template.yml`](../assets/epic_template.yml)
> - `labels: ["tech"]` → [`tech_template.yml`](../assets/tech_template.yml)
> - `labels: ["docs"]` → [`docs_template.yml`](../assets/docs_template.yml)

## Categorias de Labels

### Tipo de Issue

#### `epic`
- **Descrição**: Agrupador de alto nível
- **Uso**: Issues que agrupam múltiplas stories, tasks ou bugs relacionados
- **Características**:
  - Representa uma iniciativa maior ou tema de trabalho
  - Issue pai que organiza outras issues
  - Geralmente tem escopo amplo e longa duração

#### `story`
- **Descrição**: Unidade principal de entrega
- **Uso**: Issues que representam uma funcionalidade completa do ponto de vista do usuário
- **Características**:
  - Descreve valor para o usuário final
  - Pode estar vinculada a um EPIC
  - Geralmente segue o formato "Como [usuário], quero [ação] para [benefício]"

#### `docs`
- **Descrição**: Documentação
- **Uso**: Issues relacionadas à criação ou atualização de documentação
- **Características**:
  - README, CONTRIBUTING, PRD
  - Documentação técnica e guias
  - Arquivos de auxílio para IA (steerings, specs, skills)

#### `tech`
- **Descrição**: Tarefa técnica
- **Uso**: Issues relacionadas a tarefas técnicas que não entregam valor direto ao usuário
- **Características**:
  - Configuração de infraestrutura
  - Refatoração de código
  - Setup de ferramentas e pipelines
  - Ajustes de configuração e dependências

### Área Técnica

#### `backend`
- **Descrição**: Backend
- **Uso**: Issues relacionadas ao desenvolvimento backend
- **Características**:
  - APIs REST
  - Lógica de negócio
  - Integração com banco de dados
  - Serviços e processamento

#### `frontend`
- **Descrição**: Frontend
- **Uso**: Issues relacionadas ao desenvolvimento frontend
- **Características**:
  - Interface do usuário
  - Componentes visuais
  - Experiência do usuário (UX)
  - Integração com APIs

#### `ai`
- **Descrição**: Funcionalidades de IA
- **Uso**: Issues relacionadas a recursos de inteligência artificial
- **Características**:
  - Integração com modelos de IA
  - Processamento de linguagem natural
  - Geração automática de conteúdo
  - Machine learning

### Prioridade

#### `priority:high`
- **Descrição**: Alta prioridade
- **Uso**: Issues críticas que devem ser tratadas com urgência
- **Características**:
  - Bloqueadores
  - Bugs críticos em produção
  - Funcionalidades essenciais para releases

#### `priority:medium`
- **Descrição**: Média prioridade
- **Uso**: Issues importantes mas não urgentes
- **Características**:
  - Funcionalidades planejadas
  - Melhorias significativas
  - Bugs não críticos

#### `priority:low`
- **Descrição**: Baixa prioridade
- **Uso**: Issues que podem ser tratadas quando houver disponibilidade
- **Características**:
  - Melhorias menores
  - Refatorações não urgentes
  - Nice-to-have features

## Combinação de Labels

As labels podem e devem ser combinadas para melhor categorização. Exemplos:

- `epic` + `backend` + `priority:high`
- `story` + `frontend` + `priority:medium`
- `docs` + `ai` + `priority:low`

## Boas Práticas

1. **Sempre use pelo menos uma label de tipo** (`epic`, `story`, `tech`, `docs`, etc.)
2. **Adicione labels de área técnica** quando aplicável (`backend`, `frontend`, `ai`)
3. **Defina a prioridade** para facilitar o planejamento (`priority:high`, `priority:medium`, `priority:low`)
4. **Seja consistente** na aplicação das labels
5. **Revise periodicamente** as labels das issues para mantê-las atualizadas
