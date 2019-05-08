package com.game.user.account.model;

import com.game.SpringContext;
import com.game.role.player.model.BasePlayerInfo;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class BaseAccountInfo {

    private String accountId;

    private String nickName;

    private long showPlayerId;

    private long recentPlayerId;

    private Date loginTime;

    private Date logoutTime;

    ////
    private transient int x;
    private transient int y;
    private transient long sceneId;

    public static BaseAccountInfo valueOf(String accountId) {
        BaseAccountInfo baseAccountInfo = new BaseAccountInfo();
        baseAccountInfo.accountId = accountId;
        return baseAccountInfo;
    }

    public List<BasePlayerInfo> getBasePlayerInfos(){
        return SpringContext.getPlayerService().getBasePlayerInfos(this.getAccountId());
    }

    public BasePlayerInfo getRecentPlayerInfo(){
        BasePlayerInfo result = null;
        if (CollectionUtils.isEmpty(getBasePlayerInfos())){
            return result;
        }
        for (BasePlayerInfo basePlayerInfo : getBasePlayerInfos()) {
            if (basePlayerInfo.getId() == recentPlayerId){
                result = basePlayerInfo;
            }
        }
        if (result == null){
            result = getBasePlayerInfos().get(0);
        }
        return result;
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

    public long getRecentPlayerId() {
        return recentPlayerId;
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

    public long getShowPlayerId() {
        return showPlayerId;
    }

    public void setShowPlayerId(long showPlayerId) {
        this.showPlayerId = showPlayerId;
    }

    public void setRecentPlayerId(long recentPlayerId) {
        this.recentPlayerId = recentPlayerId;
    }

    ////

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
}
