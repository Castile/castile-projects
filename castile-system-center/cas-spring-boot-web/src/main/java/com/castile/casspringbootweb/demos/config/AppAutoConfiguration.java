package com.castile.casspringbootweb.demos.config;

import com.castile.casspringbootweb.demos.executor.TaskExecutor;
import com.castile.casspringbootweb.demos.executor.TaskManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author castile
 * @date 2025-11-25 上午12:31
 */
@Configuration
public class AppAutoConfiguration {

    @Bean
    public TaskManager taskManager(List<TaskExecutor> taskExecutors) {
        return new TaskManager(taskExecutors);
    }
}
