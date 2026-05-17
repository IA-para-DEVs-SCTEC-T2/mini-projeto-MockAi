Prompt: Implementar registro dinâmico de rotas em tempo de execução
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-17 15:15:00

## Prompt original

Antes de iniciar a implementação, leia os arquivos em `.kiro/steering/` e `.kiro/specs/` para entender as convenções do projeto.

Implemente a segunda etapa desta feature:
- Crie o use case `DynamicRouteRegistrationUseCase` para registrar rotas dinâmicas.
- Crie o port de saída `DynamicRouteRegistryPort` para a integração com o framework web.
- Implemente `DynamicRouteRegistrationService` na camada `application.service`.
- Crie um adapter Spring MVC que registra mapeamentos em tempo de execução usando `RequestMappingHandlerMapping`.
- Crie um handler genérico para as rotas dinâmicas.

Mantenha a separação de camadas do projeto e siga as convenções Spring Boot 4 usadas no código.
