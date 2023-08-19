package io.knacklab.adaptive.ingress.core;

import io.knacklab.adaptive.ingress.exception.NotFoundException;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.stereotype.Component;

@Component
public final class ActionRegistryFactory extends ServiceLocatorFactoryBean {
    public ActionRegistryFactory() {
        this.setServiceLocatorInterface(ActionRegistry.class);
        this.setServiceLocatorExceptionClass(NotFoundException.class);
    }
}
