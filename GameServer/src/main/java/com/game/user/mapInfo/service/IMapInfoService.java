package com.game.user.mapInfo.service;

import com.game.user.mapInfo.entity.MapInfoEnt;

public interface IMapInfoService {

    void saveMapInfoEnt(MapInfoEnt mapInfoEnt);

    MapInfoEnt getMapInfoEnt(String accountId);
}
