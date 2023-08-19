package io.knacklab.adaptive.store.core;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface Store {
    <T> void saveState(String store, String key, T value, Class<T> valueClass);

    <T> T getState(String store, String key, Class<T> valueClass);

    <T> List<T> getStates(String store, Class<T> valuesClass);

    boolean hasState(String store, String key);

    void deleteState(String store, String key);

    void deleteStore(String store);

    Set<String> stores(String pattern);

    long incrementValue(String key);

    void expireKey(String key, Duration duration);

    Map<Object, Object> retrieveEntries(String store);
}
