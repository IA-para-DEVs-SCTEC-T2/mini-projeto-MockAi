Prompt: Implementar a consulta de endpoints persistidos por ID de especificação
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-17 15:00:00

## Prompt original

Antes de iniciar a implementação, leia os arquivos em `.kiro/steering/` e `.kiro/specs/` para entender as convenções do projeto.

Implemente a primeira etapa desta feature:
- Crie o port de entrada para buscar endpoints por ID de especificação.
- Crie o port de saída que abstrai a consulta de persistência.
- Implemente o serviço na camada `application.service`.
- Crie o adapter de persistência em `infrastructure.persistence.adapter` usando `EndpointDefinitionRepository`.
- Adicione o método de consulta no repositório Spring Data JPA.

Use o estilo de camadas e convenções do projeto, mantendo o domínio e a aplicação desacoplados da infraestrutura.
