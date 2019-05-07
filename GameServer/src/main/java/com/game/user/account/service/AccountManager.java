package com.game.user.account.service;

import com.game.SpringContext;
import com.game.user.account.dao.AccountDao;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountManager {

    private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

    @Autowired
    private AccountDao accountDao;

    private ConcurrentHashMap<String, BaseAccountInfo> accountId2BaseAccountInfo
            = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, BaseAccountInfo> nickName2BaseAccountInfo
            = new ConcurrentHashMap<>();

    public Account getAccount(String accountId){
        AccountEnt accountEnt = getAccountEnt(accountId);
        return accountEnt == null ? null : accountEnt.getAccount();
    }

    public void saveAccount(Account account) {
        AccountEnt accountEnt = getAccountEnt(account.getAccountId());
        accountEnt.setAccount(account);
        saveAccountEnt(accountEnt);
    }

    /*public void saveAccount(String accountId) {
        AccountEnt accountEnt = getAccountEnt(accountId);
        saveAccountEnt(accountEnt);
    }*/

    public AccountEnt getAccountEnt(String accountId) {
        AccountEnt accountEnt = accountDao.get(accountId);
        return accountEnt;
    }

    public void saveAccountEnt(AccountEnt accountEnt){
        accountDao.save(accountEnt);
    }

    public void createAccount(String accountId, String password){
        AccountEnt accountEnt = AccountEnt.valueOf(accountId, password);
        Account account = accountEnt.getAccount();
        BaseAccountInfo baseAccountInfo = account.getBaseAccountInfo();
        baseAccountInfo.setLoginTime(new Date());
        account.setCreateTime(new Date());
        account.setLastLogin(new Date());

        addBaseAccountInfo(baseAccountInfo);
        accountEnt.setAccount(account);
        saveAccountEnt(accountEnt);

        logger.info("账号注册");
    }

    private void addBaseAccountInfo(BaseAccountInfo baseAccountInfo) {
        accountId2BaseAccountInfo.put(baseAccountInfo.getAccountId(), baseAccountInfo);
        String nickName = baseAccountInfo.getNickName();
        if (nickName != null && !nickName.isEmpty()){
            nickName2BaseAccountInfo.put(baseAccountInfo.getNickName(), baseAccountInfo);
        }
    }

    public BaseAccountInfo getBaseAccountInfo(String accountId){
        return accountId2BaseAccountInfo.get(accountId);
    }

    public BaseAccountInfo getBaseAccountInfoByNickName(String nickName){
        return nickName2BaseAccountInfo.get(nickName);
    }

    public Collection<BaseAccountInfo> getAllBaseAccountInfos(){
        return accountId2BaseAccountInfo.values();
    }

    public Collection<String> getAllAccountIds(){
        return accountId2BaseAccountInfo.keySet();
    }

    public boolean occupyNickName(String nickName, BaseAccountInfo baseAccountInfo){
        return nickName2BaseAccountInfo.putIfAbsent(nickName, baseAccountInfo) == null;
    }

    public boolean containNickName(String nickName){
        return nickName2BaseAccountInfo.containsKey(nickName);
    }

    public void loadAllBaseAccountInfo(){
        SpringContext.getPlayerService().loadAllPlayerInfo();;

        List<AccountEnt> accountEntList = accountDao.loadAll();
        for (AccountEnt accountEnt : accountEntList) {
            BaseAccountInfo baseAccountInfo = BaseAccountInfo.valueOf(accountEnt.getAccountId());
            baseAccountInfo.setLoginTime(accountEnt.getLastLogin());
            baseAccountInfo.setLogoutTime(accountEnt.getLastLogout());
            baseAccountInfo.setNickName(accountEnt.getNickName());
            baseAccountInfo.setRecentPlayerId(accountEnt.getRecentPlayerId());
            baseAccountInfo.setShowPlayerId(accountEnt.getShowPlayerId());
            addBaseAccountInfo(baseAccountInfo);
        }
    }

    public AccountEnt getLoginAccount(String accountId, String password) {
        AccountEnt loginAccount = accountDao.getLoginAccount(accountId, password);
        return loginAccount;
    }

    public void removeBaseAccountInfoByNickName(String nickName) {
        nickName2BaseAccountInfo.remove(nickName);
    }

    public void putBaseAccountInfoByNickName(String nickName, BaseAccountInfo baseAccountInfo) {
        nickName2BaseAccountInfo.put(nickName, baseAccountInfo);
    }

    public void putBaseAccountInfoById(BaseAccountInfo baseAccountInfo) {
        accountId2BaseAccountInfo.put(baseAccountInfo.getAccountId(), baseAccountInfo);
    }
}
