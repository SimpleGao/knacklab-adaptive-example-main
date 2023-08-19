package io.knacklab.adaptive.egress.exchange;

import io.knacklab.adaptive.egress.exception.NotFoundException;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class RestExchangeRegistryFactory extends ServiceLocatorFactoryBean {

    public RestExchangeRegistryFactory() {
        this.setServiceLocatorInterface(RestExchangeRegistry.class);
        this.setServiceLocatorExceptionClass(NotFoundException.class);
    }
}
