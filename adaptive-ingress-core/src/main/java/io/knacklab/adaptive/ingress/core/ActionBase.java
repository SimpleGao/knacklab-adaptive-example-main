package io.knacklab.adaptive.ingress.core;

import io.knacklab.adaptive.ingress.action.Action;
import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.exception.IngressException;
import io.knacklab.adaptive.ingress.messaging.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class ActionBase implements Action {
    protected final ActionRoute route;
    protected final Class<?> requestClass;
    protected final Class<?> responseClass;

    protected ActionBase(ActionConfig config, Class<?> requestClass, Class<?> responseClass) {
        this.requestClass = requestClass;
        this.responseClass = responseClass;
        this.route = config.getRoute(this.getFullName());
    }

    @Override
    public Mono<ServerResponse> execute(ServerRequest request) {
        ServerRequest changed;
        try {
            changed = this.preExecute(request);
        } catch (IngressException e) {
            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(ErrorResponse.builder()
                            .message(e.getMessage())
                            .build()), ErrorResponse.class);
        }
        final ServerRequest req = changed;
        return req.bodyToMono(this.requestClass)
                .flatMap(s -> this.postExecute(ServerResponse.ok())
                        .body(this.transform(s, req), this.responseClass)
                        .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_GATEWAY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(ErrorResponse.builder()
                                        .message(e.getMessage())
                                        .build()), ErrorResponse.class)
                        )
                )
                .switchIfEmpty(this.postExecute(ServerResponse.ok())
                        .body(this.transform(null, req), this.responseClass)
                )
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_GATEWAY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(ErrorResponse.builder()
                                .message(e.getMessage())
                                .build()), ErrorResponse.class)
                );
    }

    protected ServerRequest preExecute(ServerRequest request) {
        return request;
    }

    protected ServerResponse.BodyBuilder postExecute(ServerResponse.BodyBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON);
    }

    protected abstract Mono<?> transform(Object input, ServerRequest request) throws IngressException;

    protected String getFullName() {
        return this.getClass().getAnnotation(Service.class).value();
    }
}
