package com.game.user.account.service;

import com.game.SpringContext;
import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService implements IAccountService{

    @Autowired
    private AccountManager accountManager;

    @Override
    public BaseAccountInfo getBaseAccountInfo(String accountId) {
        if (accountId.isEmpty()){
            return null;
        }
        return accountManager.getBaseAccountInfo(accountId);
    }

    public BaseAccountInfo getBaseAccountInfoByNickName(String nickName){
        return accountManager.getBaseAccountInfoByNickName(nickName);
    }

    public Account getAccount(String accountId){
        BaseAccountInfo baseAccountInfo = accountManager.getBaseAccountInfo(accountId);
        if (baseAccountInfo == null) {
            return null;
        }
        return accountManager.getAccount(accountId);
    }

    public void loadAllAccountInfo() {
        accountManager.loadAllBaseAccountInfo();
    }

    public void createAccount(String accountId, String password){
        accountManager.createAccount(accountId, password);
        doAfterCreateAccount(accountId);
    }

    public Player createPlayer(String nickName){
        Player newPlayer = null;
        Account curLoginAccount = SpringContext.getGlobalService().getCurLoginAccount();
        if (curLoginAccount == null){
            throw new RuntimeException("当前未登录");
        }
        String accountId = curLoginAccount.getAccountId();

        List<BasePlayerInfo> basePlayerInfosByAccount
                = SpringContext.getPlayerService().getBasePlayerInfos(accountId);

        if (basePlayerInfosByAccount.isEmpty()){
            BaseAccountInfo baseAccountInfo = getBaseAccountInfo(accountId);
            if (baseAccountInfo.getNickName().isEmpty()){
                if (!accountManager.occupyNickName(nickName, baseAccountInfo)){
                    throw new RuntimeException("名字已存在");
                }
                baseAccountInfo.setNickName(nickName);
            }
            newPlayer = SpringContext.getPlayerService().createPlayer(accountId);
            baseAccountInfo.setRecentPlayerId(newPlayer.getId());
            saveAccount(accountId);

            /////
            curLoginAccount.setBaseAccountInfo(baseAccountInfo);
            SpringContext.getGlobalService().setCurLoginAccount(curLoginAccount);
        }else {
            newPlayer = SpringContext.getPlayerService().createPlayer(accountId);
        }
        return newPlayer;
    }

    public void saveAccount(String accountId){
        accountManager.saveAccount(accountId);
    }

    public AccountEnt getAccountEnt(String accountId){
        return accountManager.getAccountEnt(accountId);
    }

    public void saveAccountEnt(AccountEnt accountEnt) {
        accountManager.saveAccountEnt(accountEnt);
    }

    @Override
    public boolean getLoginAccount(String accountId, String password) {
        AccountEnt accountEnt = accountManager.getLoginAccount(accountId, password);
        if (accountEnt == null){
            return false;
        }
        return true;
    }

    public void doAfterCreateAccount(String accountId){
        //创建玩家的地图信息
        //设置玩家curMapId为起始之地
        SpringContext.getMapInfoService().createMapInfo(accountId);
    }
}
