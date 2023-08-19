package io.knacklab.adaptive.example.store.Impl;

import io.knacklab.adaptive.example.context.Context;
import io.knacklab.adaptive.example.context.KioskContext;
import io.knacklab.adaptive.example.store.PersistManager;
import io.knacklab.adaptive.store.core.Store;
import io.knacklab.adaptive.store.core.StoreManager;
import org.springframework.data.redis.core.StringRedisTemplate;

public abstract class PersistManagerImpl<T extends Context> implements PersistManager<T> {
    private final Store store;
    private String bucket;

    public PersistManagerImpl(String bucket, StoreManager storeManager, StringRedisTemplate template) {
        this.bucket = bucket;
        this.store = storeManager.getStore("store.backend-api");
    }

    @Override
    public void saveContext(String key, Context conText) {
        store.saveState(key, bucket, conText, Context.class);
    }
}

