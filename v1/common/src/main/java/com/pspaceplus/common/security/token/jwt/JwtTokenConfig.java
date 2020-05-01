package com.pspaceplus.common.security.token.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@ConditionalOnProperty(prefix = "pspaceplus.token", name = "storeType", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenConfig {

    @Value("${pspaceplus.token.jwt.key}")
    private String SIGNING_KEY;


    /**
     * tokenStore
     * JWT令牌存储
     * @author hmq
     * @date 2020/4/28 18:37
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }


}
