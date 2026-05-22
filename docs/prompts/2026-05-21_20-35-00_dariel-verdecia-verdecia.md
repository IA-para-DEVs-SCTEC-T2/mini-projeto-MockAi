Prompt: Criar testes unitários para ValidateSwaggerContentService cobrindo todos os campos obrigatórios
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:35:00

## Prompt original

Implementar testes unitários para a classe ValidateSwaggerContentService da camada application. Os testes devem cobrir todos os cenários de validação de campos obrigatórios de uma especificação OpenAPI: spec válida com todos os campos, campo openapi null, campo openapi em branco, bloco info null, info.title null, info.description null, paths null, paths vazio, path sem métodos HTTP definidos, método sem responses definidas, e acúmulo de múltiplos erros em uma única exceção InvalidSwaggerContentException. Utilizar JUnit 5 com @DisplayName e AssertJ para assertions.
