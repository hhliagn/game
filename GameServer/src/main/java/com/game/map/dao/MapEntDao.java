package com.game.map.dao;

import com.game.map.entity.MapEnt;
import com.game.map.model.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("MapEntDao")
@Transactional
public class MapEntDao{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<MapEnt> findAll(){
        String hql = "FROM MapEnt";
        Query query = getSession().createQuery(hql);
        List<MapEnt> list = query.list();
        return list;
    }

    public MapEnt findOne(int id){
        String hql = "select m from MapEnt m where id = ?";
        Query query = getSession().createQuery(hql).setInteger(0, id);
        MapEnt mapEnt = (MapEnt) query.uniqueResult();
        return mapEnt;
    }

    public void save(Map map) {
        getSession().update(map.getMapEnt());
    }
}
