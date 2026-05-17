Prompt: Etapa 4 — Tratamento explícito de erros de persistência com mensagens descritivas
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:48:30

## Prompt original

Etapa 4 — Tratamento explícito de errosImplementar o tratamento explícito de todas as falhas possíveis durante o processo de persistência, retornando mensagens descritivas para cada cenário.O que precisa ser feito:Capturar DataAccessException — falha de conexão com o bancoCapturar ConstraintViolationException — violação de integridade referencialCapturar falhas específicas da deleção de dados antigosCapturar falha genérica de persistência retornando detalhe do erroCada exceção deve gerar uma mensagem descritiva e clara do que falhouMensagens a implementar:"Falha de conexão com o banco de dados""Violação de integridade referencial: {detalhe}""Falha ao deletar dados anteriores: {detalhe}""Falha ao persistir os dados: {detalhe}"Restrições:Toda implementação deve estar em conformidade com o arquivo de steering do projetoNão alterar as mensagens já existentes da task #58
