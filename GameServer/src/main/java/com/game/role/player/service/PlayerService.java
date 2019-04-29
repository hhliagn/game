package com.game.role.player.service;

import com.game.SpringContext;
import com.game.core.id.service.IdentifyService;
import com.game.role.player.dao.PlayerDao;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.user.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PlayerService implements IPlayerService{
    @Autowired
    private PlayerDao playerDao;

    private static final int MAX_PLAYER_PER_ACCOUNT = 5;

    private ConcurrentHashMap<Long,Player> id2Player = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, CopyOnWriteArrayList<Player>> accountId2BasePlayers
            = new ConcurrentHashMap<>();

    @Override
    public Player createPlayer(String accountId) {
        long id = SpringContext.getIdentifyService()
                .getNextIdentify(IdentifyService.IdentifyType.PLAYER);
        Player player = playerDao.createPlayer(id, accountId);
        //playerDao.createPlayer(id, accountId);
        //updateAccountInfo(player);
        return player;
    }

    private void updateAccountInfo(Player player) {
        Account account = SpringContext.getAccountService().getAccount(player.getAccountId());
        account.setRecentPlayerId(player.getId());
    }

    public void loadAllPlayerInfo(){
        List<PlayerEnt> playerEntList = playerDao.findAll();
        for (PlayerEnt playerEnt : playerEntList) {
            Player player = Player.valueOf(playerEnt);
            addPlayer(player);
        }
    }

    private void addPlayer(Player player) {
        id2Player.put(player.getId(),player);
        List<Player> basePlayers = getOrCreateBasePlayers(player.getAccountId());
        basePlayers.add(player);
    }

    private List<Player> getOrCreateBasePlayers(String accountId) {
        CopyOnWriteArrayList<Player> basePlayers = accountId2BasePlayers.get(accountId);
        if (basePlayers == null) {
            basePlayers = new CopyOnWriteArrayList<Player>();
            CopyOnWriteArrayList<Player> oldBasePlayers
                    = accountId2BasePlayers.putIfAbsent(accountId, basePlayers);
            if (oldBasePlayers != null) {
                basePlayers = oldBasePlayers;
            }
        }
        return basePlayers;
    }

    public Player getPlayer(Long id){
        return id2Player.get(id);
    }

    public List<Player> getBasePlayers(String accountId){
        return accountId2BasePlayers.get(accountId);
    }

    @Override
    public boolean canCreatePlayer(String accountId) {
        List<Player> basePlayers = getBasePlayers(accountId);
        if (basePlayers == null || basePlayers.size() < MAX_PLAYER_PER_ACCOUNT){
            return true;
        }
        return false;
    }

    @Override
    public Player getRecentPlayer(Long recentPlayerId) {
        Player recentPlayer = playerDao.findOne(recentPlayerId);
        return recentPlayer;
    }
}
