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

    ////////
    public AccountEnt get(String accountId) {
        String hql = "select a from AccountEnt a where accountId = ?";
        Query query = getSession().createQuery(hql).setString(0, accountId);
        AccountEnt accountEnt = (AccountEnt) query.uniqueResult();
        accountEnt.doDeserialize();
        return accountEnt;
    }

    public void save(AccountEnt accountEnt) {
        accountEnt.doSerialize();
        getSession().saveOrUpdate(accountEnt);
    }

    public List<AccountEnt> loadAll() {
        String hql = "FROM AccountEnt";
        Query query = getSession().createQuery(hql);
        List<AccountEnt> list = query.list();
        for (AccountEnt accountEnt : list) {
            accountEnt.doDeserialize();
        }
        return list;
    }

    public AccountEnt getLoginAccount(String accountId, String password) {
        String hql = "select a from AccountEnt a where accountId = ? and password = ?";
        Query query = getSession().createQuery(hql)
                .setString(0, accountId).setString(1, password);
        AccountEnt accountEnt = (AccountEnt) query.uniqueResult();
        return accountEnt;
    }
}
