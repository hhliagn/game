package com.game;

import com.game.nettyServer.NettyServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lhh
 * @date 2019/4/27 13:09
 */
public class NettySocketTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClassPathXmlApplicationContext applicationContext
                        = new ClassPathXmlApplicationContext("applicationContext.xml");
                applicationContext.start();
                NettyServer nettyServer = new NettyServer();
            }
        }).start();
    }
}
