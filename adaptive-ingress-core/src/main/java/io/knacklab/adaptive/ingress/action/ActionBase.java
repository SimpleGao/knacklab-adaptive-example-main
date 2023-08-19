package io.knacklab.adaptive.ingress.action;

import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.core.ActionExecutor;
import io.knacklab.adaptive.ingress.core.ActionRoute;
import io.knacklab.adaptive.ingress.core.ExecutorAware;
import io.knacklab.adaptive.ingress.core.Transformer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class ActionBase implements Action, ExecutorAware, Transformer {
    protected final ActionExecutor executor;
    protected final ActionRoute route;

    protected ActionBase(ActionConfig config) {
        this(config, false);
    }

    protected ActionBase(ActionConfig config, boolean enableSession) {
        this.executor = new ActionExecutor(enableSession);
        this.route = config.getRoute(this.getFullName());
        this.executor.setExecutorAware(this);
        this.executor.setTransformer(this);
    }

    @Override
    public Mono<ServerResponse> execute(ServerRequest request) {
        return executor.execute(request);
    }

    private String getFullName() {
        return this.getClass().getAnnotation(Service.class).value();
    }
}
