package io.knacklab.adaptive.ingress.inserter;

import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ResourceInserter implements BodyInserter {
    private static final int BUFFER_SIZE = 8192;
    private final DataBufferFactory factory;

    public ResourceInserter() {
        this.factory = new DefaultDataBufferFactory();
    }

    @Override
    public Mono<ServerResponse> insertBody(ServerResponse.BodyBuilder builder, Object output) {
        if (output instanceof Resource) {
            val buffer = DataBufferUtils.read((Resource) output, factory, BUFFER_SIZE);
            return builder.body(BodyInserters.fromDataBuffers(buffer));
        }
        return builder.body(BodyInserters.empty());
    }
}
