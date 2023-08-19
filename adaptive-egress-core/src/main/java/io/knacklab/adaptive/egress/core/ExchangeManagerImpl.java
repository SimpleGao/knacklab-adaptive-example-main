package io.knacklab.adaptive.egress.core;

import io.knacklab.adaptive.egress.config.ExchangeConfig;
import io.knacklab.adaptive.egress.exchange.*;
import org.springframework.stereotype.Component;

@Component
public class ExchangeManagerImpl implements ExchangeManager {
    private final ExchangeConfig exchangeConfig;
    private final BasicExchangeRegistry basicExchangeRegistry;

    private final LightExchangeRegistry lightExchangeRegistry;

    private final RestExchangeRegistry restExchangeRegistry;

    public ExchangeManagerImpl(ExchangeConfig exchangeConfig, BasicExchangeRegistry basicExchangeRegistry,
                               LightExchangeRegistry lightExchangeRegistry, RestExchangeRegistry restExchangeRegistry) {
        this.exchangeConfig = exchangeConfig;
        this.basicExchangeRegistry = basicExchangeRegistry;
        this.lightExchangeRegistry = lightExchangeRegistry;
        this.restExchangeRegistry = restExchangeRegistry;
    }

    @Override
    public BasicExchange getBasic(String shortName) {
        return this.basicExchangeRegistry.getExchange(
                this.exchangeConfig.getFullName(shortName));
    }

    @Override
    public LightExchange getLight(String shortName) {
        return this.lightExchangeRegistry.getExchange(
                this.exchangeConfig.getFullName(shortName));
    }

    @Override
    public RestExchange getRest(String shortName) {
        return this.restExchangeRegistry.getExchange(
                this.exchangeConfig.getFullName(shortName));
    }
}
