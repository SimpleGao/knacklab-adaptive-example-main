package io.knacklab.adaptive.egress.core;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface ResponseBuilder {
    Mono<?> build(WebClient.ResponseSpec response, Transformer transformer);
}
