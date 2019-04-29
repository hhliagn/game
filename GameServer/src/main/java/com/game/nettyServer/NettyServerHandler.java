package com.game.nettyServer;

import com.game.SpringContext;
import com.game.map.model.Map;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetAddress;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    /*
     * 收到消息时，返回信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {

        String revMsg = null;

        String[] split = msg.split(" ");

        switch (split[0]) {
            case "quit":
                ctx.close();
                break;
            case "move":
                try {
                    String mapName = split[1];
                    SpringContext.getWorldService().changeMap(mapName);
                    //String mapName = SpringContext.getMapService().getMap(mapId);
                    revMsg = "切换地图成功，当前地图为：" + mapName;
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "login":
                try {
                    String accountId = split[1];
                    String password = split[2];
                    SpringContext.getLoginService().login(accountId, password);
                    revMsg = "登录成功";
                    break;
                } catch (Exception e) {
                    revMsg = e.getMessage();
                }
                break;
            case "register":
                try{
                    String account = split[1];
                    String nickName = split[2];
                    SpringContext.getRegisterService().register(account, nickName);
                    revMsg = "注册成功！";
                }catch (Exception e){
                    String message = e.getMessage();
                    revMsg = message;
                    break;
                }
                break;
            case "logout":
                try {
                    //String accountIdc = split[1];
                    SpringContext.getLogoutService().logout();
                    revMsg = "登出成功";
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "enter-the-world":
                try {
                    String curMapName = SpringContext.getWorldService().enterTheWorld();
                    revMsg = "进入世界成功，当前地图为：" + curMapName;
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "print-map":
                try {
                    String mapName = split[1];
                    String result = SpringContext.getWorldService().printMap(mapName);
                    revMsg = result;
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "print-account":
                try {
                    String result = SpringContext.getGlobalService().printAccount();
                    revMsg = result;
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "clear":
                revMsg = "clear";
                break;
        }

        // 返回客户端消息
        if (revMsg!=null){
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
