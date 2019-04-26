package com.game.user.account.service;

import com.game.user.account.model.Account;

public interface IAccountService {

    void loadAllAccountInfo();

    void addAccountInfo(Account account);

    void saveAccount(Account account);

    Account getAccount(String accountId);

    void createAccount(String accountId,String nickName, int sex, int job);
}
