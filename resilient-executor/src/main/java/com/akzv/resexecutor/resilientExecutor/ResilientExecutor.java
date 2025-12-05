package com.akzv.resexecutor.resilientExecutor;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class ResilientExecutor {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Random random = new Random();

    public <T> T execute(Supplier<T> function, ExecutorConfig<T> config) throws Exception {
        int attempts = 0;

        while (true) {
            try {
                attempts++;
                Future<T> future = executor.submit(function::get);

                return future.get(config.timeoutConfig.timeoutMillis, TimeUnit.MILLISECONDS);
            }
            catch (TimeoutException | ExecutionException e) {

                if (attempts >= config.retryConfig.maxAttempts) {
                    if (config.fallback != null) {
                        return config.fallback.apply(e);
                    }
                    throw e;
                }

                long waitSeconds = config.retryConfig.backoffSeconds[attempts - 1];
                long jitter = random.nextInt(500);
                long waitMillis = waitSeconds * 1000 + jitter;

                Thread.sleep(waitMillis);
            }
        }
    }
}