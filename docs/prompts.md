# Prompts do Projeto

> Gerado automaticamente pelo GitHub Actions.
> Arquivos individuais em `docs/prompts/` sao mantidos — nenhum foi removido.
> Ultima atualizacao: 2026-05-22

---

## Indice

- [2026-05-08 — Alterar hook de prompts para criar arquivos individuais em docs/prompts/ ao invés de atualizar prompts.md compartilhado](#alterar-hook-de-prompts-para-criar-arquivos-individuais-em-docs-prompts-ao-inv-s-de-atualizar-prompts-md-compartilhado)
- [2026-05-10 — Validar e melhorar título e descrição da issue #30 no GitHub](#validar-e-melhorar-t-tulo-e-descri-o-da-issue-30-no-github)
- [2026-05-10 — Gerar diagrama C4 completo do MockAI com 4 níveis usando Mermaid](#gerar-diagrama-c4-completo-do-mockai-com-4-n-veis-usando-mermaid)
- [2026-05-10 — Adicionar referência ao diagrama C4 no README do projeto](#adicionar-refer-ncia-ao-diagrama-c4-no-readme-do-projeto)
- [2026-05-11 — Criar CONTRIBUTING.md profissional com GitFlow, Conventional Commits e padrões corporativos](#criar-contributing-md-profissional-com-gitflow-conventional-commits-e-padr-es-corporativos)
- [2026-05-12 — Finalizar arquivo issue-title.md com padrões de nomenclatura para issues GitHub](#finalizar-arquivo-issue-title-md-com-padr-es-de-nomenclatura-para-issues-github)
- [2026-05-12 — Criar branch local feature/task39 a partir de origin/develop](#criar-branch-local-feature-task39-a-partir-de-origin-develop)
- [2026-05-12 — Criar steering file com diretrizes SOLID e Clean Architecture para geração de código Java/Spring Boot](#criar-steering-file-com-diretrizes-solid-e-clean-architecture-para-gera-o-de-c-digo-java-spring-boot)
- [2026-05-12 — Finalizar arquivo issue-labels.md com especificações de labels para GitHub](#finalizar-arquivo-issue-labels-md-com-especifica-es-de-labels-para-github)
- [2026-05-12 — Adicionar referência ao template user_story_template nos arquivos de documentação](#adicionar-refer-ncia-ao-template-user-story-template-nos-arquivos-de-documenta-o)
- [2026-05-12 — Criar epic_template.yml baseado no user_story_template com campos Visão e Escopo](#criar-epic-template-yml-baseado-no-user-story-template-com-campos-vis-o-e-escopo)
- [2026-05-12 — Criar docs_template.yml baseado no user_story_template com campos Descrição e Conteúdo mínimo](#criar-docs-template-yml-baseado-no-user-story-template-com-campos-descri-o-e-conte-do-m-nimo)
- [2026-05-12 — Ajustar referências em issue-labels e issue-title para cobrir todos os templates](#ajustar-refer-ncias-em-issue-labels-e-issue-title-para-cobrir-todos-os-templates)
- [2026-05-12 — Reescrever SKILL.md com fluxo organizado e regra de busca obrigatória ao editar issue](#reescrever-skill-md-com-fluxo-organizado-e-regra-de-busca-obrigat-ria-ao-editar-issue)
- [2026-05-12 — Adicionar nos arquivos de template da skill github-issue-management que o tipo da issue é sempre Feature](#adicionar-nos-arquivos-de-template-da-skill-github-issue-management-que-o-tipo-da-issue-sempre-feature)
- [2026-05-12 — Reorganizar SKILL.md com fluxo estruturado e obrigar identificação da operação antes de agir](#reorganizar-skill-md-com-fluxo-estruturado-e-obrigar-identifica-o-da-opera-o-antes-de-agir)
- [2026-05-12 — Atualizar tópico 2A.2 do SKILL.md para referenciar os templates docs, epic e user_story](#atualizar-t-pico-2a-2-do-skill-md-para-referenciar-os-templates-docs-epic-e-user-story)
- [2026-05-12 — Atualizar issue #18 do repositório mini-projeto-MockAi no GitHub](#atualizar-issue-18-do-reposit-rio-mini-projeto-mockai-no-github)
- [2026-05-13 — Criar PRD do projeto MockAI em docs/PRD.md com estrutura completa](#criar-prd-do-projeto-mockai-em-docs-prd-md-com-estrutura-completa)
- [2026-05-14 — Ajustar issue #58 para formato user story com template obrigatório](#ajustar-issue-58-para-formato-user-story-com-template-obrigat-rio)
- [2026-05-14 — Adicionar comando gh api PATCH type=Feature no SKILL.md](#adicionar-comando-gh-api-patch-type-feature-no-skill-md)
- [2026-05-14 — Pergunta sobre capacidade de criar TODO list para cumprimento de processos](#pergunta-sobre-capacidade-de-criar-todo-list-para-cumprimento-de-processos)
- [2026-05-14 — Atualizar título da issue #58 para formato user story via github-issue-manager](#atualizar-t-tulo-da-issue-58-para-formato-user-story-via-github-issue-manager)
- [2026-05-14 — Organizar arquivo Prompts_telas_figma.md com nota avaliativa e estrutura clara para commit](#organizar-arquivo-prompts-telas-figma-md-com-nota-avaliativa-e-estrutura-clara-para-commit)
- [2026-05-14 — Pergunta sobre o que faz o comando cat com heredoc para criar arquivo temporário](#pergunta-sobre-o-que-faz-o-comando-cat-com-heredoc-para-criar-arquivo-tempor-rio)
- [2026-05-14 — Adicionar padrão heredoc para criação de arquivo temporário na SKILL.md](#adicionar-padr-o-heredoc-para-cria-o-de-arquivo-tempor-rio-na-skill-md)
- [2026-05-14 — Criar issue EPIC com visão geral do produto usando epic_template e product.md](#criar-issue-epic-com-vis-o-geral-do-produto-usando-epic-template-e-product-md)
- [2026-05-14 — Adicionar regra de Parent Epic no corpo de Stories na SKILL.md](#adicionar-regra-de-parent-epic-no-corpo-de-stories-na-skill-md)
- [2026-05-14 — Adicionar vínculo Parent Epic #60 na issue #58](#adicionar-v-nculo-parent-epic-60-na-issue-58)
- [2026-05-14 — Atualizar issue #18 para formato user story com vínculo ao épico #60](#atualizar-issue-18-para-formato-user-story-com-v-nculo-ao-pico-60)
- [2026-05-14 — Criar tech_template.yml para issues técnicas baseado no docs_template.yml](#criar-tech-template-yml-para-issues-t-cnicas-baseado-no-docs-template-yml)
- [2026-05-14 — Criar label tech com cor laranja via gh CLI](#criar-label-tech-com-cor-laranja-via-gh-cli)
- [2026-05-14 — Adicionar label tech e título TECH nos arquivos de referência issue-labels, issue-title e SKILL](#adicionar-label-tech-e-t-tulo-tech-nos-arquivos-de-refer-ncia-issue-labels-issue-title-e-skill)
- [2026-05-14 — Criar user story unificando issues #18 e #58 com épico #60 como pai](#criar-user-story-unificando-issues-18-e-58-com-pico-60-como-pai)
- [2026-05-14 — Pergunta sobre possibilidade de atualizar campo relationship via gh CLI ou bash](#pergunta-sobre-possibilidade-de-atualizar-campo-relationship-via-gh-cli-ou-bash)
- [2026-05-14 — Vincular épico #60 como pai da issue #62 via GraphQL addSubIssue](#vincular-pico-60-como-pai-da-issue-62-via-graphql-addsubissue)
- [2026-05-14 — Extrair hierarquia da SKILL.md para issue-hierarchy.md com vínculo GraphQL e suporte a Story](#extrair-hierarquia-da-skill-md-para-issue-hierarchy-md-com-v-nculo-graphql-e-suporte-a-story)
- [2026-05-14 — Transformar issues #18 e #58 em issues técnicas com tech_template e pai Story #62](#transformar-issues-18-e-58-em-issues-t-cnicas-com-tech-template-e-pai-story-62)
- [2026-05-14 — Atualizar issue #22 para formato user story com validação de arquivo Swagger](#atualizar-issue-22-para-formato-user-story-com-valida-o-de-arquivo-swagger)
- [2026-05-14 — Atualizar issue #21 para formato tech_template com cliente OpenAI ChatGPT](#atualizar-issue-21-para-formato-tech-template-com-cliente-openai-chatgpt)
- [2026-05-14 — Atualizar descrição da issue #21 para usar .env para armazenar token](#atualizar-descri-o-da-issue-21-para-usar-env-para-armazenar-token)
- [2026-05-14 — Atualizar issue #19 para formato tech_template com endpoints dinâmicos](#atualizar-issue-19-para-formato-tech-template-com-endpoints-din-micos)
- [2026-05-14 — Atualizar issue #23 para formato tech_template com geração de resposta via IA](#atualizar-issue-23-para-formato-tech-template-com-gera-o-de-resposta-via-ia)
- [2026-05-14 — Criar user story de respostas mockadas com IA, pai de #19, #21, #23, filha do épico #60](#criar-user-story-de-respostas-mockadas-com-ia-pai-de-19-21-23-filha-do-pico-60)
- [2026-05-14 — Atualizar issue #24 para formato user story com listagem de endpoints mockados](#atualizar-issue-24-para-formato-user-story-com-listagem-de-endpoints-mockados)
- [2026-05-14 — Atualizar issue #49 para formato docs_template com agente de IA para issues](#atualizar-issue-49-para-formato-docs-template-com-agente-de-ia-para-issues)
- [2026-05-16 — Criar endpoint POST /import para validar extensão de arquivo JSON e retornar status adequado](#criar-endpoint-post-import-para-validar-extens-o-de-arquivo-json-e-retornar-status-adequado)
- [2026-05-16 — Criar o design para a spec import-endpoint](#criar-o-design-para-a-spec-import-endpoint)
- [2026-05-16 — Remover o tópico Testing Strategy do design.md da spec import-endpoint](#remover-o-t-pico-testing-strategy-do-design-md-da-spec-import-endpoint)
- [2026-05-16 — Criar tasks para a spec import-endpoint](#criar-tasks-para-a-spec-import-endpoint)
- [2026-05-16 — Excluir tasks 1, 9, 10 e 11 do tasks.md da spec import-endpoint e ajustar referências](#excluir-tasks-1-9-10-e-11-do-tasks-md-da-spec-import-endpoint-e-ajustar-refer-ncias)
- [2026-05-16 — Traduzir arquivos skills.md e specification.md para português](#traduzir-arquivos-skills-md-e-specification-md-para-portugu-s)
- [2026-05-16 — Criar skill para construir skills, usando skills.md e specification.md como referência](#criar-skill-para-construir-skills-usando-skills-md-e-specification-md-como-refer-ncia)
- [2026-05-16 — Traduzir o arquivo steering.md para português](#traduzir-o-arquivo-steering-md-para-portugu-s)
- [2026-05-16 — Renomear a skill criar-skill para create-skill (nome em inglês)](#renomear-a-skill-criar-skill-para-create-skill-nome-em-ingl-s)
- [2026-05-16 — Dúvida sobre uso de inglês ou português no campo description do frontmatter da skill](#d-vida-sobre-uso-de-ingl-s-ou-portugu-s-no-campo-description-do-frontmatter-da-skill)
- [2026-05-16 — Adicionar na skill create-skill que o nome deve ser em inglês e a descrição/conteúdo em português](#adicionar-na-skill-create-skill-que-o-nome-deve-ser-em-ingl-s-e-a-descri-o-conte-do-em-portugu-s)
- [2026-05-16 — Criar skill para criação de arquivos steering usando steering.md como referência](#criar-skill-para-cria-o-de-arquivos-steering-usando-steering-md-como-refer-ncia)
- [2026-05-16 — Tornar obrigatório deixar claro quando uma skill deve ser ativada na skill create-skill](#tornar-obrigat-rio-deixar-claro-quando-uma-skill-deve-ser-ativada-na-skill-create-skill)
- [2026-05-16 — Ajustar hook push-modifications para usar script open_pr.sh ao abrir PR](#ajustar-hook-push-modifications-para-usar-script-open-pr-sh-ao-abrir-pr)
- [2026-05-16 — Adicionar validações no open_pr.sh: bloquear alterações não commitadas e branches protegidas](#adicionar-valida-es-no-open-pr-sh-bloquear-altera-es-n-o-commitadas-e-branches-protegidas)
- [2026-05-16 — Etapa 1 — Mapeamento dos campos Swagger desserializados para entidades JPA do projeto MockAI](#etapa-1-mapeamento-dos-campos-swagger-desserializados-para-entidades-jpa-do-projeto-mockai)
- [2026-05-16 — Etapa 2 — Implementar serviço de persistência da spec Swagger no banco de dados](#etapa-2-implementar-servi-o-de-persist-ncia-da-spec-swagger-no-banco-de-dados)
- [2026-05-16 — Etapa 3 — Implementar deleção dos dados antigos com rollback transacional completo](#etapa-3-implementar-dele-o-dos-dados-antigos-com-rollback-transacional-completo)
- [2026-05-16 — Etapa 4 — Tratamento explícito de erros de persistência com mensagens descritivas](#etapa-4-tratamento-expl-cito-de-erros-de-persist-ncia-com-mensagens-descritivas)
- [2026-05-16 — erro durante o processo de compilação (continuação da etapa 4 — tratamento de erros de persistência)](#erro-durante-o-processo-de-compila-o-continua-o-da-etapa-4-tratamento-de-erros-de-persist-ncia)
- [2026-05-16 — Etapa 5 — Integrar serviço de persistência ao endpoint POST /import da task #58](#etapa-5-integrar-servi-o-de-persist-ncia-ao-endpoint-post-import-da-task-58)
- [2026-05-16 — análise dos erros (continuação da etapa 6 — validação e testes)](#an-lise-dos-erros-continua-o-da-etapa-6-valida-o-e-testes)
- [2026-05-16 — erro de dependências (continuação da etapa 6 — correção dos testes)](#erro-de-depend-ncias-continua-o-da-etapa-6-corre-o-dos-testes)
- [2026-05-16 — Não fazer testes unitários — remover os arquivos de teste criados](#n-o-fazer-testes-unit-rios-remover-os-arquivos-de-teste-criados)
- [2026-05-16 — BUILD FAILURE ao executar — ObjectMapper não disponível como bean Spring](#build-failure-ao-executar-objectmapper-n-o-dispon-vel-como-bean-spring)
- [2026-05-16 — Diagnosticar e corrigir BUILD FAILURE do exec-maven-plugin no projeto MockAI](#diagnosticar-e-corrigir-build-failure-do-exec-maven-plugin-no-projeto-mockai)
- [2026-05-16 — Corrigir e validar o arquivo OpenAPI JSON em `docs/petstore.json`](#corrigir-e-validar-o-arquivo-openapi-json-em-docs-petstore-json)
- [2026-05-16 — Diagnosticar e resolver violação de integridade referencial ao deletar dados no banco H2](#diagnosticar-e-resolver-viola-o-de-integridade-referencial-ao-deletar-dados-no-banco-h2)
- [2026-05-16 — Parar o servidor atual e verificar o funcionamento da API no H2](#parar-o-servidor-atual-e-verificar-o-funcionamento-da-api-no-h2)
- [2026-05-16 — Criar spec para implementação de client de integração com ChatGPT usando spring-ai-starter-model-openai](#criar-spec-para-implementa-o-de-client-de-integra-o-com-chatgpt-usando-spring-ai-starter-model-openai)
- [2026-05-17 — Adicionar ao design mecanismo de falha na startup se OPENAI_API_KEY não configurada](#adicionar-ao-design-mecanismo-de-falha-na-startup-se-openai-api-key-n-o-configurada)
- [2026-05-17 — Pergunta sobre como fazer o git ignorar .env mas não o .env.example](#pergunta-sobre-como-fazer-o-git-ignorar-env-mas-n-o-o-env-example)
- [2026-05-17 — Analisar PR para conflitos e criar novo PR baseado na versão de desenvolvimento](#analisar-pr-para-conflitos-e-criar-novo-pr-baseado-na-vers-o-de-desenvolvimento)
- [2026-05-17 — Ajustar todo o código para usar Groq em vez de OpenAI com Spring AI](#ajustar-todo-o-c-digo-para-usar-groq-em-vez-de-openai-com-spring-ai)
- [2026-05-17 — Revisar documentação, steerings e skills substituindo ChatGPT/OpenAI por Groq](#revisar-documenta-o-steerings-e-skills-substituindo-chatgpt-openai-por-groq)
- [2026-05-17 — Implementar issue #24 - Listar endpoints mockados disponíveis no projeto MockAI](#implementar-issue-24-listar-endpoints-mockados-dispon-veis-no-projeto-mockai)
- [2026-05-17 — Corrigir MultipleBagFetchException ao importar swagger company-manager.json](#corrigir-multiplebagfetchexception-ao-importar-swagger-company-manager-json)
- [2026-05-17 — Criar testes automatizados para a issue #24 - Listar endpoints mockados disponíveis](#criar-testes-automatizados-para-a-issue-24-listar-endpoints-mockados-dispon-veis)
- [2026-05-17 — Implementar retorno de respostas por IA no endpoint dinâmico usando AiGateway](#implementar-retorno-de-respostas-por-ia-no-endpoint-din-mico-usando-aigateway)
- [2026-05-17 — Ajustar requisitos: schema nulo retorna só status; erro de IA usa fallback estático](#ajustar-requisitos-schema-nulo-retorna-s-status-erro-de-ia-usa-fallback-est-tico)
- [2026-05-17 — Corrigir DynamicResponseBodyBuilder para retornar campos de properties com valores corretos](#corrigir-dynamicresponsebodybuilder-para-retornar-campos-de-properties-com-valores-corretos)
- [2026-05-18 — Ajuste na persistência de ENDPOINT_RESPONSE para salvar apenas o primeiro status de sucesso](#ajuste-na-persist-ncia-de-endpoint-response-para-salvar-apenas-o-primeiro-status-de-sucesso)
- [2026-05-18 — Ajuste para retornar JSON puro nos endpoints dinâmicos, removendo delimitadores de código da IA](#ajuste-para-retornar-json-puro-nos-endpoints-din-micos-removendo-delimitadores-de-c-digo-da-ia)
- [2026-05-18 — Ajuste na persistência de PATH_PARAMETER para salvar format e validar endpoint dinâmico](#ajuste-na-persist-ncia-de-path-parameter-para-salvar-format-e-validar-endpoint-din-mico)
- [2026-05-18 — Gerar curl de exemplo para cada endpoint do swagger petstore.json](#gerar-curl-de-exemplo-para-cada-endpoint-do-swagger-petstore-json)
- [2026-05-18 — Atualizar documentações do projeto com base nos prompts de hoje](#atualizar-documenta-es-do-projeto-com-base-nos-prompts-de-hoje)
- [2026-05-18 — Implementar validação do arquivo swagger no endpoint /import com campos obrigatórios](#implementar-valida-o-do-arquivo-swagger-no-endpoint-import-com-campos-obrigat-rios)
- [2026-05-18 — Criar arquivos swagger de exemplos inválidos para as validações do endpoint /import](#criar-arquivos-swagger-de-exemplos-inv-lidos-para-as-valida-es-do-endpoint-import)
- [2026-05-18 — Adicionar anotações OpenAPI nos endpoints estáticos para geração do Swagger](#adicionar-anota-es-openapi-nos-endpoints-est-ticos-para-gera-o-do-swagger)
- [2026-05-18 — Corrigir upload de arquivo no Swagger UI para o endpoint de import](#corrigir-upload-de-arquivo-no-swagger-ui-para-o-endpoint-de-import)
- [2026-05-21 — Atualizar docs, steerings e README/CONTRIBUTING com estado atual do sistema](#atualizar-docs-steerings-e-readme-contributing-com-estado-atual-do-sistema)
- [2026-05-21 — Ajustar README.md para seguir o template README_TEMPLATE.md](#ajustar-readme-md-para-seguir-o-template-readme-template-md)
- [2026-05-21 — Mover PRD.md de docs/ para a raiz do projeto e atualizar referências](#mover-prd-md-de-docs-para-a-raiz-do-projeto-e-atualizar-refer-ncias)
- [2026-05-21 — Reescrever CONTRIBUTING.md seguindo o template e o fluxo real com hooks e open_pr.sh](#reescrever-contributing-md-seguindo-o-template-e-o-fluxo-real-com-hooks-e-open-pr-sh)
- [2026-05-21 — Atualizar PRD.md com contexto completo: steerings, README, issues concluídas e src/](#atualizar-prd-md-com-contexto-completo-steerings-readme-issues-conclu-das-e-src)
- [2026-05-21 — Ajustar PRD: remover F2, CS08, L08, próximos passos e rodapé; adicionar novas funcionalidades futuras](#ajustar-prd-remover-f2-cs08-l08-pr-ximos-passos-e-rodap-adicionar-novas-funcionalidades-futuras)
- [2026-05-21 — Atualizar README com referências a PPT, telas Figma, coleção Postman e exemplos Swagger](#atualizar-readme-com-refer-ncias-a-ppt-telas-figma-cole-o-postman-e-exemplos-swagger)
- [2026-05-21 — Configurar plugin JaCoCo no pom.xml para cobertura de testes com mínimo de 40%](#configurar-plugin-jacoco-no-pom-xml-para-cobertura-de-testes-com-m-nimo-de-40)
- [2026-05-21 — Criar testes unitários para ValidateSwaggerContentService cobrindo todos os campos obrigatórios](#criar-testes-unit-rios-para-validateswaggercontentservice-cobrindo-todos-os-campos-obrigat-rios)
- [2026-05-21 — Criar testes unitários para ImportSwaggerService validando orquestração do fluxo de importação](#criar-testes-unit-rios-para-importswaggerservice-validando-orquestra-o-do-fluxo-de-importa-o)
- [2026-05-21 — Criar testes unitários para ValidateFileService validando extensão de arquivo .json](#criar-testes-unit-rios-para-validatefileservice-validando-extens-o-de-arquivo-json)
- [2026-05-21 — Criar testes unitários para GenerateEndpointResponseService cobrindo geração de respostas via IA](#criar-testes-unit-rios-para-generateendpointresponseservice-cobrindo-gera-o-de-respostas-via-ia)
- [2026-05-21 — Criar testes unitários para CheckAiConnectionService validando verificação de conectividade com IA](#criar-testes-unit-rios-para-checkaiconnectionservice-validando-verifica-o-de-conectividade-com-ia)
- [2026-05-21 — Criar testes unitários para PersistSwaggerSpecService e GetEndpointsBySpecificationIdService](#criar-testes-unit-rios-para-persistswaggerspecservice-e-getendpointsbyspecificationidservice)
- [2026-05-21 — Criar testes unitários para DynamicRouteRegistrationService validando registro e remoção de rotas](#criar-testes-unit-rios-para-dynamicrouteregistrationservice-validando-registro-e-remo-o-de-rotas)
- [2026-05-21 — Criar testes unitários para GlobalExceptionHandler validando mapeamento de exceções para HTTP](#criar-testes-unit-rios-para-globalexceptionhandler-validando-mapeamento-de-exce-es-para-http)
- [2026-05-21 — Criar testes unitários para AiConnectionController validando respostas de status de conexão](#criar-testes-unit-rios-para-aiconnectioncontroller-validando-respostas-de-status-de-conex-o)
- [2026-05-21 — Criar testes unitários para ImportController validando fluxo de importação via multipart](#criar-testes-unit-rios-para-importcontroller-validando-fluxo-de-importa-o-via-multipart)
- [2026-05-21 — Criar testes unitários para EndpointDefinitionQueryAdapter validando consulta ao repositório](#criar-testes-unit-rios-para-endpointdefinitionqueryadapter-validando-consulta-ao-reposit-rio)
- [2026-05-21 — Criar testes unitários para SwaggerSpecDeletionAdapter validando ordem de deleção e tratamento de erros](#criar-testes-unit-rios-para-swaggerspecdeletionadapter-validando-ordem-de-dele-o-e-tratamento-de-erros)
- [2026-05-21 — Criar testes unitários para AiGateway validando validações de entrada e tratamento de erros](#criar-testes-unit-rios-para-aigateway-validando-valida-es-de-entrada-e-tratamento-de-erros)
- [2026-05-21 — Criar testes unitários para DynamicResponseBodyBuilder validando construção de payloads de resposta](#criar-testes-unit-rios-para-dynamicresponsebodybuilder-validando-constru-o-de-payloads-de-resposta)
- [2026-05-21 — Criar testes unitários para HttpMethodMapper validando mapeamento de métodos HTTP](#criar-testes-unit-rios-para-httpmethodmapper-validando-mapeamento-de-m-todos-http)
- [2026-05-21 — Executar todos os testes unitários e validar que compilam e passam sem erros](#executar-todos-os-testes-unit-rios-e-validar-que-compilam-e-passam-sem-erros)
- [2026-05-21 — Validar cobertura de código com JaCoCo e confirmar que atinge mínimo de 40%](#validar-cobertura-de-c-digo-com-jacoco-e-confirmar-que-atinge-m-nimo-de-40)
- [2026-05-21 — Gerar relatório completo de testes unitários documentando toda a implementação](#gerar-relat-rio-completo-de-testes-unit-rios-documentando-toda-a-implementa-o)
- [2026-05-21 — Criar testes unitários para os principais fluxos do projeto MockAI garantindo cobertura mínima de 40%](#criar-testes-unit-rios-para-os-principais-fluxos-do-projeto-mockai-garantindo-cobertura-m-nima-de-40)
- [2026-05-21 — Adicionar referência ao arquivo de consolidação de prompts na seção de documentação adicional do README](#adicionar-refer-ncia-ao-arquivo-de-consolida-o-de-prompts-na-se-o-de-documenta-o-adicional-do-readme)
- [2026-05-22 — Criar diagramas UML (sequência e atividades) dos fluxos de importação Swagger e resposta por IA](#criar-diagramas-uml-sequ-ncia-e-atividades-dos-fluxos-de-importa-o-swagger-e-resposta-por-ia)
- [2026-05-22 — Simplificar diagramas de sequência UML para mostrar apenas Usuário, Backend, H2 e Groq](#simplificar-diagramas-de-sequ-ncia-uml-para-mostrar-apenas-usu-rio-backend-h2-e-groq)

---

## Prompts (ordem cronologica)

### Alterar hook de prompts para criar arquivos individuais em docs/prompts/ ao invés de atualizar prompts.md compartilhado

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-08_19-53-17_dariel-verdecia-verdecia.md` |
| Data | 2026-05-08 |

Prompt: Alterar hook de prompts para criar arquivos individuais em docs/prompts/ ao invés de atualizar prompts.md compartilhado
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-08 19:53:17

## Prompt original

Implementar alteração no hook responsável pela atualização do arquivo prompts.md para que, ao invés de consolidar múltiplos prompts em um único arquivo compartilhado, seja criado um arquivo individual para cada prompt solicitado à IA dentro do diretório docs/prompts.ObjetivoMelhorar rastreabilidade, auditoria, organização e histórico dos prompts utilizados no projeto, além de eliminar conflitos de merge causados por múltiplas alterações simultâneas no mesmo arquivo prompts.md.Estrutura esperadaO sistema deve criar automaticamente o diretório abaixo para armazenar todos os prompts registrados:docs/prompts Todos os arquivos gerados devem ser armazenados dentro desse diretório.Exemplo:docs/prompts/2026-05-08_19-35-21_alysson-girotto.md Requisitos da implementaçãoCada prompt enviado para a IA deve gerar automaticamente um novo arquivo.O sistema não deve mais atualizar um único arquivo compartilhado.Cada arquivo deve ser criado individualmente contendo os metadados completos do prompt.Todos os arquivos devem ser armazenados dentro do diretório docs/prompts.O diretório deve ser criado automaticamente caso não exista.O nome do arquivo deve garantir unicidade mesmo em execuções simultâneas.Estrutura do nome do arquivoO nome do arquivo deve conter:DataHoraIdentificador do usuário responsávelExemplos válidos:2026-05-08_19-35-21_alysson-girotto.md ouprompt_20260508_193521_alysson-girotto.md Regras para geração do nomeO identificador do usuário deve ser sanitizado para evitar caracteres inválidos.Preferencialmente utilizar timestamp com precisão de segundos ou milissegundos.O hook deve evitar colisões de nomes em ambientes concorrentes.O formato do timestamp deve seguir o padrão definido pelo projeto.Caso necessário, adicionar UUID complementar para garantir unicidade absoluta.Estrutura obrigatória do conteúdo do arquivoCada arquivo deve conter exatamente a seguinte estrutura:Prompt: Resumo do prompt  Responsável: Alysson Girotto Usuário: alysson.girotto Data/hora: 2026-05-08 19:35:21  Prompt original [Conteúdo total do prompt] Regras do conteúdoO campo Prompt: deve conter um resumo curto e objetivo do prompt solicitado.O campo Responsável: deve armazenar o nome do responsável pela solicitação.O campo Usuário: deve armazenar o identificador único do usuário.O usuário deve ser registrado tanto no nome do arquivo quanto no conteúdo interno.O campo Data/hora: deve utilizar timezone padronizado do projeto.O bloco Prompt original deve armazenar integralmente o conteúdo enviado para a IA sem truncamentos.Não adicionar informações de branch no arquivo, pois o versionamento de branches já é tratado automaticamente por outro hook do projeto.Requisitos técnicos adicionaisGarantir compatibilidade com múltiplos usuários executando simultaneamente.Garantir que falhas na escrita de um arquivo não afetem os demais registros.Implementar criação automática do diretório docs/prompts caso ele não exista.Manter encoding UTF-8.Validar caracteres especiais no conteúdo e no nome do arquivo.Garantir compatibilidade com Windows, Linux e macOS.Adicionar logs apropriados para troubleshooting.Garantir que o hook continue transparente para os usuários do fluxo atual.Resultado esperadoApós a implementação:Cada interação com a IA deverá gerar um arquivo independente.Todos os prompts deverão ficar centralizados no diretório docs/prompts.Não deverá mais existir conflito de merge relacionado ao arquivo prompts.md.O histórico de prompts ficará totalmente rastreável por usuário, data e hora.O sistema ficará preparado para auditoria e análise futura de prompts utilizados no projeto.

---

### Validar e melhorar título e descrição da issue #30 no GitHub

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-10_16-39-13_welton.md` |
| Data | 2026-05-10 |

Prompt: Validar e melhorar título e descrição da issue #30 no GitHub
Responsável: Welton
Usuário: welton
Data/hora: 2026-05-10 16:39:13

## Prompt original

Preciso que valide a issue 30 criada no github para ajustar a descrição e titulo dela, ela está muito simples.

---

### Gerar diagrama C4 completo do MockAI com 4 níveis usando Mermaid

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-10_16-42-08_welton.md` |
| Data | 2026-05-10 |

Prompt: Gerar diagrama C4 completo do MockAI com 4 níveis usando Mermaid
Responsável: Welton
Usuário: welton
Data/hora: 2026-05-10 16:42:08

## Prompt original

Você é um especialista em arquitetura de software e diagramas C4. Preciso que gere um diagrama C4 completo com os 4 níveis para o sistema MockAI, usando sintaxe Mermaid em formato Markdown (blocos mermaid), para que o GitHub renderize automaticamente.Contexto do sistemaMockAI é uma aplicação Spring Boot que transforma documentações Swagger/OpenAPI em APIs mock locais. O desenvolvedor envia uma spec Swagger, o sistema processa, persiste os endpoints e os disponibiliza para consumo imediato. Os payloads de resposta são gerados dinamicamente por um serviço de IA externo.Regra de negócio importante: a cada nova spec enviada, todos os endpoints anteriores são deletados e recriados.Stack técnicaJava 17 + Spring Boot 4.0.6Spring Data JPA + H2 in-memory (jdbc:h2:mem:testdb)SpringDoc OpenAPI 3.0.2Porta: 8080, context-path: /mockaiArquitetura: Clean Architecture / Hexagonal com 4 camadasAtores externosDesenvolvedor: envia POST /mockai/mocks com JSON Swagger/OpenAPIConsumidor do Mock: consome GET /mockai/mock/{id}/{path} via HTTPServiço de IA Externo: API externa que gera payloads JSON dinamicamenteCamadas internas (Clean Architecture)Camada	Tecnologia	ResponsabilidadeAPI	Spring Web MVC + Bean Validation	Controllers REST, DTOs, tratamento de exceçõesApplication	Java puro	Use cases: processar spec, criar mock, servir endpointDomain	Java puro	Modelos (ApiSpecification, Endpoint, PathParameter, EndpointResponse) e Ports (interfaces)Infrastructure	Spring Data JPA + HTTP Client	Repositórios JPA, Gateway de IA, MappersModelo de dados (6 entidades JPA)api_specification (id UUID, title, version, description, base_url)endpoint_definition (id UUID, path, http_method, summary, description, api_specification_id FK)tag (id UUID, name, description)endpoint_tags (endpoint_id FK, tag_id FK) — tabela de junção N:Npath_parameter (id UUID, name, type, required, endpoint_definition_id FK)endpoint_response (id UUID, status_code, content_type, description, response_schema, endpoint_definition_id FK)Relacionamentos:api_specification 1→N endpoint_definitionendpoint_definition N↔N tag via endpoint_tagsendpoint_definition 1→N path_parameterendpoint_definition 1→N endpoint_responseO que precisoGere o arquivo Markdown com os 4 níveis C4, cada um em um bloco Mermaid separado:Nível 1 — Contexto: sistema + atores externos + serviço de IA externoNível 2 — Containers: MockAI Application (Spring Boot) + H2 Database, com tecnologias e protocolosNível 3 — Componentes: as 4 camadas da Clean Architecture com seus componentes internosNível 4 — Código: diagrama ER com as 6 entidades, campos, tipos e relacionamentosUse cores diferentes para distinguir: pessoas (azul), sistema principal (verde), banco de dados (laranja), serviço externo (cinza), camadas internas (roxo para domain, azul claro para API, verde para application, laranja para infrastructure).

---

### Adicionar referência ao diagrama C4 no README do projeto

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-10_16-54-37_welton.md` |
| Data | 2026-05-10 |

Prompt: Adicionar referência ao diagrama C4 no README do projeto
Responsável: Welton
Usuário: welton
Data/hora: 2026-05-10 16:54:37

## Prompt original

preciso adicionar o diagrama no readme do projeto

---

### Criar CONTRIBUTING.md profissional com GitFlow, Conventional Commits e padrões corporativos

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-11_20-25-24_dariel-verdecia-verdecia.md` |
| Data | 2026-05-11 |

Prompt: Criar CONTRIBUTING.md profissional com GitFlow, Conventional Commits e padrões corporativos
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-11 20:25:24

## Prompt original

Crie um arquivo CONTRIBUTING.md profissional e completo para este projeto, seguindo o modelo GitFlow.O documento deve estar em português e preparado para uso em ambiente corporativo.O projeto utiliza a seguinte estratégia de branches:- main- develop- feature/*- release/*- hotfix/*Explique claramente:- Objetivo do fluxo GitFlow- Responsabilidade de cada branch- Quando utilizar cada tipo de branch- Fluxo correto de desenvolvimento- Processo de criação de features- Processo de release- Processo de hotfix- Estratégia de merge- Regras de versionamento- Fluxo de Pull Request- Revisão de código- Resolução de conflitosInclua exemplos práticos de:- criação de branches- comandos git- nomes padronizados de branches- commits seguindo Conventional Commits- fluxo completo desde develop até mainAdicionar também:- Pré-requisitos do projeto- Configuração do ambiente local- Execução de testes- Padrões de código- Regras de documentação- Checklist antes de abrir PR- Boas práticas de colaboraçãoUtilize Markdown profissional com:- títulos organizados- tabelas quando necessário- listas- exemplos em blocos de códigoO conteúdo deve ser técnico, objetivo, claro e pronto para uso real por equipes de desenvolvimento.

---

### Finalizar arquivo issue-title.md com padrões de nomenclatura para issues GitHub

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_19-12-49_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Finalizar arquivo issue-title.md com padrões de nomenclatura para issues GitHub
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 19:12:35

## Prompt original

Finalize a escrita desse arquivo issue-title. Ele deve servir como referência para a skill git-hub-issue-management.Neste arquivo, deve conter as seguintes especificações:Os títulos devem seguir o padrão:- Iniciar com [EPIC] para issues marcadas com label epic, referentes a uma issue que agrupa os demais tipos de issues, issue pai- Iniciar com [STORY] para issues marcadas com a label story, referentes a criação de uma nova funcionalidade, servindo como user story- Iniciar com [DOCS] para issues marcadas com a label docs, referentes a issue de criação/atualização de documentos de detalhamento do projeto como README, CONTRIBUTING, PRD e arquivos que auxiliam na utilização de IA steerings, specs, skills

---

### Criar branch local feature/task39 a partir de origin/develop

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_19-41-31_daniel-rodrigues-da-silva.md` |
| Data | 2026-05-12 |

Prompt: Criar branch local feature/task39 a partir de origin/develop
Responsável: Daniel Rodrigues da Silva
Usuário: daniel-rodrigues-da-silva
Data/hora: 2026-05-12 19:41:31

## Prompt original

crie uma nova branch local, com nome feature/task39, a partir da branch origin/develop

---

### Criar steering file com diretrizes SOLID e Clean Architecture para geração de código Java/Spring Boot

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_19-44-31_daniel-rodrigues-da-silva.md` |
| Data | 2026-05-12 |

Prompt: Criar steering file com diretrizes SOLID e Clean Architecture para geração de código Java/Spring Boot
Responsável: Daniel Rodrigues da Silva
Usuário: daniel-rodrigues-da-silva
Data/hora: 2026-05-12 19:44:31

## Prompt original

Crie um arquivo steering na pasta .kiro/steering responsável por garantir a aplicação dos princípios SOLID e Clean Architecture durante a geração de código pelo Kiro.Orientações técnicasO arquivo deve ser criado em .kiro/steering/Deve conter diretrizes claras sobre cada princípio SOLID (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion)Deve incluir orientações sobre Clean Architecture (separação de camadas, dependências apontando para dentro, regras de negócio isoladas)Deve fornecer exemplos práticos aplicáveis ao contexto Java/Spring BootDeve ser escrito em portuguêsDeve incluir front-matter adequado para configuração do steering (inclusion: always ou fileMatch conforme necessário)Critérios de AceiteArquivo steering criado em .kiro/steering/ com nome apropriadoDocumento contém explicação clara de todos os 5 princípios SOLIDDocumento contém diretrizes de Clean ArchitectureExemplos práticos incluídos para cada princípioFront-matter configurado corretamenteDocumento escrito em português

---

### Finalizar arquivo issue-labels.md com especificações de labels para GitHub

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_19-56-15_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Finalizar arquivo issue-labels.md com especificações de labels para GitHub
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 19:56:14

## Prompt original

Finalize a escrita desse arquivo issue-title. Ele deve servir como referência para a skill git-hub-issue-management.Neste arquivo, deve conter as seguintes especificações:* epic: "Agrupador de alto nível" * story: "Unidade principal de entrega" * docs: "Documentação" * backend: "Backend" * frontend: "Frontend" * ai: "Funcionalidades de IA" * "priority:high": "Alta prioridade" * "priority:medium": "Média prioridade" * "priority:low": "Baixa prioridade"

---

### Adicionar referência ao template user_story_template nos arquivos de documentação

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_20-17-09_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Adicionar referência ao template user_story_template nos arquivos de documentação
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 20:17:08

## Prompt original

Adicione que o arquivo issue-labels é referente a parte labels: ["story"] do template user_story_template e o arquivo issue-title é referente a parte title: "[STORY] " do mesmo template

---

### Criar epic_template.yml baseado no user_story_template com campos Visão e Escopo

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_20-34-07_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Criar epic_template.yml baseado no user_story_template com campos Visão e Escopo
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 20:34:08

## Prompt original

Crie um arquivo epic_template.yml. Use o user_story_template.yml como exemplo.O novo arquivo deve definir o template para issues do tipo epic, essas issues devem apresentar o seguinte padrão:titleEPIClabels: epicbody: * Visão: Define brevemente a funcionalidade ao todo que deve ser entregue* escopo do epic: as funcionalidades principais que contêmComo no exemplo:[EPIC] Plataforma Shop4u — MVP mobileVisão: Entregar a plataforma móvel Shop4u com busca, carrinho, checkout autenticado, recomendações com IA e notificações de pedido, permitindo um MVP funcional.Escopo do Epic:- Implementar funcionalidades essenciais para o fluxo de compra mobile.- Priorizar autenticação, pagamento seguro e recomendações iniciais por IA.Labels: epic, priority:highProibido: Criar ou alterar qualquer arquivo que não seja o epic_template.yml.

---

### Criar docs_template.yml baseado no user_story_template com campos Descrição e Conteúdo mínimo

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_20-45-00_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Criar docs_template.yml baseado no user_story_template com campos Descrição e Conteúdo mínimo
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 20:45:00

## Prompt original

Crie um arquivo docs_template.yml. Use o user_story_template.yml como exemplo.O novo arquivo deve definir o template para issues do tipo docs, essas issues devem apresentar o seguinte padrão:title: DOCSlabels: docsbody: * Descrição: Define brevemente as modificações que devem ser feitas* Conteúdo mínimo: checklist com as alterações que devem ser feitasComo no exemplo:[DOCS] Documentação inicial: arquitetura, convenções e uso de IADescrição: Documentar visão geral do projeto, arquitetura do MVP, contratos de API principais, convenções de desenvolvimento (branching, PR, linting) e como a equipe usará IA para acelerar backlog, docs e PRs.## Conteúdo mínimo[] Visão do sistema e escopo do MVP[] Endpoints principais (busca, carrinho, checkout, recomendações, notificações)[] Convenções de desenvolvimento e checklist de PR[] Guia rápido para rodar localmente e simular pagamentos/notifications[] Plano de coleta mínima de dados para recomendações por IA e considerações de privacidadeLabels: docs, priority:mediumProibido: Criar ou alterar qualquer arquivo que não seja o docs_template.yml.

---

### Ajustar referências em issue-labels e issue-title para cobrir todos os templates

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_20-55-00_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Ajustar referências em issue-labels e issue-title para cobrir todos os templates
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 20:55:00

## Prompt original

Ajuste as referências nos arquivos issue-lables e issue-templates para que referenciem ao outros arquivos de templates

---

### Reescrever SKILL.md com fluxo organizado e regra de busca obrigatória ao editar issue

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_21-05-00_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Reescrever SKILL.md com fluxo organizado e regra de busca obrigatória ao editar issue
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 21:05:00

## Prompt original

Defina o fluxo de gerenciamento de issues no github no arquivo SKILL.md. Ajuste o texto para ficar organizado. Defina a ordem de cada operação. Adicione que, antes de qualquer coisa, caso seja informado para editar/alterar/modificar uma issue existente é obrigatório perguntar ao usuáiro qual o link ou número da issue e, neste caso, ao receber o link ou número da issue é obrigatório buscar as informações da issue no repositório para utilizar como contexto na alteração da issue.

---

### Adicionar nos arquivos de template da skill github-issue-management que o tipo da issue é sempre Feature

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_21-46-45_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Adicionar nos arquivos de template da skill github-issue-management que o tipo da issue é sempre Feature
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 21:46:45

## Prompt original

Adicione nos arquivos de template em github-issue-management que o tipo da issue é sempre Feature

---

### Reorganizar SKILL.md com fluxo estruturado e obrigar identificação da operação antes de agir

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_21-52-50_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Reorganizar SKILL.md com fluxo estruturado e obrigar identificação da operação antes de agir
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 21:52:50

## Prompt original

Defina o fluxo de gerenciamento de issues no github no arquivo SKILL.md. Ajuste o texto para ficar organizado. Defina a ordem de cada operação. Adicione que é obrigatório como primeira operação, solicitar ao usuário se se trata de uma nova issue um de uma alteração de uma issue existente.Caso seja de issue existente, obrigatório perguntar ao usuáiro qual o link ou número da issue e, neste caso, ao receber o link ou número da issue é obrigatório buscar as informações da issue no repositório para utilizar como contexto na alteração da issue.

---

### Atualizar tópico 2A.2 do SKILL.md para referenciar os templates docs, epic e user_story

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_21-58-02_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Atualizar tópico 2A.2 do SKILL.md para referenciar os templates docs, epic e user_story
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 21:58:02

## Prompt original

No tópico **2A.2 — Montar o corpo da issue** explique que deve ser utilizado o template correspondente docs_template, epic_template, user_story_template

---

### Atualizar issue #18 do repositório mini-projeto-MockAi no GitHub

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-12_22-05-33_joaopuel.md` |
| Data | 2026-05-12 |

Prompt: Atualizar issue #18 do repositório mini-projeto-MockAi no GitHub
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-12 22:05:33

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager Atualize a task https://github.com/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi/issues/18

---

### Criar PRD do projeto MockAI em docs/PRD.md com estrutura completa

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-13_19-38-39_luizfernando.md` |
| Data | 2026-05-13 |

Prompt: Criar PRD do projeto MockAI em docs/PRD.md com estrutura completa
Responsável: luizfernando
Usuário: luizfernando
Data/hora: 2026-05-13 19:38:39

## Prompt original

Crie um Product Requirements Document (PRD) para o projeto mockai. Considere: .kiro/steering/product.mdREADME.mdbacklog e Issues do projetofuncionalidades implementadas em src/O PRD deve ser salvo em: docs/PRD.md Estrutura mínima: Visão geral do produtoProblema resolvidoObjetivosPúblico-alvoFuncionalidades principaisRegras de negócioRequisitos funcionaisRequisitos não funcionaisFluxo principal do usuárioArquitetura de alto nívelStack tecnológicaCritérios de sucessoLimitações atuaisPróximos passosImportante: Não invente funcionalidades inexistentesNão crie branchNão faça commitNão faça pushApenas gere ou atualize docs/PRD.md

---

### Ajustar issue #58 para formato user story com template obrigatório

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-00-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Ajustar issue #58 para formato user story com template obrigatório
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:00:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoAjuste a issue https://github.com/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi/issues/58 para ficar de acordo com uma user story.#Contexto da issueEssa issue deve ser responsável por apenas criar um endpoint POST /import que deve ser capaz de receber um array de binário e deserializar em um objeto json com as informações do swagger. O endpoint deve retornar uma mensagem de "Arquivo importado com sucesso"Critérios de aceite:- O endpoint deve estar funcionando e ser possível fazer requisição- O retorno deve ser "Arquivo importado com sucesso"- O array de biário deve ser deserializada em um objeto javaSugestão técnica utilizar bilbioteca jackson para deserialização.#RestriçãoProibido: Criar qualquer classe ou código no projeto. Apenas deve ser criada uma issue User Story com a descrição fornecida.

Obrigatório utilizar o user_sotry_template

---

### Adicionar comando gh api PATCH type=Feature no SKILL.md

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-05-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Adicionar comando gh api PATCH type=Feature no SKILL.md
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:05:00

## Prompt original

In the SKILL.md file add this command as a pattern to add de issue typegh api repos/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi/issues/58 --method PATCH -f type="Feature" 2>&1 | head -5

---

### Pergunta sobre capacidade de criar TODO list para cumprimento de processos

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-10-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Pergunta sobre capacidade de criar TODO list para cumprimento de processos
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:10:00

## Prompt original

Você consegue criar um TODO list para que você mesmo siga e garanta o cumprimento de processos?Somente responda se souber da resposta. Não invente reposta que não possua. Apenas responda a pergunta, não realize nehum comando ou alteração no projeto.

---

### Atualizar título da issue #58 para formato user story via github-issue-manager

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-15-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar título da issue #58 para formato user story via github-issue-manager
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:15:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager Atualize o título da task https://github.com/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi/issues/58 para ficar de acordo com um user story

---

### Organizar arquivo Prompts_telas_figma.md com nota avaliativa e estrutura clara para commit

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-17-10_welton.md` |
| Data | 2026-05-14 |

Prompt: Organizar arquivo Prompts_telas_figma.md com nota avaliativa e estrutura clara para commit
Responsável: Welton
Usuário: welton
Data/hora: 2026-05-14 12:17:10

## Prompt original

eu criei um arquivo chamado Prompts_telas_figma.md, nesse arquivo tenho um primeiro prompt que servira de base para criação de telas no figma e logo abiaxo tem 5 prompts de telas já criadas. Preciso deixar esse arquivo bem organizado para commitar. Preciso colocar uma nota no inicio dizendo que essas telas não serão de fato implementadas pois nossa aplicação não usará telas, os prompts e prints foram criados apenas para finalidade de avaliação do professor Wanderson.

---

### Pergunta sobre o que faz o comando cat com heredoc para criar arquivo temporário

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-20-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Pergunta sobre o que faz o comando cat com heredoc para criar arquivo temporário
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:20:00

## Prompt original

O que faz esse comando?cat > /tmp/issue_body.md << 'ENDOFFILE'## User StoryComo desenvolvedor integrador,quero importar um arquivo de especificação via endpoint POST /import,para que o sistema deserialize o array de binário em um objeto JSON com as informações do Swagger.## Critérios de aceitação — BDDCenário 1: Importação bem-sucedida de arquivoDado que o endpoint POST /import está disponívelQuando envio uma requisição com um array de binário contendo uma especificação Swagger válidaEntão o sistema deve deserializar o array de binário em um objeto JavaE retornar a mensagem "Arquivo importado com sucesso"Cenário 2: Endpoint acessívelDado que a aplicação está em execuçãoQuando faço uma requisição POST para /importEntão o endpoint deve estar acessível e responder corretamente## Checklist técnico- [ ] Criar endpoint POST /import- [ ] Implementar recebimento de array de binário no request body- [ ] Deserializar array de binário em objeto Java utilizando Jackson- [ ] Retornar mensagem "Arquivo importado com sucesso"- [ ] Validar que o endpoint responde corretamente a requisiçõesENDOFFILE

---

### Adicionar padrão heredoc para criação de arquivo temporário na SKILL.md

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-25-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Adicionar padrão heredoc para criação de arquivo temporário na SKILL.md
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:25:00

## Prompt original

Adicione na SKILL.md para utilizar esse comando como padrão para criação do arquivo temporário

---

### Criar issue EPIC com visão geral do produto usando epic_template e product.md

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-30-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Criar issue EPIC com visão geral do produto usando epic_template e product.md
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:30:00

## Prompt original

Crie uma issue EPIC ela deve usar o template epic_template.md e apresentar a visão geral de todo o protudo que deve ser entregue. Faça descrições sucintas e diretas. Utilize o steering product.md como contexto;

Na SKILL.md está especificado como a forma correta de criar o arquivo temporário

---

### Adicionar regra de Parent Epic no corpo de Stories na SKILL.md

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-35-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Adicionar regra de Parent Epic no corpo de Stories na SKILL.md
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:35:00

## Prompt original

Adicione no arquivo de SKILL.md que caso seja informado deve ser inserido no corpo da issue:- No corpo de cada Story, inclua:Parent Epic: #ID_DA_EPIC

---

### Adicionar vínculo Parent Epic #60 na issue #58

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-40-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Adicionar vínculo Parent Epic #60 na issue #58
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:40:00

## Prompt original

Adicione na task https://github.com/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi/issues/58 o vinculo a task pai https://github.com/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi/issues/60

---

### Atualizar issue #18 para formato user story com vínculo ao épico #60

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-45-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar issue #18 para formato user story com vínculo ao épico #60
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:45:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoAtualize a task https://github.com/orgs/IA-para-DEVs-SCTEC-T2/projects/6/views/1?pane=issue&itemId=182549452&issue=IA-para-DEVs-SCTEC-T2%7Cmini-projeto-MockAi%7C18 para que fique com o padrão de user_story_template.md.Use as especificações definidas na skill github-issue-management.#Descrição da issueA issue deve utilizar os dados recebidos pelo endpoint criado na task 58 e persistir as informações no banco de dados. Imporante que a implementação esteja de acordo com a modelagem do banco de dados.#DetalhesAjuste a task para que esteja de acordo com a nova descrição e o novo template de user_story.Atualize o título, as labels, o type e a descrição da issue.A task deve ser associada ao épico 60#RestriçõesNão implemente nenhuma funcionadade ou código nesta sessão. Apenas atualize a task correspondete.

---

### Criar tech_template.yml para issues técnicas baseado no docs_template.yml

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-50-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Criar tech_template.yml para issues técnicas baseado no docs_template.yml
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:50:00

## Prompt original

Cire um tech_template.yml. Use o docs_template.yml como um exemplo.Esse novo template deve ser definir o padrão de issue técnicas e conter:Título: [TECH]Labels: "tech"type: Featurebody:* Descrição* Checklist técnico

---

### Criar label tech com cor laranja via gh CLI

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_12-55-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Criar label tech com cor laranja via gh CLI
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 12:55:00

## Prompt original

Crie a label tech com a cor laranja usando o gh cli como no exemplo:gh label create epic --description "Agrupador de alto nível" --color 5319E7 || true

---

### Adicionar label tech e título TECH nos arquivos de referência issue-labels, issue-title e SKILL

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-00-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Adicionar label tech e título TECH nos arquivos de referência issue-labels, issue-title e SKILL
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:00:00

## Prompt original

Adicione essa label tech e o título TECH nos arquivos de referencia, como issue-labels e issue-title e SKILL

---

### Criar user story unificando issues #18 e #58 com épico #60 como pai

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-05-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Criar user story unificando issues #18 e #58 com épico #60 como pai
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:05:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoCrie um novo User story, use o template user_story_template. Este user story deve ser a junção das issues 18 e 58.#Descrição da demandaEssa nova demanda deve conceber a criação do endpoint POST /import, ser capaz de extrair as informações do arquivo swagger passado e persisti-las no banco.#DetalhesA issue deve ter o épico 60 como pai.Ela deve fornecer funcionalidade real para o usuário.#RestriçõesNão realizar qualquer alteração nas demandas 58 e 18 neste momento, apenas criar a nova demanda.Não implementar qualquer código ou funcionalidade no projeto.

---

### Pergunta sobre possibilidade de atualizar campo relationship via gh CLI ou bash

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-10-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Pergunta sobre possibilidade de atualizar campo relationship via gh CLI ou bash
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:10:00

## Prompt original

Além de adiconar Parent Epic como épico pai, é possível também atualizar o campo relationship de uma issue com a task de épico informada pelo gh cli ou bash?

---

### Vincular épico #60 como pai da issue #62 via GraphQL addSubIssue

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-15-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Vincular épico #60 como pai da issue #62 via GraphQL addSubIssue
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:15:00

## Prompt original

Tenta adicionar o épico 60 como pai da issue 62 usando este método

---

### Extrair hierarquia da SKILL.md para issue-hierarchy.md com vínculo GraphQL e suporte a Story

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-20-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Extrair hierarquia da SKILL.md para issue-hierarchy.md com vínculo GraphQL e suporte a Story
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:20:00

## Prompt original

Extrai a especificação de hierarquia da SKILL.md e transfira para um novo arquivo de referêcia issue-hierarchy.md. Além de adicionar o "Parent Epic: Id" no body da issue, também deve utilizar este comando para garantir o vínculo entre as demandas.Também ajuste para que além de Epic este ajuste possa ser usado para o vínculo com issue do tipo Story

---

### Transformar issues #18 e #58 em issues técnicas com tech_template e pai Story #62

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-25-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Transformar issues #18 e #58 em issues técnicas com tech_template e pai Story #62
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:25:00

## Prompt original

#InstruçãoAgora transforme as issue 18 e 58 em issue técnicas. Use o tech_template.md.#Descrição das issueAs issues devem conter a mesma descrição que possuem, mas no formado da issue técnica. Remover as partes de User Story e BDD.#DetalhesAs issues devem ter o título, a descrição e os labels atualizados.As issue devem ter como pai a story 62.#RestriçõesNão altere as informações de assgne das issues.Não altere outras issues não solicitadas.Não implemente qualquer funcionalide ou código, apenas atualize as tasks.

---

### Atualizar issue #22 para formato user story com validação de arquivo Swagger

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-30-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar issue #22 para formato user story com validação de arquivo Swagger
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:30:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoAtualize a task 22 para que fique com o padrão de user_story_template.md.Use as especificações definidas na skill github-issue-management.#Descrição da issueA issue deve validar o arquivo e seu conteúdo recebido pela primitiva import criada na task 62.Deve ser validado se o arquivo é um json.Se todos so parâmetros obrigatório de um arquivo swagger estão definidos.#DetalhesAjuste a task para que esteja de acordo com a nova descrição e o novo template de user_story.Atualize o título, as labels, o type e a descrição da issue.A task deve ser associada ao épico 60#RestriçõesNão implemente nenhuma funcionadade ou código nesta sessão. Apenas atualize a task correspondete.

---

### Atualizar issue #21 para formato tech_template com cliente OpenAI ChatGPT

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-35-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar issue #21 para formato tech_template com cliente OpenAI ChatGPT
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:35:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoAtualize a task 21 para que fique com o padrão de tech_template.md.Use as especificações definidas na skill github-issue-management.#Descrição da issueA issue deve criar um cliente com configurações para viabilizar a chamda de APIs do ChatGPT OpenAi, a autentivação deve ser feita pelo token da conta.#DetalhesAjuste a task para que esteja de acordo com a nova descrição e o novo template de tech.Atualize o título, as labels, o type e a descrição da issue.A task não deve estar associada a nenhuma outra neste momento.#RestriçõesNão implemente nenhuma funcionadade ou código nesta sessão. Apenas atualize a task correspondete.

---

### Atualizar descrição da issue #21 para usar .env para armazenar token

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-40-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar descrição da issue #21 para usar .env para armazenar token
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:40:00

## Prompt original

Atualize novamente a descrição da task 21. O token deve ser armazenado em um arquivo .env de variáveis de ambiente

---

### Atualizar issue #19 para formato tech_template com endpoints dinâmicos

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-45-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar issue #19 para formato tech_template com endpoints dinâmicos
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:45:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoAtualize a task 19 para que fique com o padrão de tech_template.md.Use as especificações definidas na skill github-issue-management.#Descrição da issueA issue deve criar endpoints dinâmicos com base nas especificações da API salvas no banco. As respostas padrões dos endpoints devem ser o schema de retorno do endpoint correspondente.#DetalhesAjuste a task para que esteja de acordo com a nova descrição e o novo template de tech.Atualize o título, as labels, o type e a descrição da issue.A task não deve estar associada a nenhuma outra neste momento.#RestriçõesNão implemente nenhuma funcionadade ou código nesta sessão. Apenas atualize a task correspondete.

---

### Atualizar issue #23 para formato tech_template com geração de resposta via IA

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-50-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar issue #23 para formato tech_template com geração de resposta via IA
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:50:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoAtualize a task 23 para que fique com o padrão de tech_template.md.Use as especificações definidas na skill github-issue-management.#Descrição da issueA issue deve ser reponsável por obter o schema de responta do endpoint dinâmico que está sendo acessado e enviar para a IA para que gere um exemplo realista da resposta.#DetalhesAjuste a task para que esteja de acordo com a nova descrição e o novo template de tech.Atualize o título, as labels, o type e a descrição da issue.A task não deve estar associada a nenhuma outra neste momento.#RestriçõesNão implemente nenhuma funcionadade ou código nesta sessão. Apenas atualize a task correspondete.

---

### Criar user story de respostas mockadas com IA, pai de #19, #21, #23, filha do épico #60

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_13-55-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Criar user story de respostas mockadas com IA, pai de #19, #21, #23, filha do épico #60
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 13:55:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoCrie um novo user story com o padrão de user_story_template.Use as especificações definidas na skill github-issue-management.#Descrição da issueEssa é a story define a funcionalide de resposta mockada com IA para usuário. Ela deve ser composta pelas tarefas técnicas 19, 21, 23.Nesta story, deve ser criados os endpoints dinâmicos e a conexão com a IA para geranção de respostas realistas.O usuário deve ser capaz de acessar cada endpoint definido no arquivo swagger e obter uma resposta raelista toda vez que for utilizado.#DetalhesCire a task a descrição e o template de user_story.Mantenha o padrão para o título, as labels, o type e a descrição da issue.Esta user_story deve ter o épico 60 como task pai.Esta story deve ser a task pai das tasks técnicas 19, 21, 23.#RestriçõesNão implemente nenhuma funcionadade ou código nesta sessão. Apenas atualize/crie as tasks correspondetes.

---

### Atualizar issue #24 para formato user story com listagem de endpoints mockados

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_14-00-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar issue #24 para formato user story com listagem de endpoints mockados
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 14:00:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoAtualize a task 24 para que fique com o padrão de user_story_template.md.Use as especificações definidas na skill github-issue-management.#Descrição da issueA issue deve ser capaz de retornar todos os endpoints mockados disponíveis. Deve ser realizado a consulta na base e retornar os endpoints salvos.#DetalhesAjuste a task para que esteja de acordo com a nova descrição e o novo template de user_story.Atualize o título, as labels, o type e a descrição da issue.A task deve ser associada ao épico 60#RestriçõesNão implemente nenhuma funcionadade ou código nesta sessão. Apenas atualize a task correspondete.

---

### Atualizar issue #49 para formato docs_template com agente de IA para issues

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-14_14-05-00_joaopuel.md` |
| Data | 2026-05-14 |

Prompt: Atualizar issue #49 para formato docs_template com agente de IA para issues
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-14 14:05:00

## Prompt original

Invoke github-issue-manager as subagent with 
/github-issue-manager #InstruçãoAtualize a task 49 para que fique com o padrão de docs_template.Use as especificações definidas na skill github-issue-management.#Descrição da issueA issue consite na criação de um agente de IA responsável pela criação/atualização de issues no github.#DetalhesAjuste a task para que esteja de acordo com a nova descrição e o novo template de docs.A issue não é referente a funcionalidades do produto, mas é importante para organização de issues pelo time de desenvolvimento.Atualize o título, as labels, o type e a descrição da issue.A task não deve estar associada a nehuma outra issue.#RestriçõesNão implemente nenhuma funcionadade ou código nesta sessão. Apenas atualize a task correspondete.

---

### Criar endpoint POST /import para validar extensão de arquivo JSON e retornar status adequado

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_01-03-50_daniel-rodrigues-da-silva.md` |
| Data | 2026-05-16 |

Prompt: Criar endpoint POST /import para validar extensão de arquivo JSON e retornar status adequado
Responsável: Daniel Rodrigues da Silva
Usuário: daniel-rodrigues-da-silva
Data/hora: 2026-05-16 01:03:50

## Prompt original

Crie o endpoint POST /import capaz de receber um array de binário e validar a extensão do arquivo.Se a extensão for json, retornar a mensagem "Arquivo importado com sucesso" com status code 201.Se a extensão não for json, retornar a mensagem "Arquivo com extensão inválida, deve ser .json" e status code 400.Não implementar leitura do conteúdo do arquivo.Não implementar persistência em anco de dados.Não implementar testes automatizados.

---

### Criar o design para a spec import-endpoint

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_01-10-56_daniel-rodrigues-da-silva.md` |
| Data | 2026-05-16 |

Prompt: Criar o design para a spec import-endpoint
Responsável: Daniel Rodrigues da Silva
Usuário: daniel-rodrigues-da-silva
Data/hora: 2026-05-16 01:10:56

## Prompt original

Create the design for import-endpoint

---

### Remover o tópico Testing Strategy do design.md da spec import-endpoint

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_01-21-14_daniel-rodrigues-da-silva.md` |
| Data | 2026-05-16 |

Prompt: Remover o tópico Testing Strategy do design.md da spec import-endpoint
Responsável: Daniel Rodrigues da Silva
Usuário: daniel-rodrigues-da-silva
Data/hora: 2026-05-16 01:21:14

## Prompt original

na spec import-endpoint, corriga o arquivo design.md: exclua o tópico Testing Strategy, não implemente nenhum teste automatizado.

---

### Criar tasks para a spec import-endpoint

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_01-24-15_daniel-rodrigues-da-silva.md` |
| Data | 2026-05-16 |

Prompt: Criar tasks para a spec import-endpoint
Responsável: Daniel Rodrigues da Silva
Usuário: daniel-rodrigues-da-silva
Data/hora: 2026-05-16 01:24:15

## Prompt original

Create the tasks for import-endpoint

---

### Excluir tasks 1, 9, 10 e 11 do tasks.md da spec import-endpoint e ajustar referências

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_01-31-10_daniel-rodrigues-da-silva.md` |
| Data | 2026-05-16 |

Prompt: Excluir tasks 1, 9, 10 e 11 do tasks.md da spec import-endpoint e ajustar referências
Responsável: Daniel Rodrigues da Silva
Usuário: daniel-rodrigues-da-silva
Data/hora: 2026-05-16 01:31:10

## Prompt original

na spec import-endpoint, corrija o arquivo tasks.md: exclua as tasks 1, 9, 10 e 11. Após excluir essas tasks, ajuste os tópicos Task Dependency Graph e Notes, retirando desses tópicos qualquer referência as tasks excluídas.

---

### Traduzir arquivos skills.md e specification.md para português

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_11-44-18_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Traduzir arquivos skills.md e specification.md para português
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 11:44:18

## Prompt original

Traduza esses dois arquivos para português

---

### Criar skill para construir skills, usando skills.md e specification.md como referência

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_12-17-10_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Criar skill para construir skills, usando skills.md e specification.md como referência
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 12:17:10

## Prompt original

Contrua uma skill para construir skills use os arquivos anexos como referência e mova-os para o diretório da nova skill.

---

### Traduzir o arquivo steering.md para português

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_12-39-58_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Traduzir o arquivo steering.md para português
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 12:39:58

## Prompt original

Traduza esse arquivo para português

---

### Renomear a skill criar-skill para create-skill (nome em inglês)

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_12-41-45_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Renomear a skill criar-skill para create-skill (nome em inglês)
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 12:41:45

## Prompt original

Traduza o nome da skill para inglês e o nome do arquivo

---

### Dúvida sobre uso de inglês ou português no campo description do frontmatter da skill

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_12-43-52_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Dúvida sobre uso de inglês ou português no campo description do frontmatter da skill
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 12:43:52

## Prompt original

The description in the frontmatter work better in english or it can be used in porutguese?

---

### Adicionar na skill create-skill que o nome deve ser em inglês e a descrição/conteúdo em português

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_12-46-37_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Adicionar na skill create-skill que o nome deve ser em inglês e a descrição/conteúdo em português
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 12:46:37

## Prompt original

Então adicione na skill create skill que o nome deve ser sempre em inglês, mas a descrição em português junto com o conteúdo do arquivo.

---

### Criar skill para criação de arquivos steering usando steering.md como referência

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_12-50-59_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Criar skill para criação de arquivos steering usando steering.md como referência
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 12:50:59

## Prompt original

Crie uma skill para a criação de arquivos steerings, use o arquivo em anexo como referência e mova-o para o diretório da skill.

---

### Tornar obrigatório deixar claro quando uma skill deve ser ativada na skill create-skill

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_13-18-53_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Tornar obrigatório deixar claro quando uma skill deve ser ativada na skill create-skill
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 13:18:53

## Prompt original

Atualize a skill para ser obrigatório sempre deixar claro quando uma skill deve ser ativada.

---

### Ajustar hook push-modifications para usar script open_pr.sh ao abrir PR

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_19-04-46_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Ajustar hook push-modifications para usar script open_pr.sh ao abrir PR
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 19:04:46

## Prompt original

Ajuste o push-modifications hook par a que use o script c:\git\mini-projeto-MockAi\scripts\open_pr.shpara abrir o PR

---

### Adicionar validações no open_pr.sh: bloquear alterações não commitadas e branches protegidas

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_19-23-00_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Adicionar validações no open_pr.sh: bloquear alterações não commitadas e branches protegidas
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 19:23:00

## Prompt original

Atualize este script para realizar validações antes de abrir um PR.

1. Verifique o status com `git status`
2. Se houver alterações não commitadas:
- PROIBIDO prosseguir
- Exiba a mensagem: "OPERAÇÃO BLOQUEADA: Existem alterações não commitadas. Por favor, realize o commit das alterações antes de executar o push."
- Interrompa o fluxo imediatamente
3. Verifique a branch atual com `git branch --show-current`
- PROIBIDO fazer push se a branch for `main` ou `develop` — interrompa e avise o desenvolvedor

---

### Etapa 1 — Mapeamento dos campos Swagger desserializados para entidades JPA do projeto MockAI

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_19-38-59_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Etapa 1 — Mapeamento dos campos Swagger desserializados para entidades JPA do projeto MockAI
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:38:59

## Prompt original

Etapa 1 — Mapeamento das entidades:Com base no endpoint POST /import da task #58 e na modelagem do banco de dados do projeto, identificar e mapear todos os campos da especificação Swagger desserializada para as entidades JPA correspondentes.O que precisa ser feito:Analisar o objeto Java gerado pela desserialização Jackson da task #58Analisar as entidades JPA já existentes no projetoCriar um mapeamento explícito de qual campo da spec Swagger corresponde a qual campo de qual entidadeIdentificar a ordem correta de persistência respeitando as dependências entre entidades (pai → filho)Documentar os relacionamentos encontradosRestrições:Não alterar nenhum código existente nesta etapa, apenas análise e documentaçãoToda implementação deve estar em conformidade com o arquivo de steering do projeto

---

### Etapa 2 — Implementar serviço de persistência da spec Swagger no banco de dados

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_19-41-10_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Etapa 2 — Implementar serviço de persistência da spec Swagger no banco de dados
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:41:10

## Prompt original

Etapa 2 — Implementação do serviço de persistência:Com base no mapeamento realizado na etapa anterior, implementar o serviço responsável por persistir os dados da especificação Swagger no banco de dados.O que precisa ser feito:Criar a classe de serviço de persistência seguindo os padrões do projetoImplementar o método principal anotado com @TransactionalImplementar a persistência das entidades na ordem correta (pai → filho)Utilizar os repositórios Spring Data JPA já existentes no projetoRestrições:Não conectar ainda ao endpoint /import nesta etapaToda implementação deve estar em conformidade com o arquivo de steering do projetoSeguir a modelagem e relacionamentos do banco já definidos no projetoUtilizar apenas repositórios Spring Data JPA para persistência

---

### Etapa 3 — Implementar deleção dos dados antigos com rollback transacional completo

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_19-45-24_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Etapa 3 — Implementar deleção dos dados antigos com rollback transacional completo
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:45:24

## Prompt original

Etapa 3 — Implementação da deleção e rollback:Implementar a lógica de deleção dos dados antigos antes de persistir os novos, garantindo rollback completo em caso de falha.O que precisa ser feito:Implementar a deleção dos dados existentes antes de inserir os novos dentro do mesmo @TransactionalGarantir que se a deleção falhar, o processo inteiro seja abortado e nenhum dado novo seja inseridoGarantir que não existam dados antigos misturados com dados novos em nenhum cenárioO rollback deve ser automático via @TransactionalComportamento esperado:CenárioResultadoDeleção ok + Inserção okDados novos persistidos corretamenteDeleção falhaRollback completo, dados antigos preservadosInserção falhaRollback completo, banco volta ao estado anteriorRestrições:Toda implementação deve estar em conformidade com o arquivo de steering do projetoNão alterar o serviço de persistência da etapa anterior, apenas adicionar a lógica de deleção

---

### Etapa 4 — Tratamento explícito de erros de persistência com mensagens descritivas

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_19-48-30_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Etapa 4 — Tratamento explícito de erros de persistência com mensagens descritivas
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:48:30

## Prompt original

Etapa 4 — Tratamento explícito de errosImplementar o tratamento explícito de todas as falhas possíveis durante o processo de persistência, retornando mensagens descritivas para cada cenário.O que precisa ser feito:Capturar DataAccessException — falha de conexão com o bancoCapturar ConstraintViolationException — violação de integridade referencialCapturar falhas específicas da deleção de dados antigosCapturar falha genérica de persistência retornando detalhe do erroCada exceção deve gerar uma mensagem descritiva e clara do que falhouMensagens a implementar:"Falha de conexão com o banco de dados""Violação de integridade referencial: {detalhe}""Falha ao deletar dados anteriores: {detalhe}""Falha ao persistir os dados: {detalhe}"Restrições:Toda implementação deve estar em conformidade com o arquivo de steering do projetoNão alterar as mensagens já existentes da task #58

---

### erro durante o processo de compilação (continuação da etapa 4 — tratamento de erros de persistência)

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_19-51-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: erro durante o processo de compilação (continuação da etapa 4 — tratamento de erros de persistência)
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:51:00

## Prompt original

Ocorreu um erro durante o processo de compilação da aplicação. A execução foi interrompida devido a inconsistências no código-fonte ou nas dependências do projeto. Verifique os logs de compilação para identificar a causa exata do problema e realize os ajustes necessários antes de gerar uma nova build.

---

### Etapa 5 — Integrar serviço de persistência ao endpoint POST /import da task #58

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_19-55-45_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Etapa 5 — Integrar serviço de persistência ao endpoint POST /import da task #58
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:55:45

## Prompt original

Etapa 5 — Integração com o endpoint da task #58Integrar o serviço de persistência implementado nas etapas anteriores ao endpoint POST /import da task #58, mantendo todos os comportamentos e mensagens já existentes.O que precisa ser feito:Injetar o serviço de persistência no controller/service da task #58Chamar o serviço de persistência após a desserialização bem-sucedida do arquivoGarantir que as mensagens já existentes da task #58 sejam mantidasAdicionar as novas mensagens de erro de persistência ao fluxoFluxo final esperado:Receber binário     ↓ Desserializar com Jackson (task #58 — sem alteração)     ↓ [Desserialização falha] → Mensagens de erro já existentes da task #58     ↓ [Desserialização ok] → Tentar deletar dados antigos + persistir novos     ↓                              ↓ [Persistência ok]            [Persistência falha] Mensagens da task #58        Rollback + mensagem + "Arquivo importado         descritiva do erro com sucesso"Restrições:Toda implementação deve estar em conformidade com o arquivo de steering do projetoNão alterar o contrato do endpoint /importNão remover nem modificar nenhuma mensagem ou comportamento da task #58

---

### análise dos erros (continuação da etapa 6 — validação e testes)

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_20-00-14_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: análise dos erros (continuação da etapa 6 — validação e testes)
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 20:00:14

## Prompt original

Realizar análise dos erros identificados na etapa de testes automatizados após a correção da compilação. Verificar logs de execução, validar dependências, revisar cenários de teste impactados e aplicar os ajustes necessários para garantir a estabilidade da aplicação e a aprovação da pipeline.

---

### erro de dependências (continuação da etapa 6 — correção dos testes)

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_20-06-34_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: erro de dependências (continuação da etapa 6 — correção dos testes)
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 20:06:34

## Prompt original

Analisar e corrigir todos os erros de dependências identificados no projeto durante a compilação e execução dos testes. Validar conflitos de versões, referências ausentes, pacotes incompatíveis e configurações do ambiente. Garantir que todas as dependências estejam restauradas corretamente conforme o steering e o stack definido no projeto, mantendo compatibilidade com a pipeline de build e testes automatizados.


---

### Não fazer testes unitários — remover os arquivos de teste criados

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_20-20-40_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Não fazer testes unitários — remover os arquivos de teste criados
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 20:20:40

## Prompt original

Não fazer testes unitários

---

### BUILD FAILURE ao executar — ObjectMapper não disponível como bean Spring

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_20-24-58_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: BUILD FAILURE ao executar — ObjectMapper não disponível como bean Spring
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 20:24:58

## Prompt original

BUILD FAILURE------------------------------------------------------------------------Total time:  5.712 sFinished at: 2026-05-16T20:24:22-03:00------------------------------------------------------------------------Failed to execute goal org.codehaus.mojo:exec-maven-plugin:3.5.1:exec (default-cli) on project mockai: Command execution failed.: Process exited with an error: 1 (Exit value: 1) -> [Help 1]To see the full stack trace of the errors, re-run Maven with the -e switch.Re-run Maven using the -X switch to enable full debug logging.

---

### Diagnosticar e corrigir BUILD FAILURE do exec-maven-plugin no projeto MockAI

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_20-28-30_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Diagnosticar e corrigir BUILD FAILURE do exec-maven-plugin no projeto MockAI
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 20:28:30

## Prompt original

o projeto continua falhando durante o build Maven com o seguinte erro:BUILD FAILURE ------------------------------------------------------------------------ Total time:  6.204 s Finished at: 2026-05-16T20:27:26-03:00 ------------------------------------------------------------------------ Failed to execute goal org.codehaus.mojo:exec-maven-plugin:3.5.1:exec (default-cli) on project mockai: Command execution failed.: Process exited with an error: 1 (Exit value: 1) -> [Help 1]  To see the full stack trace of the errors, re-run Maven with the -e switch. Re-run Maven using the -X switch to enable full debug logging. Analise o problema considerando o steering e o stack já definidos no projeto. Preciso que você:Identifique a causa raiz da falha do exec-maven-plugin.Revise dependências incompatíveis ou ausentes.Verifique a configuração dos plugins no pom.xml.Corrija possíveis problemas de versão do Java, Maven ou Spring Boot.Execute o build com mvn clean install -X para obter logs detalhados.Garanta que o projeto compile corretamente e que todos os testes passem.Mantenha o padrão arquitetural e as convenções já definidas no projeto.Também entregue um resumo técnico contendo:a causa do erro,os arquivos alterados,e como validar a correção localmente.

---

### Corrigir e validar o arquivo OpenAPI JSON em `docs/petstore.json`

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_20-45-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Corrigir e validar o arquivo OpenAPI JSON em `docs/petstore.json`
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 20:45:00

## Prompt original

O arquivo `docs/petstore.json` está no formato errado e precisa ser convertido para um JSON válido de OpenAPI 3.0.4. Analise o conteúdo atual, corrija a estrutura, preserve os dados e gere um documento JSON válido.

Preciso que você:
- Identifique se o arquivo está em YAML ou JSON e converta corretamente para JSON.
- Garanta que a estrutura esteja compatível com OpenAPI 3.0.4.
- Preserve todos os caminhos, componentes e schemas existentes.
- Valide o arquivo final para que seja lido por ferramentas OpenAPI.
- Explique brevemente as correções realizadas.

---

### Diagnosticar e resolver violação de integridade referencial ao deletar dados no banco H2

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_20-46-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Diagnosticar e resolver violação de integridade referencial ao deletar dados no banco H2
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 20:46:00

## Prompt original

A exclusão de dados anteriores falha com erro de integridade referencial em `endpoint_definition` para `api_specification`. Analise as entidades JPA e a lógica de deleção existente.

Preciso que você:
- Identifique o motivo da falha de `Referential integrity constraint violation`.
- Corrija a lógica de deleção para que cascatas funcionem corretamente.
- Verifique o uso de `deleteAllInBatch()` versus `deleteAll()` e ajuste conforme necessário.
- Garanta que o commit seja seguro e que a aplicação não falhe ao reinserir novos dados.

---

### Parar o servidor atual e verificar o funcionamento da API no H2

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_20-47-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-16 |

Prompt: Parar o servidor atual e verificar o funcionamento da API no H2
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 20:47:00

## Prompt original

O servidor está em execução e a porta pode estar ocupada. Preciso conferir se a API inicia corretamente e se o banco H2 está acessível.

Preciso que você:
- Identifique e pare o processo do servidor que está usando a porta 8080.
- Reinicie a aplicação de forma limpa no projeto MockAI.
- Informe o endpoint do H2 Console e a conexão JDBC correta.
- Verifique se a API está disponível e responde sem erro.
- Use um estilo claro e objetivo em português, como Kiro faria.

---

### Criar spec para implementação de client de integração com ChatGPT usando spring-ai-starter-model-openai

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-16_22-56-25_joaopuel.md` |
| Data | 2026-05-16 |

Prompt: Criar spec para implementação de client de integração com ChatGPT usando spring-ai-starter-model-openai
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-16 22:56:25

## Prompt original

#InstrulçãoCrie um spec para a implementação de um client de integração com o ChatGPT.#Detalhes1. Busque as informações da issue 21, issue que estou atuando, para serem utilizadas como contexto.2. A implementação deve consistir da criação e configuração de um client para integração com o ChatGPT.3. Apenas deve ser criado o cliente e definas as configurções nesta demanda.4. Utilize a bibllioteca spring-ai-starter-model-openai para a criação do client.5. O client deve definir alguns métodos padrões como envio de solicação para a IA.#Dependência Maven<dependency>     <groupId>org.springframework.ai</groupId>     <artifactId>spring-ai-starter-model-openai</artifactId> </dependency>

---

### Adicionar ao design mecanismo de falha na startup se OPENAI_API_KEY não configurada

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_01-21-47_joaopuel.md` |
| Data | 2026-05-17 |

Prompt: Adicionar ao design mecanismo de falha na startup se OPENAI_API_KEY não configurada
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-17 01:21:47

## Prompt original

Implemente no design a falha ao iniciar a aplicação caso a chave da API não esteja configurada

---

### Pergunta sobre como fazer o git ignorar .env mas não o .env.example

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_02-44-28_joaopuel.md` |
| Data | 2026-05-17 |

Prompt: Pergunta sobre como fazer o git ignorar .env mas não o .env.example
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-17 02:44:28

## Prompt original

There is a way to make git ignore the .env but not the .evn.example?

---

### Analisar PR para conflitos e criar novo PR baseado na versão de desenvolvimento

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_14-34-07_dariel-verdecia-verdecia.md` |
| Data | 2026-05-17 |

Prompt: Analisar PR para conflitos e criar novo PR baseado na versão de desenvolvimento
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-17 14:34:07

## Prompt original

Analise meu PR para identificar possíveis conflitos e crie um novo PR com base no novo gancho de PR na versão de desenvolvimento.

---

### Ajustar todo o código para usar Groq em vez de OpenAI com Spring AI

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_15-45-49_joaopuel.md` |
| Data | 2026-05-17 |

Prompt: Ajustar todo o código para usar Groq em vez de OpenAI com Spring AI
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-17 15:45:49

## Prompt original

Vamos ajustar todo o código para usar groq em vez de OpenAi. Usando essa doc como referência https://spring.io/blog/2024/07/31/spring-ai-with-groq-a-blazingly-fast-ai-inference-engine

---

### Revisar documentação, steerings e skills substituindo ChatGPT/OpenAI por Groq

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_16-35-06_joaopuel.md` |
| Data | 2026-05-17 |

Prompt: Revisar documentação, steerings e skills substituindo ChatGPT/OpenAI por Groq
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-17 16:35:06

## Prompt original

Revise toda a documentação e substua também para que se refira ao groq em vez de chatgpt. Não altere arquivos de specs (requirements, design, tasks).  Também revise os steerings e skills do projeto

---

### Implementar issue #24 - Listar endpoints mockados disponíveis no projeto MockAI

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_16-47-43_welton.md` |
| Data | 2026-05-17 |

Prompt: Implementar issue #24 - Listar endpoints mockados disponíveis no projeto MockAI
Responsável: Welton
Usuário: welton
Data/hora: 2026-05-17 16:47:43

## Prompt original

Você é um desenvolvedor responsável por implementar EXCLUSIVAMENTE a issue abaixo no projeto mini-projeto-MockAi, usando como base todo o fonte já criado.ISSUE:[STORY] Listar endpoints mockados disponíveis #24REGRAS CRÍTICAS:- Implemente SOMENTE o que está descrito nesta issue.- NÃO implemente funcionalidades extras.- NÃO altere regras de negócio não relacionadas.- NÃO refatore partes do projeto fora do escopo da issue.- NÃO altere arquitetura, estrutura de pastas ou padrões existentes sem necessidade direta para esta task.- NÃO atualize dependências.- NÃO renomeie classes, métodos, entidades, DTOs ou arquivos existentes sem necessidade obrigatória.- NÃO altere endpoints já existentes, exceto se for indispensável para integrar esta listagem.- NÃO implemente importação de Swagger, pois isso não faz parte desta issue.- NÃO crie telas, frontend ou documentação extra, salvo se já existir padrão obrigatório no projeto.- NÃO invente novos campos além de path, método HTTP e descrição, a menos que o modelo existente já possua esses campos.INSTRUÇÕES DE IMPLEMENTAÇÃO:1. Analise o código já existente.2. Identifique onde os endpoints importados do Swagger estão sendo persistidos.3. Reutilize entidades, repositories, services, controllers e DTOs existentes sempre que possível.4. Crie ou ajuste apenas as classes necessárias para expor uma rota de listagem dos endpoints mockados.5. A rota deve consultar a base de dados e retornar todos os endpoints persistidos.6. A resposta deve conter, no mínimo:- path- método HTTP- descrição7. Caso não existam endpoints cadastrados, retorne uma lista vazia. Use mensagem informativa somente se esse for o padrão já adotado no projeto.8. Mantenha o padrão de respostas, nomes de rotas, organização de pacotes e estilo de código já existentes no projeto.9. Adicione validação mínima para garantir que o endpoint responde corretamente, seguindo o padrão de testes já existente, se houver.10. Antes de finalizar, revise se nenhuma alteração fora do escopo foi feita.CRITÉRIOS DE CONCLUSÃO:- Existe um endpoint backend para listar os endpoints mockados disponíveis.- O endpoint consulta os dados persistidos no banco.- O retorno contém path, método HTTP e descrição.- Quando não houver registros, o retorno é lista vazia ou mensagem informativa conforme padrão do projeto.- A implementação segue os padrões existentes.- Nenhuma funcionalidade extra foi adicionada.- Nenhuma alteração desnecessária foi feita.FORMATO DA RESPOSTA FINAL:Ao terminar, informe apenas:1. Arquivos alterados/criados.2. Breve descrição do que foi implementado.3. Como testar manualmente a nova rota.4. Confirmação de que nenhuma implementação fora do escopo foi realizada.

---

### Corrigir MultipleBagFetchException ao importar swagger company-manager.json

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_16-57-41_joaopuel.md` |
| Data | 2026-05-17 |

Prompt: Corrigir MultipleBagFetchException ao importar swagger company-manager.json
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-17 16:57:41

## Prompt original

I try to import the company-manager.json swagger but got this error:
org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags: [com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity.pathParameters, com.ia.para.devs.mockai.infrastructure.persistence.entity.EndpointDefinitionEntity.responses]

---

### Criar testes automatizados para a issue #24 - Listar endpoints mockados disponíveis

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_17-10-37_welton.md` |
| Data | 2026-05-17 |

Prompt: Criar testes automatizados para a issue #24 - Listar endpoints mockados disponíveis
Responsável: Welton
Usuário: welton
Data/hora: 2026-05-17 17:10:37

## Prompt original

Você é um desenvolvedor responsável por implementar EXCLUSIVAMENTE os testes automatizados da issue abaixo no projeto mini-projeto-MockAi, usando como base todo o fonte já criado.ISSUE:[STORY] Listar endpoints mockados disponíveis #24CONTEXTO:A implementação da issue já foi feita. Agora o objetivo é criar testes para validar a rota backend que lista os endpoints mockados disponíveis.REGRAS CRÍTICAS:- Implemente SOMENTE testes relacionados a esta issue.- NÃO altere regras de negócio.- NÃO altere endpoints existentes.- NÃO refatore código de produção fora do necessário para viabilizar os testes.- NÃO atualize dependências.- NÃO crie funcionalidades novas.- NÃO altere arquitetura, estrutura de pastas ou padrões existentes.- NÃO implemente importação de Swagger.- NÃO crie telas, frontend ou documentação extra.- NÃO invente novos campos além dos já esperados no retorno: path, método HTTP e descrição.- Reutilize o padrão de testes já existente no projeto.OBJETIVO DOS TESTES:Criar testes automatizados para validar que existe uma rota backend capaz de listar os endpoints mockados persistidos no banco de dados.CENÁRIOS OBRIGATÓRIOS:1. Deve retornar status HTTP de sucesso ao consultar a rota de listagem.2. Deve retornar uma lista com os endpoints mockados persistidos no banco.3. Cada item da resposta deve conter, no mínimo:- path- método HTTP- descrição4. Quando não existirem endpoints cadastrados, deve retornar lista vazia ou mensagem informativa, conforme o padrão já adotado no projeto.5. A rota deve consultar os dados persistidos, não retornar dados mockados fixos no teste.6. O teste deve seguir o padrão de testes já existente no projeto.INSTRUÇÕES DE IMPLEMENTAÇÃO:1. Analise a estrutura atual de testes do projeto.2. Identifique se o projeto usa testes de controller, service, repository ou integração.3. Escolha o tipo de teste mais adequado conforme o padrão já existente.4. Crie massa de dados mínima necessária para validar a listagem.5. Garanta isolamento entre os testes, limpando ou preparando o banco conforme o padrão do projeto.6. Valide o status HTTP, o formato da resposta e os campos obrigatórios.7. Evite testar funcionalidades que não fazem parte desta issue.8. Antes de finalizar, revise se nenhuma alteração fora do escopo foi feita.CRITÉRIOS DE CONCLUSÃO:- Existem testes automatizados para a rota de listagem dos endpoints mockados.- Os testes validam retorno com registros cadastrados.- Os testes validam retorno sem registros cadastrados.- Os testes validam os campos path, método HTTP e descrição.- Os testes seguem o padrão existente no projeto.- Nenhuma funcionalidade extra foi implementada.- Nenhuma alteração desnecessária foi feita.FORMATO DA RESPOSTA FINAL:Ao terminar, informe apenas:1. Arquivos alterados/criados.2. Breve descrição dos testes implementados.3. Como executar os testes.4. Confirmação de que nenhuma implementação fora do escopo foi realizada.

---

### Implementar retorno de respostas por IA no endpoint dinâmico usando AiGateway

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_18-13-02_joaopuel.md` |
| Data | 2026-05-17 |

Prompt: Implementar retorno de respostas por IA no endpoint dinâmico usando AiGateway
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-17 18:13:02

## Prompt original

#Instrução
Implementar o retorno de respostas por IA.

#Detalhes
1. Buscar informações da task 23 para serem utilizadas como contexto.
2. Ao acessar o endpoint dinâmico, precisa ser buscado na base a refertencia ao schema na tabela ENDPOINT_RESPONSE.
3. Depois, deve ser buscado o objeto real de retorno na entidade no COMPONENTS_JSON  da tabela API_SPECIFICATION
4. Também deve ser buscadas descrições do endpoint nas tabelas ENDPONIT_DEFINITION e TAGS para serem utilizadas como contexto e auxiliar na geração de um retorno realista pela IA
4. Com o objeto de retorno e as descrições do endpoint, deve ser enviado um prompt para a IA por meio do AiGateway para que o retorno seja uma possível resposta realista de sucesso do endpoint em formato JSON 
5. O retorno da IA deve ser, então, utilizado como próprio retorno do endpoint
6. Considere apenas casos de sucesso para essa implementação

#Restrições
Proibido realizar ou criar quaisquer tipos de testes neste implementação
Proibido realizar testes para conferir a compilação/build ou execução do código

---

### Ajustar requisitos: schema nulo retorna só status; erro de IA usa fallback estático

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_18-29-23_joaopuel.md` |
| Data | 2026-05-17 |

Prompt: Ajustar requisitos: schema nulo retorna só status; erro de IA usa fallback estático
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-17 18:29:23

## Prompt original

Ajustar os requisitos, se o responseSchema for nulo ou vazio. Nada deve ser retornado pela primitiva, apenas o status como sucesso.
Caso ocorra algum erro ao gerar a resposta pela IA, então o handler deve utilizar o `DynamicResponseBodyBuilder` existente para gerar o corpo estático, mantendo o comportamento atual

---

### Corrigir DynamicResponseBodyBuilder para retornar campos de properties com valores corretos

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-17_19-18-20_joaopuel.md` |
| Data | 2026-05-17 |

Prompt: Corrigir DynamicResponseBodyBuilder para retornar campos de properties com valores corretos
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-17 19:18:20

## Prompt original

Estou com um problema. O método buildResponseBody está retornando:
{   "code" : "string",   "message" : "string",   "timestamp" : "date-time" }
Ao receber:
"OwnerDTO": {"required": ["birthDate","cpf","email","id","name"],"type": "object","properties": {"id": {"type": "string","description": "Identificador do responsável","format": "uuid","example": "123e4567-e89b-12d3-a456-426614174000"},"name": {"type": "string","description": "Nome do responsável","example": "João Silva"},"email": {"type": "string","description": "Email do responsável","example": "joao.silva@email.com"},"phone": {"pattern": "\\d{11}","type": "string","description": "Telefone do responsável (11 dígitos, incluindo DDD)","example": "11987654321"},"cpf": {"pattern": "\\d{11}","type": "string","description": "CPF do responsável (11 dígitos)","example": "12345678901"},"birthDate": {"type": "string","description": "Data de nascimento do responsável","format": "date","example": "1990-01-01"}},"description": "Armazena dados completos do responsável pela empresa"}
O comportamento correto deve ser:
{"id": "<repective-format>","name": "<repective-format>","email": "<repective-format>","phone": "<repective-format>","cpf": "<repective-format>","birthDate": "<repective-format>"}
Ou seja, o retorno deve ser um objeto Json formado pelos campos presentes em properties, para ser um exemplo real do retorno do endpoint.

---

### Ajuste na persistência de ENDPOINT_RESPONSE para salvar apenas o primeiro status de sucesso

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_01-40-40_joaopuel.md` |
| Data | 2026-05-18 |

Prompt: Ajuste na persistência de ENDPOINT_RESPONSE para salvar apenas o primeiro status de sucesso
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-18 01:40:40

## Prompt original

#InstriçãoAjuste na persistência de entidades da tabela ENDPOINT_RESPONSE.#DetalhesComportamento atual: Quando o endpoint apresenta várias tipos de retorno, com vários tipos de status, estão sendo salvos somente o primeiro formato de resposta sem considerar o status.Comportamento correto: Somente respostas do primeiro status de sucesso devem ser salvas como 200, 201. Não salvar retornos de falhas. Caso não houver qualquer tipo de retorno de sucesso, deve ser salvo null no response schema e 200 no status. #RestriçõesNão realizar/criar quaisquer tipos de testes.Não realizar comandos para testar de compile, build ou execução do projeto.Realize quaisquer ajustes necessários para garantir o funcionamento do novo comportamento, mas não acrescente funcionalidades não solicitadas ou altere comportamentos não solicitados.

---

### Ajuste para retornar JSON puro nos endpoints dinâmicos, removendo delimitadores de código da IA

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_01-57-35_joaopuel.md` |
| Data | 2026-05-18 |

Prompt: Ajuste para retornar JSON puro nos endpoints dinâmicos, removendo delimitadores de código da IA
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-18 01:57:35

## Prompt original

#InstruçãoAjuste ao retornar resposta de endpoint dinâmico.#DetalhesAs respostas de endpoint dinâmicos estão sendo retornadas como code snippet com ```json ``` em vez de um objeto json (application-json)Alterar par que a IA retorne apenas o valor JSON sem ``json `` ou qualquer delimitador de código.#RestriçõesNão realizar/criar quaisquer tipos de testes.Não realizar comandos para testar de compile, build ou execução do projeto.Realize quaisquer ajustes necessários para garantir o funcionamento do novo comportamento, mas não acrescente funcionalidades não solicitadas ou altere comportamentos não solicitados.

---

### Ajuste na persistência de PATH_PARAMETER para salvar format e validar endpoint dinâmico

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_02-21-52_joaopuel.md` |
| Data | 2026-05-18 |

Prompt: Ajuste na persistência de PATH_PARAMETER para salvar format e validar endpoint dinâmico
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-18 02:21:52

## Prompt original

#Instrução
Ajuste na persistência da entidade da tabela PATH_PARAMETER .

#Detalhes
Comportamento atual: Quando um endpoint contém um path parameter id no formato uuid, os parâmetros estão sendo salvos como type = string. Isso ocorre no seguinte problema: Tendo um swagger com duas primitivas exemplo: owner/{id} e owner/all, as duas são tratadas da mesma forma pelo mesmo endpoint dinâmico.

Comportamento correto: ajustar para que sejam salvos como todas as propriedades de cada endponint parameter e que sejam validados corretamente na chamada do endpoint dinâmico.

Como no exemplo anterior. em vez de salvarmos somente type = string, salvamos todo o objeto:
{"name": "id","in": "path","description": "Identificador único do recurso","required": true,"schema": {"type": "string","format": "uuid"}}

Assim no endpoint dinâmico, validados que owner/{id} se refere ao endpoint finalizado com uuid e owner/all se refere a outro endpoint finalizado com em all

#Restrições
Não realizar/criar quaisquer tipos de testes.
Não realizar comandos para testar de compile, build ou execução do projeto.
Realize quaisquer ajustes necessários para garantir o funcionamento do novo comportamento, como ajustar a entidade PATH_PARAMETER , mas não acrescente funcionalidades não solicitadas ou altere comportamentos não solicitados.

---

### Gerar curl de exemplo para cada endpoint do swagger petstore.json

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_02-37-02_joaopuel.md` |
| Data | 2026-05-18 |

Prompt: Gerar curl de exemplo para cada endpoint do swagger petstore.json
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-18 02:37:02

## Prompt original

Gere um curl exemplo para cada endpoint presente neste swagger.Apenas retorne os curl, não faça nenhuma alteração/implementação de código.

---

### Atualizar documentações do projeto com base nos prompts de hoje

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_02-54-00_joaopuel.md` |
| Data | 2026-05-18 |

Prompt: Atualizar documentações do projeto com base nos prompts de hoje
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-18 02:54:00

## Prompt original

#InstruçãoAtualizar todos as documentações do projeto para que estejam de acordo com as novas modificações.#Detalhes1. Buscar os últimos prompts realizados hoje em docs/prompts.2. Atualizar todas as documentações presentes em docs, steerings e skills do projeto para que estejam de acordo com as novas alterações.3. Garanta que os arquivos de database-schema sejam atualizados corretamente.#RestriçõesNão acrescente funcionalidades não criadas recentemente ou não solicitadas.Não implemente/atualize qualquer comportamento do sistema.

---

### Implementar validação do arquivo swagger no endpoint /import com campos obrigatórios

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_19-52-23_joaopuel.md` |
| Data | 2026-05-18 |

Prompt: Implementar validação do arquivo swagger no endpoint /import com campos obrigatórios
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-18 19:52:23

## Prompt original

#InstruçãoImplementar validação do arquivo swagger ao receber o arquivo pelo endpoint /import#Detalhes1. Busque a issue 22 do github por gh cli para contexto2. Adicione validações simples para garantir que o arquivo tenha os requisitos mínimos para ser possíver gerar um endpoint dinâmico, como título, descrição, path e status.#RestriçõesNão realizar ou crie quaisquer tipos de testes nesta implementação.Não altere nenhuma funcionalide e comportamentos que já existem no projeto. 

---

### Criar arquivos swagger de exemplos inválidos para as validações do endpoint /import

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_19-57-59_joaopuel.md` |
| Data | 2026-05-18 |

Prompt: Criar arquivos swagger de exemplos inválidos para as validações do endpoint /import
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-18 19:57:59

## Prompt original

Crie arquivos swagger de exemplos inválidos que devem ser barrados pelas novas validações emC:\git\mini-projeto-MockAi\docs\swagger-examples\invalid

---

### Adicionar anotações OpenAPI nos endpoints estáticos para geração do Swagger

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_20-15-09_joaopuel.md` |
| Data | 2026-05-18 |

Prompt: Adicionar anotações OpenAPI nos endpoints estáticos para geração do Swagger
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-18 20:15:09

## Prompt original

Adicione as anotações do OpenAPI para que seja gerada o swagger do projeto.Apenas adicione anotações nos endpoints estáticos, não adicione nos endpoint dinâmicos.

---

### Corrigir upload de arquivo no Swagger UI para o endpoint de import

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-18_22-22-14_welton.md` |
| Data | 2026-05-18 |

Prompt: Corrigir upload de arquivo no Swagger UI para o endpoint de import
Responsável: Welton
Usuário: welton
Data/hora: 2026-05-18 22:22:14

## Prompt original

os endpoints estão funcionando, testei via postman. Só tem um detelhe, anteriormente eu conseguia anexar o arquivo no ednpoit de import pela documentação do swagger no link http://localhost:8080/mockai/swagger-ui/index.html#/Import/importFilee agora não consigo mais 

---

### Atualizar docs, steerings e README/CONTRIBUTING com estado atual do sistema

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_01-32-11_joaopuel.md` |
| Data | 2026-05-21 |

Prompt: Atualizar docs, steerings e README/CONTRIBUTING com estado atual do sistema
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-21 01:32:11

## Prompt original

Analise todo o projeto e atualize as docs em ./docs, steerings e README e COntribuiting para que estejam atualizadas com o estado atual do sitema.No arquivo de produto destaque que o sistema apenas retorna respostas de sucesso dos endpoints mockados, se não houver aviso a respeito.

---

### Ajustar README.md para seguir o template README_TEMPLATE.md

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_02-07-05_joaopuel.md` |
| Data | 2026-05-21 |

Prompt: Ajustar README.md para seguir o template README_TEMPLATE.md
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-21 02:07:05

## Prompt original

Ajuste o arquivo c:\git\mini-projeto-MockAi\README.md para que esteja de acordo com o template c:\git\mini-projeto-MockAi\README_TEMPLATE.md.

#Detalhes
1. O arquivo readme deve seguir o template disponibilizado, remova tópicos desnecessários.
2. Mantenha os pontos da documentação e adicione o swagger neste tópico

---

### Mover PRD.md de docs/ para a raiz do projeto e atualizar referências

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_02-18-20_joaopuel.md` |
| Data | 2026-05-21 |

Prompt: Mover PRD.md de docs/ para a raiz do projeto e atualizar referências
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-21 02:18:20

## Prompt original

Ajuste para que o PRD fique na raiz do projeto e ajuste as docs necessárias

---

### Reescrever CONTRIBUTING.md seguindo o template e o fluxo real com hooks e open_pr.sh

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_02-41-37_joaopuel.md` |
| Data | 2026-05-21 |

Prompt: Reescrever CONTRIBUTING.md seguindo o template e o fluxo real com hooks e open_pr.sh
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-21 02:41:37

## Prompt original

Ajuste o arquivo c:\git\mini-projeto-MockAi\CONTRIBUTING.md para ficar de acordo com o template c:\git\mini-projeto-MockAi\CONTRIBUTING_TEMPLATE.md.

#Detalhes
1. Consulte o README para convenções de commits e fluxo de desenvolvimento.
2. Consulte os hooks /commit-modifications e /push-modifications , juntamente com o script c:\git\mini-projeto-MockAi\scripts\open_pr.sh para entender o fluxo e o checklist do PR.
3. O arquivo contributing deve seguir o template disponibilizado, remova tópicos desnecessários.

---

### Atualizar PRD.md com contexto completo: steerings, README, issues concluídas e src/

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_03-10-02_joaopuel.md` |
| Data | 2026-05-21 |

Prompt: Atualizar PRD.md com contexto completo: steerings, README, issues concluídas e src/
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-21 03:10:02

## Prompt original

#Instrução
Ajuste o arquivo c:\git\mini-projeto-MockAi\PRD.md.#Consultar para adicionar como contexto- steerings- README.md- backlog e Issues do projeto (Buscar com gh cli) busque issues concluídas- funcionalidades implementadas em src/#Estrutura mínima- Visão geral do produto- Problema resolvido- Objetivos- Público-alvo- Funcionalidades principais- Regras de negócio- Requisitos funcionais- Requisitos não funcionais- Fluxo principal do usuário- Arquitetura de alto nível- Stack tecnológica- Critérios de sucesso- Limitações atuais- User stories [issues github]- Próximos passos#Restrições- Não invente funcionalidades inexistentes- Não crie branch- Não faça commit- Não faça push- Apenas atualize docs/PRD.md- Para as user stories, busque as issues com títulos que iniciam com [STORY} e que estão concluídas- Em caso de dúvidas, contexto incompleto, faça perguntas

---

### Ajustar PRD: remover F2, CS08, L08, próximos passos e rodapé; adicionar novas funcionalidades futuras

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_03-36-29_joaopuel.md` |
| Data | 2026-05-21 |

Prompt: Ajustar PRD: remover F2, CS08, L08, próximos passos e rodapé; adicionar novas funcionalidades futuras
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-21 03:36:29

## Prompt original

#Instrução
Ajuste o arquivo PRD.
#Detalhes
1. A extração e persistência dos dados do swagger não caracterizam uma funcionalidade. Isso faz parte do processo de importação do arquivo.
2. Arquitetura limpa não é um critério de sucesso
3. Sem cobertura de testes automatizados não é uma limitação atual
4. Em próximos passos, remover referencias a issues já abertas. Próximos passos será adicionar novas funcionalidades, como suporte a arquivo YAML, conexão com banco de dados postgre para remover banco em memória, suporte a multiplas especificações de API para poder acessar endpoints de especificações diferentes sem a necessidade de importar o arquivo swager toda vez.
5. Remover comentário de documento atualizado com base em

---

### Atualizar README com referências a PPT, telas Figma, coleção Postman e exemplos Swagger

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_03-45-05_joaopuel.md` |
| Data | 2026-05-21 |

Prompt: Atualizar README com referências a PPT, telas Figma, coleção Postman e exemplos Swagger
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-21 03:45:05

## Prompt original

c:\git\mini-projeto-MockAi\README.md
Adicionar na documentação adicional referência a arquivo de apresentação ppt c:\git\mini-projeto-MockAi\docs\apresentacao\MockAI.pptx
Adicionar na documentação adicional referência a propostas de telas c:\git\mini-projeto-MockAi\docs\propostas_de_telas_apenas_avaliativo\Prompts_telas_figma.md
Adicionar tópico de teste você mesmo com apoio da coleção do postman c:\git\mini-projeto-MockAi\docs\MockAi.postman_collection.json
e exemplos de swagger em docs/swagger-examples

---

### Configurar plugin JaCoCo no pom.xml para cobertura de testes com mínimo de 40%

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_20-30-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Configurar plugin JaCoCo no pom.xml para cobertura de testes com mínimo de 40%
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:30:00

## Prompt original

Configurar o plugin JaCoCo (Java Code Coverage) no pom.xml do projeto MockAI para gerar relatórios de cobertura de código e validar que a cobertura mínima de 40% das linhas seja atingida. O plugin deve incluir as execuções prepare-agent (instrumentação), report (geração de relatório HTML na fase test) e check (validação de cobertura mínima na fase verify). A regra de cobertura deve ser aplicada no nível BUNDLE com counter LINE e value COVEREDRATIO com minimum 0.40. O relatório deve ser gerado em target/site/jacoco/index.html.

---

### Criar testes unitários para ValidateSwaggerContentService cobrindo todos os campos obrigatórios

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_20-35-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para ValidateSwaggerContentService cobrindo todos os campos obrigatórios
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:35:00

## Prompt original

Implementar testes unitários para a classe ValidateSwaggerContentService da camada application. Os testes devem cobrir todos os cenários de validação de campos obrigatórios de uma especificação OpenAPI: spec válida com todos os campos, campo openapi null, campo openapi em branco, bloco info null, info.title null, info.description null, paths null, paths vazio, path sem métodos HTTP definidos, método sem responses definidas, e acúmulo de múltiplos erros em uma única exceção InvalidSwaggerContentException. Utilizar JUnit 5 com @DisplayName e AssertJ para assertions.

---

### Criar testes unitários para ImportSwaggerService validando orquestração do fluxo de importação

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_20-40-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para ImportSwaggerService validando orquestração do fluxo de importação
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:40:00

## Prompt original

Implementar testes unitários para a classe ImportSwaggerService da camada application. Os testes devem validar a orquestração completa do fluxo de importação: desserialização do JSON via ObjectMapper, delegação da validação ao ValidateSwaggerContentUseCase, persistência via PersistSwaggerSpecUseCase e registro de rotas via DynamicRouteRegistrationUseCase. Cenários: importação bem-sucedida verificando que todos os use cases são chamados na ordem correta; JSON inválido lançando InvalidSwaggerContentException; propagação de exceção quando validação de conteúdo falha sem chamar persistência. Utilizar Mockito com @Mock, @InjectMocks e @Spy para o ObjectMapper.

---

### Criar testes unitários para ValidateFileService validando extensão de arquivo .json

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_20-45-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para ValidateFileService validando extensão de arquivo .json
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:45:00

## Prompt original

Implementar testes unitários para a classe ValidateFileService da camada application. Os testes devem cobrir a lógica de validação de extensão de arquivo: aceitar arquivo com extensão .json, aceitar extensão .JSON (case insensitive), rejeitar extensões inválidas (.xml, .yaml, .txt, .pdf) usando @ParameterizedTest com @ValueSource, e rejeitar arquivo sem extensão. Todas as rejeições devem lançar InvalidExtensionException com mensagem contendo ".json". Utilizar JUnit 5 com testes parametrizados e AssertJ.

---

### Criar testes unitários para GenerateEndpointResponseService cobrindo geração de respostas via IA

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_20-50-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para GenerateEndpointResponseService cobrindo geração de respostas via IA
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:50:00

## Prompt original

Implementar testes unitários para a classe GenerateEndpointResponseService da camada application. Os testes devem cobrir: retorno null quando endpoint não tem respostas, retorno null quando responseSchema é null ou vazio, retorno null quando DynamicResponseBodyBuilder resolve schema como null, envio de prompt à IA e retorno da resposta, remoção de code fences (```json ... ```) da resposta da IA, lançamento de AiCommunicationException quando IA falha, retorno null quando IA responde com string vazia, e priorização de resposta 200 sobre 201. Utilizar Mockito com @Mock para AiPort e DynamicResponseBodyBuilder, e @Spy para ObjectMapper.

---

### Criar testes unitários para CheckAiConnectionService validando verificação de conectividade com IA

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_20-55-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para CheckAiConnectionService validando verificação de conectividade com IA
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:55:00

## Prompt original

Implementar testes unitários para a classe CheckAiConnectionService da camada application. Os testes devem cobrir todos os cenários de verificação de conectividade com o serviço de IA: retornar true quando IA responde com conteúdo válido ("pong"), retornar false quando IA responde com null, retornar false quando IA responde com string vazia, retornar false quando IA responde com string em branco (apenas espaços), e retornar false quando IA lança exceção (RuntimeException). O serviço usa o prompt fixo "ping" para testar a conexão. Utilizar Mockito com @Mock para AiPort.

---

### Criar testes unitários para PersistSwaggerSpecService e GetEndpointsBySpecificationIdService

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-00-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para PersistSwaggerSpecService e GetEndpointsBySpecificationIdService
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:00:00

## Prompt original

Implementar testes unitários para as classes PersistSwaggerSpecService e GetEndpointsBySpecificationIdService da camada application. Para PersistSwaggerSpecService: validar que delega a persistência ao PersistSwaggerSpecPort e retorna o UUID gerado. Para GetEndpointsBySpecificationIdService: validar retorno de endpoints quando specificationId é válido, lançamento de NullPointerException quando specificationId é null (via Objects.requireNonNull), e retorno de lista vazia quando não há endpoints. Utilizar Mockito com @Mock e @InjectMocks para ambos os serviços.

---

### Criar testes unitários para DynamicRouteRegistrationService validando registro e remoção de rotas

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-05-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para DynamicRouteRegistrationService validando registro e remoção de rotas
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:05:00

## Prompt original

Implementar testes unitários para a classe DynamicRouteRegistrationService da camada application. Os testes devem cobrir: cenário de registerRoutes que deve primeiro chamar unregisterAll() no DynamicRouteRegistryPort e depois registerRoutes() com os endpoints obtidos do GetEndpointsBySpecificationIdUseCase; e cenário de unregisterRoutes que deve delegar a chamada ao DynamicRouteRegistryPort.unregisterRoutes(). Verificar a ordem das chamadas e a correta passagem de parâmetros. Utilizar Mockito com @Mock para GetEndpointsBySpecificationIdUseCase e DynamicRouteRegistryPort.

---

### Criar testes unitários para GlobalExceptionHandler validando mapeamento de exceções para HTTP

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-10-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para GlobalExceptionHandler validando mapeamento de exceções para HTTP
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:10:00

## Prompt original

Implementar testes unitários para a classe GlobalExceptionHandler da camada adapter.in.web.handler. Os testes devem validar o mapeamento correto de cada exceção de domínio para o status HTTP correspondente: InvalidExtensionException → HTTP 400 com mensagem contendo ".json"; InvalidSwaggerContentException → HTTP 400 com mensagem da exceção; DatabaseConnectionException → HTTP 503 com mensagem sobre banco de dados; ReferentialIntegrityException → HTTP 409 com mensagem da exceção; PersistenceDeletionException → HTTP 500 com mensagem da exceção; PersistenceFailureException → HTTP 500 com mensagem da exceção. Testar diretamente os métodos do handler sem MockMvc.

---

### Criar testes unitários para AiConnectionController validando respostas de status de conexão

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-15-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para AiConnectionController validando respostas de status de conexão
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:15:00

## Prompt original

Implementar testes unitários para a classe AiConnectionController da camada adapter.in.web. Os testes devem cobrir os dois cenários do endpoint GET /test-ai-connection: retornar HTTP 200 com mensagem "funcional" quando CheckAiConnectionUseCase.checkConnection() retorna true; e retornar HTTP 503 com mensagem "indisponível" quando checkConnection() retorna false. Utilizar Mockito com @Mock para CheckAiConnectionUseCase e @InjectMocks para o controller, testando diretamente o método sem MockMvc.

---

### Criar testes unitários para ImportController validando fluxo de importação via multipart

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-20-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para ImportController validando fluxo de importação via multipart
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:20:00

## Prompt original

Implementar testes unitários para a classe ImportController da camada adapter.in.web. Os testes devem cobrir: importação bem-sucedida retornando HTTP 201 com mensagem "Arquivo importado com sucesso" verificando que ValidateFileUseCase.validate() e ImportSwaggerUseCase.importSpec() são chamados com FileData correto; e propagação de InvalidExtensionException quando a validação de extensão falha. Utilizar MockMultipartFile do Spring Test para simular o upload de arquivo, e Mockito com @Mock para os use cases.

---

### Criar testes unitários para EndpointDefinitionQueryAdapter validando consulta ao repositório

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-25-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para EndpointDefinitionQueryAdapter validando consulta ao repositório
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:25:00

## Prompt original

Implementar testes unitários para a classe EndpointDefinitionQueryAdapter da camada infrastructure.persistence.adapter. Os testes devem validar a delegação correta ao EndpointDefinitionRepository: retorno de endpoints quando o repositório encontra registros para o specificationId fornecido, e retorno de lista vazia quando não há endpoints para a spec. Verificar que o método findAllByApiSpecificationId() do repositório é chamado com o UUID correto. Utilizar Mockito com @Mock para EndpointDefinitionRepository e @InjectMocks para o adapter.

---

### Criar testes unitários para SwaggerSpecDeletionAdapter validando ordem de deleção e tratamento de erros

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-30-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para SwaggerSpecDeletionAdapter validando ordem de deleção e tratamento de erros
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:30:00

## Prompt original

Implementar testes unitários para a classe SwaggerSpecDeletionAdapter da camada infrastructure.persistence.adapter. Os testes devem cobrir: verificação da ordem correta de deleção usando Mockito InOrder (apiSpecificationRepository.deleteAll() → entityManager.flush() → tagRepository.deleteAllInBatch() → entityManager.flush()); lançamento de PersistenceDeletionException quando deleteAll() falha com DataAccessException; e lançamento de PersistenceDeletionException quando flush() falha. Utilizar Mockito com @Mock para ApiSpecificationRepository, TagRepository e EntityManager, e doThrow() com QueryTimeoutException para simular falhas.

---

### Criar testes unitários para AiGateway validando validações de entrada e tratamento de erros

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-35-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para AiGateway validando validações de entrada e tratamento de erros
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:35:00

## Prompt original

Implementar testes unitários para a classe AiGateway da camada infrastructure.ai.gateway. Os testes devem cobrir as validações de entrada e tratamento de erros: lançar IllegalArgumentException quando prompt é null, vazio ou whitespace; lançar AiCommunicationException quando API key não está configurada (vazia ou null); e lançar AiCommunicationException com mensagem "Erro inesperado" quando ocorre RuntimeException durante a chamada ao ChatClient. Utilizar mocks manuais (Mockito.mock()) para ChatClient.Builder e ChatClient, instanciando o AiGateway diretamente no teste com diferentes valores de API key.

---

### Criar testes unitários para DynamicResponseBodyBuilder validando construção de payloads de resposta

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-40-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para DynamicResponseBodyBuilder validando construção de payloads de resposta
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:40:00

## Prompt original

Implementar testes unitários para a classe DynamicResponseBodyBuilder da camada adapter.in.web.dynamic. Os testes devem cobrir: retorno null para schema null, vazio e JSON inválido; construção de objeto simples com propriedades string e integer; construção de array com 3 itens por padrão; resolução de $ref local quando componentsJson é fornecido; geração de valor boolean; geração de valor number; uso do primeiro valor de enum; e resolução de allOf mesclando propriedades de múltiplos schemas. Instanciar o builder diretamente com ObjectMapper real (sem mocks) para testar a lógica de construção de payloads.

---

### Criar testes unitários para HttpMethodMapper validando mapeamento de métodos HTTP

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-45-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para HttpMethodMapper validando mapeamento de métodos HTTP
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:45:00

## Prompt original

Implementar testes unitários para a classe utilitária HttpMethodMapper da camada application.util. Os testes devem cobrir: mapeamento correto de todos os métodos HTTP suportados (GET, POST, PUT, DELETE, PATCH) para o enum RequestMethod do Spring MVC, incluindo variações de case (minúsculo, maiúsculo) e com espaços usando @ParameterizedTest com @CsvSource; lançamento de IllegalArgumentException para método null; lançamento de IllegalArgumentException para método vazio; e lançamento de IllegalArgumentException com mensagem "Unsupported HTTP method" para método não suportado como OPTIONS.

---

### Executar todos os testes unitários e validar que compilam e passam sem erros

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-50-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Executar todos os testes unitários e validar que compilam e passam sem erros
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:50:00

## Prompt original

Executar todos os testes unitários criados para o projeto MockAI usando Maven (mvnw test) e validar que todos compilam corretamente e passam sem falhas. Verificar que não há erros de compilação relacionados a imports, tipos ou APIs incompatíveis. Corrigir qualquer problema encontrado, como incompatibilidades com a versão do Spring AI 2.0.0-M6 (por exemplo, classes como CallPromptResponseSpec que não existem nessa versão). Garantir que os 91 testes unitários passem com 0 falhas e 0 erros.

---

### Validar cobertura de código com JaCoCo e confirmar que atinge mínimo de 40%

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_21-55-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Validar cobertura de código com JaCoCo e confirmar que atinge mínimo de 40%
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:55:00

## Prompt original

Executar a fase verify do Maven (mvnw verify) para validar que a cobertura de código medida pelo JaCoCo atinge o mínimo de 40% configurado na regra do plugin. Verificar o relatório HTML gerado em target/site/jacoco/index.html e confirmar a cobertura por pacote. O resultado esperado é "All coverage checks have been met" com cobertura total de aproximadamente 49% de instruções. Documentar a cobertura alcançada por cada pacote do projeto.

---

### Gerar relatório completo de testes unitários documentando toda a implementação

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_22-00-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Gerar relatório completo de testes unitários documentando toda a implementação
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 22:00:00

## Prompt original

Gerar um relatório completo em Markdown (docs/relatorio-testes-unitarios.md) documentando toda a implementação de testes unitários do projeto MockAI. O relatório deve conter: configuração de infraestrutura de testes (dependências e plugin JaCoCo), tabela detalhada de cada classe testada organizada por camada (Application, API, Infrastructure, Utilitários) com quantidade de testes e cenários cobertos, cobertura por pacote segundo o JaCoCo, padrões de teste utilizados (JUnit 5, Mockito, AssertJ), comandos para execução e geração de relatório, e observações sobre limitações e testes de integração pré-existentes.

---

### Criar testes unitários para os principais fluxos do projeto MockAI garantindo cobertura mínima de 40%

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_22-05-00_dariel-verdecia-verdecia.md` |
| Data | 2026-05-21 |

Prompt: Criar testes unitários para os principais fluxos do projeto MockAI garantindo cobertura mínima de 40%
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 22:05:00

## Prompt original

Criar testes unitários para os principais fluxos do projeto MockAI, garantindo cobertura mínima de 40% do código total. Checklist técnico: Configurar dependências de teste (Mockito + JUnit) no pom.xml se necessário. Implementar testes unitários para os casos de uso (camada application). Implementar testes unitários para os adapters de entrada (camada api). Implementar testes unitários para os adapters de saída (camada infrastructure). Garantir cobertura mínima de 40% do código total. Validar cobertura com relatório (ex: JaCoCo). Implementar tudo por etapas para não estragar nada.

---

### Adicionar referência ao arquivo de consolidação de prompts na seção de documentação adicional do README

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-21_22-55-58_joaopuel.md` |
| Data | 2026-05-21 |

Prompt: Adicionar referência ao arquivo de consolidação de prompts na seção de documentação adicional do README
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-21 22:55:58

## Prompt original

c:\git\mini-projeto-MockAi\docs\prompts.mdAdicione a referência ao arquivo de prompts na seção de documentação adicional do readme

---

### Criar diagramas UML (sequência e atividades) dos fluxos de importação Swagger e resposta por IA

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-22_14-00-46_joaopuel.md` |
| Data | 2026-05-22 |

Prompt: Criar diagramas UML (sequência e atividades) dos fluxos de importação Swagger e resposta por IA
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-22 14:00:46

## Prompt original

#InstruçãoCrie diagramas UML do projeto#Detalhes1. Crie um arquivo .md de diagramas UML do projeto.2. Crie 2 diagramas UML: Diagrama de sequência e diagrama de atividades.#Diagrama de sequência1. Descreva a sequência de interações da funcionalide de importação do arquivo swagger e da funcionalidade de obtenção de resposta por IA2. Mostre a ordem das chamadas entre componentes3. Evidencia responsabilidades em cada etapa4. Exiba integrações entre backend e serviço externo Groq#Diagrama de atividades1. Modele o fluxo de execução do processo de impotação de arquivo swagger e processo de obtenção de resposta por IA2. Represente decisões, desvios e ramificações relevantes3. Evidencie início, término e caminhos alternativos

---

### Simplificar diagramas de sequência UML para mostrar apenas Usuário, Backend, H2 e Groq

| Campo | Valor |
|-------|-------|
| Arquivo | `docs/prompts/2026-05-22_14-05-24_joaopuel.md` |
| Data | 2026-05-22 |

Prompt: Simplificar diagramas de sequência UML para mostrar apenas Usuário, Backend, H2 e Groq
Responsável: joaopuel
Usuário: joaopuel
Data/hora: 2026-05-22 14:05:24

## Prompt original

Atualize o diagrama UML de sequência, considere que os componentes são apenas a entrada do Usuário, o serviço backend, o database H2 e o serviço externo groq. Não é necessário descrever o fluxo entre as camadas/componentes internos do projeto.

---

