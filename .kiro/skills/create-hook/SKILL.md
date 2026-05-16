---
name: create-hook
description: Cria e configura hooks de agente no Kiro seguindo o schema correto. Use quando o usuário pedir para criar, adicionar, configurar ou automatizar um hook, definir gatilhos de eventos na IDE, ou quando precisar automatizar tarefas repetitivas com hooks de agente.
---

## Criação de Hooks de Agente

Os hooks de agente automatizam tarefas repetitivas e aplicam boas práticas executando ações predefinidas quando eventos específicos ocorrem na IDE.

Consulte a [documentação completa de hooks](references/hooks.md) para entender todos os conceitos antes de criar.

## Processo de Criação

### 1. Entender o propósito do hook

Antes de criar qualquer arquivo, pergunte ou infira:
- **Evento de gatilho**: qual evento deve disparar o hook
- **Ação**: o que deve acontecer quando o evento ocorrer (prompt ao agente ou comando shell)
- **Escopo**: quais arquivos ou ferramentas o hook deve monitorar (se aplicável)
- **Objetivo**: qual problema o hook resolve ou qual prática ele reforça

### 2. Estrutura do arquivo de hook

Os hooks ficam em `.kiro/hooks/` e devem seguir exatamente este schema JSON:

```json
{
  "name": "string (obrigatório)",
  "version": "string (obrigatório)",
  "description": "string (opcional)",
  "when": {
    "type": "um dos tipos de evento listados abaixo",
    "patterns": ["padrões de arquivo (obrigatório apenas para eventos de arquivo)"],
    "toolTypes": ["categorias ou padrões regex de ferramentas (obrigatório para preToolUse e postToolUse)"]
  },
  "then": {
    "type": "askAgent ou runCommand",
    "prompt": "string (obrigatório apenas para askAgent)",
    "command": "string (obrigatório apenas para runCommand)"
  }
}
```

### 3. Tipos de evento disponíveis

| Tipo | Descrição |
|------|-----------|
| `fileEdited` | Quando o usuário salva um arquivo de código |
| `fileCreated` | Quando o usuário cria um novo arquivo |
| `fileDeleted` | Quando o usuário exclui um arquivo existente |
| `userTriggered` | Quando o usuário aciona manualmente o hook |
| `promptSubmit` | Quando uma mensagem é enviada ao agente |
| `agentStop` | Quando uma execução do agente é concluída |
| `preToolUse` | Antes de uma ferramenta ser executada |
| `postToolUse` | Após uma ferramenta ser executada |
| `preTaskExecution` | Antes do status de uma tarefa de spec ser definido como em andamento |
| `postTaskExecution` | Após o status de uma tarefa de spec ser definido como concluído |

### 4. Tipos de ação disponíveis

**`askAgent`** — envia um prompt ao agente:
```json
{
  "then": {
    "type": "askAgent",
    "prompt": "Revise as alterações e verifique se seguem os padrões do projeto"
  }
}
```

**`runCommand`** — executa um comando shell:
```json
{
  "then": {
    "type": "runCommand",
    "command": "npm run lint"
  }
}
```

### 5. Categorias de ferramentas para preToolUse e postToolUse

Categorias válidas: `read`, `write`, `shell`, `web`, `spec`, `*`

Para ferramentas MCP, use padrões regex: `".*sql.*"` corresponde a qualquer ferramenta com "sql" no nome.

### 6. Exemplos práticos

**Hook de lint ao salvar arquivos TypeScript:**
```json
{
  "name": "Lint ao Salvar",
  "version": "1.0.0",
  "description": "Executa lint automaticamente ao salvar arquivos TypeScript",
  "when": {
    "type": "fileEdited",
    "patterns": ["*.ts", "*.tsx"]
  },
  "then": {
    "type": "runCommand",
    "command": "npm run lint"
  }
}
```

**Hook de revisão antes de operações de escrita:**
```json
{
  "name": "Revisar Operações de Escrita",
  "version": "1.0.0",
  "description": "Verifica se operações de escrita seguem os padrões de codificação",
  "when": {
    "type": "preToolUse",
    "toolTypes": ["write"]
  },
  "then": {
    "type": "askAgent",
    "prompt": "Verifique se esta operação de escrita segue os padrões do projeto"
  }
}
```

**Hook de testes após conclusão de tarefa:**
```json
{
  "name": "Executar Testes Após Tarefa",
  "version": "1.0.0",
  "description": "Executa a suíte de testes após a conclusão de cada tarefa de spec",
  "when": {
    "type": "postTaskExecution"
  },
  "then": {
    "type": "runCommand",
    "command": "npm run test"
  }
}
```

### 7. Verificar o hook criado

Confirme que:
- [ ] O arquivo está em `.kiro/hooks/` com extensão `.json`
- [ ] Os campos `name` e `version` estão presentes
- [ ] O campo `when.type` é um dos tipos de evento válidos
- [ ] `when.patterns` está presente para eventos de arquivo (`fileEdited`, `fileCreated`, `fileDeleted`)
- [ ] `when.toolTypes` está presente para `preToolUse` e `postToolUse`
- [ ] O campo `then.type` é `askAgent` ou `runCommand`
- [ ] `then.prompt` está presente quando `then.type` é `askAgent`
- [ ] `then.command` está presente quando `then.type` é `runCommand`

## Atenção: preToolUse e Dependências Circulares

Hooks `preToolUse` são frequentemente usados para controle de acesso. Regras importantes:
- Se o hook indicar que o acesso **não** foi concedido, a invocação da ferramenta é **proibida**
- Se não houver indicação de negação, a ferramenta **deve** ser invocada novamente com os mesmos parâmetros
- Detecte e evite dependências circulares: se o Hook A exige a Ferramenta X, que dispara o Hook A novamente, pule o hook nas invocações aninhadas
