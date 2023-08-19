package io.knacklab.adaptive.example.test;

import io.knacklab.adaptive.egress.config.EnableAdaptiveEgress;
import io.knacklab.adaptive.ingress.config.EnableAdaptiveIngress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAdaptiveEgress
@EnableAdaptiveIngress
@SpringBootApplication
@ComponentScan("io.knacklab.adaptive.example")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
