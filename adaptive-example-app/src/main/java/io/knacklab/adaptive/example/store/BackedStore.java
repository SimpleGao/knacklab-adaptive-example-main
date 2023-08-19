package io.knacklab.adaptive.example.store;

import io.knacklab.adaptive.store.config.StoreConfig;
import io.knacklab.adaptive.store.core.StoreBase;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service("store.backend-api")
public class BackedStore extends StoreBase {

    protected BackedStore(StoreConfig config, StringRedisTemplate template) {
        super(config, template);
    }
}
