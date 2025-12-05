# Retry, Backoff e Jitter



## Retry
Retry é a estratégia de tentar novamente quando ocorre uma falha temporária.

Exemplos típicos:
- instabilidade momentânea da rede
- serviço ocupado
- congestionamento de requests

No projeto, o retry é configurado com:

```java
retryCount
delaySequence (1s, 2s, 4s ...)
```

## Backoff exponencial
Backoff é o intervalo de espera entre tentativas. Ex: 1s -> 2s -> 4s -> 8s

Isso deve ser configurado porque insistir rapidamente sobrecarrega ainda mais o serviço, e se não deu certo
após tentar novamente depois de um segundo, dificilmente vai dar certo depois de esperar mais um segundo.

Um tempo maior entre as tentativas diminui o esforço do sistema e tem mais chances de uma tentativa que dê certo,
com o serviço tendo voltado a funcionar seja lá por qual o motivo.

## Jitter
Jitter é uma variação aleatória no atraso. Ex: 4s + 0.0–0.5s aleatórios

Isso evita "thundering herd" — quando vários clientes fazem retry ao mesmo tempo e derrubam o servidor. O código implementa jitter assim:

```java
Thread.sleep(delayMs + random.nextInt(500));
```

