package com.summer.socket.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ServerInboundHandler
 * 入站
 * Create By hmq
 * @date 2020/5/22 12:04
 */
public class ServerInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Logger logger = LoggerFactory.getLogger(ServerInboundHandler.class);
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {

        logger.info("channelGroup size === :{}" ,channelGroup.size());
        int capacity = frame.content().capacity();
        String text = frame.text();
        if (4 == capacity){
            if ("PING".equals(text)){
                ctx.channel().writeAndFlush(new TextWebSocketFrame(Unpooled.copiedBuffer("PONG".getBytes())));
            }
            if ("PONG".equals(text)){
                ctx.channel().writeAndFlush(new TextWebSocketFrame(Unpooled.copiedBuffer("PING".getBytes())));
            }
        }

        logger.info("收到消息 ====：{}", text);
    }

    /**
     * userEventTriggered
     *
     * @author hmq
     * @date 2020/5/22 15:06
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            //将  evt 向下转型 IdleStateEvent
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            logger.warn(eventType);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(Unpooled.copiedBuffer("PINGPONG".getBytes())));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel active ====:{}", ctx.channel().id().asLongText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel inactive ====:{}", ctx.channel().id().asLongText());
        super.channelInactive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel added ====:{}", ctx.channel().id().asLongText());
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel removed ====:{}", ctx.channel().id().asLongText());
        logger.info("channelGroup size === :{}" ,channelGroup.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error("channel exception ====:{}", ctx.channel().id().asLongText());
        ctx.close();
    }



}
