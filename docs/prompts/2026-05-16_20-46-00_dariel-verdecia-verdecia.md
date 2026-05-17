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
