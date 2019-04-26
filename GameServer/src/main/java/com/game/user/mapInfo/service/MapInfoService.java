package com.game.user.mapInfo.service;

import com.game.map.entity.MapEnt;
import com.game.map.service.IMapService;
import com.game.user.mapInfo.dao.MapInfoDao;
import com.game.user.mapInfo.entity.MapInfoEnt;
import com.game.user.mapInfo.model.MapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapInfoService implements IMapInfoService {

    @Autowired
    private MapInfoDao mapInfoDao;

    public MapInfo getMapInfo(String accountId){
        MapInfoEnt mapInfoEnt = mapInfoDao.findOne(accountId);
        return MapInfo.valueOf(mapInfoEnt);
    }

    public void saveMapInfo(MapInfo mapInfo){
        mapInfoDao.save(mapInfo);
    }
}
