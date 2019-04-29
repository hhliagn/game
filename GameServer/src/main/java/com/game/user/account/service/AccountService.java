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

    private static Logger logger = LoggerFactory.getLogger("account");

    @Autowired
    private AccountDao accountDao;

    private HashMap<String, Account> id2Account = new HashMap<>();

    private HashMap<String, Account> nickName2Account = new HashMap<>();

    public void loadAllAccountInfo() {
        try {
            SpringContext.getPlayerService().loadAllPlayerInfo();
            List<AccountEnt> accountEntList = accountDao.findAll();
            if (accountEntList == null){
                logger.warn("账号表中不存在数据");
            }
            for (AccountEnt accountEnt : accountEntList) {
                Account account = Account.valueOf(accountEnt);
                addAccountInfo(account);
            }
        } catch (Exception e) {
            logger.warn("加载用户数据出错");
            e.printStackTrace();
            throw new RuntimeException("加载用户数据出错");
        }
    }

    public void addAccountInfo(Account account) {
        try {
            if (account == null){
                logger.warn("添加的账户为null");
            }
            Account existId = id2Account.get(account.getAccountId());
            if (existId == null){
                id2Account.put(account.getAccountId(), account);
            }

            Account existNickName = nickName2Account.get(account.getNickName());
            if (existNickName == null){
                nickName2Account.put(account.getNickName(), account);
            }
        } catch (Exception e) {
            logger.warn("添加账户信息出错");
            e.printStackTrace();
            throw new RuntimeException("添加账户信息出错");
        }
    }

    public void saveAccount(Account account) {
        try {
            accountDao.save(account);
            id2Account.put(account.getAccountId(),account);
            nickName2Account.put(account.getNickName(), account);
        } catch (Exception e) {
            logger.warn("保存账户出错");
            e.printStackTrace();
            throw new RuntimeException("保存账户出错");
        }
    }

    public Account getAccount(String accountId) {
        Account account = id2Account.get(accountId);
        return account;
    }

    public Account getAccountByNickName(String nickName){
        Account account = nickName2Account.get(nickName);
        return account;
    }

    public void createAccount(String accountId, String password){
        boolean exist = isExistAccount(accountId);
        if (!exist){

            Account account = null;
            try {
                account = accountDao.createAccount(accountId, password);
            } catch (Exception e) {
                logger.warn("账户表创建用户失败");
                e.printStackTrace();
                throw new RuntimeException("账户表创建用户失败");
            }
            addAccountInfo(account);
            doAfterCreateAccount(account);
        }
    }

    @Override
    public Account getLoginAccount(String accountId, String password) {
        try {
            Account loginAccount = accountDao.findOne(accountId, password);
            return loginAccount;
        } catch (Exception e) {
            logger.info("获取登录用户出错");
            e.printStackTrace();
            throw new RuntimeException("获取登录用户出错");
        }
    }

    public void doAfterCreateAccount(Account account){
        //创建玩家的地图信息
        //设置玩家curMapId为起始之地
        SpringContext.getMapInfoService().createMapInfo(account.getAccountId());
    }

    private boolean isExistAccount(String accountId) {
        Account account = getAccount(accountId);
        //Account accountByNickName = getAccountByNickName(nickName);
        if (account != null){
            logger.info("账户id已存在！");
            throw new RuntimeException("账户id已存在！");
        }
        /*if (accountByNickName != null){
            logger.info("账户昵称已存在！");
            throw new RuntimeException("账户昵称已存在！");
        }*/
        return false;
    }

    private Player createPlayer(String name) {

        Account curLoginAccount = SpringContext.getGlobalService().getCurLoginAccount();
        if (curLoginAccount == null){
            throw new RuntimeException("当前未登录");
        }
        String accountId = curLoginAccount.getAccountId();

        List<Player> basePlayers = SpringContext.getPlayerService().getBasePlayers(accountId);
        if (basePlayers == null){
            SpringContext.getPlayerService().createPlayer(accountId);
            curLoginAccount.setNickName(name);
            saveAccount(curLoginAccount);
        }else {
            SpringContext.getPlayerService().createPlayer(accountId);
        }
        if (canCreatePlayer(accountId)){

            Player player = SpringContext.getPlayerService().createPlayer(accountId);
            if (player == null){
                logger.warn("创建的用户为null");
            }
            return player;
        }else {
            return getRecentPlayer(accountId);
        }
    }

    private Player getRecentPlayer(String accountId) {
        Account account = getAccount(accountId);
        long recentPlayerId = account.getRecentPlayerId();
        Player player = SpringContext.getPlayerService().getRecentPlayer(recentPlayerId);
        if (player == null){
            logger.warn("获取到的账户最近玩家为null");
        }
        return player;
    }

    private boolean canCreatePlayer(String accountId) {
        boolean result = SpringContext.getPlayerService().canCreatePlayer(accountId);
        return result;
    }

    public List<Player> getBasePlayers(String accountId){
        List<Player> basePlayers = SpringContext.getPlayerService().getBasePlayers(accountId);
        if (basePlayers == null || basePlayers.size() == 0){
            logger.warn("账号不存在对应的玩家");
        }
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
