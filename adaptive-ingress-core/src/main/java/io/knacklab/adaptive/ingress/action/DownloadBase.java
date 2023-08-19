package io.knacklab.adaptive.ingress.action;

import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.extractor.BodyExtractors;
import io.knacklab.adaptive.ingress.inserter.BodyInserters;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public abstract class DownloadBase extends ActionBase {
    protected DownloadBase(ActionConfig config, Class<?> requestClass) {
        this(config, requestClass, false);
    }

    protected DownloadBase(ActionConfig config, Class<?> requestClass, boolean enableSession) {
        super(config, enableSession);
        this.executor.setBodyInserter(BodyInserters.resourceDate());
        this.executor.setBodyExtractor(BodyExtractors.objectDate(requestClass));
    }

    @Override
    public ServerRequest preExecute(ServerRequest request) {
        return request;
    }

    @Override
    public ServerResponse.BodyBuilder postExecute(ServerResponse.BodyBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
    }
}
