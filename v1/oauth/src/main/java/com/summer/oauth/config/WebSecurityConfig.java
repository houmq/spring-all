package com.summer.oauth.config;

import com.summer.common.security.handler.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * WebSecurityConfig
 *
 * Create By hmq
 * @date 2020/4/30 17:13
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ALLOW_URL = new String[]{"/actuator/**","/logout/**"};

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;


    /**
     * authenticationManagerBean
     * 认证管理器
     * @author hmq
     * @date 2020/4/28 15:16
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * passwordEncoder
     * 密码编码器
     * @author hmq
     * @date 2020/4/28 15:17
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * configure
     * WEB安全拦截机制
     * @author hmq
     * @date 2020/4/28 15:17
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(ALLOW_URL).permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(customLogoutSuccessHandler);

    }

}
