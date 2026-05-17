Prompt: Etapa 5 — Integrar serviço de persistência ao endpoint POST /import da task #58
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-16 19:55:45

## Prompt original

Etapa 5 — Integração com o endpoint da task #58Integrar o serviço de persistência implementado nas etapas anteriores ao endpoint POST /import da task #58, mantendo todos os comportamentos e mensagens já existentes.O que precisa ser feito:Injetar o serviço de persistência no controller/service da task #58Chamar o serviço de persistência após a desserialização bem-sucedida do arquivoGarantir que as mensagens já existentes da task #58 sejam mantidasAdicionar as novas mensagens de erro de persistência ao fluxoFluxo final esperado:Receber binário     ↓ Desserializar com Jackson (task #58 — sem alteração)     ↓ [Desserialização falha] → Mensagens de erro já existentes da task #58     ↓ [Desserialização ok] → Tentar deletar dados antigos + persistir novos     ↓                              ↓ [Persistência ok]            [Persistência falha] Mensagens da task #58        Rollback + mensagem + "Arquivo importado         descritiva do erro com sucesso"Restrições:Toda implementação deve estar em conformidade com o arquivo de steering do projetoNão alterar o contrato do endpoint /importNão remover nem modificar nenhuma mensagem ou comportamento da task #58
