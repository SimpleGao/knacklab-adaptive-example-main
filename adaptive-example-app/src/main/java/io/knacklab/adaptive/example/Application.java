package io.knacklab.adaptive.example;

import io.knacklab.adaptive.egress.config.EnableAdaptiveEgress;
import io.knacklab.adaptive.ingress.config.EnableAdaptiveIngress;
import io.knacklab.adaptive.store.config.EnableAdaptiveStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAdaptiveEgress
@EnableAdaptiveIngress
@EnableAdaptiveStore
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
