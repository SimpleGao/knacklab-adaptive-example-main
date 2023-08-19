package io.knacklab.adaptive.ingress.test.suite;

import io.knacklab.adaptive.ingress.test.messaging.MessagingResponse;
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
@ActiveProfiles("messaging")
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@TypeExcludeFilters({WebFluxTypeExcludeFilter.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IngressMessagingTest {
    @Autowired
    @SuppressWarnings("unused")
    private WebTestClient webClient;

    @Test
    void testMessagingAction() {
        final String name = "Lucile";
        MessagingResponse expected = MessagingResponse.builder()
                .name(name)
                .age("25")
                .gender("female")
                .build();
        this.webClient
                .get()
                .uri(builder -> builder.path("/messaging")
                        .queryParam("name", name)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(MessagingResponse.class).isEqualTo(expected);
    }
}
