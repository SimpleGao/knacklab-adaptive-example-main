package io.knacklab.adaptive.ingress.core;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface ExecutorAware {
    ServerRequest preExecute(ServerRequest request);

    ServerResponse.BodyBuilder postExecute(ServerResponse.BodyBuilder builder);

}
