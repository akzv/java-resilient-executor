package com.akzv.resexecutor.resilientExecutor;

import java.util.function.Supplier;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;

public class ResilientExecutorCB {
    private final ResilientExecutor resilientExecutor = new ResilientExecutor();

    public <T> T executeWithBreaker(Supplier<T> fn, ExecutorConfig<T> executorConfig,
                                    CircuitBreaker circuitBreaker) throws Exception {
        Supplier<T> decorated =
                CircuitBreaker.decorateSupplier(circuitBreaker, () -> {
                    try {
                        return resilientExecutor.execute(fn, executorConfig);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return decorated.get();
    }
}