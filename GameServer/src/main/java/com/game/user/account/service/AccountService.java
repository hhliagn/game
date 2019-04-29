package com.game.user.account.service;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.user.account.dao.AccountDao;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Component
public class AccountService implements IAccountService{

    private static Logger logger = LoggerFactory.getLogger("ON-OFF");

    @Autowired
    private AccountDao accountDao;

    private HashMap<String, Account> id2Account = new HashMap<>();

    private HashMap<String, Account> nickName2Account = new HashMap<>();

    public void loadAllAccountInfo() {
        SpringContext.getPlayerService().loadAllPlayerInfo();
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

    public Account getAccountByNickName(String nickName){
        Account account = nickName2Account.get(nickName);
        return account;
    }

    public void createAccount(String accountId, String nickName){
        boolean exist = isExistAccount(accountId,nickName);
        if (exist){
            return;
        }
        Player player = createPlayer(accountId);
        long recentPlayerId = player.getId();
        Account account = accountDao.createAccount(accountId, nickName, recentPlayerId);
        addAccountInfo(account);
    }

    private boolean isExistAccount(String accountId,String nickName) {
        Account account = getAccount(accountId);
        Account accountByNickName = getAccountByNickName(nickName);
        if (account != null){
            logger.info("账户id已存在！");
            throw new RuntimeException("账户id已存在！");
        }
        if (accountByNickName != null){
            logger.info("账户昵称已存在！");
            throw new RuntimeException("账户昵称已存在！");
        }
        return false;
    }

    private Player createPlayer(String accountId) {
        if (canCreatePlayer(accountId)){
            Player player = SpringContext.getPlayerService().createPlayer(accountId);
            return player;
        }else {
            return getRecentPlayer(accountId);
        }
    }

    private Player getRecentPlayer(String accountId) {
        Account account = getAccount(accountId);
        long recentPlayerId = account.getRecentPlayerId();
        Player player = SpringContext.getPlayerService().getRecentPlayer(recentPlayerId);
        return player;
    }

    private boolean canCreatePlayer(String accountId) {
        boolean result = SpringContext.getPlayerService().canCreatePlayer(accountId);
        return result;
    }

    public List<Player> getBasePlayers(String accountId){
        List<Player> basePlayers = SpringContext.getPlayerService().getBasePlayers(accountId);
        return basePlayers;
    }

    public HashMap<String, Account> getId2Account() {
        return id2Account;
    }

    public void setId2Account(HashMap<String, Account> id2Account) {
        this.id2Account = id2Account;
    }

    public HashMap<String, Account> getNickName2Account() {
        return nickName2Account;
    }

    public void setNickName2Account(HashMap<String, Account> nickName2Account) {
        this.nickName2Account = nickName2Account;
    }
}
