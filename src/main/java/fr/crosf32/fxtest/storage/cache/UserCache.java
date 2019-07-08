package fr.crosf32.fxtest.storage.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserCache {
    Cache<String, DataObject> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public Optional<String> getUserName() {
        return Optional.of(cache.getIfPresent("user_session").getName());
    }
}
