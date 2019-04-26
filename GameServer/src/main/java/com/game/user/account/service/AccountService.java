package com.game.user.account.service;

import com.game.role.player.model.Player;
import com.game.user.account.dao.AccountDao;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Component
public class AccountService implements IAccountService{

    @Autowired
    private AccountDao accountDao;

    private HashMap<String, Account> id2Account;

    private HashMap<String, Account> nickName2Account;

    public void loadAllAccountInfo() {
        List<AccountEnt> accountEntList = accountDao.findAll();
        for (AccountEnt accountEnt : accountEntList) {
            Account account = Account.valueOf(accountEnt);
            addAccountInfo(account);
        }
    }

    public void addAccountInfo(Account account) {
        if (id2Account == null){
            id2Account = new HashMap<String, Account>();
        }
        if (account == null){
            return;
        }
        Account existId = id2Account.get(account.getAccountId());
        if (existId == null){
            id2Account.put(account.getAccountId(), account);
        }
        Account existNickName = nickName2Account.get(account.getNickName());
        if (existNickName == null){
            nickName2Account.put(account.getNickName(), account);
        }
    }

    public void saveAccount(Account account) {
        accountDao.save(account);
    }

    public Account getAccount(String accountId) {
        return id2Account.get(accountId);
    }

    public void createAccount(String accountId, String nickName, int sex, int job){

        Account account = Account.valueOf(accountId, nickName);
        addAccountInfo(account);
        createPlayer(accountId, sex, job);
        Account accountAfter = getAccount(accountId);
        accountDao.createAccount(accountAfter);
    }

    private void createPlayer(String accountId, int sex, int job) {
        // 创建玩家-idService获取Id-加入accountId-playerList--加入id-player map
        // 更新账号信息 -- setRecentPlayerId
    }

    public List<Player> getPlayerList(String accountId){

        return null;
    }

}
