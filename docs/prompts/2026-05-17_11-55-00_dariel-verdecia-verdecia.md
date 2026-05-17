Prompt: Implementar handler padrão que retorna payload baseado no responseSchema da especificação
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-17 16:10:00

## Prompt original
Objetivo: implementar a integração entre importação de especificações OpenAPI e o registro dinâmico de rotas em tempo de execução, incluindo um teste de integração que valide:

- importação de arquivo OpenAPI via endpoint `/import`
- registro de rota dinâmica a partir da especificação persistida
- resposta conforme schema JSON definido na spec
- recriação correta da rota após nova importação da mesma especificação

