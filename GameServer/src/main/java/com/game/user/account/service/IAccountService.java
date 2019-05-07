package com.game.user.account.service;

import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;

import java.util.Collection;

public interface IAccountService {

    void loadAllAccountInfo();

    Collection<BaseAccountInfo> getAllBaseAccountInfos();

    Collection<String> getAllAccountIds();

    BaseAccountInfo getBaseAccountInfo(String accountId);

    BaseAccountInfo getBaseAccountInfoByNickName(String nickName);

    //void saveAccount(String accountId);

    Account getAccount(String accountId);

    void createAccount(String accountId,String password);

    void createRole(String accountId, String nickName, int job, int sex);

    void changeNickname(String accountId, String nickName);

    void changePlayerModel(String accountId, Long playerId);

//    AccountEnt getAccountEnt(String accountId);
//
//    void saveAccountEnt(AccountEnt accountEnt);

    void saveAccount(Account account);

    Account getLoginAccount(String accountId, String password);

    void putBaseAccountInfoById(BaseAccountInfo baseAccountInfo);
}
