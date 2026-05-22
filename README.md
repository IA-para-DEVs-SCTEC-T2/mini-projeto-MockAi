# MockAI

## Objetivo

Simular o funcionamento real de uma API REST a partir de sua documentação Swagger/OpenAPI, disponibilizando endpoints e payloads prontos para consumo imediato — sem dependência do serviço real.

## Visão geral

Desenvolvedores que precisam implementar o consumo de uma API REST frequentemente ficam bloqueados quando o serviço ainda não está disponível. O MockAI resolve isso: receba uma spec OpenAPI (JSON), e os endpoints ficam disponíveis na hora, com respostas geradas dinamicamente por IA (Groq).

## Funcionalidades

- Importação de spec Swagger/OpenAPI 3.0+ via `POST /import`
- Criação automática de endpoints mockados (registro dinâmico, sem reinicialização)
- Geração de payloads realistas com IA (Groq), com fallback estático
- Listagem dos endpoints mockados ativos via `GET /endpoints`
- Verificação de conectividade com a IA via `GET /test-ai-connection`

## Pré-requisitos

- Java 17
- Maven 3.9.7+
- Chave de API do Groq (obtenha em [console.groq.com/keys](https://console.groq.com/keys))

## Como instalar

```bash
git clone https://github.com/IA-para-DEVs-SCTEC-T2/mini-projeto-MockAi.git
cd mini-projeto-MockAi
cp .env.example .env
# Edite .env e defina GROQ_API_KEY=sua_chave_aqui
# Obtenha sua chave em: https://console.groq.com/keys
mvn clean compile
```

## Como executar localmente

```bash
mvn spring-boot:run
```

## Estrutura de pastas

```text
.
├── docs/
├── scripts/
├── src/
│   └── main/java/com/ia/para/devs/mockai/
│       ├── adapter/in/web/     # Controllers, DTOs, roteamento dinâmico
│       ├── application/        # Casos de uso e serviços
│       ├── domain/             # Modelos, ports e exceções
│       └── infrastructure/     # JPA, gateway de IA (Groq)
├── CONTRIBUTING.md
└── README.md
```

## Fluxo de desenvolvimento

```text
1. Escolher uma issue no board do GitHub
2. Usar o hook /commit-modifications → informar o número da issue → cria a branch feature/task<N> e realiza o commit seguindo a convenção de commits
3. Usar o hook /push-modifications → realiza o push e abre o PR
4. Revisão do PR e merge manual
```

>> [Board do projeto no GitHub](https://github.com/orgs/IA-para-DEVs-SCTEC-T2/projects/6/views/1)

## Convenção de commits

```text
feat: nova funcionalidade
fix: correção
docs: documentação
chore: configuração
refactor: refatoração
test: testes
```

## Teste você mesmo

A coleção do Postman e os exemplos de Swagger estão prontos para uso imediato.

**Coleção Postman:** [`docs/MockAi.postman_collection.json`](docs/MockAi.postman_collection.json)

Importe o arquivo no Postman. A coleção contém:

| Grupo | Descrição |
|-------|-----------|
| `import` | `POST /import` — envia um arquivo Swagger para criar os endpoints mockados |
| `endpoints` | `GET /endpoints` — lista todos os endpoints mockados ativos |
| `test-ai-connection` | `GET /test-ai-connection` — verifica conectividade com o Groq |
| `Pet swagger` | Endpoints mockados gerados a partir do `petstore.json` |
| `Company manager swagger` | Endpoints mockados gerados a partir do `company-manager.json` |

**Exemplos de Swagger disponíveis em [`docs/swagger-examples/`](docs/swagger-examples/):**

| Arquivo | Descrição |
|---------|-----------|
| [`petstore.json`](docs/swagger-examples/petstore.json) | API de petstore — exemplo clássico OpenAPI com endpoints de pets |
| [`company-manager.json`](docs/swagger-examples/company-manager.json) | API de gerenciamento de empresas e proprietários |

**Fluxo rápido:**
1. Suba a aplicação: `mvn spring-boot:run`
2. No Postman, execute `import` apontando para um dos arquivos acima
3. Execute `endpoints` para ver as rotas criadas
4. Teste os endpoints mockados — as respostas são geradas dinamicamente pela IA

---

## Documentação adicional

Consultar a pasta `docs/`.

- [PRD — Product Requirements Document](docs/PRD.md)
- [Diagrama de Arquitetura C4](docs/architecture-diagram.md)
- [Diagramas UML — Sequência e Atividades](docs/uml-diagrams.md)
- [Schema do Banco de Dados](docs/database-schema.md)
- [Apresentação do projeto](docs/apresentacao/MockAI.pptx)
- [Propostas de telas (apenas avaliativo)](docs/propostas_de_telas_apenas_avaliativo/Prompts_telas_figma.md)
- [Swagger UI](http://localhost:8080/mockai/swagger-ui.html) — disponível com a aplicação rodando
- [Consolidação de Prompts](docs/prompts.md) — histórico de todas as solicitações feitas ao Kiro
- [Guia de Contribuição](CONTRIBUTING.md)

## Integrantes

- Daniel Rodrigues da Silva
- Dariel Verdecia Verdecia
- João Ricardo Tasca Puel
- Luiz Fernando Amaral
- Welton Sabino