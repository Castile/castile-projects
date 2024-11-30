package com.castile.common.extension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

@SpringBootTest
class CasCommonExtensionStarterApplicationTests {
    @Autowired
    private ServiceExecutor serviceExecutor;

    @Test
    void test_extension(){
        serviceExecutor.executeVoid(IStudentService.class, "A", service->{
            service.addStudent();
            service.getStudentName("A-1001");
        });

        String result = serviceExecutor.execute(IStudentService.class, "B", new Function<IStudentService, String>() {
            @Override
            public String apply(IStudentService iStudentService) {
                iStudentService.addStudent();
                String studentName = iStudentService.getStudentName("B-1001");
                return studentName;
            }
        });

        System.out.println("返回的结果是： "+ result);

        Assertions.assertEquals("BTom", result);
    }
}
