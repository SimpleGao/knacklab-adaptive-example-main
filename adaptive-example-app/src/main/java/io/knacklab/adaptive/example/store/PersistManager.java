package io.knacklab.adaptive.example.store;

import io.knacklab.adaptive.example.context.Context;
import io.knacklab.adaptive.example.context.KioskContext;

public interface PersistManager<T extends Context> {
    void saveContext( String key, T conText);

}
