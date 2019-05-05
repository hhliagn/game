package com.game.user.account.service;

import com.game.role.player.model.Player;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;

public interface IAccountService {

    void loadAllAccountInfo();

    void saveAccount(String accountId);

    Account getAccount(String accountId);

    AccountEnt getAccountEnt(String accountId);

    void saveAccountEnt(AccountEnt accountEnt);

    void createAccount(String accountId,String password);

    Player createPlayer(String nickName);

    boolean getLoginAccount(String accountId, String password);

    BaseAccountInfo getBaseAccountInfo(String accountId);

    BaseAccountInfo getBaseAccountInfoByNickName(String nickName);
}
