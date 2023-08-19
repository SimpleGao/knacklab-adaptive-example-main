package io.knacklab.adaptive.example.test.suite.egress;

import io.knacklab.adaptive.egress.core.ExchangeManager;
import io.knacklab.adaptive.example.messaging.egress.AgeOutput;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@AutoConfigureJson
@AutoConfigureCache
@AutoConfigureWebFlux
@ContextConfiguration
@ImportAutoConfiguration
@RunWith(SpringRunner.class)
@ActiveProfiles("mock-egress")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PredictAgeTest {
    @Autowired
    @SuppressWarnings("unused")
    private ExchangeManager exchangeManager;

    @Test
    void testPredictAge() {
        final String name = "foo";
//        final Exchange exchange = this.exchangeManager.getExchange("agify.predict-age");
//        final Mono<AgeOutput> response = exchange.cast().execute(name, String.class, AgeOutput.class);
//        StepVerifier.create(response)
//                .assertNext(res -> {
//                    Assert.assertNotNull(res);
//                    Assert.assertEquals("23", res.getAge());
//                    Assert.assertEquals(name, res.getName());
//                    Assert.assertEquals(1000, res.getCount());
//                })
//                .verifyComplete();
    }
}
