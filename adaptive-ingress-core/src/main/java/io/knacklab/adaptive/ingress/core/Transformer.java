package io.knacklab.adaptive.ingress.core;

import io.knacklab.adaptive.ingress.messaging.IngressContext;
import reactor.core.publisher.Mono;

public interface Transformer {
    Mono<?> transform(IngressContext context, Object input);
}
