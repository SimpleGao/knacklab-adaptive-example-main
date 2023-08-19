package io.knacklab.adaptive.egress.client;

import io.knacklab.adaptive.egress.exception.EgressException;
import org.springframework.web.reactive.function.client.WebClient;

public class BaseClientFactory implements WebclientFactory {
    private static final WebclientFactory instance = new BaseClientFactory();

    @Override
    public WebClient getWebclient(String baseUrl) throws EgressException {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    public static WebclientFactory getInstance() {
        return instance;
    }
}
