Prompt: Criar testes unitários para ValidateFileService validando extensão de arquivo .json
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:45:00

## Prompt original

Implementar testes unitários para a classe ValidateFileService da camada application. Os testes devem cobrir a lógica de validação de extensão de arquivo: aceitar arquivo com extensão .json, aceitar extensão .JSON (case insensitive), rejeitar extensões inválidas (.xml, .yaml, .txt, .pdf) usando @ParameterizedTest com @ValueSource, e rejeitar arquivo sem extensão. Todas as rejeições devem lançar InvalidExtensionException com mensagem contendo ".json". Utilizar JUnit 5 com testes parametrizados e AssertJ.
