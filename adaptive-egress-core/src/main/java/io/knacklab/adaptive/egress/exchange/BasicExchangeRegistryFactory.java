package io.knacklab.adaptive.egress.exchange;

import io.knacklab.adaptive.egress.exception.NotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class BasicExchangeRegistryFactory extends ServiceLocatorFactoryBean {
    public BasicExchangeRegistryFactory() {
        this.setServiceLocatorInterface(BasicExchangeRegistry.class);
        this.setServiceLocatorExceptionClass(NotFoundException.class);
    }

}
