package com.game;

import com.game.core.server.NettyServer;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

    private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) {

        //第一次启动自动创表,第二次启动注释掉
        autoCreateTable();

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationContext.start();

        SpringContext.getGlobalService().writeData();
        SpringContext.getGlobalService().onStart();

        //netty server
        new Thread(new Runnable() {
            @Override
            public void run() {
                NettyServer nettyServer = new NettyServer();
            }
        }).start();
    }

    private static void autoCreateTable() {
        Configuration cfg = new Configuration().configure();
        SchemaExport export = new SchemaExport(cfg);
        export.create(true, true);
    }
}
