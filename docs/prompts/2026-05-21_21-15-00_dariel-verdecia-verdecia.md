Prompt: Criar testes unitários para AiConnectionController validando respostas de status de conexão
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:15:00

## Prompt original

Implementar testes unitários para a classe AiConnectionController da camada adapter.in.web. Os testes devem cobrir os dois cenários do endpoint GET /test-ai-connection: retornar HTTP 200 com mensagem "funcional" quando CheckAiConnectionUseCase.checkConnection() retorna true; e retornar HTTP 503 com mensagem "indisponível" quando checkConnection() retorna false. Utilizar Mockito com @Mock para CheckAiConnectionUseCase e @InjectMocks para o controller, testando diretamente o método sem MockMvc.
