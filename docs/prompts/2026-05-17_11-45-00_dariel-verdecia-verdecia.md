Prompt: Implementar handler padrão que retorna payload baseado no responseSchema da especificação
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-17 15:45:00

## Prompt original

Antes de iniciar a implementação, leia os arquivos em `.kiro/steering/` e `.kiro/specs/` para entender as convenções do projeto.

Implemente a quarta etapa desta feature:
- Crie um construtor de payloads que transforma o JSON do `responseSchema` em um corpo de resposta de exemplo.
- Atualize o handler genérico de rotas dinâmicas para resolver a definição de endpoint pelo padrão da rota e método HTTP.
- Faça o handler retornar o status code, o content type e o corpo de exemplo compatíveis com a resposta persistida.
- Garanta suporte a `200`, `201`, `204` e escolha um fallback razoável quando houver múltiplas respostas.

Mantenha a arquitetura em camadas e siga o padrão de implementação que já existe no projeto.
