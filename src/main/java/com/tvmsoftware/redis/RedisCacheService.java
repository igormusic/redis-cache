package com.tvmsoftware.redis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

@Service
public class RedisCacheService implements CacheService{

    private final Jedis jedis = new Jedis();
    private final AppConfig config;

    public RedisCacheService(AppConfig config) {
        this.config = config;
    }

    @Override
    public void put(String key, String value) {
        Transaction t = jedis.multi();

        t.set(key,value);
        t.expire(key, config.getTimeout());

        t.exec();
    }

    @Override
    public String get(String key) {
        Transaction t = jedis.multi();

        Response<String> response = t.get(key);
        t.expire(key,config.getTimeout());

        t.exec();

        return response.get();
    }
}
