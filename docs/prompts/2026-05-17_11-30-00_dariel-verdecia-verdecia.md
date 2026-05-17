Prompt: Implementar utilitário de mapeamento de métodos HTTP e integrá-lo ao registro de rotas dinâmicas
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-17 15:30:00

## Prompt original

Antes de iniciar a implementação, leia os arquivos em `.kiro/steering/` e `.kiro/specs/` para entender as convenções do projeto.

Implemente a terceira etapa desta feature:
- Crie um utilitário de mapeamento de métodos HTTP (`GET`, `POST`, `PUT`, `DELETE`, `PATCH`).
- Garanta que o utilitário normalize métodos em maiúsculas e lance erro legível quando receber métodos não suportados.
- Integre o utilitário ao serviço de registro dinâmico de rotas criado na etapa 2.
- Ajuste a implementação do adapter Spring MVC para usar o utilitário ao construir `RequestMappingInfo`.

Mantenha a arquitetura em camadas e a separação entre domínio, aplicação e infraestrutura.
