package com.libqa.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author edell.lee
 * @version 2020-06-23 09:51
 * @implNote
 */
@SpringBootApplication
@EnableCaching
public class RedisSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisSampleApplication.class);
    }
}
