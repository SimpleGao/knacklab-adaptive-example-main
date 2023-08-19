package io.knacklab.adaptive.example.test.suite.ingress;

import io.knacklab.adaptive.example.messaging.ingress.PredictResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTypeExcludeFilter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureJson
@AutoConfigureCache
@AutoConfigureWebFlux
@ContextConfiguration
@ImportAutoConfiguration
@ActiveProfiles("mock-egress")
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@TypeExcludeFilters({WebFluxTypeExcludeFilter.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EgressMockingTest {
    @Autowired
    @SuppressWarnings("unused")
    private WebTestClient webClient;

    @Test
    void testPredictAction() {
        final String name = "Lucile";
        PredictResponse expected = PredictResponse.builder()
                .name(name)
                .age("23")
                .gender("male")
                .build();
        this.webClient
                .get()
                .uri(builder -> builder.path("/predict-gender-age")
                        .queryParam("name", name)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(PredictResponse.class).isEqualTo(expected);
    }
}
