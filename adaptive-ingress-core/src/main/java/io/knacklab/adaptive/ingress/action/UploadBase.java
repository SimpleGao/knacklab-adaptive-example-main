package io.knacklab.adaptive.ingress.action;

import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.extractor.BodyExtractors;
import io.knacklab.adaptive.ingress.inserter.BodyInserters;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public abstract class UploadBase extends ActionBase {

    protected UploadBase(ActionConfig config, Class<?> responseClass) {
        this(config, responseClass, false);

    }

    protected UploadBase(ActionConfig config, Class<?> responseClass, boolean enableSession) {
        super(config, enableSession);
        this.executor.setBodyExtractor(BodyExtractors.multiPartDate());
        this.executor.setBodyInserter(BodyInserters.objectDate(responseClass));
    }

    @Override
    public ServerRequest preExecute(ServerRequest request) {
        return request;
    }

    @Override
    public ServerResponse.BodyBuilder postExecute(ServerResponse.BodyBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON);
    }
}
