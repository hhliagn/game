package com.game.user.mapInfo.service;

import com.game.user.mapInfo.entity.MapInfoDao;
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
        return mapInfoDao.get(accountId);
    }

    public void saveMapInfoEnt(MapInfoEnt mapInfoEnt){
        mapInfoDao.save(mapInfoEnt);
    }
}
