package io.knacklab.adaptive.example.ingress;

import com.fasterxml.jackson.databind.JsonNode;
import io.knacklab.adaptive.egress.core.ExchangeBase;
import io.knacklab.adaptive.egress.core.ExchangeManager;
import io.knacklab.adaptive.example.messaging.ExchangeContext;
import io.knacklab.adaptive.example.messaging.ingress.PredictResponse;
import io.knacklab.adaptive.ingress.action.MessagingBase;
import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.exception.ActionException;
import io.knacklab.adaptive.ingress.exception.IngressException;
import io.knacklab.adaptive.ingress.messaging.IngressContext;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("ingress.predict")
public class PredictAction extends MessagingBase {
    private static final String PARAM_NAME = "name";
    private static final String MESSAGE_QUERY_PARAM_NOT_FOUND = "Query Parameter 'name' Not Found";
    final private ExchangeManager exchangeManager;

    public PredictAction(ActionConfig config, ExchangeManager exchangeManager) {
        super(config, String.class, PredictResponse.class);
        this.exchangeManager = exchangeManager;
    }


    @Override
    public Mono<?> transform(IngressContext context, Object input) {
        val request = context.getRequest();
        val name = request.queryParam(PARAM_NAME);
        if (name.isPresent()) {
            val content = ExchangeContext.builder()
                    .kioskId("")
                    .ticketId(name.get())
                    .build();
            final val agify = this.exchangeManager.getRest("agify.predict-age");
            final Mono<JsonNode> ageOutput = agify.
                    execute(JsonNode.class, ExchangeBase.BeanToJson(), content)
                    .cast(JsonNode.class)
                    .onErrorMap(ex -> new ActionException(HttpStatus.NOT_FOUND, ex.getMessage()));

            final val genderize = this.exchangeManager.getRest("genderize.predict-gender");
            final Mono<JsonNode> genderOutput = genderize
                    .execute(JsonNode.class, ExchangeBase.BeanToJson(), content)
                    .cast(JsonNode.class)
                    .onErrorMap(ex -> new ActionException(HttpStatus.NOT_FOUND, ex.getMessage()));

            return Mono.zip(ageOutput, genderOutput, (age, gender) -> PredictResponse.builder()
                    .name(age.textValue())
                    .age(age.textValue())
                    .gender(gender.asText())
                    .build());
        }
        return Mono.error(new IngressException(MESSAGE_QUERY_PARAM_NOT_FOUND));
    }
}
