Prompt: Criar testes unitários para GenerateEndpointResponseService cobrindo geração de respostas via IA
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:50:00

## Prompt original

Implementar testes unitários para a classe GenerateEndpointResponseService da camada application. Os testes devem cobrir: retorno null quando endpoint não tem respostas, retorno null quando responseSchema é null ou vazio, retorno null quando DynamicResponseBodyBuilder resolve schema como null, envio de prompt à IA e retorno da resposta, remoção de code fences (```json ... ```) da resposta da IA, lançamento de AiCommunicationException quando IA falha, retorno null quando IA responde com string vazia, e priorização de resposta 200 sobre 201. Utilizar Mockito com @Mock para AiPort e DynamicResponseBodyBuilder, e @Spy para ObjectMapper.
