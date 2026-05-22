Prompt: Criar testes unitários para CheckAiConnectionService validando verificação de conectividade com IA
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:55:00

## Prompt original

Implementar testes unitários para a classe CheckAiConnectionService da camada application. Os testes devem cobrir todos os cenários de verificação de conectividade com o serviço de IA: retornar true quando IA responde com conteúdo válido ("pong"), retornar false quando IA responde com null, retornar false quando IA responde com string vazia, retornar false quando IA responde com string em branco (apenas espaços), e retornar false quando IA lança exceção (RuntimeException). O serviço usa o prompt fixo "ping" para testar a conexão. Utilizar Mockito com @Mock para AiPort.
