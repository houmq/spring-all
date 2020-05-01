package com.summer.gateway.filter;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.summer.common.util.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;


/**
 * TokenAnalysisGatewayFilterFactory
 *
 * Create By hmq
 * @date 2020/4/30 8:57
 */
public class TokenVerifyGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenVerifyGatewayFilterFactory.Config> {


    private static String userInfo = "";

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    public TokenVerifyGatewayFilterFactory() {
        super(Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return  Arrays.asList("name", "resource", "ignore");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return verify(config);
    }

    private GatewayFilter verify(Config config){
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String path = exchange.getRequest().getURI().getPath();

            if (config.getIgnore().contains(path)){
                return chain.filter(exchange);
            }

            if (StringUtils.isNotBlank(token)){
                String jwtToken = jwtToken(token);
                if (StringUtils.isNotBlank(jwtToken)){
                    String info = JwtHelper.decode(jwtToken).getClaims();
                    if (access(info, config.getResource())){
                        String authToken = "bearer " + jwtToken;
                        ServerHttpRequest request = exchange.getRequest().mutate().header(config.getName(), userInfo)
                                .header(HttpHeaders.AUTHORIZATION, authToken).build();
                        return chain.filter(exchange.mutate().request(request).build());
                    }
                    return writeError(exchange, Result.error("resource unavailable !!"));
                }
                return writeError(exchange, Result.error("token timeout !!"));
            }
            return writeError(exchange,Result.error("token cannot be empty !!"));
        };
    }


    private String jwtToken(String uuidToken){
        byte[] bytes = redisConnectionFactory.getConnection().stringCommands().get(uuidToken.getBytes());
        if (null != bytes && bytes.length != 0){
            return new String(bytes, IOUtils.UTF8);
        }
        return null;
    }

    private Mono<Void> writeError(ServerWebExchange exchange, Result result){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(JSONObject.toJSONString(result).getBytes());
        return exchange.getResponse().writeWith(Flux.just(dataBuffer));
    }


    private boolean access(String jsonString, String resource){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONArray aud = jsonObject.getJSONArray("aud");
        userInfo = jsonObject.getString("user_name");
        return aud.contains(resource);
    }


    public static class Config {

        private String name;
        private String resource;
        private String ignore;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public List<String> getIgnore() {
            return Arrays.asList(ignore.split("#"));
        }

        public void setIgnore(String ignore) {
            this.ignore = ignore;
        }
    }
}
