package io.knacklab.adaptive.ingress.config;

import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.lang.annotation.*;

@Documented
@EnableWebFlux
@Target({ElementType.TYPE})
@Import({ActionConfig.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAdaptiveIngress {
}
