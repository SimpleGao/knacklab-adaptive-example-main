package io.knacklab.adaptive.ingress.test.suite;

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
@ActiveProfiles("path")
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@TypeExcludeFilters({WebFluxTypeExcludeFilter.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IngressPathTest {
    @Autowired
    @SuppressWarnings("unused")
    private WebTestClient webClient;

    @Test
    void testForDeleteMethod() {
        String expected = "/delete";
        this.webClient
                .delete()
                .uri(expected)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(expected);
    }

    @Test
    void testForGetMethod() {
        String expected = "/get";
        this.webClient
                .get()
                .uri(expected)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(expected);
    }

    @Test
    void testForPostMethod() {
        String expected = "/post";
        this.webClient
                .post()
                .uri(expected)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(expected);
    }

    @Test
    void testForPutMethod() {
        String expected = "/put";
        this.webClient
                .put()
                .uri(expected)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(expected);
    }
}
