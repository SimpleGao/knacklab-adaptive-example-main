package io.knacklab.adaptive.store.core;

import io.knacklab.adaptive.store.expection.NotFoundException;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.stereotype.Component;

@Component
public final class StoreRegistryFactory extends ServiceLocatorFactoryBean {
    public StoreRegistryFactory() {
        this.setServiceLocatorInterface(StoreRegistry.class);
        this.setServiceLocatorExceptionClass(NotFoundException.class);
    }
}
