package com.game.role.player.service;

import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import com.game.user.account.model.BaseAccountInfo;
import java.util.List;

public interface IPlayerService {

    BasePlayerInfo getBasePlayerInfo(Long playerId);

    void loadAllPlayerInfo();

    Player getPlayer(Long id);

    void savePlayer(Player player);

    void createRole(String accountId, int job, int sex);

    //Account相关
    List<BasePlayerInfo> getBasePlayerInfos(String accountId);

    Player getPlayer(String accountId);

    List<Player> getPlayers(String accountId);

    String getAccountId(Long playerId);

    BaseAccountInfo getBaseAccountInfo(Long playerId);
}
