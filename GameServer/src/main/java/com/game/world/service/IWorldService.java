package com.game.world.service;

import com.game.world.service.map.entity.MapEnt;

public interface IWorldService {

    void initMapData();

    void changeMap(String mapName);

    String printMap(String mapName);

//    MapEnt getCurMap();

//    String printAccount();
}
