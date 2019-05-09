package com.game.user.account.model;

import java.util.Date;

public class BaseAccountInfo {

    private String accountId;

    private String nickName;

    private Date loginTime;

    private Date logoutTime;

    ///场景使用
    private transient int x;
    private transient int y;
    private transient long sceneId;
    private transient int mapId;

    public static BaseAccountInfo valueOf(String accountId) {
        BaseAccountInfo baseAccountInfo = new BaseAccountInfo();
        baseAccountInfo.accountId = accountId;
        baseAccountInfo.mapId = 1;
        return baseAccountInfo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getSceneId() {
        return sceneId;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
