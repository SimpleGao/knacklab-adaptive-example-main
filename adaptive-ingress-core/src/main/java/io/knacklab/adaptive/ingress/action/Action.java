package io.knacklab.adaptive.ingress.action;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface Action {
    Mono<ServerResponse> execute(ServerRequest request);
}
