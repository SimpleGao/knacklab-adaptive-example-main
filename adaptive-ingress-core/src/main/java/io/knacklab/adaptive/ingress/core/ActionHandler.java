package io.knacklab.adaptive.ingress.core;

import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.exception.IngressException;
import io.knacklab.adaptive.ingress.messaging.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ActionHandler {
    private static final Log logger = LogFactory.getLog(ActionHandler.class);
    private static final String FORMAT_REJECTING_REQUEST = "Handling request rejection with given routing path - [%s]...";
    private static final String FORMAT_RESOLVING_REQUEST = "Handling request resolution with given routing path - [%s]...";
    private static final String FORMAT_NO_ACTION_FOUND = "No action found with given routing path - [%s]";

    private final ActionConfig config;
    private final ActionRegistry registry;

    public static ErrorResponse getResponse(Throwable exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }

    public Mono<ServerResponse> resolve(ServerRequest request) {
        if (ActionHandler.logger.isTraceEnabled()) {
            ActionHandler.logger.trace(String.format(FORMAT_RESOLVING_REQUEST, request.path()));
        }
        val name = this.config.getFullName(request);
        try {
            if (name.isPresent()) {
                return registry.getAction(name.get()).execute(request);
            }
        } catch (IngressException e) {
            if (ActionHandler.logger.isTraceEnabled()) {
                ActionHandler.logger.trace(e.getMessage());
            }
        }
        return reject(request);
    }

    public Mono<ServerResponse> reject(ServerRequest request) {
        if (ActionHandler.logger.isTraceEnabled()) {
            ActionHandler.logger.trace(String.format(FORMAT_REJECTING_REQUEST, request.path()));
        }
        val response = ErrorResponse.builder()
                .message(String.format(FORMAT_NO_ACTION_FOUND, request.path()))
                .build();
        return ServerResponse.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(response), ErrorResponse.class);
    }
}
