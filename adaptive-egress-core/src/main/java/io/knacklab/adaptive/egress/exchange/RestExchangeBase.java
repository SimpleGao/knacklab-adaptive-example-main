package io.knacklab.adaptive.egress.exchange;

import com.fasterxml.jackson.databind.JsonNode;
import io.knacklab.adaptive.egress.client.BaseClientFactory;
import io.knacklab.adaptive.egress.client.WebclientFactory;
import io.knacklab.adaptive.egress.config.ExchangeConfig;
import io.knacklab.adaptive.egress.core.ExchangeBase;
import io.knacklab.adaptive.egress.core.ResponseBuilders;
import io.knacklab.adaptive.egress.exception.EgressException;
import io.knacklab.adaptive.egress.messaging.EgressRequest;
import lombok.val;
import reactor.core.publisher.Mono;

public abstract class RestExchangeBase extends BasicExchangeBase implements RestExchange {
    protected final Class<?> contextClass;
    protected final Class<?> outputClass;
    protected final Class<?> inputClass;
    protected Class<?> outputClazz;

    protected RestExchangeBase(ExchangeConfig config, Class<?> inputClass, Class<?> outputClass) {
        this(config, BaseClientFactory.getInstance(), inputClass, outputClass, Object.class);
    }

    protected RestExchangeBase(ExchangeConfig config, WebclientFactory factory, Class<?> inputClass, Class<?> outputClass, Class<?> contentClass) {
        super(config, factory);
        this.executor.setResponseBuilder(ResponseBuilders.objectBuilder(JsonNode.class));
        this.contextClass = contentClass;
        this.outputClass = outputClass;
        this.inputClass = inputClass;
    }

    @Override
    public EgressRequest build(Object input, Object... args) {
        ensureInput();
        val json = getJsonNde(input);
        return makeRequest(buildInput(json, inputClass), getContent(args));
    }

    @Override
    public Object transform(Object output) {
        val json = (JsonNode) output;
        try {
            return castResponse(makeResponse(json, outputClass), outputClazz);
        } catch (Exception e) {
            throw ExchangeBase.mapError(e);
        }
    }

    @Override
    public <T> Mono<T> execute(Class<T> outputClazz, Object input, Object... args) {
        this.outputClazz = outputClazz;
        return this.executor.execute(input, args).cast(outputClazz);
    }

    protected void ensureInput(Object... args) {
        if (args.length > 0 && !contextClass.isInstance(args[0])) {
            throw new EgressException("Context Type MisMatch -" + this.getFullName());
        }
    }

    protected JsonNode getJsonNde(Object input) {
        if (inputClass.isInstance(input)) {
            return ExchangeBase.BeanToJson(input);
        }
        return (JsonNode) input;
    }

    protected Object getContent(Object... args) {
        return args.length > 0 ? args[0] : null;
    }

    protected abstract EgressRequest makeRequest(Object input, Object content);

    protected abstract <T> T makeResponse(JsonNode output, Class<T> outputClass) throws EgressException;
}

