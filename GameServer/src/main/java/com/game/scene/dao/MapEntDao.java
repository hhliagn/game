package com.game.scene.dao;

import com.game.scene.entity.MapEnt;
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

    public List<MapEnt> loadAll(){
        try {
            String hql = "FROM MapEnt";
            Query query = getSession().createQuery(hql);
            List<MapEnt> list = query.list();
            return list;
        }catch (Exception e){
            throw new RuntimeException("获取地图数据出错");
        }
    }

    public MapEnt get(int id){
        String hql = "select m from MapEnt m where id = ?";
        Query query = getSession().createQuery(hql).setInteger(0, id);
        MapEnt mapEnt = (MapEnt) query.uniqueResult();
        return mapEnt;
    }

    public void save(MapEnt mapEnt) {
        getSession().saveOrUpdate(mapEnt);
    }
}
