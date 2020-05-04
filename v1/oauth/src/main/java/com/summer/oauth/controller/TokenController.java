package com.summer.oauth.controller;

import com.summer.common.dto.TokenDto;
import com.summer.common.exception.GlobalErrorInfoException;
import com.summer.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
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

    private TokenDto againEnhancer(OAuth2AccessToken token){
        String shortToken = UUID.randomUUID().toString();
        redisConnectionFactory.getConnection().stringCommands().set(shortToken.getBytes(), token.getValue().getBytes());
        redisConnectionFactory.getConnection().expire(shortToken.getBytes(), token.getExpiresIn());
        return TokenDto.builder().accessToken(shortToken).expiration(token.getExpiration())
                .expiresIn(token.getExpiresIn()).other(token.getAdditionalInformation()).build();
    }

}
