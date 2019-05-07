package com.game.role.player.service;

import com.game.role.player.constant.Job;
import com.game.role.player.dao.PlayerDao;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PlayerManager {

    @Autowired
    private PlayerDao playerDao;

    private int ACCOUNT_MAX_PLAYER = 5;

    private ConcurrentHashMap<Long, BasePlayerInfo> id2BasePlayerInfos = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, CopyOnWriteArrayList<BasePlayerInfo>> accountId2PlayerInfos
            = new ConcurrentHashMap<>();

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
        id2BasePlayerInfos.putIfAbsent(basePlayerInfo.getId(), basePlayerInfo);
        List<BasePlayerInfo> basePlayerInfos
                = getOrCreateBasePlayerInfos(basePlayerInfo.getAccountId());
        basePlayerInfos.add(basePlayerInfo);
    }

    public void addBasePlayerInfo(String accountId, long id, String name, int job, int sex){
        BasePlayerInfo basePlayerInfo = new BasePlayerInfo();
        //basePlayerInfo.setLevel(1);
        basePlayerInfo.setAccountId(accountId);
        basePlayerInfo.setId(id);
        basePlayerInfo.setName(name);
        basePlayerInfo.setSex(sex);
        basePlayerInfo.setJob(job);
        addBasePlayerInfo(basePlayerInfo);
    }

    public List<BasePlayerInfo> getOrCreateBasePlayerInfos(String accountId) {
        CopyOnWriteArrayList<BasePlayerInfo> basePlayerInfos = accountId2PlayerInfos.get(accountId);
        if (basePlayerInfos == null){
            basePlayerInfos = new CopyOnWriteArrayList<>();
            CopyOnWriteArrayList<BasePlayerInfo> oldBasePlayerInfos
                    = accountId2PlayerInfos.putIfAbsent(accountId, basePlayerInfos);

            if (oldBasePlayerInfos != null){
                basePlayerInfos = oldBasePlayerInfos;
            }
        }
        return basePlayerInfos;
    }

    public BasePlayerInfo getBasePlayerInfo(Long id){
        return id2BasePlayerInfos.get(id);
    }

    public Collection<BasePlayerInfo> getAllBasePlayerInfos(){
        return id2BasePlayerInfos.values();
    }

    public int getCreatePlayerCount(){
        return id2BasePlayerInfos.size();
    }

    public PlayerEnt getPlayerEnt(Long id){
        return playerDao.get(id);
    }

    public void savePlayerEnt(PlayerEnt playerEnt) {
        playerDao.save(playerEnt);
    }

    /*public PlayerEnt createPlayer(long id, String accountId) {
        PlayerEnt playerEnt = PlayerEnt.valueOf(id, accountId);
        BasePlayerInfo basePlayerInfo = new BasePlayerInfo(id, accountId, 1);
        addBasePlayerInfo(basePlayerInfo);
        playerDao.save(playerEnt);
        return playerEnt;
    }*/

    public int getAccountMaxPlayer() {
        return ACCOUNT_MAX_PLAYER;
    }

    private void analysisDefaultNames(){
        Map<Integer, List<String>> job2NamesMap = new HashMap<>(3);
        for (Job value : Job.values()) {
            String jobName = value.getName();
            List<String> list = new ArrayList<>();
            list.add("男" + jobName);
            list.add("女" + jobName);
            job2NamesMap.put(value.getValue(), list);
        }
        Job.setJob2NamesMap(job2NamesMap);
    }
}
