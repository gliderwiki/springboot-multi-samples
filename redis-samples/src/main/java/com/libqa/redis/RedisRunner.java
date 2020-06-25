package com.libqa.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author edell.lee
 * @version 2020-06-24 12:17
 * @implNote
 */
@Component
public class RedisRunner implements ApplicationRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ValueOperations<String, String> valueMap = redisTemplate.opsForValue();
        valueMap.set("edell", "lee");
        valueMap.set("cafe", "ciel");
        valueMap.set("kakao", "corp");
        valueMap.set("hello", "world");
    }
}
