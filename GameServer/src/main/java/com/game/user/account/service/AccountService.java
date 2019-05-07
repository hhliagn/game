package com.game.user.account.service;

import com.game.SpringContext;
import com.game.role.player.model.BasePlayerInfo;
import com.game.role.player.model.Player;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
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

    @Override
    public Collection<BaseAccountInfo> getAllBaseAccountInfos() {
        return accountManager.getAllBaseAccountInfos();
    }

    @Override
    public Collection<String> getAllAccountIds() {
        return accountManager.getAllAccountIds();
    }

    public void createAccount(String accountId, String password){
        accountManager.createAccount(accountId, password);
        doAfterCreateAccount(accountId);
    }

    public void createRole(String accountId, String nickName, int job, int sex){
        List<BasePlayerInfo> basePlayerInfosByAccount
                = SpringContext.getPlayerService().getBasePlayerInfos(accountId);
        if (!CollectionUtils.isEmpty(basePlayerInfosByAccount)){
            SpringContext.getPlayerService().createRole(accountId, job, sex);
        }else {
            BaseAccountInfo baseAccountInfo = getBaseAccountInfo(accountId);
            if (StringUtils.isEmpty(baseAccountInfo.getNickName())){
                if (!accountManager.occupyNickName(nickName, baseAccountInfo)){
                    throw new RuntimeException("名字已存在");
                }

                changeNickname(accountId, nickName);
            }

            Player newPlayer = SpringContext.getPlayerService().createPlayer(accountId, nickName, job, sex);
            baseAccountInfo.setShowPlayerId(newPlayer.getId());
            accountManager.saveAccount(getAccount(accountId));
        }
    }

    @Override
    public void changeNickname(String accountId, String nickName) {
        BaseAccountInfo baseAccountInfo = getBaseAccountInfo(accountId);
        if (!StringUtils.isEmpty(baseAccountInfo.getNickName())){
            accountManager.removeBaseAccountInfoByNickName(baseAccountInfo.getNickName());
        }

        baseAccountInfo.setNickName(nickName);
        accountManager.saveAccount(getAccount(accountId));
    }

    @Override
    public void changePlayerModel(String accountId, Long playerId) {
        Player player = SpringContext.getPlayerService().getPlayer(playerId);
        BaseAccountInfo baseAccountInfo = player.getBaseAccountInfo();
        if (player.getId() == baseAccountInfo.getShowPlayerId()){
            return;
        }

        baseAccountInfo.setShowPlayerId(playerId);
        accountManager.saveAccount(getAccount(accountId));
    }

//    public void saveAccount(String accountId){
////        accountManager.saveAccount(accountId);
////    }

//    public AccountEnt getAccountEnt(String accountId){
//        return accountManager.getAccountEnt(accountId);
//    }
//
//    @Override
//    public void saveAccountEnt(AccountEnt accountEnt) {
//        accountManager.saveAccountEnt(accountEnt);
//    }


    public void saveAccount(Account account) {
        accountManager.saveAccount(account);
    }

    @Override
    public Account getLoginAccount(String accountId, String password) {
        AccountEnt accountEnt = accountManager.getLoginAccount(accountId, password);
        if (accountEnt == null){
            return null;
        }
        return accountEnt.getAccount();
    }

    @Override
    public void putBaseAccountInfoById(BaseAccountInfo baseAccountInfo) {
        accountManager.putBaseAccountInfoById(baseAccountInfo);
    }

    public void doAfterCreateAccount(String accountId){
        //创建玩家的地图信息
        //设置玩家curMapId为起始之地
        SpringContext.getMapInfoService().createMapInfoEnt(accountId);
    }
}
