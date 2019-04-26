package com.game.map.service;

import com.game.map.model.Map;
import com.game.pojo.model.Entity;
import com.game.role.player.model.Player;

import java.util.List;

public interface IMapService {

    void initMapData();

    Map getMapById(int id);

    void save(Map map);

    List<Entity> getAllEntity(int mapId);

    boolean isPlayerCurMap(Player player, int mapId);
}
