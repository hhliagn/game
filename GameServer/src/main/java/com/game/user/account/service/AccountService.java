package com.game.user.account.service;

import com.game.SpringContext;
import com.game.role.player.model.BasePlayerInfo;
import com.game.user.account.entity.AccountDao;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import com.game.user.mapInfo.entity.MapInfoEnt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class AccountService implements IAccountService{

    private static Logger logger = LoggerFactory.getLogger("AccountService");

    @Autowired
    private AccountDao accountDao;

    private Map<String, BaseAccountInfo> accountId2BaseAccountInfo = new HashMap<>();

    private Map<String, BaseAccountInfo> nickName2BaseAccountInfo = new HashMap<>();

    private List<Account> loginAccounts = new ArrayList<>();

    @Override
    public BaseAccountInfo getBaseAccountInfo(String accountId) {
        if (StringUtils.isEmpty(accountId)){
            return null;
        }
        return accountId2BaseAccountInfo.get(accountId);
    }

    @Override
    public BaseAccountInfo getBaseAccountInfoByNickName(String nickName){
        if (StringUtils.isEmpty(nickName)){
            return null;
        }
        return nickName2BaseAccountInfo.get(nickName);
    }

    @Override
    public void loadAllAccountInfo() {
        SpringContext.getPlayerService().loadAllPlayerInfo();;
        List<AccountEnt> accountEntList = accountDao.loadAll();
        for (AccountEnt accountEnt : accountEntList) {
            BaseAccountInfo baseAccountInfo = BaseAccountInfo.valueOf(accountEnt.getAccountId());
            baseAccountInfo.setLoginTime(accountEnt.getLastLogin());
            baseAccountInfo.setLogoutTime(accountEnt.getLastLogout());
            baseAccountInfo.setNickName(accountEnt.getNickName());
            baseAccountInfo.setMapId(accountEnt.getCurMapId());
            addBaseAccountInfo(baseAccountInfo);
        }
    }

    private void addBaseAccountInfo(BaseAccountInfo baseAccountInfo) {
        accountId2BaseAccountInfo.put(baseAccountInfo.getAccountId(), baseAccountInfo);
        String nickName = baseAccountInfo.getNickName();
        if (nickName != null && !nickName.isEmpty()){
            nickName2BaseAccountInfo.put(baseAccountInfo.getNickName(), baseAccountInfo);
        }
    }

    @Override
    public Account getAccount(String accountId){
        BaseAccountInfo baseAccountInfo = getBaseAccountInfo(accountId);
        if (baseAccountInfo == null) {
            return null;
        }
        AccountEnt accountEnt = getAccountEnt(accountId);
        return accountEnt == null ? null : accountEnt.getAccount();
    }

    @Override
    public void saveAccount(Account account) {
        AccountEnt accountEnt = getAccountEnt(account.getAccountId());
        accountEnt.setAccount(account);
        saveAccountEnt(accountEnt);
    }

    private AccountEnt getAccountEnt(String accountId) {
        return accountDao.get(accountId);
    }

    private void saveAccountEnt(AccountEnt accountEnt){
        accountDao.save(accountEnt);
    }

    @Override
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

        createMapInfo(accountId);
        logger.info("账号注册");
    }

    @Override
    public void createRole(String accountId, String nickName, int job, int sex){
        BaseAccountInfo baseAccountInfo = getBaseAccountInfo(accountId);
        if (baseAccountInfo == null){
            throw new RuntimeException("用户不存在");
        }
        List<BasePlayerInfo> basePlayerInfosByAccount
                = SpringContext.getPlayerService().getBasePlayerInfos(accountId);
        if (CollectionUtils.isEmpty(basePlayerInfosByAccount)) {
            if (StringUtils.isEmpty(baseAccountInfo.getNickName())) {
                if (containNickName(nickName)) {
                    throw new RuntimeException("名字已存在");
                }
                setNickname(accountId, nickName);
            }
        }
        SpringContext.getPlayerService().createRole(accountId, job, sex);
        saveAccount(getAccount(accountId));
    }

    private boolean containNickName(String nickName){
        return nickName2BaseAccountInfo.containsKey(nickName);
    }

    @Override
    public void setNickname(String accountId, String nickName) {
        BaseAccountInfo baseAccountInfo = getBaseAccountInfo(accountId);
        if (baseAccountInfo == null){
            return;
        }
        String oldNickName = baseAccountInfo.getNickName();
        if (!StringUtils.isEmpty(oldNickName)){
            removeBaseAccountInfoByNickName(oldNickName);
        }
        baseAccountInfo.setNickName(nickName);
        saveAccount(getAccount(accountId));
    }

    @Override
    public Account getLoginAccount() {
        if (loginAccounts.size() == 0){
            return null;
        }
        return loginAccounts.get(0);
    }

    private void removeBaseAccountInfoByNickName(String nickName) {
        nickName2BaseAccountInfo.remove(nickName);
    }

    public Account getLoginAccount(String accountId, String password) {
        AccountEnt accountEnt = accountDao.getLoginAccount(accountId, password);
        if (accountEnt == null){
            return null;
        }
        return accountEnt.getAccount();
    }

    @Override
    public Account login(String accountId, String password) {

        BaseAccountInfo baseAccountInfo = getBaseAccountInfo(accountId);
        if (baseAccountInfo == null){
            createAccount(accountId, password);
            baseAccountInfo = getBaseAccountInfo(accountId);
        }

        Account loginAccount = getLoginAccount();
        if (loginAccount != null){
            return loginAccount;
        }

        loginAccount = getLoginAccount(accountId, password);
        if (loginAccount == null){
            throw new RuntimeException("密码错误");
        }
        loginAccounts.add(loginAccount);
        doAfterLogin(accountId);
        logger.info("用户登录");
        return loginAccount;
    }

    @Override
    public void logout(String accountId) {
        Date date = new Date();
        Account account = SpringContext.getAccountService().getAccount(accountId);
        account.setLastLogout(date);
        account.getBaseAccountInfo().setLogoutTime(date);
        SpringContext.getAccountService().saveAccount(account);
        logger.info("用户登出");
    }

    private void doAfterLogin(String accountId) {
        //设置新的登录时间
        Date now = new Date();
        Account account = SpringContext.getAccountService().getAccount(accountId);
        account.setLastLogin(now);
        account.getBaseAccountInfo().setLoginTime(now);
        SpringContext.getAccountService().saveAccount(account);
    }

    private void createMapInfo(String accountId){
        MapInfoEnt mapInfoEnt = MapInfoEnt.valueOf(accountId);
        SpringContext.getMapInfoService().saveMapInfoEnt(mapInfoEnt);
    }
}
