Prompt: Criar endpoint POST /import para validar extensão de arquivo JSON e retornar status adequado
Responsável: Daniel Rodrigues da Silva
Usuário: daniel-rodrigues-da-silva
Data/hora: 2026-05-16 01:03:50

## Prompt original

Crie o endpoint POST /import capaz de receber um array de binário e validar a extensão do arquivo.Se a extensão for json, retornar a mensagem "Arquivo importado com sucesso" com status code 201.Se a extensão não for json, retornar a mensagem "Arquivo com extensão inválida, deve ser .json" e status code 400.Não implementar leitura do conteúdo do arquivo.Não implementar persistência em anco de dados.Não implementar testes automatizados.
