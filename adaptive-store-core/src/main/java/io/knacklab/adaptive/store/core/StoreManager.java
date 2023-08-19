package io.knacklab.adaptive.store.core;

import io.knacklab.adaptive.store.config.StoreConfig;
import org.springframework.stereotype.Component;

@Component
public class StoreManager {
    private final StoreConfig storeConfig;
    private final StoreRegistry storeRegistry;

    public StoreManager(StoreConfig storeConfig, StoreRegistry storeRegistry) {
        this.storeConfig = storeConfig;
        this.storeRegistry = storeRegistry;
    }

    public Store getStore(String shortName) {
        return this.storeRegistry.getStore(
                this.storeConfig.getFullName(shortName));

    }
}
