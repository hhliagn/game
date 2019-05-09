package com.game.user.account.entity;

import com.game.SpringContext;
import com.game.common.util.JsonUtils;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "account")
@org.hibernate.annotations.Table(appliesTo = "account", comment = "账号信息")
public class AccountEnt implements Serializable {

    @Transient
    private Account account;

    @Lob
    @Column(columnDefinition = "blob comment '账号基本数据'")
    private byte[] accountData;

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE comment '账号Id'", nullable = false)
    private String accountId;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE comment '账号密码'", nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE comment '昵称'")
    private String nickName;

    @Column(columnDefinition = "int default 0 comment '创建时间'")
    private Date createTime;

    @Column(columnDefinition = "timestamp comment '最后登录时间'")
    private Date lastLogin;

    @Column(columnDefinition = "timestamp comment '上次登出时间'")
    private Date lastLogout;

    @Column(columnDefinition = "int default 0 comment '用户当前所在地图id")
    private int curMapId;

    public static AccountEnt valueOf(String accountId, String password) {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.accountId = accountId;
        accountEnt.password = password;
        accountEnt.account = Account.valueOf(accountId);
        return accountEnt;
    }

    public String getId () {
        return accountId;
    }

    public void doSerialize(){
        BaseAccountInfo baseAccountInfo = account.getBaseAccountInfo();
        this.nickName = baseAccountInfo.getNickName();
        this.curMapId = baseAccountInfo.getMapId();
        this.lastLogin = account.getLastLogin();
        this.lastLogout = account.getLastLogout();
        this.createTime = account.getCreateTime();

        Account account = getAccount();
        this.accountData = JsonUtils.toNoCompressBytes(account);
    }

    public void doDeserialize(){
        account = JsonUtils.toObjectWithNoCompress(getAccountData(), Account.class);
        account.setBaseAccountInfo(SpringContext.getAccountService().getBaseAccountInfo(accountId));
        account.setLastLogin(this.lastLogin);
        account.setLastLogout(this.lastLogout);
        account.setCreateTime(this.createTime);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public byte[] getAccountData() {
        return accountData;
    }

    public void setAccountData(byte[] accountData) {
        this.accountData = accountData;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public int getCurMapId() {
        return curMapId;
    }

    public void setCurMapId(int curMapId) {
        this.curMapId = curMapId;
    }
}
