package com.castile.casspringbootweb.demos.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author castile
 * @date 2025-11-25 上午12:31
 */
@Slf4j
public class TaskManager {

    private List<TaskExecutor> taskExecutorList;

    public TaskManager(List<TaskExecutor> taskExecutorList) {
        this.taskExecutorList = taskExecutorList;
    }

    public void executeTask(){
        try {
            for (TaskExecutor taskExecutor : taskExecutorList) {
                taskExecutor.execute();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<TaskExecutor> getTaskExecutorList() {
        return taskExecutorList;
    }

    public void setTaskExecutorList(List<TaskExecutor> taskExecutorList) {
        this.taskExecutorList = taskExecutorList;
    }
}
