package io.knacklab.adaptive.ingress.core;

import io.knacklab.adaptive.ingress.action.Action;

public interface ActionRegistry {
    Action getAction(String name);
}
