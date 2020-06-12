package com.summer.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisUtil {

    @Autowired(required = false)
    private StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {

    }
}
