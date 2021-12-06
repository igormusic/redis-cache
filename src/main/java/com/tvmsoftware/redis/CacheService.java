package com.tvmsoftware.redis;

public interface CacheService {
    void put(String key, String value);
    String get(String key);
}
