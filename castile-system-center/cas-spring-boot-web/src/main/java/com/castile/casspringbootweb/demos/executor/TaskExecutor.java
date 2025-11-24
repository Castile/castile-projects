package com.castile.casspringbootweb.demos.executor;

/**
 * @author castile
 * @date 2025-11-25 上午12:29
 */
public interface TaskExecutor {

    String taskName();

    void execute() throws Exception;
}
