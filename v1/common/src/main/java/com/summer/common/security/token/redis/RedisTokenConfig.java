package com.summer.common.security.token.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@ConditionalOnProperty(prefix = "summer", name = "storeType", havingValue = "redis")
public class RedisTokenConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Bean
    public TokenStore tokenStore(){
        return new CustomRedisTokenStore(redisConnectionFactory);
    }

}
