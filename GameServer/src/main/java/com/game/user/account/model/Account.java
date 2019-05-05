package com.game.user.account.model;

import com.game.SpringContext;
import com.game.user.account.entity.AccountEnt;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.Date;

public class Account {

    private transient BaseAccountInfo baseAccountInfo;
    private transient Date createTime;
    private transient Date lastLogin;
    private transient Date lastLogout;

    public String getAccountId(){
        return this.baseAccountInfo.getAccountId();
    }

    public String getNickName(){
        return baseAccountInfo.getNickName();
    }

    public BaseAccountInfo getBaseAccountInfo(){
        return baseAccountInfo;
    }

    public void setBaseAccountInfo(BaseAccountInfo baseAccountInfo) {
        this.baseAccountInfo = baseAccountInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(Date lastLogout) {
        this.lastLogout = lastLogout;
    }

    public static Account valueOf(String accountId){
        Account account = new Account();
        BaseAccountInfo baseAccountInfo = SpringContext.getAccountService()
                .getBaseAccountInfo(accountId);
        if (baseAccountInfo == null){
            baseAccountInfo = BaseAccountInfo.valueOf(accountId);
        }
        account.baseAccountInfo = baseAccountInfo;
        return account;
    }
}