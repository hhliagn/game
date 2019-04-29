package com.game.user.mapInfo.service;

import com.game.user.mapInfo.model.MapInfo;

public interface IMapInfoService {

    MapInfo getMapInfo(String accountId);

    void saveMapInfo(MapInfo mapInfo);

    void createMapInfo(String accountId);

    String getCurMap(String accountId);

    String getLastMap(String accountId);
}
