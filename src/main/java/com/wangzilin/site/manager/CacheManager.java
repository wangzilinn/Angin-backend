package com.wangzilin.site.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 9/25/2021 3:00 PM
 */
@Component
public class CacheManager {
    HashMap<String, String> map = new HashMap<>();
    Cache<String,String> cache;

    public CacheManager() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .build();
    }

    public void put(String key, String value){
        cache.put(key, value);
    }

    public String get(String key){
        return cache.getIfPresent(key);
    }

    public void remove(String key) {
        cache.invalidate(key);
    }
}
