package com.game.user.account.dao;

import com.game.map.entity.MapEnt;
import com.game.map.model.Map;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional
public class AccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<AccountEnt> findAll(){
        String hql = "FROM AccountEnt";
        Query query = getSession().createQuery(hql);
        List<AccountEnt> list = query.list();
        return list;
    }

    public Account findOne(String accountId){
        String hql = "select a from AccountEnt a where id = ?";
        Query query = getSession().createQuery(hql).setString(0, accountId);
        AccountEnt accountEnt = (AccountEnt) query.uniqueResult();
        return Account.valueOf(accountEnt);
    }

    public Account findOne(String accountId, String password){
        String hql = "select a from AccountEnt a where accountId = ? and password = ?";
        Query query = getSession().createQuery(hql)
                .setString(0, accountId).setString(1, password);
        AccountEnt accountEnt = (AccountEnt) query.uniqueResult();
        return Account.valueOf(accountEnt);
    }

    public void save(Account account) {
        getSession().update(account.getAccountEnt());
    }

    public Account createAccount(String accountId,String password) {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId(accountId);
        accountEnt.setNickName(null);
        accountEnt.setRecentPlayerId(null);
        accountEnt.setNowLogin(new Date());
        accountEnt.setNowLogout(null);
        accountEnt.setLastLogin(null);
        accountEnt.setLastLogout(null);
        accountEnt.setPassword(password);
        getSession().save(accountEnt);
        Account account = Account.valueOf(accountEnt);
        return account;
    }
}
