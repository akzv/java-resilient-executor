package com.akzv.resexecutor.resilientExecutor;

public class RetryConfig {
    public int maxAttempts;
    public long[] backoffSeconds;

    public RetryConfig(int maxAttempts, long[] backoffSeconds) {
        this.maxAttempts = maxAttempts;
        this.backoffSeconds = backoffSeconds;
    }
}