package io.knacklab.adaptive.egress.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Import({ExchangeConfig.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAdaptiveEgress {
}
