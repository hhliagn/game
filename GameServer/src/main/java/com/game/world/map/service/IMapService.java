package com.game.world.map.service;

import com.game.world.service.map.entity.MapEnt;
import com.game.world.service.pojo.entity.EntityEnt;

import java.util.List;

public interface IMapService {

    void initMapData();

    MapEnt getMapEnt(int mapId);

    void saveMapEnt(int mapId);

    MapEnt getMapEntById(int mapId);

    MapEnt getMapEntByName(String mapName);

    List<EntityEnt> getEntities(int mapId);

    boolean isPlayerCurMap(String accountId, int mapId);

    void changeMap(String mapName);
}
