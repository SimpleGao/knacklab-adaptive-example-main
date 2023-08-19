package io.knacklab.adaptive.example.store.Impl;

import io.knacklab.adaptive.example.context.Context;
import io.knacklab.adaptive.example.context.KioskContext;
import io.knacklab.adaptive.example.store.KioskManager;
import io.knacklab.adaptive.store.core.StoreManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class KioskMangerImpl extends PersistManagerImpl<KioskContext> implements KioskManager {

    private static final String BUCKET = "kiosk-context";

    protected KioskMangerImpl(StoreManager storeManager, StringRedisTemplate template) {
        super(BUCKET, storeManager, template);
    }
}
