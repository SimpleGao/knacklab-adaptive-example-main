package io.knacklab.adaptive.ingress.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ActionRoute {
    private static final String MOCK_SUFFIX = ".mock";

    private boolean mock;
    private String method;
    private String pattern;
    private String name;

    public static String getShortName(String fullName) {
        if (fullName.contains(MOCK_SUFFIX)) {
            return fullName.replace(MOCK_SUFFIX, Strings.EMPTY);
        }
        return fullName;
    }

    public String getFullName() {
        val result = new StringBuilder(this.name);
        if (this.mock) {
            result.append(MOCK_SUFFIX);
        }
        return result.toString();
    }

    public boolean isMatched(String fullName) {
        return this.name.equals(
                ActionRoute.getShortName(fullName));
    }

    public boolean isMatched(ServerRequest request) {
        val path = UriComponentsBuilder
                .fromPath(this.pattern)
                .build(request.pathVariables())
                .getPath();
        return path.equals(request.path()) &&
                method.equalsIgnoreCase(request.methodName());
    }

    public RequestPredicate toPredicate() {
        return method(getHttpMethod())
                .and(path(this.pattern))
                .and(accept(MediaType.APPLICATION_JSON));
    }

    private HttpMethod getHttpMethod() {
        return HttpMethod.valueOf(this.method.toUpperCase());
    }
}
