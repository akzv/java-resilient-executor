# Timeout — Controlando Lentidão

Timeout é essencial em sistemas ubíquos.
Sem timeout:
- threads ficam presas
- filas crescem sem limite
- serviços ficam indisponíveis
- deadlocks podem acontecer

O projeto usa Future + ExecutorService:
```java
Future<T> future = executor.submit(action::execute);
future.get(timeoutMs, TimeUnit.MILLISECONDS);
```

Se o tempo estourar: TimeoutException, o executor então aplica retry ou fallback.