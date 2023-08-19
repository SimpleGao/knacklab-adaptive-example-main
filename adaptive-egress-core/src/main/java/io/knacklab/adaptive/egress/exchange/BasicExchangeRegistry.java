package io.knacklab.adaptive.egress.exchange;

public interface BasicExchangeRegistry {
    BasicExchange getExchange(String name);
}
