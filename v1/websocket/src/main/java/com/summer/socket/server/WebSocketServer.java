package com.summer.socket.server;

import com.summer.socket.handler.ServerInitializerHandler;
import com.summer.socket.listener.ServerStarterListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServer {

    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private static final int SO_BACKLOG = 128;
    private static final int BOSS_THREADS = 1;

    private final int port;
    private final String path;


    public WebSocketServer(int port, String path) {
        this.port = port;
        this.path = path;
    }


    public void run(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup(BOSS_THREADS);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, SO_BACKLOG)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerInitializerHandler(path));

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.addListener(new ServerStarterListener());
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("SOCKET SERVER 启动失败--:{}" ,e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
