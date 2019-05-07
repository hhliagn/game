package com.game.user.mapInfo.service;

import com.game.user.mapInfo.entity.MapInfoEnt;

public interface IMapInfoService {

    void saveMapInfoEnt(String accountId);

    void saveMapInfoEnt(MapInfoEnt mapInfoEnt);

    void createMapInfoEnt(String accountId);

    MapInfoEnt getMapInfoEnt(String accountId);
}
