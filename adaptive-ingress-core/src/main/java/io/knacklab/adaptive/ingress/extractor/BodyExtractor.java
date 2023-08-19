package io.knacklab.adaptive.ingress.extractor;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface BodyExtractor {
    Mono<?> extractBody(ServerRequest request);
}
