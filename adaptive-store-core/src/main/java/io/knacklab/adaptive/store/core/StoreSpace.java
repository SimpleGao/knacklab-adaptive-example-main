package io.knacklab.adaptive.store.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.logging.log4j.util.Strings;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class StoreSpace {
    private static final String MOCK_SUFFIX = ".mock";
    private boolean mock;
    private String prefix;
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

    public boolean isMatched(String shortName) {
        return this.name.equals(shortName);
    }
}
