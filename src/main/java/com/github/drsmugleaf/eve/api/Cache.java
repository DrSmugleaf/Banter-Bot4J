package com.github.drsmugleaf.eve.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
class Cache<K, V> {

    @Nonnull
    private final Map<K, V> CACHE = new HashMap<>();

    @Nonnull
    private final Map<K, Long> EXPIRES = new HashMap<>();

    Cache() {}

    private void checkExpire(@Nonnull K key) {
        if (EXPIRES.containsKey(key)) {
            if (EXPIRES.get(key) < System.currentTimeMillis()) {
                EXPIRES.remove(key);
                CACHE.remove(key);
            }
        }
    }

    @Nullable
    V get(K key) {
        checkExpire(key);

        if (!CACHE.containsKey(key)) {
            return null;
        }

        return CACHE.get(key);
    }

    void set(@Nonnull K key, @Nonnull V value, @Nonnull Long until) {
        CACHE.put(key, value);
        EXPIRES.put(key, until);
    }

    void set(@Nonnull K key, @Nonnull V value, @Nonnull Date until) {
        CACHE.put(key, value);
        EXPIRES.put(key, until.getTime());
    }

}
