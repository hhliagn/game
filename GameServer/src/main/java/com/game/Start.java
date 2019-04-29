package com.game;

import com.game.nettyServer.NettyServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

    private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationContext.start();

        SpringContext.getGlobalService().onStart();

        new Thread(new Runnable() {
            @Override
            public void run() {
                NettyServer nettyServer = new NettyServer();
            }
        }).start();
    }
}
