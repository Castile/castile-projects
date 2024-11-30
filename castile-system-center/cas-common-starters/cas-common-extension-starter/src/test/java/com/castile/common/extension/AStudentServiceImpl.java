package com.castile.common.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author castile
 * @date 2024-12-01 00:48
 */
@Service("A")
@Slf4j
@Extension(bizName = "A")
public class AStudentServiceImpl implements IStudentService {
    @Override
    public void addStudent() {
        log.warn("Add student in A");
    }

    @Override
    public String getStudentName(String studentId) {
        log.warn("Get student name in A: {}", studentId);
        return "Tom";
    }
}
