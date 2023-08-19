package io.knacklab.adaptive.egress.exchange;

import io.knacklab.adaptive.egress.client.BaseClientFactory;
import io.knacklab.adaptive.egress.client.WebclientFactory;
import io.knacklab.adaptive.egress.config.ExchangeConfig;
import io.knacklab.adaptive.egress.core.ExchangeBase;
import io.knacklab.adaptive.egress.core.ResponseBuilders;
import reactor.core.publisher.Mono;

import java.util.Collections;

public abstract class BasicExchangeBase extends ExchangeBase implements BasicExchange {
    protected BasicExchangeBase(ExchangeConfig config) {
        this(config, BaseClientFactory.getInstance());
    }

    protected BasicExchangeBase(ExchangeConfig config, WebclientFactory clientFactory) {
        super(config, clientFactory);
    }

    @Override
    public <T> Mono<T> execute(Class<T> outputClazz, Object input) {
        return execute(outputClazz, input, Collections.EMPTY_LIST.toArray());
    }

    @Override
    public <T> Mono<T> execute(Class<T> outputClazz, Object input, Object... args) {
        this.executor.setResponseBuilder(ResponseBuilders.objectBuilder(outputClazz));
        return this.executor.execute(input, args).cast(outputClazz);
    }
}
