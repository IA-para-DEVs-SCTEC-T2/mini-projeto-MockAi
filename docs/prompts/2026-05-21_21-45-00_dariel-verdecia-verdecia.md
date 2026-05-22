Prompt: Criar testes unitários para HttpMethodMapper validando mapeamento de métodos HTTP
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:45:00

## Prompt original

Implementar testes unitários para a classe utilitária HttpMethodMapper da camada application.util. Os testes devem cobrir: mapeamento correto de todos os métodos HTTP suportados (GET, POST, PUT, DELETE, PATCH) para o enum RequestMethod do Spring MVC, incluindo variações de case (minúsculo, maiúsculo) e com espaços usando @ParameterizedTest com @CsvSource; lançamento de IllegalArgumentException para método null; lançamento de IllegalArgumentException para método vazio; e lançamento de IllegalArgumentException com mensagem "Unsupported HTTP method" para método não suportado como OPTIONS.
