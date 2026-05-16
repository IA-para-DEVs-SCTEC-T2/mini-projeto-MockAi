> ## Índice de Documentação
> Acesse o índice completo da documentação em: https://kiro.dev/llms.txt
> Use este arquivo para descobrir todas as páginas disponíveis antes de explorar mais.

# Hooks

> Automatize tarefas repetitivas e aplique boas práticas com os hooks de agente do Kiro

Os hooks de agente são ferramentas de automação poderosas que otimizam seu fluxo de desenvolvimento executando automaticamente ações predefinidas do agente quando eventos específicos ocorrem na sua IDE. Com os hooks, você elimina a necessidade de solicitar manualmente tarefas rotineiras e garante consistência em toda a base de código.

## O que são hooks de agente?

Hooks de agente são gatilhos automatizados que executam prompts de agente predefinidos ou comandos shell quando eventos específicos ocorrem na sua IDE. Em vez de solicitar manualmente a execução de tarefas rotineiras, os hooks configuram respostas automáticas a eventos como:

- Salvar, criar ou excluir arquivos
- Envio de prompt pelo usuário e conclusão de turno do agente
- Antes ou depois de invocações de ferramentas
- Antes ou depois da execução de tarefas de spec
- Gatilhos manuais sob demanda

Os hooks de agente transformam seu fluxo de desenvolvimento por meio de automação inteligente. Ao configurar hooks para tarefas comuns, você pode:

- Manter qualidade de código consistente
- Prevenir vulnerabilidades de segurança
- Reduzir sobrecarga manual
- Padronizar processos da equipe
- Criar ciclos de desenvolvimento mais rápidos

Seja trabalhando em um projeto pequeno ou gerenciando uma base de código grande, os hooks de agente ajudam a garantir que tarefas rotineiras sejam tratadas de forma automática e consistente, permitindo que você se concentre em construir um ótimo software.

  [Vídeo](https://kiro.dev/videos/kiro-hook.mp4)

## Como os hooks de agente funcionam

O sistema de hooks de agente segue um processo simples de duas etapas:

1. **Detecção de Evento**: O sistema monitora eventos específicos na sua IDE
2. **Ação Automatizada**: Quando um evento ocorre, uma ação — seja um prompt de agente predefinido ou um comando shell — é executada

Esse fluxo de automação elimina tarefas repetitivas e garante consistência em toda a base de código.

## Configurando hooks de agente

Você pode criar hooks de duas formas — descreva o que deseja em linguagem natural e deixe o Kiro gerar a configuração, ou preencha um formulário manualmente.

### Criando um hook

1. Navegue até a seção **Agent Hooks** no painel do Kiro
2. Clique no botão **+** para criar um novo hook
3. Escolha como deseja criar o hook:
   - **Criar um hook manualmente** — configure um hook à mão em um formulário
   - **Pedir ao Kiro para criar um hook** — descreva um hook em linguagem natural e deixe o Kiro criá-lo

### Pedir ao Kiro para criar um hook

1. Selecione **Pedir ao Kiro para criar um hook**
2. Descreva o fluxo do hook em linguagem natural
3. Pressione **Enter** ou clique em **Enviar** para prosseguir
4. Revise a configuração gerada, ajuste se necessário e clique em **Salvar Hook**

### Criar um hook manualmente

1. Selecione **Criar um hook manualmente** para abrir o formulário
2. Preencha os campos do formulário:
   - **Título** — um nome curto para o hook
   - **Descrição** — o que o hook faz
   - **Evento** — o tipo de gatilho (ex.: Salvar Arquivo, Pós Uso de Ferramenta, Pré Execução de Tarefa)
   - **Nome da ferramenta** — para hooks de Pré/Pós Uso de Ferramenta, especifique quais ferramentas corresponder
   - **Padrão de arquivo** — para hooks de eventos de arquivo, especifique quais arquivos corresponder
   - **Ação** — escolha **Perguntar ao Kiro** (prompt de agente) ou **Executar Comando** (comando shell)
   - **Instruções** ou **Comando** — o prompt ou comando shell a executar
3. Clique em **Criar Hook** quando terminar, ou **Limpar** para redefinir o formulário

Você também pode abrir a interface de Hooks pela Paleta de Comandos com `Cmd + Shift + P` (Mac) ou `Ctrl + Shift + P` (Windows/Linux) e digitando `Kiro: Open Kiro Hook UI`.

## Próximos passos

Agora que você criou um arquivo de hook, pode aprender mais sobre hooks aqui:

- **[Tipos de Hook](https://kiro.dev/docs/hooks/types.md)** - Aprenda sobre os diferentes tipos de gatilho e seus casos de uso
- **[Ações de Hook](https://kiro.dev/docs/hooks/actions.md)** - Aprenda sobre as diferentes ações de hook e seus casos de uso
- **[Gerenciamento](https://kiro.dev/docs/hooks/management.md)** - Aprenda a organizar, editar e manter seus hooks
- **[Boas Práticas](https://kiro.dev/docs/hooks/best-practices.md)** - Siga padrões para um design eficaz de hooks
- **[Exemplos](https://kiro.dev/docs/hooks/examples.md)** - Veja exemplos e templates que você pode usar
