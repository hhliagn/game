package com.game.user.mapInfo.dao;

import com.game.map.entity.MapEnt;
import com.game.user.mapInfo.entity.MapInfoEnt;
import com.game.user.mapInfo.model.MapInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Component("MapInfoDao")
@Transactional
public class MapInfoDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<MapEnt> findAll(){
        String hql = "FROM MapInfoEnt";
        Query query = getSession().createQuery(hql);
        List<MapEnt> list = query.list();
        return list;
    }

    public MapInfoEnt findOne(String accountId){
        String hql = "select m from MapInfoEnt m where accountId = ?";
        Query query = getSession().createQuery(hql).setString(0, accountId);
        MapInfoEnt mapInfoEnt = (MapInfoEnt) query.uniqueResult();
        return mapInfoEnt;
    }

    public void save(MapInfo mapInfo) {
        getSession().saveOrUpdate(mapInfo.getMapInfoEnt());
    }
}
