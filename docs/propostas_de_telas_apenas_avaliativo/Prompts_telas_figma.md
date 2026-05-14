> ⚠️ **NOTA IMPORTANTE**
> As telas descritas neste documento **não serão implementadas** na aplicação.
> O MockAI é uma API backend e **não possui interface gráfica**.
> Os prompts e prints foram criados exclusivamente para **fins avaliativos** solicitados pelo professor Wanderson.

---

# Prompts de Telas — MockAI (Figma)

---

## Prompt base

Crie uma interface web moderna em tema dark para um sistema chamado MockAI. O design deve seguir um estilo futurista, minimalista e profissional, inspirado em ferramentas modernas de desenvolvimento e plataformas SaaS premium.

OBJETIVO:
Criar uma tela moderna, elegante e tecnológica para gerenciamento de APIs mockadas, projetos, logs, autenticação, configurações e funcionalidades administrativas.

ESTILO VISUAL:
- Tema dark premium
- Fundo principal escuro (#070B14 ou similar)
- Bordas suaves em azul neon
- Glow discreto azul
- Sombras leves e elegantes
- Aparência clean e tecnológica
- Layout responsivo
- Espaçamento confortável entre elementos
- Tipografia moderna e minimalista
- Componentes com aparência SaaS profissional
- Design inspirado em:
  - Vercel
  - GitHub Dark
  - Postman
  - Insomnia
  - Linear
  - Supabase

PALETA DE CORES:
- Fundo principal: azul/preto muito escuro
- Elementos ativos: azul claro neon
- Hover: azul translúcido suave
- Texto principal: branco suave
- Texto secundário: cinza claro
- Bordas: azul escuro translúcido

COMPONENTES:
Todos os componentes devem seguir o mesmo padrão visual:
- Inputs modernos com fundo dark translúcido
- Bordas arredondadas médias/grandes
- Hover elegante
- Glow discreto em foco
- Botões minimalistas
- Cards escuros com bordas suaves
- Modais modernos
- Scrollbar customizada dark
- Ícones minimalistas
- Badges modernas
- Tabelas elegantes
- Sidebar premium
- Tabs modernas
- Dropdowns sofisticados

BOTÕES:
- Primário:
  Azul claro vibrante com leve glow
- Secundário:
  Fundo escuro com borda suave
- Hover suave e moderno

INPUTS:
- Fundo azul/preto translúcido
- Borda azul escura
- Placeholder discreto
- Glow azul ao focar
- Altura confortável

CARDS E MODAIS:
- Fundo escuro elegante
- Bordas arredondadas
- Borda azul suave
- Sombras discretas
- Aparência premium

HEADER E NAVEGAÇÃO:
- Barra superior moderna
- Navegação clean
- Ícones minimalistas
- Menus sofisticados
- Branding discreto do MockAI

EXPERIÊNCIA:
- Aparência profissional real
- Sensação de produto enterprise
- UX moderna e fluida
- Visual tecnológico e premium
- Interface pronta para produção

IMPORTANTE:
Toda nova tela criada deve seguir EXATAMENTE esse padrão visual, mantendo consistência entre:
- cores
- espaçamentos
- bordas
- glow
- tipografia
- componentes
- interações
- aparência SaaS premium

RESULTADO ESPERADO:
A interface deve parecer um produto real, moderno e profissional chamado MockAI, focado em gerenciamento de APIs mockadas e ferramentas administrativas para desenvolvedores.

---

## Tela Login

Crie uma interface web moderna em tema dark para um sistema chamado MockAI. A tela deve representar uma área de login administrativo com aparência futurista, minimalista e profissional, seguindo o padrão visual de dashboards modernos de desenvolvimento/API.

Objetivo da tela:
Tela de acesso administrativo para gerenciamento de endpoints mockados e projetos.

Visual geral:
- Fundo escuro (#070B14 ou similar)
- Card centralizado vertical e horizontalmente
- Bordas arredondadas grandes
- Borda azul neon suave
- Sombras discretas
- Pequenos efeitos glow azul
- Layout responsivo
- Tipografia moderna e elegante
- UX clean e premium
- Estilo inspirado em Vercel, GitHub Dark, Insomnia e Postman

Branding:
Adicionar o nome "MockAI" no topo da aplicação ou discretamente dentro do card.

Estrutura do card:
Título principal:
"Acesso administrativo"

Subtítulo:
"Entre para gerenciar endpoints e projetos."

Campos do formulário:
- Input "Usuario"
  Valor preenchido: "admin"

- Input "Senha"
  Campo password preenchido com bullets/senha mascarada

Botão principal:
- Botão grande ocupando toda a largura
- Cor azul clara vibrante
- Texto:
"Acessar painel"

Texto auxiliar no rodapé:
"Dica: apos o login, use o menu superior para atualizar dados, importar Mockoon e criar novos projetos."

Detalhes visuais:
- Inputs com fundo azul-escuro translúcido
- Bordas suaves em azul
- Hover moderno nos inputs e botão
- Placeholder discreto
- Espaçamento confortável entre os elementos
- Botão com leve glow azul
- Fontes clean e modernas
- Sensação de ferramenta SaaS profissional

Resultado esperado:
A interface deve parecer uma tela real de login administrativo de um sistema moderno chamado MockAI para gerenciamento de APIs mockadas.

---

## Tela Novo Endpoint

Crie uma interface web moderna em tema dark para um sistema chamado MockAI. A tela deve ser um modal centralizado com cantos arredondados e aparência futurista, seguindo um estilo semelhante a dashboards modernos de desenvolvimento/API.

Objetivo da tela:
Tela para cadastro de um novo endpoint mockado de API.

Visual geral:
- Fundo escuro (#070B14 ou similar)
- Borda suave azul neon
- Sombras discretas
- Layout responsivo
- Tipografia moderna e limpa
- Espaçamento elegante entre campos
- Estilo inspirado em Vercel, Postman, Insomnia e GitHub Dark
- Aparência profissional SaaS
- Pequenos brilhos neon azuis
- UX clean e moderna

Branding:
Adicionar o nome "MockAI" no topo da aplicação ou discretamente no modal.

Estrutura do modal:
Título no topo esquerdo:
"Novo endpoint"

Campos do formulário organizados em duas colunas:

Linha 1:
- Select "Grupo"
  Valor selecionado: "Api fiscalizacao"
- Select "Método"
  Valor selecionado: "GET"

Linha 2:
- Input "Rota*"
  Placeholder: "/users"
- Input "Status"
  Valor: "200"

Linha 3:
- Input "Delay (ms)"
  Valor: "0"
- Checkbox "Ativo"
  Marcado por padrão

Linha 4:
- Textarea "Body (JSON)"
  Grande
  Fundo escuro
  Borda azul suave
  Altura aproximada de 180px

Botões:
No canto inferior esquerdo:
- Botão primário azul claro com texto "Criar"
- Botão secundário escuro com texto "Cancelar"

Detalhes visuais:
- Inputs com fundo azul-escuro
- Bordas discretas em azul
- Hover suave
- Cantos arredondados
- Componentes modernos e minimalistas
- Interface com sensação premium e tecnológica

Resultado esperado:
A interface deve parecer um produto profissional real para gerenciamento de endpoints mockados de APIs chamado MockAI.

---

## Tela atualização/edição de um endpoint

Crie uma interface web moderna em tema dark para um sistema chamado MockAI. A tela deve representar um modal de edição de endpoint mockado de API, com aparência futurista, minimalista e profissional, seguindo o padrão visual de ferramentas modernas de desenvolvimento.

Objetivo da tela:
Tela para atualização/edição de um endpoint mockado de API.

Visual geral:
- Fundo escuro (#070B14 ou similar)
- Modal centralizado
- Bordas arredondadas grandes
- Borda azul neon suave
- Sombras discretas
- Pequenos efeitos glow azul
- Layout responsivo
- Tipografia moderna e elegante
- UX clean e premium
- Estilo inspirado em Vercel, GitHub Dark, Postman e Insomnia

Branding:
Adicionar o nome "MockAI" discretamente na interface ou no topo da aplicação.

Estrutura do modal:
Título principal:
"Atualizar endpoint"

Campos do formulário organizados em duas colunas:

Linha 1:
- Select "Grupo"
  Valor selecionado:
  "Api fiscalizacao"

- Select "Metodo"
  Valor selecionado:
  "GET"

Linha 2:
- Input "Rota*"
  Valor:
  "/fiscalizacao-api"

- Input "Status"
  Valor:
  "200"

Linha 3:
- Input "Delay (ms)"
  Valor:
  "0"

- Checkbox "Ativo"
  Marcado por padrão

Linha 4:
- Textarea "Body (JSON)"
  Grande
  Fundo escuro
  Borda azul suave
  Altura aproximada de 180px

Conteúdo do JSON:
{
  "status": 1,
  "message": ["Operação efetuada com sucesso"]
}

Botões:
No canto inferior esquerdo:
- Botão primário azul clara com texto:
  "Atualizar"

- Botão secundário escuro com texto:
  "Cancelar"

Detalhes visuais:
- Inputs com fundo azul-escuro translúcido
- Bordas suaves em azul
- Hover moderno nos campos
- Glow sutil no campo ativo
- Textarea estilizada para edição JSON
- Espaçamento confortável entre elementos
- Componentes modernos e minimalistas
- Sensação de ferramenta SaaS profissional

Resultado esperado:
A interface deve parecer um produto profissional real para gerenciamento e edição de endpoints mockados de APIs em um sistema chamado MockAI.

---

## Tela lista de endpoints criados

Crie uma interface web moderna em tema dark para um sistema chamado MockAI. A tela deve representar um painel administrativo para gerenciamento de APIs mockadas, seguindo um visual futurista, minimalista e profissional inspirado em ferramentas modernas de desenvolvimento.

Objetivo da tela: Dashboard principal para gerenciamento de projetos, rotas mockadas e logs de APIs.

Visual geral:

Tema dark premium
Fundo escuro (#070B14 ou similar)
Bordas suaves em azul neon
Glow discreto azul
Layout responsivo
Tipografia moderna e clean
Estilo inspirado em Vercel, GitHub Dark, Postman, Insomnia e dashboards SaaS modernos
Componentes minimalistas e elegantes
Sensação de produto profissional real
Estrutura da tela:

HEADER SUPERIOR: Barra horizontal no topo da aplicação contendo:

Nome do sistema: "MockAI Lab"
Subtítulo pequeno: "URL base por grupo: /api/mock//"
No canto superior direito:

Botão "Rotas" destacado em azul
Botão "Logs"
Ícone de tema dark/light (lua)
Botão/Menu hamburguer com texto: "Menu"
LAYOUT PRINCIPAL: Divisão em duas áreas:

SIDEBAR ESQUERDA — PROJETOS Card lateral escuro com bordas arredondadas contendo:
Título: "PROJETOS"

Lista de projetos mockados: Cada projeto deve ser exibido em um card moderno contendo:

Nome do projeto
Quantidade de rotas
Quantidade de logs
Botão copiar
Botão deletar
Projetos exibidos:

Api fiscalizacao "8 rotas · 9 logs"
Dev "3 rotas · 8 logs"
usuarios "2 rotas · 8 logs"
O projeto ativo deve possuir:

Glow azul
Borda azul destacada
Fundo levemente iluminado
ÁREA PRINCIPAL — ROTAS Container principal com bordas arredondadas e fundo dark.
Título: "ROTAS - API FISCALIZACAO"

No topo da área:

Campo de busca com placeholder: "Filtrar rotas…"
No canto superior direito:

Botão azul claro: "+ Novo"
LISTA DE ROTAS: Exibir cards/lista de endpoints mockados.

Cada item deve conter:

Badge do método HTTP: GET ou POST
Status HTTP: 200
Caminho da rota: Exemplos: /fiscalizacao-api /fiscalizacao-api/bio/ense/ws/pt/configs/tipos /fiscalizacao-api/bio/ense/ws/pt/operacoes /fiscalizacao-api/bio/ense/ws/pt/titulos/oper /fiscalizacao-api/bio/ense/ws/pt/titulos/operacao
Ações abaixo da rota:

Botão copiar
Botão abrir/testar
Botão editar
Botão deletar
Detalhes visuais:

Cards escuros com bordas suaves
Hover elegante
Ícones minimalistas
Badges HTTP modernas
Scrollbar customizada dark
Espaçamento confortável
Inputs com glow azul discreto
Componentes premium estilo SaaS
Resultado esperado: A interface deve parecer um sistema real e profissional para gerenciamento de APIs mockadas chamado MockAI, com aparência moderna, tecnológica e pronta para produção.

---

## Tela Import de endpoints

Crie uma interface web moderna em tema dark para um sistema chamado MockAI. A tela deve representar um modal de importação de configurações de arquivos, com aparência futurista, minimalista e profissional, seguindo o padrão visual de dashboards modernos para desenvolvimento de APIs.

Objetivo da tela: Tela para importar endpoints mockados através de arquivo JSON ou colagem manual de JSON.

Visual geral:

Fundo escuro (#070B14 ou similar)
Modal centralizado
Bordas arredondadas grandes
Borda azul neon suave
Sombras discretas
Pequenos efeitos glow azul
Layout responsivo
Tipografia moderna e elegante
UX clean e premium
Estilo inspirado em Vercel, GitHub Dark, Postman e Insomnia
Aparência SaaS profissional
Branding: Adicionar o nome "MockAI" discretamente na interface ou no topo da aplicação.

Estrutura do modal: Título principal: "Importar JSON"

Abaixo do título: Sistema de abas moderno contendo:

Aba ativa: "Carregar arquivo"
Aba secundária: "Colar JSON"
A aba ativa deve possuir:

Linha inferior azul glow
Texto destacado em azul claro
Separador horizontal abaixo das abas.

Conteúdo da aba "Carregar arquivo": Texto auxiliar: "Selecione o arquivo JSON."

Campo de upload estilizado:

Input de upload moderno
Fundo azul-escuro translúcido
Borda suave azul
Texto: "Escolher arquivo Nenhum arquivo escolhido"
Hover elegante
Botões: No canto inferior esquerdo:

Botão secundário escuro com texto: "Fechar"
Detalhes visuais:

Inputs e uploads com fundo dark translúcido
Bordas suaves em azul
Glow discreto no elemento ativo
Hover moderno
Componentes minimalistas
Espaçamento confortável
Sensação de ferramenta tecnológica premium
Design clean e elegante
Resultado esperado: A interface deve parecer um sistema profissional real chamado MockAI para gerenciamento e importação de APIs mockadas, com visual moderno, tecnológico e pronto para produção.
