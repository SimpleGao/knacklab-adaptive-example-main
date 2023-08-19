package io.knacklab.adaptive.ingress.test;

import io.knacklab.adaptive.ingress.config.EnableAdaptiveIngress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdaptiveIngress
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
