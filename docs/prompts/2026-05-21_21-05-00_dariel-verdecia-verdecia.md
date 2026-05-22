Prompt: Criar testes unitários para DynamicRouteRegistrationService validando registro e remoção de rotas
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:05:00

## Prompt original

Implementar testes unitários para a classe DynamicRouteRegistrationService da camada application. Os testes devem cobrir: cenário de registerRoutes que deve primeiro chamar unregisterAll() no DynamicRouteRegistryPort e depois registerRoutes() com os endpoints obtidos do GetEndpointsBySpecificationIdUseCase; e cenário de unregisterRoutes que deve delegar a chamada ao DynamicRouteRegistryPort.unregisterRoutes(). Verificar a ordem das chamadas e a correta passagem de parâmetros. Utilizar Mockito com @Mock para GetEndpointsBySpecificationIdUseCase e DynamicRouteRegistryPort.
