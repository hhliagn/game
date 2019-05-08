package com.game.core.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    public static MyClientWindow frame;

    public void setFrame(MyClientWindow frame) {
        this.frame = frame;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.equals("clear")){
            frame.clear();
        }else {
            frame.appendText(msg);
            //Message.revMsg = msg;
            System.out.println("客户端接受的消息: " + msg);
        }
    }

    //
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("正在连接... ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接关闭! ");
        super.channelInactive(ctx);
    }
}
