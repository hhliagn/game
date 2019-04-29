package com.game.pojo.service;

import com.game.pojo.dao.EntityEntDao;
import com.game.pojo.entity.EntityEnt;
import com.game.pojo.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.relation.RelationNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class EntityService implements IEntityService {

    private static final Logger logger = LoggerFactory.getLogger("entity");

    @Autowired
    private EntityEntDao entityEntDao;

    private HashMap<Long,Entity> id2Entity = new HashMap<Long, Entity>();

    public List<Entity> findAll() {
        try {
            List<Entity> entityList = new ArrayList<Entity>();
            List<EntityEnt> entityEntList = entityEntDao.findAll();
            for (EntityEnt entityEnt : entityEntList) {
                Entity entity = Entity.valueOf(entityEnt);
                entityList.add(entity);
                addEntityList(entity);
            }
            return entityList;
        } catch (Exception e) {
            logger.warn("查询所有实体集合出错");
            e.printStackTrace();
            throw new RuntimeException("查询所有实体集合出错");
        }
    }

    private void addEntityList(Entity entity) {
        id2Entity.put(entity.getId(),entity);
    }

    public Entity getEntityById(long id){
        return id2Entity.get(id);
    }

    public Entity findOne(Long id) {
        try {
            EntityEnt entityEnt = entityEntDao.findOne(id);
            Entity entity = Entity.valueOf(entityEnt);
            return entity;
        } catch (Exception e) {
            logger.warn("获取单个实体出错");
            e.printStackTrace();
            throw new RuntimeException("获取单个实体出错");
        }
    }

    public List<Entity> getEntityList(long[] entityIds) {
        try {
            List<Entity> entityList = new ArrayList<Entity>();
            for (long entityId : entityIds) {
                Entity entity = findOne(entityId);
                entityList.add(entity);
            }
            return entityList;
        } catch (Exception e) {
            logger.warn("获取实体集合出错");
            e.printStackTrace();
            throw new RuntimeException("获取实体集合出错");
        }
    }

    public void save(Entity entity){
        try {
            entityEntDao.save(entity);
        } catch (Exception e) {
            logger.warn("保存实体出错");
            e.printStackTrace();
            throw new RuntimeException("保存实体出错");
        }
    }
}
