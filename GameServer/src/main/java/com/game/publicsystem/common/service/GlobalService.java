package com.game.publicsystem.common.service;

import com.game.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalService {

    private static Logger logger = LoggerFactory.getLogger("ON-OFF");

    public void onStart(){

        logger.info("初始化账号数据。。。");
        SpringContext.getAccountService().loadAllAccountInfo();

        logger.info("初始化地图数据。。。");
        SpringContext.getMapService().initMapData();
    }
}
