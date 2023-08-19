//package io.knacklab.adaptive.egress.test.exchange;
//
//import io.knacklab.adaptive.egress.config.ExchangeConfig;
//import io.knacklab.adaptive.egress.core.ExchangeBase;
//import io.knacklab.adaptive.egress.test.messaging.GenderResponse;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//@Service("genderize.predict-gender")
//public class PredictGenderExchange extends ExchangeBase {
//    private static final String PARAM_NAME = "name";
//
//    public PredictGenderExchange(ExchangeConfig config) {
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
//                .bodyToMono(GenderResponse.class);
//    }
//}
