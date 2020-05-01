package com.summer.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * ConfigApplication
 *
 * Create By hmq
 * @date 2020/4/30 17:19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@MapperScan(basePackages = {"com.pspaceplus.config.dao"})
@ComponentScan(value = {"com.pspaceplus"})
public class ConfigApplication {

    private static Logger logger = LoggerFactory.getLogger(ConfigApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ConfigApplication.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.info("CONFIG SERVER ACTIVE PROFILE ======: {}", profile);
        }
    }


}
