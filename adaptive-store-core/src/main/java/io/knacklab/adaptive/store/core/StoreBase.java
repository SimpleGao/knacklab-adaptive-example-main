package io.knacklab.adaptive.store.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.knacklab.adaptive.store.config.StoreConfig;
import io.knacklab.adaptive.store.expection.StoreException;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class StoreBase implements Store {
    protected static final String FORMAT_STORE_NAME = "%s:%s";
    protected final StringRedisTemplate template;
    protected final ObjectMapper mapper;

    protected final StoreSpace spec;


    protected StoreBase(StoreConfig config, StringRedisTemplate template) {
        this.spec = config.getSpec(this.getFullName());
        this.mapper = new ObjectMapper();
        this.template = template;
    }

    @Override
    public <T> void saveState(String store, String key, T value, Class<T> valueClass) {
        Objects.requireNonNull(store);
        Objects.requireNonNull(key);
        Objects.requireNonNull(valueClass);
        try {
            val json = this.mapper.writeValueAsString(value);
            template.opsForHash().put(this.getStoreName(store), key, json);
        } catch (JsonProcessingException e) {
            throw new StoreException(e.getMessage());
        }
    }

    @Override
    public <T> T getState(String store, String key, Class<T> valueClass) {
        Objects.requireNonNull(store);
        Objects.requireNonNull(key);
        String json = (String) template.opsForHash().get(this.getStoreName(store), key);

        try {
            return this.mapper.readValue(json, valueClass);
        } catch (JsonProcessingException e) {
            throw new StoreException(e.getMessage());
        }
    }

    @Override
    public <T> List<T> getStates(String store, Class<T> valuesClass) {
        Objects.requireNonNull(store);
        Objects.requireNonNull(valuesClass);
        return template.opsForHash().values(this.getStoreName(store))
                .stream()
                .map(json -> {
                    try {
                        return this.mapper.readValue((JsonParser) json, valuesClass);
                    } catch (IOException e) {
                        throw new StoreException(e.getMessage());
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasState(String store, String key) {
        Objects.requireNonNull(store);
        Objects.requireNonNull(key);
        return template.opsForHash().hasKey(this.getStoreName(store), key);
    }

    @Override
    public void deleteState(String store, String key) {
        Objects.requireNonNull(store);
        Objects.requireNonNull(key);
        template.opsForHash().delete(getStoreName(store), key);
    }

    @Override
    public void deleteStore(String store) {
        Objects.requireNonNull(store);
        template.delete(this.getStoreName(store));
    }

    @Override
    public Set<String> stores(String pattern) {
        Objects.requireNonNull(pattern);
        return template.keys(pattern);
    }

    @Override
    public long incrementValue(String key) {
        return template.boundValueOps(key).increment(1);
    }

    @Override
    public void expireKey(String key, Duration duration) {
        template.expire(key, duration);
    }

    @Override
    public Map<Object, Object> retrieveEntries(String store) {
        return template.opsForHash().entries(this.getStoreName(store));
    }

    protected String getFullName() {
        return this.getClass().getAnnotation(Service.class).value();
    }

    private String getStoreName(String store) {
        return String.format(FORMAT_STORE_NAME, this.spec.getPrefix(), store);
    }
}
