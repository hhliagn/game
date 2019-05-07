package com.game.role.player.service;

import com.game.SpringContext;
import com.game.core.id.service.IdentifyService;
import com.game.role.player.constant.Job;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import com.game.user.account.service.AccountManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private Player createPlayer(String accountId, long playerId, String name, int job, int sex){
        playerManager.addBasePlayerInfo(accountId, playerId, name, job, sex);
        PlayerEnt playerEnt = PlayerEnt.valueOf(playerId, accountId, name, job, sex);
        playerManager.savePlayerEnt(playerEnt);
        return getPlayer(playerId);
    }

    public Player createPlayer(String accountId, String nickName, int job, int sex){
        logger.info("create player");
        String pName = getName(job, sex);
        long playerId
                = SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.PLAYER);
        Player player = createPlayer(accountId, playerId, pName, job, sex);

        //playerManager.initNewRole(player);
        //savePlayer(player);
        return player;
    }

    @Override
    public void createRole(String accountId, int job, int sex) {
        List<BasePlayerInfo> basePlayerInfos = playerManager.getOrCreateBasePlayerInfos(accountId);
        if (basePlayerInfos.size() >= getAccountMaxPlayer()){
            throw new RuntimeException("创建玩家数超过上限");
        }
        long playerId = SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.PLAYER);
        String name = getName(job, sex);
        Player player = createPlayer(accountId, playerId, name, job, sex);
        this.savePlayer(player);
    }

    private String getName(int job, int sex) {
        return Job.valueOf(job).getName(sex);
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
        return playerEnt == null ? null : playerEnt.getPlayer();
    }

    public Player getPlayer(String accountId){
        BaseAccountInfo baseAccountInfo
                = SpringContext.getAccountService().getBaseAccountInfo(accountId);
        if (baseAccountInfo == null){
            return null;
        }
        List<BasePlayerInfo> basePlayerInfos = getBasePlayerInfos(accountId);
        if (basePlayerInfos != null && basePlayerInfos.size() > 0){
            BasePlayerInfo basePlayerInfo = basePlayerInfos.get(0);
            return getPlayer(basePlayerInfo.getId());
        }
        return null;
    }

    public Player getRecentPlayer(String accountId){
        BaseAccountInfo baseAccountInfo
                = SpringContext.getAccountService().getBaseAccountInfo(accountId);
        return getPlayer(baseAccountInfo.getRecentPlayerId());
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

   /* public Player createPlayer(String accountId){
        long id = SpringContext.getIdentifyService()
                .getNextIdentify(IdentifyService.IdentifyType.PLAYER);
        PlayerEnt playerEnt = playerManager.createPlayer(id, accountId);
        Player player = playerEnt.getPlayer();
        //Player player = getPlayer(id);
        //savePlayer(player);
        return player;
    }*/

    /*public Player createRole(String accountId){

    }*/

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
        updateAccountInfo(account, player);
        savePlayer(player);
        logger.info("玩家登录");
    }

    private void updateAccountInfo(Account account, Player player) {
        Date now = new Date();
        BaseAccountInfo baseAccountInfo = player.getBaseAccountInfo();
        baseAccountInfo.setLoginTime(now);
        baseAccountInfo.setRecentPlayerId(player.getId());
        account.setLastLogin(now);

        accountManager.saveAccount(account);
    }

    public void logout(Player player){
        Date lastLogout = new Date();
        BaseAccountInfo baseAccountInfo
                = SpringContext.getAccountService().getBaseAccountInfo(player.getAccountId());
        baseAccountInfo.setLogoutTime(lastLogout);
        String accountId = player.getAccountId();
        Account account = SpringContext.getAccountService().getAccount(accountId);
        account.setLastLogout(lastLogout);
//        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(player.getAccountId());
//        accountEnt.setAccount(account);
        SpringContext.getAccountService().saveAccount(account);
    }

//    public void showAccountAlterDetailInfo(String accountId, String nickName){
//
//    }

    private int getAccountMaxPlayer(){
        return this.playerManager.getAccountMaxPlayer();
    }

//    public void rename(Player player, long playerId,String name){
//
//    }

    public boolean checkPlayerId(String accountId, long playerId){
        BasePlayerInfo basePlayerInfo = playerManager.getBasePlayerInfo(playerId);
        return !(basePlayerInfo == null ||
                !StringUtils.equals(basePlayerInfo.getAccountId(), accountId));
    }
}
