package io.knacklab.adaptive.egress.exchange;

import reactor.core.publisher.Mono;

public interface LightExchange {
    <T> Mono<T> execute(Class<T> outputClazz, Object input);

    <T> Mono<T> execute(Class<T> outputClazz, Object input, Object... args);
}
