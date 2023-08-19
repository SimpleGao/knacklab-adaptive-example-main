package io.knacklab.adaptive.ingress.test.action;

import io.knacklab.adaptive.ingress.config.ActionConfig;
import io.knacklab.adaptive.ingress.core.ActionBase;
import io.knacklab.adaptive.ingress.exception.IngressException;
import io.knacklab.adaptive.ingress.test.messaging.MessagingResponse;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Service("dummy")
@Profile("messaging")
public class MessagingAction extends ActionBase {
    private static final String PARAM_NAME = "name";
    private static final String MESSAGE_QUERY_PARAM_NOT_FOUND = "Query Parameter 'name' Not Found";

    public MessagingAction(ActionConfig config) {
        super(config, Object.class, MessagingResponse.class);
    }

    @Override
    protected Mono<?> transform(Object input, ServerRequest request) throws IngressException {
        val name = request.queryParam(PARAM_NAME);
        if (name.isPresent()) {
            return Mono.just(MessagingResponse.builder()
                    .name(name.get())
                    .age("25")
                    .gender("female")
                    .build());
        }
        return Mono.error(new IngressException(MESSAGE_QUERY_PARAM_NOT_FOUND));
    }
}
