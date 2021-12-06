package com.tvmsoftware.redis;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class RedisCacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private AppConfig appConfig;

    @Test()
    void setAndGetBeforeTimeoutWorks() throws InterruptedException {
        cacheService.put("key1", "value1");

        Thread.sleep(appConfig.getTimeout()/2 * 1000L);

        String result = cacheService.get("key1");

        assertEquals("value1",result);
    }

    @Test()
    void getWhenKeyMissingReturnsNull() {
        String result = cacheService.get("key2");
        assertNull(result);
    }

    @Test()
    void setAndGetAfterExpireReturnsNull() throws InterruptedException {
        cacheService.put("key3", "value3");

        Thread.sleep(appConfig.getTimeout() * 1000L + 500L);

        String result = cacheService.get("key3");

        assertNull(result);
    }

    @Test
    void gettingValueExtendsRollingWindow() throws InterruptedException {
        cacheService.put("key4", "value4");

        Thread.sleep(appConfig.getTimeout() * 900L);

        cacheService.get("key4");

        Thread.sleep(appConfig.getTimeout() * 900L);

        String result = cacheService.get("key4");

        assertEquals("value4",result);
    }

}