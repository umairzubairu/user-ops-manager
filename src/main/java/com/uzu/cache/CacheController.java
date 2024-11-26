package com.uzu.cache;

import com.uzu.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @DeleteMapping("/{userId}")
    public String clearCacheForUser(@PathVariable String userId) {
        cacheService.clearCacheForUser(userId);
        return "Cache cleared for user: " + userId;
    }

    @DeleteMapping("/")
    public String clearAllCache() {
        cacheService.clearAllUserProfilesCache();
        return "All user profile caches cleared.";
    }
}