package com.game.user.account.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

public class AccountEnt {

    @Id
    private String accountId;
    private String nickName;
    private Date LastLogin;
    private Date LastLogout;
    private Long recentPlayerId;

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

    public Date getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        LastLogin = lastLogin;
    }

    public Date getLastLogout() {
        return LastLogout;
    }

    public void setLastLogout(Date lastLogout) {
        LastLogout = lastLogout;
    }

    public Long getRecentPlayerId() {
        return recentPlayerId;
    }

    public void setRecentPlayerId(Long recentPlayerId) {
        this.recentPlayerId = recentPlayerId;
    }
}
