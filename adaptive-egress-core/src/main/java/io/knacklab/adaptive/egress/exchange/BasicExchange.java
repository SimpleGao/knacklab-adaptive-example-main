package io.knacklab.adaptive.egress.exchange;

import reactor.core.publisher.Mono;

public interface BasicExchange {
    <T> Mono<T> execute(Class<T> outputClazz, Object input);

    <T> Mono<T> execute(Class<T> outputClazz, Object input, Object... args);
}
