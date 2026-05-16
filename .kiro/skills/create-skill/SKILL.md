---
name: create-skill
description: Cria uma nova skill seguindo o padrão Agent Skills. Use quando o usuário pedir para criar, construir ou montar uma skill, ou quando precisar estruturar um pacote de instruções reutilizável para o Kiro.
metadata:
  author: joaopuel
  version: "1.0"
---

## Objetivo

Criar uma skill válida seguindo o padrão [Agent Skills](https://agentskills.io), com estrutura de diretórios correta, frontmatter válido e instruções claras.

## Processo

### 1. Entender o propósito da skill

Antes de criar qualquer arquivo, pergunte ou infira:
- **Nome**: identificador único em inglês (lowercase, hífens, máx. 64 chars)
- **Descrição**: o que faz e **quando deve ser ativada** — obrigatório ser explícito (máx. 1024 chars)
- **Gatilhos de ativação**: quais palavras-chave, intenções ou situações devem disparar a skill
- **Escopo**: workspace (`.kiro/skills/`) ou global (`~/.kiro/skills/`)
- **Conteúdo**: instruções, scripts, referências ou assets necessários

> A descrição é o único mecanismo de ativação automática da skill. Se não deixar claro **quando** usá-la, o agente não saberá ativá-la no momento certo.

### 2. Validar o nome

O nome deve:
- Estar **sempre em inglês** — o nome aparece como comando slash (`/create-skill`) e deve ser natural de digitar
- Conter apenas letras minúsculas (`a-z`), números e hífens (`-`)
- Não começar nem terminar com hífen
- Não conter hífens consecutivos (`--`)
- Corresponder exatamente ao nome da pasta

Exemplos válidos: `code-review`, `deploy-aws`, `create-skill`
Exemplos inválidos: `Code-Review`, `-deploy`, `criar-skill` (português não permitido)

### 3. Criar a estrutura de diretórios

```
<escopo>/skills/<nome-da-skill>/
├── SKILL.md           # Obrigatório
├── references/        # Documentação detalhada (opcional)
├── scripts/           # Scripts executáveis (opcional)
└── assets/            # Templates e recursos estáticos (opcional)
```

Crie apenas os diretórios necessários. Não crie pastas vazias.

### 4. Escrever o SKILL.md

O arquivo deve seguir exatamente este formato:

```markdown
---
name: <nome-da-skill-em-ingles>
description: <descrição em português do que faz e quando usar>
---

## <Título principal>

<Instruções em português>
```

Regras de idioma:
- **`name`**: sempre em inglês — é usado como comando slash e deve ser intuitivo de digitar
- **`description`**: sempre em português — o matching de ativação automática usa a linguagem dos seus prompts
- **Corpo do SKILL.md**: sempre em português — as instruções são consumidas pelo agente no contexto do time

Regras da `description`:
- Deve descrever **o que a skill faz** e **quando deve ser ativada** — ambos são obrigatórios
- Inclua palavras-chave específicas que o usuário usaria ao pedir a tarefa
- Evite descrições genéricas como "ajuda com X" — prefira "Use quando o usuário pedir para criar/configurar/revisar X"
- Exemplo ruim: `Skill para criar skills.`
- Exemplo bom: `Cria uma nova skill seguindo o padrão Agent Skills. Use quando o usuário pedir para criar, construir ou montar uma skill, ou quando precisar estruturar um pacote de instruções reutilizável para o Kiro.`

Campos opcionais do frontmatter:
- `license`: nome da licença ou arquivo de licença
- `compatibility`: requisitos de ambiente
- `metadata`: pares chave-valor adicionais (author, version, etc.)
- `allowed-tools`: ferramentas pré-aprovadas (experimental)

### 5. Adicionar referências (se necessário)

Se a skill tiver documentação extensa, mova o conteúdo detalhado para `references/`:
- `references/REFERENCE.md` — referência técnica completa
- Arquivos específicos de domínio conforme necessário

Referencie no `SKILL.md` com caminho relativo:
```markdown
Consulte [a referência completa](references/REFERENCE.md) para detalhes.
```

### 6. Adicionar scripts (se necessário)

Scripts em `scripts/` devem:
- Ser autocontidos ou documentar dependências claramente
- Incluir mensagens de erro úteis
- Tratar casos extremos

### 7. Verificar a skill criada

Confirme que:
- [ ] O nome da pasta corresponde ao campo `name` no frontmatter
- [ ] O campo `name` está em inglês
- [ ] O campo `description` está em português
- [ ] O corpo do `SKILL.md` está em português
- [ ] O `SKILL.md` tem frontmatter YAML válido (campos `name` e `description` presentes)
- [ ] A descrição deixa explícito **quando** a skill deve ser ativada (não apenas o que ela faz)
- [ ] A descrição contém palavras-chave que o usuário usaria ao pedir a tarefa
- [ ] O conteúdo do `SKILL.md` tem menos de 500 linhas
- [ ] Referências usam caminhos relativos a partir da raiz da skill

## Referências

Consulte a documentação completa em:
- [Guia de uso de skills](references/skills.md)
- [Especificação do formato](references/specification.md)
