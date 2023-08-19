package io.knacklab.adaptive.egress.core;

import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class VoidBuilder implements ResponseBuilder {

    @Override
    public Mono<?> build(WebClient.ResponseSpec response, Transformer transformer) {
        Assert.notNull(transformer, "Transformer Is Null");
        Assert.notNull(response, "Response Is Null");
        return response.toBodilessEntity().map(ignored -> transformer.transform(null));
    }
}
