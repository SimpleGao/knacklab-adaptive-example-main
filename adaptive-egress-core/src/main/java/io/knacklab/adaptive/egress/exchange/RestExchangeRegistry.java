package io.knacklab.adaptive.egress.exchange;

public interface RestExchangeRegistry {
    RestExchange getExchange(String name);
}
