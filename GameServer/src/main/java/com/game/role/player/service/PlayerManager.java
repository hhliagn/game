package com.game.role.player.service;

import com.game.role.player.dao.PlayerDao;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PlayerManager {

    @Autowired
    private PlayerDao playerDao;

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
    }

    private void addBasePlayerInfo(BasePlayerInfo basePlayerInfo) {
        id2BasePlayerInfos.putIfAbsent(basePlayerInfo.getId(), basePlayerInfo);
        List<BasePlayerInfo> basePlayerInfos
                = getOrCreateBasePlayerInfos(basePlayerInfo.getAccountId());
        basePlayerInfos.add(basePlayerInfo);
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


    public PlayerEnt getPlayerEnt(Long id){
        return playerDao.get(id);
    }

    public void savePlayerEnt(PlayerEnt playerEnt) {
        playerDao.save(playerEnt);
    }

    public PlayerEnt createPlayer(long id, String accountId) {
        PlayerEnt playerEnt = PlayerEnt.valueOf(id, accountId);
        BasePlayerInfo basePlayerInfo = new BasePlayerInfo(id, accountId, 1);
        addBasePlayerInfo(basePlayerInfo);
        playerDao.save(playerEnt);
        return playerEnt;
    }
}
