package com.pspaceplus.eureka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;


@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(EurekaServerApplication.class);


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(EurekaServerApplication.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.info("EUREKA SERVER ACTIVE PROFILE ==========:{}", profile);
        }
    }


}
