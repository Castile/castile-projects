package com.castile.common.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author castile
 * @date 2024-12-01 00:50
 */
@Service("B")
@Slf4j
@Extension(bizName = "B")
public class BStudentServiceImpl implements IStudentService {
    @Override
    public void addStudent() {
        log.warn("addStudent in B!");
    }

    @Override
    public String getStudentName(String studentId) {
        log.warn("getStudentName in B!");
        return "BTom";
    }
}
