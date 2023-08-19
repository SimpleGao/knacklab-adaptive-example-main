package io.knacklab.adaptive.egress.exchange;

import io.knacklab.adaptive.egress.exception.NotFoundException;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class LightExchangeRegistryFactory extends ServiceLocatorFactoryBean {
    public LightExchangeRegistryFactory() {
        this.setServiceLocatorInterface(LightExchangeRegistry.class);
        this.setServiceLocatorExceptionClass(NotFoundException.class);
    }
}
