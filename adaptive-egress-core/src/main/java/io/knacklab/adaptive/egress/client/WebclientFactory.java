package io.knacklab.adaptive.egress.client;

import io.knacklab.adaptive.egress.exception.EgressException;
import org.springframework.web.reactive.function.client.WebClient;

public interface WebclientFactory {
    WebClient getWebclient(String baseUrl) throws EgressException;
}
