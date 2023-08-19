package io.knacklab.adaptive.ingress.extractor;

import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class ObjectExtractor implements BodyExtractor {
    private final Class<?> requestClass;

    public ObjectExtractor(Class<?> requestClass) {
        this.requestClass = requestClass;
    }

    @Override
    public Mono<?> extractBody(ServerRequest request) {
        return request.bodyToMono(requestClass);
    }
}
