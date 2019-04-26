package com.game.pojo.dao;

import com.game.pojo.entity.EntityEnt;
import com.game.pojo.model.Entity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component("EntityEntDao")
@Transactional
public class EntityEntDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<EntityEnt> findAll(){
        String hql = "FROM EntityEnt";
        Query query = getSession().createQuery(hql);
        List<EntityEnt> list = query.list();
        return list;
    }

    public EntityEnt findOne(long id) {
        String hql = "select e from EntityEnt e where id = ?";
        Query query = getSession().createQuery(hql).setLong(0, id);
        EntityEnt entityEnt = (EntityEnt) query.uniqueResult();
        return entityEnt;
    }

    public void save(Entity entity) {
        getSession().update(entity.getEntityEnt());
    }
}
