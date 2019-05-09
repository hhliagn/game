package com.game.role.player.service;

import com.game.SpringContext;
import com.game.core.id.service.IdentifyService;
import com.game.role.player.constant.Job;
import com.game.role.player.constant.Sex;
import com.game.role.player.entity.PlayerDao;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import com.game.user.account.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlayerService implements IPlayerService{

    private static final Logger logger = LoggerFactory.getLogger("player");

    @Autowired
    private PlayerDao playerDao;

    private static int ACCOUNT_MAX_PLAYER = 5;

    private Map<Long, BasePlayerInfo> id2BasePlayerInfos = new HashMap<>();

    private Map<String, List<BasePlayerInfo>> accountId2PlayerInfos = new HashMap<>();

    @Override
    public BasePlayerInfo getBasePlayerInfo(Long id){
        return id2BasePlayerInfos.get(id);
    }

    @Override
    public void loadAllPlayerInfo(){
        List<PlayerEnt> playerEntList = playerDao.loadAll();
        for (PlayerEnt playerEnt : playerEntList) {
            BasePlayerInfo basePlayerInfo = new BasePlayerInfo();
            basePlayerInfo.setAccountId(playerEnt.getAccountId());
            basePlayerInfo.setId(playerEnt.getId());
            basePlayerInfo.setStatus(playerEnt.getAlive());
            addBasePlayerInfo(basePlayerInfo);
        }

        //解析职业数据
        analysisDefaultNames();
    }

    private void addBasePlayerInfo(BasePlayerInfo basePlayerInfo) {
        id2BasePlayerInfos.put(basePlayerInfo.getId(), basePlayerInfo);
        List<BasePlayerInfo> basePlayerInfos
                = getOrCreateBasePlayerInfos(basePlayerInfo.getAccountId());
        basePlayerInfos.add(basePlayerInfo);
    }

    private List<BasePlayerInfo> getOrCreateBasePlayerInfos(String accountId) {
        List<BasePlayerInfo> basePlayerInfos = accountId2PlayerInfos.get(accountId);
        if (basePlayerInfos == null){
            basePlayerInfos = new ArrayList<>();
            accountId2PlayerInfos.put(accountId, basePlayerInfos);
        }
        return basePlayerInfos;
    }

    private void analysisDefaultNames(){
        Map<Integer, List<String>> job2NamesMap = new HashMap<>(3);
        for (Job job : Job.values()) {
            List<String> list = new ArrayList<>();
            String jobName = job.getName();
            for (Sex sex : Sex.values()) {
                String sexName = sex.getName();
                list.add(sexName + jobName);
            }
            job2NamesMap.put(job.getValue(), list);
        }
        Job.setJob2NamesMap(job2NamesMap);
    }

    @Override
    public Player getPlayer(Long id){
        PlayerEnt playerEnt = getPlayerEnt(id);
        return playerEnt == null ? null : playerEnt.getPlayer();
    }

    @Override
    public void savePlayer(Player player){
        if (player == null){
            return;
        }
        PlayerEnt playerEnt = getPlayerEnt(player.getId());
        playerEnt.setPlayer(player);
        savePlayerEnt(playerEnt);
    }

    private PlayerEnt getPlayerEnt(Long id){
        return playerDao.get(id);
    }

    private void savePlayerEnt(PlayerEnt playerEnt) {
        playerDao.save(playerEnt);
    }

    @Override
    public void createRole(String accountId, int job, int sex) {
        List<BasePlayerInfo> basePlayerInfos = getOrCreateBasePlayerInfos(accountId);
        if (basePlayerInfos.size() >= getAccountMaxPlayer()){
            throw new RuntimeException("创建玩家数超过上限");
        }
        long playerId = SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.PLAYER);
        String name = getName(job, sex);
        createRole(accountId, playerId, name, job, sex);
    }

    private void createRole(String accountId, long playerId, String name, int job, int sex){
        addBasePlayerInfo(accountId, playerId, name, job, sex);
        PlayerEnt playerEnt = PlayerEnt.valueOf(playerId, accountId, name, job, sex);
        savePlayerEnt(playerEnt);
        logger.info("创建角色");
    }

    private void addBasePlayerInfo(String accountId, long id, String name, int job, int sex){
        BasePlayerInfo basePlayerInfo = new BasePlayerInfo();
        basePlayerInfo.setAccountId(accountId);
        basePlayerInfo.setId(id);
        basePlayerInfo.setName(name);
        basePlayerInfo.setSex(sex);
        basePlayerInfo.setJob(job);
        addBasePlayerInfo(basePlayerInfo);
    }

    private int getAccountMaxPlayer() {
        return ACCOUNT_MAX_PLAYER;
    }

    private String getName(int job, int sex) {
        return Job.valueOf(job).getName(sex);
    }

    @Override
    public List<BasePlayerInfo> getBasePlayerInfos(String accountId) {
        return getOrCreateBasePlayerInfos(accountId);
    }

    @Override
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

    @Override
    public List<Player> getPlayers(String accountId){
        List<BasePlayerInfo> basePlayerInfos = getBasePlayerInfos(accountId);
        List<Player> players = new ArrayList<>();
        for (BasePlayerInfo basePlayerInfo : basePlayerInfos) {
            Player player = getPlayer(basePlayerInfo.getId());
            players.add(player);
        }
        return players;
    }

    @Override
    public String getAccountId(Long playerId){
        BasePlayerInfo basePlayerInfo = getBasePlayerInfo(playerId);
        return basePlayerInfo == null ? null : basePlayerInfo.getAccountId();
    }

    @Override
    public BaseAccountInfo getBaseAccountInfo(Long playerId){
        return SpringContext.getAccountService().getBaseAccountInfo(getAccountId(playerId));
    }
}
