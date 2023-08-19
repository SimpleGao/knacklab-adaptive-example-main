package io.knacklab.adaptive.ingress.extractor;

import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class FormExtractor implements BodyExtractor{
    public FormExtractor() {
    }

    @Override
    public Mono<?> extractBody(ServerRequest request) {
        return request.body(BodyExtractors.toFormData());
    }
}
