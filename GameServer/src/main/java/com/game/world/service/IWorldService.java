package com.game.world.service;

public interface IWorldService {

    void initMapData();

    void changeMap(String mapName);

    String enterTheWorld();

    String printMap(String mapName);
}
