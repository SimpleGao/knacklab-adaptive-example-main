package io.knacklab.adaptive.egress.client;

import io.knacklab.adaptive.egress.exception.EgressException;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.val;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

public class WeekClientFactory implements WebclientFactory {

    private static final WebclientFactory instance = new WeekClientFactory();

    @Override
    public WebClient getWebclient(String baseUrl) throws EgressException {
        try {
            val content = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
            val client = HttpClient.create()
                    .secure(spec -> spec.sslContext(content));
            return WebClient.builder()
                    .baseUrl(baseUrl)
                    .clientConnector(new ReactorClientHttpConnector(client))
                    .build();
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebclientFactory getInstance() {
        return instance;
    }

}
