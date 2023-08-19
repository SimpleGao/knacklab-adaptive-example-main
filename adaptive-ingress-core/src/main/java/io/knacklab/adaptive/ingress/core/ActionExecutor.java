package io.knacklab.adaptive.ingress.core;

import io.knacklab.adaptive.ingress.action.Action;
import io.knacklab.adaptive.ingress.exception.ActionException;
import io.knacklab.adaptive.ingress.exception.EmptyException;
import io.knacklab.adaptive.ingress.exception.IngressException;
import io.knacklab.adaptive.ingress.extractor.BodyExtractor;
import io.knacklab.adaptive.ingress.inserter.BodyInserter;
import io.knacklab.adaptive.ingress.messaging.ErrorResponse;
import io.knacklab.adaptive.ingress.messaging.IngressContext;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class ActionExecutor implements Action {
    private final boolean enableSession;
    private ExecutorAware executorAware;
    private Transformer transformer;
    private BodyExtractor bodyExtractor;
    private BodyInserter bodyInserter;

    public ActionExecutor(boolean enableSession) {
        this.enableSession = enableSession;
    }

    public void setExecutorAware(ExecutorAware executorAware) {
        this.executorAware = executorAware;
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

    public void setBodyExtractor(BodyExtractor bodyExtractor) {
        this.bodyExtractor = bodyExtractor;
    }

    public void setBodyInserter(BodyInserter bodyInserter) {
        this.bodyInserter = bodyInserter;
    }

    @Override
    public Mono<ServerResponse> execute(ServerRequest request) {
        ensureDependencies();
        try {
            executorAware.preExecute(request);
        } catch (IngressException error) {
            return buildError(error);
        }
        return bodyExtractor.extractBody(request)
                .switchIfEmpty(Mono.error(EmptyException.build()))
                .flatMap(input -> handleInput(request, input))
                .onErrorResume(EmptyException.class, ignored -> handleInput(request, null))
                .onErrorResume(exception -> buildError(exception));
    }

    private void ensureDependencies() {
        if (Objects.isNull(executorAware)) {
            throw new ActionException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
        if (Objects.isNull(bodyExtractor)) {
            throw new ActionException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
        if (Objects.isNull(bodyInserter)) {
            throw new ActionException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
        if (Objects.isNull(transformer)) {
            throw new ActionException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }

    }

    private static Mono<ServerResponse> buildError(Throwable throwable) {
        return Mono.just(throwable)
                .map(throwable1 -> mapException(throwable1))
                .flatMap(error -> ServerResponse.status(error.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(ActionHandler.getResponse(error)), ErrorResponse.class));
    }

    private static ActionException mapException(Throwable exception) {
        if (exception instanceof ActionException) {
            return (ActionException) exception;
        }
        if (exception instanceof IngressException) {
            return new ActionException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }

        return new ActionException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private Mono<ServerResponse> handleInput(ServerRequest request, Object input) {
        return getSession(request)
                .switchIfEmpty(Mono.error(EmptyException.build()))
                .flatMap(session -> transform(request, session, input))
                .onErrorResume(EmptyException.class, ignored -> transform(request, null, input));
    }

    private Mono<ServerResponse> transform(ServerRequest request, WebSession session, Object input) {
        val context = IngressContext.builder()
                .request(request)
                .session(session)
                .build();
        return transformer.transform(context, input)
                .flatMap(output -> handleOutput(output));
    }

    private Mono<ServerResponse> handleOutput(Object output) {
        val builder = executorAware.postExecute(ServerResponse.ok());
        return bodyInserter.insertBody(builder, output);
    }

    private Mono<WebSession> getSession(ServerRequest request) {
        return enableSession ? request.session() : Mono.empty();
    }
}
