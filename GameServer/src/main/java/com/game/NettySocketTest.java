package com.game;

import com.game.nettyServer.NettyServer;

/**
 * @author lhh
 * @date 2019/4/27 13:09
 */
public class NettySocketTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NettyServer nettyServer = new NettyServer();
            }
        }).start();
    }
}
