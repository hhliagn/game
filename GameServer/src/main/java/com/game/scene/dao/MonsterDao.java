package com.game.scene.dao;

import com.game.scene.entity.MonsterEnt;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component("MonsterDao")
@Transactional
public class MonsterDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    ////
    public List<MonsterEnt> loadAll() {
        String hql = "FROM MonsterEnt";
        Query query = getSession().createQuery(hql);
        List<MonsterEnt> monsterEntList = query.list();
        return monsterEntList;
    }

    public MonsterEnt get(long id) {
        String hql = "select p from MonsterEnt p where id = ?";
        Query query = getSession().createQuery(hql).setLong(0, id);
        MonsterEnt monsterEnt = (MonsterEnt) query.uniqueResult();
        return monsterEnt;
    }

    public void save(MonsterEnt monsterEnt) {
        getSession().saveOrUpdate(monsterEnt);
    }

    public List<MonsterEnt> findByMapId(int mapId) {
        String hql = "select p from MonsterEnt p where mapId = ?";
        Query query = getSession().createQuery(hql).setInteger(0, mapId);
        List<MonsterEnt> monsterEntList = query.list();
        return monsterEntList;
    }
}
