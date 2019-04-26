package com.game.user.account.model;

import com.game.user.account.entity.AccountEnt;

import java.util.Date;

public class Account {

    private String accountId;
    private String nickName;
    private Date LastLogin;
    private Date LastLogout;
    private long recentPlayerId;

    private AccountEnt accountEnt;

    public static Account valueOf(AccountEnt accountEnt){
        Account account = new Account();
        account.accountId = accountEnt.getAccountId();
        account.nickName = accountEnt.getNickName();
        account.LastLogin = accountEnt.getLastLogin();
        account.LastLogout = accountEnt.getLastLogout();
        account.recentPlayerId = accountEnt.getRecentPlayerId();
        account.accountEnt = accountEnt;
        return account;
    }

    public static Account valueOf(String accountId, String nickName){
        Account account = new Account();
        account.setAccountId(accountId);
        account.setNickName(nickName);
        return account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
        this.accountEnt.setAccountId(accountId);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        this.accountEnt.setNickName(nickName);
    }

    public Date getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        LastLogin = lastLogin;
        this.accountEnt.setLastLogin(lastLogin);
    }

    public Date getLastLogout() {
        return LastLogout;
    }

    public void setLastLogout(Date lastLogout) {
        LastLogout = lastLogout;
        this.accountEnt.setLastLogout(lastLogout);
    }

    public AccountEnt getAccountEnt() {
        return accountEnt;
    }

    public void setAccountEnt(AccountEnt accountEnt) {
        this.accountEnt = accountEnt;
    }

    public long getRecentPlayerId() {
        return recentPlayerId;
    }

    public void setRecentPlayerId(long recentPlayerId) {
        this.recentPlayerId = recentPlayerId;
        this.accountEnt.setRecentPlayerId(recentPlayerId);
    }
}
