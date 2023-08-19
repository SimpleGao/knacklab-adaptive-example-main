package io.knacklab.adaptive.example.ingress;

import io.knacklab.adaptive.example.context.KioskContext;
import io.knacklab.adaptive.example.context.UserContext;
import io.knacklab.adaptive.example.store.KioskManager;
import io.knacklab.adaptive.ingress.action.MessagingBase;
import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.core.ActionBase;
import io.knacklab.adaptive.ingress.exception.IngressException;
import io.knacklab.adaptive.ingress.messaging.IngressContext;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Service("start.journey")
public class StartJourney extends MessagingBase {
    private final KioskManager kioskManager;

    protected StartJourney(ActionConfig config, KioskManager kioskManager) {
        super(config, String.class, UserContext.class);
        this.kioskManager = kioskManager;
    }

    @Override
    public Mono<?> transform(IngressContext context, Object input) {
        val serverRequest = context.getRequest();
        val kioskId = serverRequest.pathVariable("kiosk-id");
        val kioskContext = KioskContext.builder()
                .kioskId(kioskId)
                .name("xxx")
                .build();

        kioskManager.saveContext(kioskId, kioskContext);
        return Mono.just(kioskContext);
    }
}
