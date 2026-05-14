---
inclusion: always
description: Diretrizes de SOLID e Clean Architecture para geração de código. Deve ser consultado sempre que for necessário criar ou modificar classes, interfaces, serviços, repositórios ou qualquer componente da aplicação.
---

# Princípios SOLID e Clean Architecture

Este documento define as diretrizes obrigatórias de design de código que o Kiro deve seguir ao gerar ou modificar qualquer componente da aplicação. Todos os exemplos são aplicáveis ao contexto Java/Spring Boot.

---

## Princípios SOLID

### S — Single Responsibility Principle (Princípio da Responsabilidade Única)

Uma classe deve ter apenas um motivo para mudar. Cada classe deve ser responsável por uma única parte do comportamento do sistema.

**Diretriz:** Ao criar uma classe, verifique se ela faz apenas uma coisa. Se a descrição da classe precisar da palavra "e", provavelmente ela tem mais de uma responsabilidade.

**Errado:**
```java
@Service
public class PedidoService {

    public void processarPedido(Pedido pedido) {
        // valida o pedido
        if (pedido.getItens().isEmpty()) throw new IllegalArgumentException("Pedido sem itens");

        // persiste no banco
        pedidoRepository.save(pedido);

        // envia e-mail de confirmação
        emailService.enviar("confirmacao@email.com", "Pedido confirmado");
    }
}
```

**Correto:**
```java
@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final NotificacaoService notificacaoService;

    public void processarPedido(Pedido pedido) {
        pedidoValidator.validar(pedido);
        pedidoRepository.save(pedido);
        notificacaoService.notificarConfirmacao(pedido);
    }
}
```

---

### O — Open/Closed Principle (Princípio Aberto/Fechado)

Classes devem estar abertas para extensão, mas fechadas para modificação. Novos comportamentos devem ser adicionados por meio de extensão, não alterando código existente.

**Diretriz:** Prefira interfaces e abstrações para pontos de variação. Use polimorfismo em vez de condicionais (`if/else`, `switch`) para selecionar comportamentos.

**Errado:**
```java
public class CalculadoraDesconto {
    public double calcular(String tipoCliente, double valor) {
        if (tipoCliente.equals("VIP")) return valor * 0.8;
        if (tipoCliente.equals("COMUM")) return valor * 0.95;
        return valor;
    }
}
```

**Correto:**
```java
public interface EstrategiaDesconto {
    double calcular(double valor);
}

@Component("VIP")
public class DescontoVip implements EstrategiaDesconto {
    public double calcular(double valor) { return valor * 0.8; }
}

@Component("COMUM")
public class DescontoComum implements EstrategiaDesconto {
    public double calcular(double valor) { return valor * 0.95; }
}

@Service
public class CalculadoraDesconto {
    private final Map<String, EstrategiaDesconto> estrategias;

    public double calcular(String tipoCliente, double valor) {
        return estrategias.getOrDefault(tipoCliente, v -> v).calcular(valor);
    }
}
```

---

### L — Liskov Substitution Principle (Princípio da Substituição de Liskov)

Subtipos devem ser substituíveis por seus tipos base sem alterar a corretude do programa. Uma subclasse não deve quebrar o contrato estabelecido pela superclasse ou interface.

**Diretriz:** Ao criar subclasses ou implementações de interfaces, garanta que o comportamento esperado pelo contrato seja preservado. Nunca lance exceções inesperadas nem ignore comportamentos definidos na interface.

**Errado:**
```java
public interface Repositorio<T> {
    void salvar(T entidade);
    T buscarPorId(Long id);
}

// Viola LSP: lança exceção em operação prevista no contrato
public class RepositorioSomenteLeitura implements Repositorio<Produto> {
    public void salvar(Produto p) {
        throw new UnsupportedOperationException("Somente leitura");
    }
    public Produto buscarPorId(Long id) { /* ... */ return null; }
}
```

**Correto:**
```java
public interface RepositorioLeitura<T> {
    T buscarPorId(Long id);
}

public interface RepositorioEscrita<T> extends RepositorioLeitura<T> {
    void salvar(T entidade);
}
```

---

### I — Interface Segregation Principle (Princípio da Segregação de Interfaces)

Clientes não devem ser forçados a depender de interfaces que não utilizam. Prefira interfaces pequenas e coesas a interfaces grandes e genéricas.

**Diretriz:** Divida interfaces grandes em interfaces menores e específicas. Uma classe deve implementar apenas os métodos que realmente usa.

**Errado:**
```java
public interface ServicoNotificacao {
    void enviarEmail(String destinatario, String mensagem);
    void enviarSms(String telefone, String mensagem);
    void enviarPushNotification(String deviceToken, String mensagem);
}

// Esta classe só envia e-mail, mas é forçada a implementar SMS e Push
public class ServicoEmail implements ServicoNotificacao {
    public void enviarEmail(String dest, String msg) { /* implementa */ }
    public void enviarSms(String tel, String msg) { throw new UnsupportedOperationException(); }
    public void enviarPushNotification(String token, String msg) { throw new UnsupportedOperationException(); }
}
```

**Correto:**
```java
public interface EnviadorEmail {
    void enviarEmail(String destinatario, String mensagem);
}

public interface EnviadorSms {
    void enviarSms(String telefone, String mensagem);
}

public interface EnviadorPush {
    void enviarPushNotification(String deviceToken, String mensagem);
}

@Service
public class ServicoEmail implements EnviadorEmail {
    public void enviarEmail(String destinatario, String mensagem) { /* implementa */ }
}
```

---

### D — Dependency Inversion Principle (Princípio da Inversão de Dependência)

Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações. Abstrações não devem depender de detalhes; detalhes devem depender de abstrações.

**Diretriz:** Dependa sempre de interfaces, nunca de implementações concretas. Use injeção de dependência (via construtor, preferencialmente) para fornecer as implementações.

**Errado:**
```java
@Service
public class PedidoService {
    // Dependência direta da implementação concreta
    private final PedidoRepositoryImpl pedidoRepository = new PedidoRepositoryImpl();
}
```

**Correto:**
```java
@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository; // depende da abstração

    // Injeção via construtor (preferível ao @Autowired em campo)
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
}
```

---

## Clean Architecture

A Clean Architecture organiza o código em camadas concêntricas, onde as dependências sempre apontam para dentro — em direção às regras de negócio. O núcleo da aplicação não conhece frameworks, bancos de dados ou detalhes de infraestrutura.

### Camadas e Responsabilidades

```
┌─────────────────────────────────────────┐
│           Frameworks & Drivers          │  ← Controllers, JPA, REST clients
│  ┌───────────────────────────────────┐  │
│  │      Interface Adapters           │  │  ← DTOs, Mappers, Presenters
│  │  ┌─────────────────────────────┐  │  │
│  │  │      Use Cases              │  │  │  ← Regras de aplicação (Services)
│  │  │  ┌───────────────────────┐  │  │  │
│  │  │  │      Entities         │  │  │  │  ← Regras de negócio puras
│  │  │  └───────────────────────┘  │  │  │
│  │  └─────────────────────────────┘  │  │
│  └───────────────────────────────────┘  │
└─────────────────────────────────────────┘
```

### Estrutura de Pacotes Recomendada

```
src/main/java/com/exemplo/mockai/
├── domain/                        # Entidades e regras de negócio puras
│   ├── model/                     # Entidades de domínio (sem anotações JPA)
│   └── exception/                 # Exceções de domínio
├── application/                   # Casos de uso (regras de aplicação)
│   ├── port/
│   │   ├── in/                    # Interfaces de entrada (use cases)
│   │   └── out/                   # Interfaces de saída (repositórios, gateways)
│   └── service/                   # Implementações dos casos de uso
├── adapter/                       # Adaptadores (interface com o mundo externo)
│   ├── in/
│   │   └── web/                   # Controllers REST, DTOs de request/response
│   └── out/
│       ├── persistence/           # Implementações JPA, entidades JPA, mappers
│       └── external/              # Clientes HTTP, integrações externas
└── config/                        # Configurações Spring (Beans, Security, etc.)
```

### Regras de Dependência

- **Domínio** não depende de nada externo — nenhuma anotação Spring, JPA ou framework.
- **Application (Use Cases)** depende apenas do domínio e de interfaces (ports).
- **Adapters** dependem dos use cases via interfaces de entrada (`port/in`).
- **Infraestrutura** implementa as interfaces de saída (`port/out`) definidas na camada de aplicação.

**Exemplo de Port de entrada:**
```java
// application/port/in/CriarMockUseCase.java
public interface CriarMockUseCase {
    MockCriado executar(CriarMockCommand command);
}
```

**Exemplo de Port de saída:**
```java
// application/port/out/SalvarEndpointPort.java
public interface SalvarEndpointPort {
    Endpoint salvar(Endpoint endpoint);
}
```

**Exemplo de Use Case:**
```java
// application/service/CriarMockService.java
@Service
public class CriarMockService implements CriarMockUseCase {
    private final SalvarEndpointPort salvarEndpointPort; // depende da abstração

    public CriarMockService(SalvarEndpointPort salvarEndpointPort) {
        this.salvarEndpointPort = salvarEndpointPort;
    }

    public MockCriado executar(CriarMockCommand command) {
        // lógica de aplicação aqui, sem conhecer JPA ou HTTP
        Endpoint endpoint = new Endpoint(command.getPath(), command.getMethod());
        return new MockCriado(salvarEndpointPort.salvar(endpoint));
    }
}
```

**Exemplo de Adapter de saída (implementa o port):**
```java
// adapter/out/persistence/EndpointPersistenceAdapter.java
@Component
public class EndpointPersistenceAdapter implements SalvarEndpointPort {
    private final EndpointJpaRepository jpaRepository;
    private final EndpointMapper mapper;

    public Endpoint salvar(Endpoint endpoint) {
        EndpointEntity entity = mapper.toEntity(endpoint);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
```

### Diretrizes Gerais de Clean Architecture

- **Nunca** injete `HttpServletRequest`, `HttpServletResponse` ou qualquer objeto HTTP em use cases ou domínio.
- **Nunca** use anotações JPA (`@Entity`, `@Column`) em classes de domínio — crie entidades JPA separadas na camada de persistência.
- **Sempre** use DTOs para comunicação entre camadas (request/response nos controllers, commands/results nos use cases).
- **Sempre** use mappers explícitos para converter entre entidades de domínio, entidades JPA e DTOs.
- Exceções de domínio devem ser lançadas no domínio e tratadas nos adapters (ex: `@ControllerAdvice`).
- Validações de formato (ex: campo obrigatório, tamanho) pertencem ao adapter de entrada. Validações de regra de negócio pertencem ao domínio.
