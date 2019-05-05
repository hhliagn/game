package com.game.role.player.service;

import com.game.SpringContext;
import com.game.core.id.service.IdentifyService;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import com.game.user.account.service.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleUnresolved;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PlayerService implements IPlayerService{

    private static final Logger logger = LoggerFactory.getLogger("player");

    @Autowired
    private PlayerManager playerManager;

    @Autowired
    private AccountManager accountManager;

    private static final int MAX_PLAYER_PER_ACCOUNT = 5;

    public void loadAllPlayerInfo(){
        playerManager.loadAllPlayerInfo();
    }

    @Override
    public Player createPlayer(String accountId) {
        long id = SpringContext.getIdentifyService()
                .getNextIdentify(IdentifyService.IdentifyType.PLAYER);
        PlayerEnt playerEnt = playerManager.createPlayer(id, accountId);
        Player player = playerEnt.getPlayer();
        //Player player = getPlayer(id);
        //savePlayer(player);
        return player;
    }

    public void savePlayer(Player player){
        if (player == null){
            return;
        }
        PlayerEnt playerEnt = playerManager.getPlayerEnt(player.getId());
        playerEnt.setPlayer(player);
        playerManager.savePlayerEnt(playerEnt);
    }

    public Player getPlayer(Long id){
        PlayerEnt playerEnt = playerManager.getPlayerEnt(id);
        if (playerEnt == null){
            return null;
        }
        Player player = playerEnt.getPlayer();
        return player;
    }

    public String getAccountId(Long playerId){
        BasePlayerInfo basePlayerInfo = playerManager.getBasePlayerInfo(playerId);
        return basePlayerInfo == null ? null : basePlayerInfo.getAccountId();
    }

    public BasePlayerInfo getBasePlayerInfo(Long playerId){
        return playerManager.getBasePlayerInfo(playerId);
    }

    public BaseAccountInfo getBaseAccountInfo(Long playerId){
        return SpringContext.getAccountService().getBaseAccountInfo(getAccountId(playerId));
    }

    @Override
    public List<BasePlayerInfo> getBasePlayerInfos(String accountId) {
        return playerManager.getOrCreateBasePlayerInfos(accountId);
    }

    public List<Player> getAllPlayers(String accountId){
        List<BasePlayerInfo> basePlayerInfos = getBasePlayerInfos(accountId);
        List<Player> players = new ArrayList<>();
        for (BasePlayerInfo basePlayerInfo : basePlayerInfos) {
            Player player = getPlayer(basePlayerInfo.getId());
            players.add(player);
        }
        return players;
    }

    public void login(Long playerId){
        if (playerId == null || playerId <= 0){
            throw new RuntimeException("playerId不存在");
        }
        Player player = getPlayer(playerId);
        if (player == null){
            throw new RuntimeException("player不存在");
        }

        String accountId = player.getAccountId();
        BaseAccountInfo baseAccountInfo = SpringContext.getAccountService().getBaseAccountInfo(accountId);
        login(player, baseAccountInfo);
    }

    public void login(Player player, BaseAccountInfo baseAccountInfo){
        Account account = SpringContext.getAccountService().getAccount(player.getAccountId());
        logger.info("用户登录");
        updateAccountInfo(account, player);
        savePlayer(player);
    }

    private void updateAccountInfo(Account account, Player player) {
        Date now = new Date();
        BaseAccountInfo baseAccountInfo = player.getBaseAccountInfo();
        baseAccountInfo.setLoginTime(now);
        baseAccountInfo.setRecentPlayerId(player.getId());

        account.setLastLogin(now);

        accountManager.saveAccount(player.getAccountId());
    }
}
