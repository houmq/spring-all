package com.summer.eureka;


import ch.qos.logback.access.ViewStatusMessagesServlet;
import ch.qos.logback.access.tomcat.LogbackValve;
import org.apache.commons.lang.StringUtils;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;


@EnableEurekaServer
@SpringBootApplication
@ServletComponentScan
public class EurekaServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(EurekaServerApplication.class);

    @Value("${spring.profiles.active}")
    private String active;


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(EurekaServerApplication.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.info("EUREKA SERVER ACTIVE PROFILE ==========:{}", profile);
        }
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> customize(){
        return (ConfigurableServletWebServerFactory factory) -> {
            if(factory instanceof TomcatServletWebServerFactory){
                TomcatServletWebServerFactory serverFactory = (TomcatServletWebServerFactory)factory;
                LogbackValve logbackValve = new LogbackValve();
                logbackValve.setFilename("logback-access-" + active + ".xml");
                serverFactory.addContextValves(logbackValve);
                serverFactory.addConnectorCustomizers(connector -> {
                    AbstractHttp11Protocol httpProtocol = (AbstractHttp11Protocol) connector.getProtocolHandler();
                    httpProtocol.setCompression("on");
                    httpProtocol.setCompressionMinSize(256);
                    String[] mimeTypes = httpProtocol.getCompressibleMimeTypes();
                    String mimeTypesWithJson = StringUtils.join(mimeTypes, ",") + "," + MediaType.APPLICATION_JSON_VALUE;
                    httpProtocol.setCompressibleMimeType(mimeTypesWithJson);
                    httpProtocol.setMaxConnections(200);
                    httpProtocol.setMaxThreads(200);
                    httpProtocol.setSessionTimeout(3000);
                    httpProtocol.setConnectionTimeout(3000);

                });
            }
        };
    }


    @Bean
    public ServletRegistrationBean accessLog(){
        ServletRegistrationBean<ViewStatusMessagesServlet> bean = new ServletRegistrationBean<>(new ViewStatusMessagesServlet());
        bean.addUrlMappings("/log-access");
        return bean;
    }



}
