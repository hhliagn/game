package com.game.role.player.service;

import com.game.role.player.model.Player;

import java.util.List;

public interface IPlayerService {

    Player createPlayer(String accountId);

    void loadAllPlayerInfo();

    Player getPlayer(Long id);

    List<Player> getBasePlayers(String accountId);

    boolean canCreatePlayer(String accountId);

    Player getRecentPlayer(Long recentPlayerId);
}
