package com.game.user.mapInfo.service;

import com.game.user.mapInfo.dao.MapInfoDao;
import com.game.user.mapInfo.entity.MapInfoEnt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapInfoService implements IMapInfoService {

    private static final Logger logger = LoggerFactory.getLogger("mapInfo");

    @Autowired
    private MapInfoDao mapInfoDao;

    @Override
    public MapInfoEnt getMapInfoEnt(String accountId) {
        MapInfoEnt mapInfoEnt = mapInfoDao.get(accountId);
        return mapInfoEnt;
    }

    public void saveMapInfoEnt(String accountId){
        mapInfoDao.save(getMapInfoEnt(accountId));
    }

    public void saveMapInfoEnt(MapInfoEnt mapInfoEnt){
        mapInfoDao.save(mapInfoEnt);
    }

    public void createMapInfoEnt(String accountId){
        MapInfoEnt mapInfoEnt = new MapInfoEnt();
        mapInfoEnt.setAccountId(accountId);
        mapInfoEnt.setCurMapId(1);
        mapInfoEnt.setLastMapId(1);
        saveMapInfoEnt(mapInfoEnt);
        logger.info("新增新用户地图数据");
    }
}
