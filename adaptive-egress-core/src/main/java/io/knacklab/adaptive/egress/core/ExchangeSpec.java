package io.knacklab.adaptive.egress.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ExchangeSpec {
    private static final String MOCK_SUFFIX = ".mock";

    private boolean mock;
    private String method;
    private String pattern;
    private String baseUrl;
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

    public HttpMethod getHttpMethod() {
        return HttpMethod.valueOf(this.method.toUpperCase());
    }

    public boolean isMatched(String shortName) {
        return this.name.equals(shortName);
    }
}
