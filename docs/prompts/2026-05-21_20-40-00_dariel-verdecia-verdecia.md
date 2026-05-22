Prompt: Criar testes unitários para ImportSwaggerService validando orquestração do fluxo de importação
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:40:00

## Prompt original

Implementar testes unitários para a classe ImportSwaggerService da camada application. Os testes devem validar a orquestração completa do fluxo de importação: desserialização do JSON via ObjectMapper, delegação da validação ao ValidateSwaggerContentUseCase, persistência via PersistSwaggerSpecUseCase e registro de rotas via DynamicRouteRegistrationUseCase. Cenários: importação bem-sucedida verificando que todos os use cases são chamados na ordem correta; JSON inválido lançando InvalidSwaggerContentException; propagação de exceção quando validação de conteúdo falha sem chamar persistência. Utilizar Mockito com @Mock, @InjectMocks e @Spy para o ObjectMapper.
