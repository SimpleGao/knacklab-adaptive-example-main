package io.knacklab.adaptive.store.config;

import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.lang.annotation.*;


@Documented
@EnableWebFlux
@Target({ElementType.TYPE})
@Import({StoreConfig.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAdaptiveStore {
}
