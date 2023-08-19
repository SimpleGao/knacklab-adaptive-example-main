package io.knacklab.adaptive.egress.core;

import io.knacklab.adaptive.egress.exchange.BasicExchange;
import io.knacklab.adaptive.egress.exchange.LightExchange;
import io.knacklab.adaptive.egress.exchange.RestExchange;

public interface ExchangeManager {

    BasicExchange getBasic(String shortName);

    LightExchange getLight(String shortName);

    RestExchange getRest(String shortName);
}
