# Circuit Breaker – Proteção contra falhas persistentes

O padrão **Circuit Breaker** evita que um sistema continue chamando um serviço claramente indisponível.

Estados:

### CLOSED
Tudo normal.

### OPEN
Circuito "desarmado". Chamadas não são executadas — falham imediatamente.
Objetivo: evitar sobrecarregar um serviço que já está falhando.

### HALF-OPEN
Permite algumas requisições de teste.
Se funcionarem -> volta para CLOSED.
Se falharem -> volta para OPEN.

O projeto utiliza **Resilience4j**, que implementa:
- contagem de falhas
- janela deslizante
- tempo de resfriamento (wait duration)
- transições automáticas de estado

A classe wrapper `ResilientExecutorCB` recebe um Supplier<T> decorado,
e antes da execução verifica o status:
- OPEN -> exceção imediatamente
- CLOSED ou HALF-OPEN -> permite chamada

A execução acontece dentro de:
- timeout
- retry
- jitter
- fallback
(implementados no ResilientExecutor)

Se a execução falhar o breaker registra a falha e pode eventualmente abrir o circuito.