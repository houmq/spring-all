package com.summer.config.config;

import com.summer.common.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;



/**
 * ResourceServerConfig
 *
 * Create By hmq
 * @date 2020/4/30 17:16
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Value("${pspaceplus.resource.id}")
    public String RESOURCE_ID;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID)          //资源id
                .tokenStore(tokenStore)
                .stateless(true)
                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/**").access("#oauth2.hasScope('CONFIG')")
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
    }

}
