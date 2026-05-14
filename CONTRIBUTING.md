# Guia de Contribuição — MockAI

> Documento oficial de contribuição para o projeto **MockAI**. Leia com atenção antes de abrir qualquer Pull Request.

---

## Sumário

1. [Pré-requisitos](#1-pré-requisitos)
2. [Configuração do Ambiente Local](#2-configuração-do-ambiente-local)
3. [Visão Geral do GitFlow](#3-visão-geral-do-gitflow)
4. [Responsabilidade de Cada Branch](#4-responsabilidade-de-cada-branch)
5. [Fluxo de Desenvolvimento](#5-fluxo-de-desenvolvimento)
6. [Processo de Feature](#6-processo-de-feature)
7. [Processo de Release](#7-processo-de-release)
8. [Processo de Hotfix](#8-processo-de-hotfix)
9. [Estratégia de Merge](#9-estratégia-de-merge)
10. [Versionamento](#10-versionamento)
11. [Conventional Commits](#11-conventional-commits)
12. [Fluxo de Pull Request](#12-fluxo-de-pull-request)
13. [Revisão de Código](#13-revisão-de-código)
14. [Resolução de Conflitos](#14-resolução-de-conflitos)
15. [Execução de Testes](#15-execução-de-testes)
16. [Padrões de Código](#16-padrões-de-código)
17. [Regras de Documentação](#17-regras-de-documentação)
18. [Checklist antes de Abrir PR](#18-checklist-antes-de-abrir-pr)
19. [Boas Práticas de Colaboração](#19-boas-práticas-de-colaboração)

---

## 1. Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas e configuradas:

| Ferramenta | Versão mínima | Verificação |
|------------|---------------|-------------|
| Java (JDK) | 17 | `java -version` |
| Maven | 3.9.7 | `mvn -version` |
| Git | 2.40+ | `git --version` |
| IDE | IntelliJ IDEA / VS Code | — |

Configure também sua identidade no Git:

```bash
git config --global user.name "Seu Nome Completo"
git config --global user.email "seu.email@empresa.com"
```

---

## 2. Configuração do Ambiente Local

### 2.1 Clonar o repositório

```bash
git clone https://github.com/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi.git
cd mini-projeto-MockAi
```

### 2.2 Configurar a branch de trabalho

Sempre parta da branch `develop` atualizada:

```bash
git checkout develop
git pull origin develop
```

### 2.3 Compilar o projeto

```bash
mvn clean compile
```

### 2.4 Executar a aplicação localmente

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

- **API:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`
- **H2 Console:** `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Usuário: `sa` | Senha: *(vazio)*

---

## 3. Visão Geral do GitFlow

O **GitFlow** é um modelo de ramificação que organiza o desenvolvimento em branches com responsabilidades bem definidas. Ele separa o trabalho em andamento do código estável, facilitando releases controladas e correções emergenciais.

```
main ──────────────────────────────────────────────────► (produção)
  │                                    ▲           ▲
  │                              release/*     hotfix/*
  │                                    │           │
develop ──────────────────────────────►│───────────┤──► (integração)
  │          │          │              │
feature/A  feature/B  feature/C        │
  │          │          │              │
  └──────────┴──────────┘              │
             (merge em develop)        │
                                       │
                               (merge em main e develop)
```

---

## 4. Responsabilidade de Cada Branch

| Branch | Propósito | Origem | Destino do merge |
|--------|-----------|--------|-----------------|
| `main` | Código em produção. Sempre estável e versionado. | — | — |
| `develop` | Integração contínua de features. Base para novas funcionalidades. | `main` | `release/*` |
| `feature/*` | Desenvolvimento de novas funcionalidades. | `develop` | `develop` |
| `release/*` | Preparação e estabilização de uma nova versão. | `develop` | `main` e `develop` |
| `hotfix/*` | Correção urgente de bugs em produção. | `main` | `main` e `develop` |

### Regras de proteção

- `main` e `develop` são branches **protegidas** — nenhum commit direto é permitido.
- Todo código entra nessas branches exclusivamente via **Pull Request aprovado**.

---

## 5. Fluxo de Desenvolvimento

O ciclo completo de desenvolvimento segue esta sequência:

```
develop → feature/* → develop → release/* → main
                                              ↑
                                          hotfix/* (quando necessário)
```

**Passo a passo resumido:**

1. Crie uma `feature/*` a partir de `develop`
2. Desenvolva e faça commits seguindo o padrão Conventional Commits
3. Abra PR da `feature/*` para `develop`
4. Após aprovação e merge, a feature está integrada
5. Quando o conjunto de features estiver pronto para release, crie uma `release/*` a partir de `develop`
6. Faça ajustes finais, bump de versão e testes na `release/*`
7. Abra PR da `release/*` para `main` (e também para `develop`)
8. Após merge em `main`, crie a tag de versão

---

## 6. Processo de Feature

### 6.1 Criando uma feature branch

```bash
# Atualize develop antes de criar a branch
git checkout develop
git pull origin develop

# Crie a feature branch
git checkout -b feature/nome-descritivo-da-feature
```

### 6.2 Padrão de nomenclatura

```
feature/<descricao-curta-em-kebab-case>
```

**Exemplos válidos:**

```
feature/upload-swagger-documentation
feature/generate-mock-endpoints
feature/list-active-endpoints
feature/delete-all-endpoints
```

**Exemplos inválidos:**

```
feature/nova_funcionalidade     ❌ (underscore não permitido)
feature/NovaFuncionalidade      ❌ (PascalCase não permitido)
feature/fix-bug                 ❌ (use hotfix/* para correções)
minha-feature                   ❌ (sem prefixo)
```

### 6.3 Desenvolvendo na feature

```bash
# Faça suas alterações e commits
git add .
git commit -m "feat(swagger): adiciona endpoint de upload de documentação OpenAPI"

# Mantenha a branch atualizada com develop
git fetch origin
git rebase origin/develop

# Envie para o repositório remoto
git push origin feature/upload-swagger-documentation
```

### 6.4 Finalizando a feature

Abra um Pull Request da `feature/*` para `develop` (veja a seção [Fluxo de Pull Request](#12-fluxo-de-pull-request)).

Após aprovação e merge, delete a branch:

```bash
git branch -d feature/upload-swagger-documentation
git push origin --delete feature/upload-swagger-documentation
```

---

## 7. Processo de Release

### 7.1 Quando criar uma release

Crie uma `release/*` quando:

- Um conjunto de features está completo e integrado em `develop`
- O time decidiu que é hora de publicar uma nova versão
- Todos os critérios de aceite das features incluídas foram validados

### 7.2 Criando a release branch

```bash
git checkout develop
git pull origin develop

git checkout -b release/1.2.0
```

### 7.3 Padrão de nomenclatura

```
release/<versao-semver>
```

**Exemplos:**

```
release/1.0.0
release/1.1.0
release/2.0.0
```

### 7.4 Atividades na release branch

Na `release/*` são permitidos apenas:

- Bump de versão no `pom.xml`
- Correções de bugs menores encontrados durante testes de homologação
- Atualização de documentação (CHANGELOG, README)

```bash
# Exemplo: atualizar versão no pom.xml
# Edite a tag <version> no pom.xml para 1.2.0

git add pom.xml
git commit -m "chore(release): bump version para 1.2.0"

git push origin release/1.2.0
```

### 7.5 Finalizando a release

Abra **dois** Pull Requests:

1. `release/1.2.0` → `main`
2. `release/1.2.0` → `develop`

Após merge em `main`, crie a tag:

```bash
git checkout main
git pull origin main
git tag -a v1.2.0 -m "Release v1.2.0"
git push origin v1.2.0
```

Delete a branch de release:

```bash
git branch -d release/1.2.0
git push origin --delete release/1.2.0
```

---

## 8. Processo de Hotfix

### 8.1 Quando criar um hotfix

Use `hotfix/*` exclusivamente para **correções urgentes em produção** (`main`). Nunca use para novas funcionalidades.

### 8.2 Criando a hotfix branch

```bash
git checkout main
git pull origin main

git checkout -b hotfix/correcao-endpoint-nulo
```

### 8.3 Padrão de nomenclatura

```
hotfix/<descricao-curta-do-problema>
```

**Exemplos:**

```
hotfix/null-pointer-endpoint-response
hotfix/swagger-parse-error
hotfix/cors-header-missing
```

### 8.4 Desenvolvendo o hotfix

```bash
# Corrija o problema
git add .
git commit -m "fix(endpoint): corrige NullPointerException ao processar resposta vazia"

# Bump de versão patch (ex: 1.2.0 → 1.2.1)
git add pom.xml
git commit -m "chore(release): bump version para 1.2.1"

git push origin hotfix/null-pointer-endpoint-response
```

### 8.5 Finalizando o hotfix

Abra **dois** Pull Requests:

1. `hotfix/*` → `main`
2. `hotfix/*` → `develop`

Após merge em `main`, crie a tag:

```bash
git checkout main
git pull origin main
git tag -a v1.2.1 -m "Hotfix v1.2.1 - corrige NullPointerException"
git push origin v1.2.1
```

Delete a branch:

```bash
git branch -d hotfix/null-pointer-endpoint-response
git push origin --delete hotfix/null-pointer-endpoint-response
```

---

## 9. Estratégia de Merge

| Tipo de merge | Estratégia | Motivo |
|---------------|------------|--------|
| `feature/*` → `develop` | **Squash merge** (recomendado) ou merge commit | Mantém o histórico de `develop` limpo |
| `release/*` → `main` | **Merge commit** (sem fast-forward) | Preserva o ponto de release no histórico |
| `release/*` → `develop` | **Merge commit** | Garante que ajustes da release voltem para develop |
| `hotfix/*` → `main` | **Merge commit** (sem fast-forward) | Rastreabilidade do hotfix |
| `hotfix/*` → `develop` | **Merge commit** | Garante que a correção esteja em develop |

**Regra geral:** nunca use `git merge` diretamente nas branches protegidas. Todo merge ocorre via Pull Request.

---

## 10. Versionamento

O projeto segue o padrão **Semantic Versioning (SemVer)**: `MAJOR.MINOR.PATCH`

| Componente | Quando incrementar | Exemplo |
|------------|-------------------|---------|
| `MAJOR` | Mudanças incompatíveis com versões anteriores (breaking changes) | `1.0.0` → `2.0.0` |
| `MINOR` | Novas funcionalidades compatíveis com versões anteriores | `1.0.0` → `1.1.0` |
| `PATCH` | Correções de bugs compatíveis com versões anteriores | `1.0.0` → `1.0.1` |

A versão é gerenciada na tag `<version>` do `pom.xml`:

```xml
<version>1.2.0</version>
```

Durante o desenvolvimento em `develop`, a versão deve ter o sufixo `-SNAPSHOT`:

```xml
<version>1.3.0-SNAPSHOT</version>
```

---

## 11. Conventional Commits

Todos os commits devem seguir o padrão [Conventional Commits](https://www.conventionalcommits.org/):

```
<tipo>(<escopo>): <descrição curta em português>

[corpo opcional]

[rodapé opcional]
```

### 11.1 Tipos permitidos

| Tipo | Uso |
|------|-----|
| `feat` | Nova funcionalidade |
| `fix` | Correção de bug |
| `docs` | Alterações em documentação |
| `style` | Formatação, sem alteração de lógica |
| `refactor` | Refatoração sem nova feature ou fix |
| `test` | Adição ou correção de testes |
| `chore` | Tarefas de build, configuração, dependências |
| `perf` | Melhoria de performance |
| `ci` | Alterações em pipelines de CI/CD |
| `revert` | Reversão de commit anterior |

### 11.2 Escopos sugeridos

```
swagger, endpoint, mock, api, persistence, config, release, deps
```

### 11.3 Exemplos práticos

```bash
# Nova funcionalidade
git commit -m "feat(swagger): implementa parser de documentação OpenAPI 3.0"

# Correção de bug
git commit -m "fix(endpoint): corrige status HTTP incorreto em respostas sem body"

# Documentação
git commit -m "docs(readme): atualiza instruções de configuração do ambiente"

# Refatoração
git commit -m "refactor(mock): extrai lógica de geração de payload para serviço dedicado"

# Testes
git commit -m "test(swagger): adiciona testes unitários para o parser de OpenAPI"

# Configuração
git commit -m "chore(deps): atualiza SpringDoc OpenAPI para versão 3.0.2"

# Breaking change (MAJOR)
git commit -m "feat(api)!: altera contrato do endpoint de upload de documentação

BREAKING CHANGE: o campo 'content' foi renomeado para 'specification'"
```

### 11.4 Regras de commit

- Descrição em **português**, no imperativo presente ("adiciona", "corrige", "remove")
- Máximo de **72 caracteres** na linha de título
- Corpo do commit separado por linha em branco
- Referencie issues quando aplicável: `Closes #42`

---

## 12. Fluxo de Pull Request

### 12.1 Criando o PR

1. Certifique-se de que sua branch está atualizada com a branch de destino
2. Abra o PR pelo GitHub com título seguindo o padrão Conventional Commits
3. Preencha o template de PR completamente
4. Solicite revisão de pelo menos **1 revisor**

### 12.2 Template de PR

```markdown
## Descrição
<!-- O que foi feito? Qual problema resolve? -->

## Tipo de mudança
- [ ] Nova funcionalidade (feat)
- [ ] Correção de bug (fix)
- [ ] Refatoração (refactor)
- [ ] Documentação (docs)
- [ ] Outro: ___

## Issue relacionada
Closes #<número>

## Como testar
<!-- Passos para validar as mudanças -->
1. 
2. 

## Checklist
- [ ] Código compila sem erros (`mvn clean compile`)
- [ ] Testes passam (`mvn test`)
- [ ] Segue os padrões de código do projeto
- [ ] Documentação atualizada (se aplicável)
- [ ] Sem arquivos desnecessários (logs, .class, IDE configs)
```

### 12.3 Regras de PR

- PRs para `develop`: mínimo **1 aprovação**
- PRs para `main`: mínimo **2 aprovações**
- Nenhum PR pode ser mergeado com checks de CI falhando
- O autor do PR **não pode** aprovar o próprio PR

---

## 13. Revisão de Código

### 13.1 Responsabilidades do revisor

- Verificar se o código resolve o problema descrito
- Avaliar legibilidade, manutenibilidade e aderência aos padrões
- Checar cobertura de testes
- Identificar possíveis problemas de performance ou segurança
- Dar feedback construtivo e objetivo

### 13.2 Tipos de feedback

| Prefixo | Significado |
|---------|-------------|
| `[bloqueante]` | Deve ser corrigido antes do merge |
| `[sugestão]` | Melhoria opcional, não bloqueia |
| `[dúvida]` | Pedido de esclarecimento |
| `[elogio]` | Reconhecimento de boa prática |

**Exemplo de comentário:**

```
[bloqueante] Este método não trata o caso em que a lista de endpoints retorna vazia.
Considere adicionar uma verificação e lançar uma exceção de negócio adequada.
```

### 13.3 SLA de revisão

- PRs devem ser revisados em até **2 dias úteis**
- PRs urgentes (hotfix) devem ser revisados em até **4 horas**

---

## 14. Resolução de Conflitos

### 14.1 Prevenção

- Mantenha sua branch atualizada com `develop` regularmente
- Prefira branches de vida curta (máximo 3 dias sem merge)
- Comunique ao time quando for alterar arquivos compartilhados

### 14.2 Resolvendo conflitos via rebase

```bash
# Atualize develop
git fetch origin
git checkout develop
git pull origin develop

# Volte para sua feature e faça rebase
git checkout feature/minha-feature
git rebase origin/develop

# Resolva os conflitos nos arquivos marcados
# Após resolver cada arquivo:
git add <arquivo-resolvido>
git rebase --continue

# Se precisar abortar:
git rebase --abort

# Envie a branch atualizada (force push necessário após rebase)
git push origin feature/minha-feature --force-with-lease
```

### 14.3 Regras para resolução

- Nunca resolva conflitos aceitando cegamente uma das versões — entenda o que cada lado faz
- Em caso de dúvida, consulte o autor do código conflitante
- Após resolver, execute os testes antes de fazer push
- Use `--force-with-lease` em vez de `--force` para evitar sobrescrever trabalho de outros

---

## 15. Execução de Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório detalhado
mvn test -Dsurefire.useFile=false

# Executar um teste específico
mvn test -Dtest=NomeDaClasseTest

# Gerar relatório de cobertura (se configurado)
mvn verify
```

### Regras de testes

- Todo código novo deve ter testes correspondentes
- Testes não devem depender de ordem de execução
- Testes devem ser independentes e isolados (use H2 em memória)
- Nenhum PR deve reduzir a cobertura de testes existente

---

## 16. Padrões de Código

### 16.1 Arquitetura em camadas

O projeto segue arquitetura em camadas. Respeite as dependências entre elas:

```
API (Controllers, DTOs)
    ↓
Application (Use Cases, Services)
    ↓
Domain (Entities, Value Objects, Interfaces)
    ↑
Infrastructure (JPA Entities, Repositories, Adapters)
```

- **Domain:** Java puro, sem dependências de frameworks
- **Application:** Depende apenas de abstrações do Domain
- **Infrastructure:** Implementações concretas (JPA, H2)
- **API:** Controllers Spring MVC, DTOs, validações Bean Validation

### 16.2 Convenções Java

- Nomes de classes: `PascalCase`
- Nomes de métodos e variáveis: `camelCase`
- Constantes: `UPPER_SNAKE_CASE`
- Pacotes: `lowercase.separado.por.pontos`
- Não exponha entidades JPA diretamente na API — use DTOs
- Prefira injeção de dependência via construtor

### 16.3 Boas práticas obrigatórias

- Sem `System.out.println` — use o logger do projeto
- Sem código comentado — use git para histórico
- Sem imports não utilizados
- Métodos com responsabilidade única (máximo ~20 linhas como referência)
- Exceções de negócio devem ser tipadas e descritivas

---

## 17. Regras de Documentação

- **Javadoc** obrigatório em interfaces públicas e métodos de domínio complexos
- **Swagger/OpenAPI:** todos os endpoints devem ter anotações `@Operation`, `@ApiResponse` e `@Parameter`
- **README.md:** mantenha atualizado com qualquer mudança de configuração ou execução
- **CHANGELOG.md:** atualize a cada release com as mudanças incluídas

**Exemplo de documentação de endpoint:**

```java
@Operation(
    summary = "Carrega documentação Swagger",
    description = "Recebe uma especificação OpenAPI 3.0 em formato JSON e cria os endpoints mock correspondentes."
)
@ApiResponse(responseCode = "201", description = "Mock criado com sucesso")
@ApiResponse(responseCode = "400", description = "Documentação inválida ou mal formatada")
@PostMapping("/api/specifications")
public ResponseEntity<Void> uploadSpecification(@Valid @RequestBody SpecificationRequest request) {
    // ...
}
```

---

## 18. Checklist antes de Abrir PR

Antes de abrir qualquer Pull Request, confirme todos os itens abaixo:

### Código

- [ ] O código compila sem erros: `mvn clean compile`
- [ ] Todos os testes passam: `mvn test`
- [ ] Nenhum teste existente foi quebrado
- [ ] Sem warnings de compilação não tratados
- [ ] Sem código comentado ou `TODO` sem issue associada

### Branch e commits

- [ ] Branch criada a partir da branch correta (`develop` para features, `main` para hotfix)
- [ ] Branch atualizada com a branch de destino (rebase feito)
- [ ] Commits seguem o padrão Conventional Commits
- [ ] Histórico de commits está limpo (sem commits de "WIP" ou "fix typo" soltos)

### Qualidade

- [ ] Código segue os padrões de arquitetura do projeto
- [ ] Entidades JPA não estão expostas diretamente na API
- [ ] Endpoints documentados com anotações Swagger
- [ ] Sem `System.out.println` no código

### PR

- [ ] Título do PR segue o padrão Conventional Commits
- [ ] Template de PR preenchido completamente
- [ ] Issue relacionada referenciada (`Closes #N`)
- [ ] Revisor(es) solicitado(s)
- [ ] Branch de destino correta selecionada

---

## 19. Boas Práticas de Colaboração

- **Comunique-se cedo:** se encontrar um bloqueio ou dúvida, sinalize no canal do time antes de perder tempo
- **Branches curtas:** features devem durar no máximo 3 dias. Features maiores devem ser quebradas em partes menores
- **Commits frequentes:** faça commits pequenos e coesos. Facilita revisão e reversão
- **Não acumule PRs:** abra o PR assim que a feature estiver pronta, não espere acumular várias
- **Respeite o revisor:** dê contexto suficiente no PR para que o revisor entenda o que foi feito sem precisar perguntar
- **Aceite feedback:** comentários de revisão são sobre o código, não sobre você
- **Atualize sua branch:** antes de pedir revisão, certifique-se de que sua branch está atualizada com `develop`
- **Delete branches mergeadas:** mantenha o repositório limpo deletando branches após o merge
- **Não force push em branches compartilhadas:** use `--force-with-lease` apenas em branches pessoais e com cautela

---

## Referências

- [GitFlow — Vincent Driessen](https://nvie.com/posts/a-successful-git-branching-model/)
- [Conventional Commits](https://www.conventionalcommits.org/pt-br/v1.0.0/)
- [Semantic Versioning](https://semver.org/lang/pt-BR/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [SpringDoc OpenAPI](https://springdoc.org/)

---

*Dúvidas ou sugestões sobre este guia? Abra uma issue com o label `documentation`.*
