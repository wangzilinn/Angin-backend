package com.wangzilin.site.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wangzilin.site.model.file.Painting;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangzilinn@gmail.com
 * @Date: 9/25/2021 3:00 PM
 */
@Component
public class CacheManager {

    Cache<String,String> captchaCache;
    Cache<String, Painting> paintingCache;

    HashMap<CacheType, Cache<String, ?>> cacheMap = new HashMap<>();

    public enum  CacheType{
        CAPTCHA(String.class),
        PAINTING(Painting.class);

        private final Class<?> clazz;

        CacheType(Class<?> clazz) {
            this.clazz = clazz;
        }

    }

    public CacheManager() {
        captchaCache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .build();

        paintingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .build();

        cacheMap.put(CacheType.CAPTCHA, captchaCache);
        cacheMap.put(CacheType.PAINTING, paintingCache);
    }

    public <T> void put(CacheType cacheType, String key, T value){
        Cache<String, T> cache = (Cache<String, T>) cacheMap.get(cacheType);
        cache.put(key, value);
    }

    public <T> T get(CacheType cacheType, String key){
        Cache<String, ?> cache = cacheMap.get(cacheType);
        return (T)cache.getIfPresent(key);
    }

    public void remove(CacheType cacheType, String key) {
        Cache<String, ?> cache = cacheMap.get(cacheType);
        cache.invalidate(key);
    }
}
