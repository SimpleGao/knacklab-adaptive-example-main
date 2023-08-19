//package io.knacklab.adaptive.egress.test.exchange;
//
//import io.knacklab.adaptive.egress.config.ExchangeConfig;
//import io.knacklab.adaptive.egress.core.ExchangeBase;
//import io.knacklab.adaptive.egress.test.messaging.AgeResponse;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//@Service("agify.predict-age")
//public class PredictAgeExchange extends ExchangeBase {
//    private static final String PARAM_NAME = "name";
//
//    public PredictAgeExchange(ExchangeConfig config) {
//        super(config);
//    }
//
//    @Override
//    protected Mono<?> transform(Object input) {
//        String name = String.valueOf(input);
//        return this.client
//                .method(this.spec.getHttpMethod())
//                .uri(builder -> builder.path(this.spec.getPattern())
//                        .queryParam(PARAM_NAME, name)
//                        .build())
//                .retrieve()
//                .bodyToMono(AgeResponse.class);
//    }
//}
