package com.akzv.resexecutor.resilientExecutor;

public interface FallbackFunction<T> {
    T apply(Throwable error);
}