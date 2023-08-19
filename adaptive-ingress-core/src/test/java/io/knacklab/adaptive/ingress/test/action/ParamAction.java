package io.knacklab.adaptive.ingress.test.action;

import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.core.ActionBase;
import io.knacklab.adaptive.ingress.exception.IngressException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Service("dummy")
@Profile("param")
class ParamAction extends ActionBase {
    public ParamAction(ActionConfig config) {
        super(config, String.class, String.class);
    }

    @Override
    protected Mono<?> transform(Object input, ServerRequest request) throws IngressException {
        return Mono.just(request.queryParams().toString());
    }
}
