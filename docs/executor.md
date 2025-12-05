# ResilientExecutor

A classe **ResilientExecutor** é o núcleo do projeto.
Ela recebe:

- uma função a ser executada
- um `ExecutorConfig` contendo:
  - Timeout
  - Retry / Backoff / Jitter
  - Fallback

O comportamento completo é:
1. Inicia a execução da função em uma thread (ExecutorService).
2. Espera o resultado com timeout.
3. Se exceder o tempo -> gera TimeoutException.
4. Se ocorrer falha -> tenta novamente conforme as regras de retry.
5. Se as tentativas se esgotarem -> aciona o método fallback.

Este executor encapsula vários padrões de resiliência e representa o modo como sistemas distribuídos robustos lidam com falhas inevitáveis.