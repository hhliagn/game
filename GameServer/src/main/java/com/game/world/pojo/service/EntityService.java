package com.game.world.service.pojo.service;

import com.game.world.service.pojo.dao.EntityEntDao;
import com.game.world.service.pojo.entity.EntityEnt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityService implements IEntityService {

    private static final Logger logger = LoggerFactory.getLogger("entity");

    @Autowired
    private EntityEntDao entityEntDao;

    public EntityEnt getEntityEnt(long id) {
        try {
            EntityEnt entityEnt = entityEntDao.get(id);
            return entityEnt;
        } catch (Exception e) {
            throw new RuntimeException("获取单个实体出错");
        }
    }

    /*public void saveEntityEnt(long id){
        EntityEnt entityEnt = getEntityEnt(id);
        entityEntDao.save(entityEnt);
    }*/
}
