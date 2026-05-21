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

## Documentação adicional

Consultar a pasta `docs/`.

- [PRD — Product Requirements Document](docs/PRD.md)
- [Diagrama de Arquitetura C4](docs/architecture-diagram.md)
- [Schema do Banco de Dados](docs/database-schema.md)
- [Swagger UI](http://localhost:8080/mockai/swagger-ui.html) — disponível com a aplicação rodando
- [Guia de Contribuição](CONTRIBUTING.md)

## Integrantes

- Daniel Rodrigues da Silva
- Dariel Verdecia Verdecia
- João Ricardo Tasca Puel
- Luiz Fernando Amaral
- Welton Sabino