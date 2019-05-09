package com.game.scene.entity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component("NpcDao")
@Transactional
public class NpcDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    ////
    public List<NpcEnt> loadAll() {
        String hql = "FROM NpcEnt";
        Query query = getSession().createQuery(hql);
        List<NpcEnt> npcEntList = query.list();
        return npcEntList;
    }

    public NpcEnt get(long id) {
        String hql = "select p from NpcEnt p where id = ?";
        Query query = getSession().createQuery(hql).setLong(0, id);
        NpcEnt npcEnt = (NpcEnt) query.uniqueResult();
        return npcEnt;
    }

    public void save(NpcEnt npcEnt) {
        getSession().saveOrUpdate(npcEnt);
    }

    public List<NpcEnt> findByMapId(int mapId) {
        String hql = "select p from NpcEnt p where mapId = ?";
        Query query = getSession().createQuery(hql).setInteger(0, mapId);
        List<NpcEnt> npcEntList = query.list();
        return npcEntList;
    }
}
