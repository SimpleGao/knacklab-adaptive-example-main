package io.knacklab.adaptive.egress.client;

import io.knacklab.adaptive.egress.exception.EgressException;
import org.springframework.web.reactive.function.client.WebClient;

public class BasicClientFactory implements WebclientFactory {

    private final int bufferSize;

    public BasicClientFactory(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public WebClient getWebclient(String baseUrl) throws EgressException {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(bufferSize))
                .build();
    }

    public static WebclientFactory getInstance(int bufferSize) {
        return new BasicClientFactory(bufferSize);
    }
}
