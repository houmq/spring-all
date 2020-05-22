package com.summer.socket.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * ServerInitializerHandler
 *
 * Create By hmq
 * @date 2020/5/22 11:46
 */
public class ServerInitializerHandler extends ChannelInitializer<SocketChannel> {

    private final String path;

    public ServerInitializerHandler(String path) {
        this.path = path;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());                                 // http 编解码
        pipeline.addLast(new ChunkedWriteHandler());                             // 是以块方式写
        pipeline.addLast(new HttpObjectAggregator(1024 * 100)); // 数据传输大小
        pipeline.addLast(new WebSocketServerProtocolHandler(path));              // ws://localhost:7000/path
        pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
        pipeline.addLast(new ServerInboundHandler());                            //自定义
    }
}
