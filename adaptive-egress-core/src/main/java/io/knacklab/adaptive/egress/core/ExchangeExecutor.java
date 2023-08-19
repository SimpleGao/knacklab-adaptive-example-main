package io.knacklab.adaptive.egress.core;

import io.knacklab.adaptive.egress.client.WebclientFactory;
import io.knacklab.adaptive.egress.exception.EgressException;
import io.knacklab.adaptive.egress.messaging.EgressRequest;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

import static io.knacklab.adaptive.egress.messaging.EgressRequest.BodyType.Value;

public class ExchangeExecutor {
    private final WebclientFactory factory;
    private final ExchangeSpec spec;
    private ResponseBuilder responseBuilder;
    private RequestBuilder requestBuilder;
    private Transformer transformer;
    private EgressRequest request;


    public ExchangeExecutor(WebclientFactory factory, ExchangeSpec spec) {
        this.factory = factory;
        this.spec = spec;
    }

    public void setResponseBuilder(ResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    public void setRequestBuilder(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

    public Mono<?> execute(Object input, Object... args) {
        ensureDependencies();
        request = requestBuilder.build(input, args);
        if (Objects.isNull(request)) {
            throw new EgressException("ExchangeRequest Is Null");
        }
        return responseBuilder.build(getResponse(), transformer)
                .onErrorMap(ExchangeBase::mapError);
    }

    private void ensureDependencies() {
        if (Objects.isNull(requestBuilder)) {
            throw new RuntimeException("RequestBuilder Is Null");
        }
        if (Objects.isNull(responseBuilder)) {
            throw new RuntimeException("ResponseBuilder Is Null");
        }
        if (Objects.isNull(transformer)) {
            throw new RuntimeException("Transformer Is Null");
        }
    }

    private WebClient.ResponseSpec getResponse() {
        return buildClient()
                .method(spec.getHttpMethod())
                .uri(getPath())
                .headers(this::putHeaders)
                .body(getBody())
                .retrieve()
                .onStatus(HttpStatus::isError, ExchangeBase::handleError);
    }

    private WebClient buildClient() {
        Assert.notNull(factory, "WebClientFactory Is Mull");
        Assert.notNull(spec, "ExchangeSpec Is Mull");
        if (spec.isMock()) {
            throw new EgressException("WebClient Is Null");
        }
        return factory.getWebclient(spec.getBaseUrl());
    }

    private String getPath() {
        return Optional.ofNullable(request.getPath())
                .orElseGet(spec::getPattern);
    }

    private void putHeaders(HttpHeaders headers) {
        val inputs = request.getHeaders();
        if (Objects.nonNull(inputs)) {
            headers.putAll(inputs);
        }
    }

    private BodyInserter<?, ? super ClientHttpRequest> getBody() {
        val body = request.getBody();
        switch (request.getBodyType()) {
            case Value:
                return BodyInserters.fromValue(body);
            case Form:
                return BodyInserters.fromFormData(ExchangeBase.getFormData(body));
            case Multipart:
                return BodyInserters.fromMultipartData(ExchangeBase.getMultipartData(body));
            default:
                return BodyInserters.empty();
        }
    }
}
