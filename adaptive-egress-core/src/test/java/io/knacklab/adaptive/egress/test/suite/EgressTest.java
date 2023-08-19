//package io.knacklab.adaptive.egress.test.suite;
//
//import io.knacklab.adaptive.egress.core.ExchangeManager;
//import io.knacklab.adaptive.egress.exception.EgressException;
//import io.knacklab.adaptive.egress.test.messaging.AgeResponse;
//import io.knacklab.adaptive.egress.test.messaging.GenderResponse;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
//import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//@AutoConfigureJson
//@AutoConfigureCache
//@AutoConfigureWebFlux
//@ContextConfiguration
//@ImportAutoConfiguration
//@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class EgressTest {
//    @Autowired
//    @SuppressWarnings("unused")
//    private ExchangeManager exchangeManager;
//
//    @Test
//    void testPredictAgeExchange() {
//        final Exchange exchange = this.exchangeManager.getExchange("agify.predict-age");
//        final Mono<AgeResponse> response = exchange.cast().execute("Lucile", String.class, AgeResponse.class);
//        StepVerifier.create(response)
//                .assertNext(res -> {
//                    Assert.assertNotNull(res);
//                    Assert.assertNotNull(res.getAge());
//                    Assert.assertNotNull(res.getName());
//                    Assert.assertTrue(res.getCount() > 0);
//                })
//                .verifyComplete();
//    }
//
//    @Test
//    void testPredictAgeExchangeWithError() {
//        final Exchange exchange = this.exchangeManager.getExchange("agify.predict-age");
//        final Mono<AgeResponse> response = exchange.cast().execute(null, String.class, AgeResponse.class);
//        StepVerifier.create(response)
//                .expectError(EgressException.class)
//                .verify();
//    }
//
//    @Test
//    void testPredictGenderExchange() {
//        final Exchange exchange = this.exchangeManager.getExchange("genderize.predict-gender");
//        final Mono<GenderResponse> response = exchange.cast().execute("Apolline", String.class, GenderResponse.class);
//        StepVerifier.create(response)
//                .assertNext(res -> {
//                    Assert.assertNotNull(res);
//                    Assert.assertNotNull(res.getName());
//                    Assert.assertNotNull(res.getGender());
//                    Assert.assertTrue(res.getProbability() > 0.1);
//                    Assert.assertTrue(res.getCount() > 0);
//                })
//                .verifyComplete();
//    }
//
//    @Test
//    void testPredictGenderExchangeWithError() {
//        final Exchange exchange = this.exchangeManager.getExchange("genderize.predict-gender");
//        final Mono<GenderResponse> response = exchange.cast().execute(null, String.class, GenderResponse.class);
//        StepVerifier.create(response)
//                .expectError(EgressException.class)
//                .verify();
//    }
//}
