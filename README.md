# Java Resilient Executor: projeto que simula um executor resiliente com timeout, retry, backoff, jitter, fallback e circuit breaker utilizando Resilience4j

Este projeto é uma demonstração prática de estratégias de tolerância a falhas utilizadas em sistemas distribuídos e ubíquos.
O objetivo é mostrar como construir, em Java, um executor configurável capaz de lidar com:
- Indisponibilidade temporária de serviços
- Lentidão (timeouts)
- Falhas intermitentes (retries com backoff)
- Falhas persistentes (fallback)
- Controle de falhas repetidas (circuit breaker)

## Tecnologias utilizadas
- **Java**
- **Maven**
- **Resilience4j** (para o Circuit Breaker)

## Como executar

1. Certifique-se de ter Java e Maven instalados

### Para compilar:

Após clonar o projeto e navegar para o diretório raiz, execute

```bash
cd resilient-executor
mvn clean compile
```

### Para executar a simulação:
```bash
mvn exec:java -Dexec.mainClass="com.akzv.resexecutor.Main"
```

Esse comando executará o arquivo Main.java, que contém vários testes prontos demonstrando:
- requisição bem-sucedida
- timeout + fallback
- retry + fallback
- serviço instável (fail -> retry -> success)
- uso do circuit breaker

## Documentação das classes
A explicação dos conceitos e dos padrões aplicados está na pasta docs/:
- [executor.md](docs/executor.md) — como funciona o executor resiliente
- [retry.md](docs/retry.md) — retry, backoff e jitter
- [timeout.md](docs/timeout.md) — mecanismo de timeout com thread-futures
- [fallback.md](docs/fallback.md) — como o fallback protege o sistema
- [circuit-breaker.md](docs/circuit-breaker.md) — explicação do Circuit Breaker
