package io.knacklab.adaptive.egress.test;

import io.knacklab.adaptive.egress.config.EnableAdaptiveEgress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdaptiveEgress
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
