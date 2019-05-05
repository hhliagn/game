package com.game.user.mapInfo.service;

import com.game.user.mapInfo.entity.MapInfoEnt;
import com.game.user.mapInfo.model.MapInfo;

public interface IMapInfoService {

    MapInfo getMapInfo(String accountId);

    void saveMapInfoEnt(MapInfoEnt mapInfoEnt);

    void createMapInfo(String accountId);

    String getCurMap(String accountId);

    String getLastMap(String accountId);

    MapInfoEnt getMapInfoEnt(String accountId);
}
