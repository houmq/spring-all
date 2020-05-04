package com.summer.oauth;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * OauthApplication
 *
 * Create By hmq
 * @date 2020/4/30 17:15
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@MapperScan(basePackages = {"com.summer.oauth.dao"})
@ComponentScan(value = {"com.summer.common.security","com.summer.common.exception","com.summer.oauth"})
public class OauthApplication {

    private static Logger logger = LoggerFactory.getLogger(OauthApplication.class);

    @Value("${spring.profiles.active}")
    private String profile;


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(OauthApplication.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.info("OAUTH ACTIVE PROFILE ======== :{}", profile);
        }
    }


}
