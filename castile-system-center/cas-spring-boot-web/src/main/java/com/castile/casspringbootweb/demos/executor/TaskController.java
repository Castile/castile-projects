package com.castile.casspringbootweb.demos.executor;

import com.castile.casspringbootweb.demos.web.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author castile
 * @date 2025-11-25 上午12:33
 */
@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskManager taskManager;

    @GetMapping("/getTasks")
    public BaseResponse<List<TaskExecutor>> getTasks() {

        int size = taskManager.getTaskExecutorList().size();

        log.warn("getTasks size: {}", size);

        log.warn("getTasks: {}", taskManager.getTaskExecutorList());


        BaseResponse<List<TaskExecutor>> listBaseResponse = new BaseResponse<>();
        listBaseResponse.setResult(taskManager.getTaskExecutorList());

        return listBaseResponse;
    }
}
