package com.game.user.mapInfo.model;

import com.game.user.mapInfo.entity.MapInfoEnt;

public class MapInfo {

    private int id;
    private String accountId;
    private int curMapId;
    private int lastMapId;

    private MapInfoEnt mapInfoEnt = new MapInfoEnt();

    public static MapInfo valueOf(MapInfoEnt mapInfoEnt){

        MapInfo mapInfo = new MapInfo();
        mapInfo.mapInfoEnt = mapInfoEnt;
        mapInfo.id = mapInfoEnt.getId();
        mapInfo.accountId = mapInfoEnt.getAccountId();
        mapInfo.curMapId = mapInfoEnt.getCurMapId();
        mapInfo.lastMapId = mapInfoEnt.getLastMapId();
        return mapInfo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
        this.mapInfoEnt.setAccountId(accountId);
    }

    public int getCurMapId() {
        return curMapId;
    }

    public void setCurMapId(int curMapId) {
        this.curMapId = curMapId;
        this.mapInfoEnt.setCurMapId(curMapId);
    }

    public int getLastMapId() {
        return lastMapId;
    }

    public void setLastMapId(int lastMapId) {
        this.lastMapId = lastMapId;
        this.mapInfoEnt.setLastMapId(lastMapId);
    }

    public MapInfoEnt getMapInfoEnt() {
        return mapInfoEnt;
    }

    public void setMapInfoEnt(MapInfoEnt mapInfoEnt) {
        this.mapInfoEnt = mapInfoEnt;
    }
}
