package com.game.role.player.service;

import com.game.SpringContext;
import com.game.core.id.service.IdentifyService;
import com.game.role.player.dao.PlayerDao;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.user.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PlayerService implements IPlayerService{

    private static final Logger logger = LoggerFactory.getLogger("player");
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
        try {
            Player player = playerDao.createPlayer(id, accountId);
            //playerDao.createPlayer(id, accountId);
            //updateAccountInfo(player);
            addPlayer(player);
            return player;
        } catch (Exception e) {
            logger.warn("创建玩家保存到数据库出错");
            e.printStackTrace();
            throw new RuntimeException("创建玩家保存到数据库出错");
        }
    }

    private void updateAccountInfo(Player player) {
        try {
            Account account = SpringContext.getAccountService().getAccount(player.getAccountId());
            if (account == null){
                logger.warn("玩家对应的账户不存在");
            }
            account.setRecentPlayerId(player.getId());
        } catch (Exception e) {
            logger.warn("更新账户最近玩家id出错");
            e.printStackTrace();
            throw new RuntimeException("更新账户最近玩家id出错");
        }
    }

    public void loadAllPlayerInfo(){
        try {
            List<PlayerEnt> playerEntList = playerDao.findAll();
            if (playerEntList == null || playerEntList.size() == 0){
                logger.warn("玩家数据表数据为空");
            }
            for (PlayerEnt playerEnt : playerEntList) {
                Player player = Player.valueOf(playerEnt);
                addPlayer(player);
            }
        } catch (Exception e) {
            logger.warn("加载玩家数据出错");
            e.printStackTrace();
            throw new RuntimeException("加载玩家数据出错");
        }
    }

    private void addPlayer(Player player) {
        if (getPlayer(player.getId()) != null){
            logger.warn("id对应的玩家已存在");
            return;
        }
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
        try {
            Player recentPlayer = playerDao.findOne(recentPlayerId);
            if (recentPlayer == null){
                logger.warn("最近玩家为null");
            }
            return recentPlayer;
        } catch (Exception e) {
            logger.info("获取最近玩家出错");
            e.printStackTrace();
            throw new RuntimeException("获取最近玩家出错");
        }
    }
}
