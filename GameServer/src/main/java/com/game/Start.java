package com.game;

import com.game.user.mapInfo.model.MapInfo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

    private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationContext.start();

        //SpringContext.getMapService().initMapData();
        MapInfo mapInfo = SpringContext.getMapInfoService().getMapInfo("lhh");
        mapInfo.setCurMapId(2);
        SpringContext.getMapInfoService().saveMapInfo(mapInfo);
    }
}
