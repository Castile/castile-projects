package com.castile.samples;

import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author castile
 * @date 2024-12-04 23:16
 */
@RestController("/")
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        RestTemplate restTemplate = RestTemplateBuilder.create();
        return  restTemplate.getForObject("cse://provider/test/health", String.class);
    }
}
