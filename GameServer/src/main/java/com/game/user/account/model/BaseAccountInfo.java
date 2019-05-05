package com.game.user.account.model;

import com.game.SpringContext;
import com.game.role.player.model.BasePlayerInfo;

import java.util.Date;
import java.util.List;

public class BaseAccountInfo {

    private String accountId;

    private String nickName;

    private Long recentPlayerId;

    private Date loginTime;

    private Date logoutTime;

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

    public Long getRecentPlayerId() {
        return recentPlayerId;
    }

    public void setRecentPlayerId(Long recentPlayerId) {
        this.recentPlayerId = recentPlayerId;
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
}
