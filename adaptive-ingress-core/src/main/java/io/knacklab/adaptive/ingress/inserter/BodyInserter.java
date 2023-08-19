package io.knacklab.adaptive.ingress.inserter;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BodyInserter {
    Mono<ServerResponse> insertBody(ServerResponse.BodyBuilder builder,Object output);
}
