package com.pspaceplus.oauth.server;

import com.pspaceplus.common.security.handler.CustomAuthenticationEntryPoint;
import com.pspaceplus.common.security.handler.CustomWebResponseExceptionTranslator;
import com.pspaceplus.common.security.token.jwt.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;


/**
 * AuthorizationServer
 *
 * Create By hmq
 * @date 2020/4/30 17:14
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;


    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JwtTokenEnhancer();
    }


    /**
     * clientDetailsService
     * 客户端配置 appID appsecret 保存再数据库中
     * @author hmq
     * @date 2020/4/27 16:24
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }


    /**
     * authorizationCodeServices
     * 设置授权码模式的授权码如何存取
     * @author hmq
     * @date 2020/4/27 16:28
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource){
        return new JdbcClientDetailsService(dataSource);
    }


    /**
     * configure
     * 客户端详情服务
     * @author hmq
     * @date 2020/4/27 16:27
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService);
    }


    /**
     * configure
     *
     * @author hmq
     * @date 2020/4/28 14:55
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        if (null != accessTokenConverter){
            DefaultTokenServices service = new DefaultTokenServices();
            service.setClientDetailsService(jdbcClientDetailsService);  //客户端详情服务
            service.setTokenStore(tokenStore);                      //令牌存储策略
//            service.setSupportRefreshToken(true);
            //令牌增强
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer()));
            service.setTokenEnhancer(tokenEnhancerChain);

            endpoints
                    .authenticationManager(authenticationManager)           //认证管理器
                    .authorizationCodeServices(authorizationCodeServices)   //授权码服务
                    .tokenServices(service)                                 //令牌管理服务
                    .allowedTokenEndpointRequestMethods(HttpMethod.POST);
        } else {
            endpoints
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)           //认证管理器
                    .authorizationCodeServices(authorizationCodeServices)   //授权码服务
                    .allowedTokenEndpointRequestMethods(HttpMethod.POST);
        }

        endpoints.exceptionTranslator(customWebResponseExceptionTranslator);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security.tokenKeyAccess("permitAll()")             //oauth/token_key是公开
                .checkTokenAccess("permitAll()")           //oauth/check_token公开
                .allowFormAuthenticationForClients()       //表单认证（申请令牌)
                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }


}
