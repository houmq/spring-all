package com.summer.socket.listener;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ServerStarterListener
 * <p>
 * Create By hmq
 *
 * @date 2020/5/22 11:40
 */
public class ServerStarterListener implements ChannelFutureListener {

    private static Logger logger = LoggerFactory.getLogger(ServerStarterListener.class);

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            logger.info("socket server 启动成功...");
        } else {
            logger.info("socket server 启动失败...");
        }
    }
}
