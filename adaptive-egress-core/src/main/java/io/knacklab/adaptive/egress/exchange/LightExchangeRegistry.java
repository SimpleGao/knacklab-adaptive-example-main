package io.knacklab.adaptive.egress.exchange;

public interface LightExchangeRegistry {
    LightExchange getExchange(String name);
}
