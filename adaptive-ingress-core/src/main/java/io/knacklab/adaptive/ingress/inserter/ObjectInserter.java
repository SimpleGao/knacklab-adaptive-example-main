package io.knacklab.adaptive.ingress.inserter;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ObjectInserter implements BodyInserter {
    private final Class<?> responseClass;

    public ObjectInserter(Class<?> responseClass) {
        this.responseClass = responseClass;
    }

    @Override
    public Mono<ServerResponse> insertBody(ServerResponse.BodyBuilder builder, Object output) {
        if (responseClass.isInstance(output)) {
            return builder.body(BodyInserters.fromValue(output));
        }
        return builder.body(BodyInserters.empty());
    }
}
