# Fallback — Último Recurso

Fallback é uma função alternativa que será chamada quando:
- timeout ocorrer repetidamente
- retries se esgotarem
- o serviço estiver fora do ar
- ocorrer falha inesperada

Exemplo real:
- retornar dados de cache
- resposta padrão
- mensagem de erro amigável
- rota alternativa de comunicação

O projeto usa função funcional:

```java
FallbackFunction<T> fallback;
```

Exemplo: (error) -> "FALLBACK: serviço indisponível"

O fallback garante que o usuário receba algo, mesmo que o serviço remoto tenha falhado completamente.