package io.knacklab.adaptive.ingress.config;

import io.knacklab.adaptive.ingress.core.ActionHandler;
import io.knacklab.adaptive.ingress.core.ActionRoute;
import io.knacklab.adaptive.ingress.exception.IngressException;
import io.knacklab.adaptive.ingress.exception.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Data
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "ingress", ignoreInvalidFields = true)
@ComponentScan("io.knacklab.adaptive.ingress")
public class ActionConfig {
    private static final String FORMAT_NO_ACTION_FOUND = "No action found with given name - [%s]";
    private List<ActionRoute> actions = new ArrayList<>();

    public Optional<String> getFullName(ServerRequest request) {
        return this.actions.stream()
                .filter(action -> action.isMatched(request))
                .map(ActionRoute::getFullName)
                .findFirst();
    }

    public ActionRoute getRoute(String fullName) throws IngressException {
        val result = this.actions.stream()
                .filter(action -> action.isMatched(fullName))
                .findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        throw new NotFoundException(String.format(FORMAT_NO_ACTION_FOUND, fullName));
    }

    @Bean
    public RouterFunction<ServerResponse> actionDispatcher(ActionHandler handler) {
        return this.actions.stream()
                .map(action -> route(action.toPredicate(), handler::resolve))
                .reduce(RouterFunction::and)
                .orElse(route(RequestPredicates.all(), handler::reject));
    }

}
