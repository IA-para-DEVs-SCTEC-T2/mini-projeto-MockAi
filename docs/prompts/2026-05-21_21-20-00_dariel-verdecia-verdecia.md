Prompt: Criar testes unitários para ImportController validando fluxo de importação via multipart
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:20:00

## Prompt original

Implementar testes unitários para a classe ImportController da camada adapter.in.web. Os testes devem cobrir: importação bem-sucedida retornando HTTP 201 com mensagem "Arquivo importado com sucesso" verificando que ValidateFileUseCase.validate() e ImportSwaggerUseCase.importSpec() são chamados com FileData correto; e propagação de InvalidExtensionException quando a validação de extensão falha. Utilizar MockMultipartFile do Spring Test para simular o upload de arquivo, e Mockito com @Mock para os use cases.
