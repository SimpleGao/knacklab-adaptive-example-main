package io.knacklab.adaptive.egress.core;

import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.image.DataBuffer;
import java.util.Objects;

public class StreamBuilder implements ResponseBuilder {
    @Override
    public Mono<?> build(WebClient.ResponseSpec response, Transformer transformer) {
        Assert.notNull(transformer, "Transformer Is Null");
        Assert.notNull(response, "Response Is Null");
        return response.toEntityFlux(DataBuffer.class)
                .flatMap(entity -> Objects.requireNonNull(entity.getBody().collectList()))
                .map(output -> transformer.transform(output));
    }
}
