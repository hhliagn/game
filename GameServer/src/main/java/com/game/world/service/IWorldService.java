package com.game.world.service;

import com.game.role.player.model.Player;

public interface IWorldService {

    void initMapData();

    void changeMap(Player player, int mapId);

    void leaveOldMap(String accountId, int mapId);

    boolean canEnter(String accountId, int mapId);

    void enterNewMap(String accountId, int mapId);
}
