Prompt: Etapa 1 — Mapeamento dos campos Swagger desserializados para entidades JPA do projeto MockAI
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:38:59

## Prompt original

Etapa 1 — Mapeamento das entidades:Com base no endpoint POST /import da task #58 e na modelagem do banco de dados do projeto, identificar e mapear todos os campos da especificação Swagger desserializada para as entidades JPA correspondentes.O que precisa ser feito:Analisar o objeto Java gerado pela desserialização Jackson da task #58Analisar as entidades JPA já existentes no projetoCriar um mapeamento explícito de qual campo da spec Swagger corresponde a qual campo de qual entidadeIdentificar a ordem correta de persistência respeitando as dependências entre entidades (pai → filho)Documentar os relacionamentos encontradosRestrições:Não alterar nenhum código existente nesta etapa, apenas análise e documentaçãoToda implementação deve estar em conformidade com o arquivo de steering do projeto
