package io.knacklab.adaptive.example.egress;

import com.fasterxml.jackson.databind.JsonNode;
import io.knacklab.adaptive.egress.config.ExchangeConfig;
import io.knacklab.adaptive.egress.core.ExchangeBase;
import io.knacklab.adaptive.egress.exception.EgressException;
import io.knacklab.adaptive.egress.exchange.RestExchangeBase;
import io.knacklab.adaptive.egress.messaging.EgressRequest;
import io.knacklab.adaptive.example.messaging.ExchangeContext;
import io.knacklab.adaptive.example.messaging.egress.GenderOutput;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("genderize.predict-gender")
public class PredictGender extends RestExchangeBase {
    private static final String PARAM_NAME = "name";

    public PredictGender(ExchangeConfig config) {
        super(config, JsonNode.class, JsonNode.class);
    }


    @Override
    protected EgressRequest makeRequest(Object input, Object content) {
        val headers = new HttpHeaders();
        val context = (ExchangeContext) content;
        headers.add("X-TEST", "test");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        return EgressRequest.builder()
                .headers(headers)
                .path(this.spec.getPattern())
                .body(input)
                .build();
    }

    @Override
    protected <T> T makeResponse(JsonNode output, Class<T> outputClass) throws EgressException {
        return convertOutput(output, outputClass);
    }
}
