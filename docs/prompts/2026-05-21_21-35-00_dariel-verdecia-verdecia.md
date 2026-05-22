Prompt: Criar testes unitários para AiGateway validando validações de entrada e tratamento de erros
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:35:00

## Prompt original

Implementar testes unitários para a classe AiGateway da camada infrastructure.ai.gateway. Os testes devem cobrir as validações de entrada e tratamento de erros: lançar IllegalArgumentException quando prompt é null, vazio ou whitespace; lançar AiCommunicationException quando API key não está configurada (vazia ou null); e lançar AiCommunicationException com mensagem "Erro inesperado" quando ocorre RuntimeException durante a chamada ao ChatClient. Utilizar mocks manuais (Mockito.mock()) para ChatClient.Builder e ChatClient, instanciando o AiGateway diretamente no teste com diferentes valores de API key.
