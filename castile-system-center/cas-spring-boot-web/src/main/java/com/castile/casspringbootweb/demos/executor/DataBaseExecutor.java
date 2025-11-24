package com.castile.casspringbootweb.demos.executor;

import cn.hutool.cron.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author castile
 * @date 2025-11-25 上午12:29
 */
@Slf4j
@Component
public class DataBaseExecutor implements TaskExecutor {
    @Override
    public String taskName() {
        return "db-task";
    }

    @Override
    public void execute() throws Exception {
        log.warn("execute db task!");
    }


    @Override
    public String toString() {
        return "Executor:" + taskName();
    }
}
