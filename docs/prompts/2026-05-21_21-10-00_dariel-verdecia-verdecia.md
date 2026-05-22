Prompt: Criar testes unitários para GlobalExceptionHandler validando mapeamento de exceções para HTTP
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:10:00

## Prompt original

Implementar testes unitários para a classe GlobalExceptionHandler da camada adapter.in.web.handler. Os testes devem validar o mapeamento correto de cada exceção de domínio para o status HTTP correspondente: InvalidExtensionException → HTTP 400 com mensagem contendo ".json"; InvalidSwaggerContentException → HTTP 400 com mensagem da exceção; DatabaseConnectionException → HTTP 503 com mensagem sobre banco de dados; ReferentialIntegrityException → HTTP 409 com mensagem da exceção; PersistenceDeletionException → HTTP 500 com mensagem da exceção; PersistenceFailureException → HTTP 500 com mensagem da exceção. Testar diretamente os métodos do handler sem MockMvc.
