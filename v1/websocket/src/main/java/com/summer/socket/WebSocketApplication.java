package com.summer.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class WebSocketApplication {

    private static Logger logger = LoggerFactory.getLogger(WebSocketApplication.class);

    @Value("${spring.profiles.active}")
    private String profile;


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(WebSocketApplication.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.info("WEBSOCKET ACTIVE PROFILE ======== :{}", profile);
        }
    }

}
