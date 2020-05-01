package com.pspaceplus.oauth.controller;

import com.pspaceplus.common.exception.GlobalErrorInfoException;
import com.pspaceplus.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TokenController
 *
 * Create By hmq
 * @date 2020/4/30 16:52
 */
@RestController
@RequestMapping("/oauth")
public class TokenController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRATION = "expiration";
    private static final String EXPIRES_IN = "expires_in";


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * postAccessToken
     *
     * @author hmq
     * @date 2020/4/30 17:14
     */
    @PostMapping("/token")
    public Result postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws Exception {
        OAuth2AccessToken token = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        if (null == token){
            throw new GlobalErrorInfoException("token 获取失败.");
        }
        return Result.ok(againEnhancer(token));
    }

    private Map<String, Object> againEnhancer(OAuth2AccessToken token){
        String shortToken = UUID.randomUUID().toString();
        redisConnectionFactory.getConnection().stringCommands().set(shortToken.getBytes(), token.getValue().getBytes());
        redisConnectionFactory.getConnection().expire(shortToken.getBytes(), token.getExpiresIn());
        Map<String, Object> data = new HashMap<>();
        data.put(ACCESS_TOKEN, shortToken);
        data.put(EXPIRATION, token.getExpiration());
        data.put(EXPIRES_IN, token.getExpiresIn());
        data.put("other", token.getAdditionalInformation());
        return data;
    }

}
