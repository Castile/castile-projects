package com.castile.samples.health;

import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author castile
 * @date 2024-12-04 00:09
 */
@Component
public class HealthChecker extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        RestTemplate restTemplate = RestTemplateBuilder.create();
        String result = restTemplate.getForObject("cse://provider/test/health", String.class);
        if ("UP".equals(result)) {
            builder.up().withDetail("cseState", "UP");
        } else {
            builder.down().withDetail("cseState", "DOWN");
        }
    }
}
