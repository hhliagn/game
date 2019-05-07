package com.game.core.server;

import com.game.MessageHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static MessageHandler messageHandler = new MessageHandler();

    /*
     * 收到消息时，返回信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {

        messageHandler.setMessage(msg);
        messageHandler.handle();
        String revMsg = messageHandler.getMessage();

        // 返回客户端消息
        if (revMsg != null){
            ctx.writeAndFlush(revMsg +"\n");
        }
    }

    /*
     * 建立连接时，返回消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ \n");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端连接关闭!");
        super.channelInactive(ctx);
    }
}
