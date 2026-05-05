---
inclusion: always
---

# Idioma do Projeto

Este documento define as convenções de idioma adotadas em todo o projeto.

## Regras Gerais

### Português Brasileiro

Os seguintes elementos devem ser escritos em **português brasileiro**:

- Documentação (arquivos `.md`, READMEs, wikis)
- Arquivos de steering (`.kiro/steering/*.md`)
- Skills e specs
- Comentários no código-fonte
- Javadocs e anotações de documentação
- Mensagens de log descritivas
- Mensagens de erro e exceções voltadas ao desenvolvedor
- Descrições em arquivos de configuração

### Inglês (US)

Os seguintes elementos devem ser escritos em **inglês americano**:

- Nomes de arquivos e diretórios
- Nomes de classes, interfaces, enums e anotações
- Nomes de métodos e funções
- Nomes de variáveis, constantes e parâmetros
- Nomes de campos em objetos, DTOs e formulários
- Chaves de propriedades em arquivos de configuração (ex.: `application.properties`)
- Campos de Frontmatter em arquivos Markdown (ex.: `inclusion`, `fileMatchPattern`)
- Nomes de branches, tags e commits Git
- Identificadores em geral no código-fonte

## Exemplos

### Correto

```java
/**
 * Caso de uso responsável por criar um novo pedido.
 * Valida os dados de entrada e persiste o pedido no repositório.
 */
public class CreateOrderUseCase {

    // Repositório utilizado para persistir os pedidos
    private final OrderRepository orderRepository;

    /**
     * Executa a criação do pedido com os dados fornecidos.
     *
     * @param request dados necessários para criar o pedido
     * @return pedido criado com identificador gerado
     */
    public OrderResponse execute(CreateOrderRequest request) {
        // Valida se o cliente existe antes de criar o pedido
        ...
    }
}
```

```markdown
---
inclusion: always
---

# Título do Documento

Descrição em português brasileiro.
```

### Incorreto

```java
/**
 * Use case responsible for creating a new order.
 */
public class CriarPedidoUseCase { // nome da classe em português — errado
    ...
}
```
