package com.akzv.resexecutor;

import com.akzv.resexecutor.resilientExecutor.CircuitBreakerFactory;
import com.akzv.resexecutor.resilientExecutor.ExecutorConfig;
import com.akzv.resexecutor.resilientExecutor.FallbackFunction;
import com.akzv.resexecutor.resilientExecutor.ResilientExecutor;
import com.akzv.resexecutor.resilientExecutor.ResilientExecutorCB;
import com.akzv.resexecutor.resilientExecutor.RetryConfig;
import com.akzv.resexecutor.resilientExecutor.TimeoutConfig;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

public class Main {
    public static void main(String[] args) throws Exception {
        ResilientExecutor resilientExecutor = new ResilientExecutor();
        RetryConfig retryConfig = new RetryConfig(
                3,
                new long[]{1, 2, 4}
        );
        TimeoutConfig timeoutConfig = new TimeoutConfig(2000);
        FallbackFunction<String> fallbackFunction = (error) -> 
                "FALLBACK: resposta alternativa (erro: " + error.getMessage() + ")";
        ExecutorConfig<String> executorConfig = new ExecutorConfig<>(retryConfig, timeoutConfig, fallbackFunction);
        ExecutorConfig<String> executorConfigWithDefaultFallback = new ExecutorConfig<>(retryConfig, timeoutConfig);
        testExecutor(resilientExecutor, executorConfig);
        testExecutor(resilientExecutor, executorConfigWithDefaultFallback);
        testExecutorCB(executorConfig);
        testCircuitBreakerOpen();
        Thread.sleep(1000);

        return;
    }

    public static void testExecutor(ResilientExecutor resilientExecutor, ExecutorConfig<String> executorConfig) throws Exception {
        System.out.println("Teste 1: Servico OK");
        String r1 = resilientExecutor.execute(TestMethods::okService, executorConfig);
        System.out.println("Resultado: " + r1);

        System.out.println("\nTeste 2: Servico lento (deve cair no timeout + fallback)");
        String r2 = resilientExecutor.execute(TestMethods::slowService, executorConfig);
        System.out.println("Resultado: " + r2);

        System.out.println("\nTeste 3: Servico com falha (retry + fallback)");
        String r3 = resilientExecutor.execute(TestMethods::faultyService, executorConfig);
        System.out.println("Resultado: " + r3);

        System.out.println("\nTeste 4: Servico instavel (falha e depois funciona) ####");
        String r4 = resilientExecutor.execute(TestMethods::unstableService, executorConfig);
        System.out.println("Resultado: " + r4);
    }

    public static void testExecutorCB(ExecutorConfig<String> executorConfig) {
        System.out.println("\nTeste com CircuitBreaker");

        CircuitBreaker circuitBreaker = CircuitBreakerFactory.createDefaultBreaker("test-breaker");
        ResilientExecutorCB resilientExecutorCB = new ResilientExecutorCB();

        for (int i = 1; i <= 5; i++) {
            try {
                System.out.println("Tentativa CB " + i);
                String result = resilientExecutorCB.executeWithBreaker(
                        TestMethods::faultyService,
                        executorConfig,
                        circuitBreaker
                );
                System.out.println("Resultado: " + result);
            } catch (Exception ex) {
                System.out.println("Erro capturado (ou CB open): " + ex.getMessage());
            }

        }
    }

    public static void testCircuitBreakerOpen() {
        CircuitBreaker circuitBreaker = CircuitBreakerFactory.createDefaultBreaker("breaker-open-test");
        RetryConfig retry = new RetryConfig(1, new long[]{1});
        TimeoutConfig timeout = new TimeoutConfig(2000);
        ExecutorConfig<String> executorConfig = new ExecutorConfig<>(retry, timeout, null);
        ResilientExecutorCB resilientExecutorCB = new ResilientExecutorCB();

        for (int i = 1; i <= 10; i++) {
            try {
                System.out.println("Tentativa CB " + i);
                String r = resilientExecutorCB.executeWithBreaker(
                    TestMethods::faultyService,
                    executorConfig,
                    circuitBreaker
                );
                System.out.println("Resultado: " + r);
            }
            catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }

            System.out.println("Estado atual do CB: " + circuitBreaker.getState());
        }
    }
}