package com.game.role.player.model;

import com.game.SpringContext;
import com.game.role.player.entity.PlayerEnt;
import com.game.user.account.model.BaseAccountInfo;
import com.game.user.mapInfo.entity.MapInfoEnt;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private transient PlayerEnt playerEnt;
    private transient BaseAccountInfo baseAccountInfo;

    private Player(PlayerEnt playerEnt, BaseAccountInfo baseAccountInfo){
        this.playerEnt = playerEnt;
        this.baseAccountInfo = baseAccountInfo;
    }

    public static Player valueOf(PlayerEnt playerEnt, BaseAccountInfo baseAccountInfo){
        return new Player(playerEnt, baseAccountInfo);
    }

    public String getAccountId() {
        return baseAccountInfo.getAccountId();
    }

    public List<Player> getAllPlayers(){
        List<BasePlayerInfo> basePlayerInfos
                = SpringContext.getPlayerService().getBasePlayerInfos(getAccountId());
        List<Player> players = new ArrayList<>(basePlayerInfos.size());
        for (BasePlayerInfo basePlayerInfo : basePlayerInfos) {
            players.add(SpringContext.getPlayerService().getPlayer(basePlayerInfo.getId()));
        }
        return players;
    }

    public int getCurrMapId(){
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(getAccountId());
        return mapInfoEnt.getCurMapId();
    }

    public void setCurMapId(int newMapId){
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(getAccountId());
        mapInfoEnt.setCurMapId(newMapId);
        SpringContext.getMapInfoService().saveMapInfoEnt(mapInfoEnt);
    }

    public int getStatus(){
        return playerEnt.getAlive();
    }

    public void setStatus(int status){
        playerEnt.setAlive(status);
    }

    public Long getId(){
        return playerEnt.getId();
    }

    public BaseAccountInfo getBaseAccountInfo() {
        return baseAccountInfo;
    }

    public void setBaseAccountInfo(BaseAccountInfo baseAccountInfo) {
        this.baseAccountInfo = baseAccountInfo;
    }

    public String getName() {
        return playerEnt.getName();
    }

    public int getJob() {
        return playerEnt.getJob();
    }

    public int getSex() {
        return playerEnt.getSex();
    }
}
