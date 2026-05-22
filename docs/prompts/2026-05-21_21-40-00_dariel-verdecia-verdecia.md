Prompt: Criar testes unitários para DynamicResponseBodyBuilder validando construção de payloads de resposta
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:40:00

## Prompt original

Implementar testes unitários para a classe DynamicResponseBodyBuilder da camada adapter.in.web.dynamic. Os testes devem cobrir: retorno null para schema null, vazio e JSON inválido; construção de objeto simples com propriedades string e integer; construção de array com 3 itens por padrão; resolução de $ref local quando componentsJson é fornecido; geração de valor boolean; geração de valor number; uso do primeiro valor de enum; e resolução de allOf mesclando propriedades de múltiplos schemas. Instanciar o builder diretamente com ObjectMapper real (sem mocks) para testar a lógica de construção de payloads.
