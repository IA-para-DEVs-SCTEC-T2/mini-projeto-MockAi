Prompt: Resolver referência $ref nos schemas de resposta dos endpoints dinâmicos para gerar mock real
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-17 12:19:34

## Prompt original

Resolver a referência $ref nos schemas de resposta dos endpoints dinâmicos. Atualmente o endpoint retorna o $ref bruto (ex: { "$ref": "#/components/schemas/Animal" }). O comportamento esperado é que o sistema resolva essa referência na spec completa armazenada no banco de dados, gere um mock com valores reais baseados nos properties e type de cada campo, e retorne esse objeto como resposta do endpoint dinâmico.
