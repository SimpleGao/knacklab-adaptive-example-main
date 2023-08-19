package io.knacklab.adaptive.store.core;

public interface StoreRegistry {
    Store getStore(String name);
}
