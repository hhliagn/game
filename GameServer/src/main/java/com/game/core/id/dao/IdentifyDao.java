package com.game.core.id.dao;

import com.game.core.id.entity.IdentifyEnt;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("IdentifyDao")
@Transactional
public class IdentifyDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<IdentifyEnt> findAll(){
        String hql = "FROM IdentifyEnt";
        Query query = getSession().createQuery(hql);
        List<IdentifyEnt> list = query.list();
        return list;
    }

    public IdentifyEnt findOne(String id){
        String hql = "select i from IdentifyEnt i where id = ?";
        Query query = getSession().createQuery(hql).setString(0, id);
        IdentifyEnt identifyEnt = (IdentifyEnt) query.uniqueResult();
        return identifyEnt;
    }

    public void save(IdentifyEnt identifyEnt) {
        getSession().saveOrUpdate(identifyEnt);
    }
}
