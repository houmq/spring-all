package com.summer.common.security.token.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Map;


/**
 * JwtTokenEnhancer
 *
 * Create By hmq
 * @date 2020/4/30 16:53
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Value("${summer.information:285565796@qq.com}")
    public String information;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken) oAuth2AccessToken;
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        additionalInformation.putAll(Collections.singletonMap("mail", information));
        accessToken.setAdditionalInformation(additionalInformation);
        return oAuth2AccessToken;
    }
}
