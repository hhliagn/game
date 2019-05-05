package com.game.role.player.service;

import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import com.game.user.account.model.BaseAccountInfo;

import java.util.List;

public interface IPlayerService {

    void loadAllPlayerInfo();

    Player createPlayer(String accountId);

    void savePlayer(Player player);

    Player getPlayer(Long id);

    String getAccountId(Long playerId);

    BasePlayerInfo getBasePlayerInfo(Long playerId);

    BaseAccountInfo getBaseAccountInfo(Long playerId);

    List<BasePlayerInfo> getBasePlayerInfos(String accountId);

    List<Player> getAllPlayers(String accountId);

    void login(Long playerId);
}
