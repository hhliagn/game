package com.game.pojo.service;

import com.game.pojo.dao.EntityEntDao;
import com.game.pojo.entity.EntityEnt;
import com.game.pojo.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class EntityService implements IEntityService {

    @Autowired
    private EntityEntDao entityEntDao;

    private HashMap<Long,Entity> id2Entity = new HashMap<Long, Entity>();

    public List<Entity> findAll() {
        List<Entity> entityList = new ArrayList<Entity>();
        List<EntityEnt> entityEntList = entityEntDao.findAll();
        for (EntityEnt entityEnt : entityEntList) {
            Entity entity = Entity.valueOf(entityEnt);
            entityList.add(entity);
            addEntityList(entity);
        }

        return entityList;
    }

    private void addEntityList(Entity entity) {
        id2Entity.put(entity.getId(),entity);
    }

    public Entity getEntityById(long id){
        return id2Entity.get(id);
    }


    public Entity findOne(Long id) {
        EntityEnt entityEnt = entityEntDao.findOne(id);
        Entity entity = Entity.valueOf(entityEnt);
        return entity;
    }

    public List<Entity> getEntityList(long[] entityIds) {
        List<Entity> entityList = new ArrayList<Entity>();
        for (long entityId : entityIds) {
            Entity entity = findOne(entityId);
            entityList.add(entity);
        }
        return entityList;
    }

    public void save(Entity entity){
        entityEntDao.save(entity);
    }
}
