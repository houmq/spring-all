package com.summer.socket;

import com.summer.socket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * WebSocketRunner
 *
 * Create By hmq
 * @date 2020/5/22 11:24
 */
@Component
public class WebSocketRunner implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(WebSocketRunner.class);

    @Value("${socket.port:8898}")
    private int port;

    @Value("${socket.path:/path}")
    private String path;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("SOCKET RUNNER RUNNING....");
        new WebSocketServer(port, path).run();

    }
}
