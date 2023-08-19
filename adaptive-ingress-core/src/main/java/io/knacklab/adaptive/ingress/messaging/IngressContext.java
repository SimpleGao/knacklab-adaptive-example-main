package io.knacklab.adaptive.ingress.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.WebSession;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngressContext {
    private ServerRequest request;
    private WebSession session;
}
