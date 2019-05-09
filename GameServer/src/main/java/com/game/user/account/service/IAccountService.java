package com.game.user.account.service;

import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;

public interface IAccountService {

    BaseAccountInfo getBaseAccountInfo(String accountId);

    BaseAccountInfo getBaseAccountInfoByNickName(String nickName);

    void loadAllAccountInfo();

    Account getAccount(String accountId);

    void saveAccount(Account account);

    void createAccount(String accountId,String password);

    void createRole(String accountId, String nickName, int job, int sex);

    void setNickname(String accountId, String nickName);

    Account getLoginAccount();

    Account getLoginAccount(String accountId, String password);

    Account login(String accountId, String password);

    void logout(String accountId);
}
