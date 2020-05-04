package com.summer.gateway;


import com.summer.gateway.filter.TokenVerifyGatewayFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


/**
 * GatewayApplication
 *
 * Create By hmq
 * @date 2020/4/30 18:07
 */
@SpringBootApplication(exclude = ReactiveUserDetailsServiceAutoConfiguration.class)
@EnableDiscoveryClient
public class GatewayApplication {

    private static Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(GatewayApplication.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.info("gateway active profiles =======:{}", profile);
        }
    }

    @Bean
    public TokenVerifyGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new TokenVerifyGatewayFilterFactory();
    }

}
