Prompt: Etapa 3 — Implementar deleção dos dados antigos com rollback transacional completo
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:45:24

## Prompt original

Etapa 3 — Implementação da deleção e rollback:Implementar a lógica de deleção dos dados antigos antes de persistir os novos, garantindo rollback completo em caso de falha.O que precisa ser feito:Implementar a deleção dos dados existentes antes de inserir os novos dentro do mesmo @TransactionalGarantir que se a deleção falhar, o processo inteiro seja abortado e nenhum dado novo seja inseridoGarantir que não existam dados antigos misturados com dados novos em nenhum cenárioO rollback deve ser automático via @TransactionalComportamento esperado:CenárioResultadoDeleção ok + Inserção okDados novos persistidos corretamenteDeleção falhaRollback completo, dados antigos preservadosInserção falhaRollback completo, banco volta ao estado anteriorRestrições:Toda implementação deve estar em conformidade com o arquivo de steering do projetoNão alterar o serviço de persistência da etapa anterior, apenas adicionar a lógica de deleção
