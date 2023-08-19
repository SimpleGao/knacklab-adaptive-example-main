package io.knacklab.adaptive.ingress.action;

import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.extractor.BodyExtractors;
import io.knacklab.adaptive.ingress.inserter.BodyInserters;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public abstract class MessagingBase extends ActionBase {

    protected MessagingBase(ActionConfig config, Class<?> requestClass, Class<?> responseClass) {
        this(config, requestClass, responseClass, false);
    }

    protected MessagingBase(ActionConfig config, Class<?> requestClass, Class<?> responseClass, boolean enableSession) {
        super(config, enableSession);
        this.executor.setBodyExtractor(BodyExtractors.objectDate(requestClass));
        this.executor.setBodyInserter(BodyInserters.objectDate(responseClass));
    }

    @Override
    public ServerRequest preExecute(ServerRequest serverRequest) {
        return serverRequest;
    }

    @Override
    public ServerResponse.BodyBuilder postExecute(ServerResponse.BodyBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON);
    }
}
