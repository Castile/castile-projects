package com.castile.casspringbootweb.demos.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author castile
 * @date 2025-11-25 上午12:39
 */
@Slf4j
@Component
public class FileExportTask implements TaskExecutor{
    @Override
    public String taskName() {
        return "FileExportTask";
    }

    @Override
    public void execute() throws Exception {
    log.warn("FileExportTask execute");
    }

    @Override
    public String toString() {
        return "FileExportTask: File";
    }
}
