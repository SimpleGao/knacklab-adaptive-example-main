package io.knacklab.adaptive.egress.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.knacklab.adaptive.egress.client.WebclientFactory;
import io.knacklab.adaptive.egress.config.ExchangeConfig;
import io.knacklab.adaptive.egress.exception.EgressException;
import io.knacklab.adaptive.egress.exception.ExchangeException;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.awt.image.DataBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ExchangeBase implements RequestBuilder, Transformer {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    protected final ExchangeExecutor executor;
    protected final ExchangeSpec spec;


    protected ExchangeBase(ExchangeConfig config, WebclientFactory factory) {
        this.spec = config.getSpec(getFullName());
        this.executor = new ExchangeExecutor(factory, spec);
        this.executor.setResponseBuilder(ResponseBuilders.voidBuilder());
        this.executor.setRequestBuilder(this);
        this.executor.setTransformer(this);
    }

    public static JsonNode BeanToJson() {
        return MAPPER.createObjectNode();
    }

    public static JsonNode BeanToJson(Object bean) {
        if (Objects.isNull(bean)) {
            return BeanToJson();
        }
        return MAPPER.valueToTree(bean);
    }

    public static <T> T convertOutput(JsonNode output, Class<T> outputClass) {
        try {
            return ExchangeBase.MAPPER.treeToValue(output, outputClass);
        } catch (JsonProcessingException exception) {
            throw ExchangeBase.mapError(exception);
        }
    }

    protected static <T> T castResponse(Object output, Class<T> outputClass) throws EgressException {
        if (Objects.equals(outputClass, JsonNode.class)) {
            return outputClass.cast(MAPPER.valueToTree(output));
        } else if (outputClass.isInstance(output)) {
            return outputClass.cast(output);
        }
        throw new EgressException("Cast Output Fail");
    }

    protected static MultiValueMap<String, String> getFormData(Object body) {
        val result = new LinkedMultiValueMap<String, String>();
        val input = result.getClass().cast(body);
        for (val key : input.keySet()) {
            val list = Objects.requireNonNull(input.get(key));
            result.addAll(String.valueOf(key), castList(list, String.class));
        }
        return result;
    }

    protected static MultiValueMap<String, ?> getMultipartData(Object body) {
        val result = new LinkedMultiValueMap<String, Object>();
        val input = result.getClass().cast(body);
        for (val key : input.keySet()) {
            val list = Objects.requireNonNull(input.get(key));
            result.addAll(String.valueOf(key), castList(list, Object.class));
        }
        return result;
    }

    protected static <T> List<T> castList(List<?> list, Class<T> elementClass) {
        return list.stream()
                .map(elementClass::cast)
                .collect(Collectors.toList());
    }

    protected static List<DataBuffer> getDataBufferList(Object output) {
        return output instanceof List<?>
                ? convertList((List<?>) output)
                : Collections.emptyList();
    }

    protected static List<DataBuffer> convertList(List<?> list) {
        return list.stream()
                .map(ExchangeBase::toDataBuffer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected static DataBuffer toDataBuffer(Object input) {
        return input instanceof DataBuffer ? (DataBuffer) input : null;
    }

    protected static Mono<ExchangeException> handleError(ClientResponse response) {
        return response.bodyToMono(String.class).map(ExchangeException::new);
    }

    protected static EgressException mapError(Throwable exception) {
        return new EgressException(exception.getMessage());
    }

    protected <T> T buildInput(JsonNode input, Class<T> inputClass) {
        try {
            if (inputClass == JsonNode.class) {
                return inputClass.cast(input);
            }
            return inputClass.cast(MAPPER.treeToValue(input, inputClass));
        } catch (Exception exception) {
            throw mapError(exception);
        }
    }

    protected String getFullName() {
        return getClass().getAnnotation(Service.class).value();
    }

}
