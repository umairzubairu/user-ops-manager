package com.uzu.user.api.cache.service;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public void clearCacheForUser(String userId) {
        cacheManager.getCache("userProfiles").evict(userId);
    }

    public void clearAllUserProfilesCache() {
        cacheManager.getCache("userProfiles").clear();
    }

    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
    public void evictAllCaches() {
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

}