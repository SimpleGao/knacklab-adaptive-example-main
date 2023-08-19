package io.knacklab.adaptive.egress.core;

import io.knacklab.adaptive.egress.messaging.EgressRequest;

public interface RequestBuilder {
    EgressRequest build(Object input, Object... args);
}
