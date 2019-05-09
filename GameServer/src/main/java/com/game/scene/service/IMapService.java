package com.game.scene.service;

import com.game.scene.entity.MapEnt;

public interface IMapService {

    void initMapData();

    MapEnt getMapEnt(int mapId);

    void saveMapEnt(MapEnt mapEnt);

    MapEnt getMapEntById(int mapId);

    MapEnt getMapEntByName(String mapName);

    boolean isPlayerCurMap(String accountId, int mapId);

    void changeMap(String accountId, String mapName);

    int name2Id(String mapName);
}

