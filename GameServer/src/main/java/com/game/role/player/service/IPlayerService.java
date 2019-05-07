package com.game.role.player.service;

import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import com.game.user.account.model.BaseAccountInfo;

import java.util.List;

public interface IPlayerService {

    void loadAllPlayerInfo();

    List<BasePlayerInfo> getBasePlayerInfos(String accountId);

    List<Player> getAllPlayers(String accountId);

    BasePlayerInfo getBasePlayerInfo(Long playerId);

    //对应职业玩家数据
    //BasePlayerInfo getBasePlayerInfo(String accountId, int job);

    Player getPlayer(Long id);

    Player getPlayer(String accountId);

    //获取最近登录的玩家
    Player getRecentPlayer(String accountId);

    void savePlayer(Player player);

    //根据玩家id获取账号
    String getAccountId(Long playerId);

    BaseAccountInfo getBaseAccountInfo(Long playerId);

    Player createPlayer(String accountId, String nickname, int job, int sex);

    void createRole(String accountId, int job, int sex);

    // 登录选择角色
    //void selectPlayer(String accountId, long id);

    void login(Long playerId);

    //void login(Player player, BaseAccountInfo baseAccountInfo);

    void logout(Player player);

    //void checkPlayerId(String accountId, Long playerId);

    //String getName(String accountId, int job, String name, int sex);

    //void showAccountAlterDetailInfo(String accountId, String nickName);

    //转职改名字
    //void onChangeJob(ChangeJobSyncEvent event);

}
