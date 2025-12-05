package com.akzv.resexecutor.resilientExecutor;

public class ExecutorConfig<T> {
    public RetryConfig retryConfig;
    public TimeoutConfig timeoutConfig;
    public FallbackFunction<T> fallback;

    public ExecutorConfig(RetryConfig retryConfig, TimeoutConfig timeoutConfig, FallbackFunction<T> fallback) {
        this.retryConfig = retryConfig;
        this.timeoutConfig = timeoutConfig;
        this.fallback = fallback;
    }

    public ExecutorConfig(RetryConfig retryConfig, TimeoutConfig timeoutConfig) {
        this.retryConfig = retryConfig;
        this.timeoutConfig = timeoutConfig;
        this.fallback = (err) -> null;
    }

}