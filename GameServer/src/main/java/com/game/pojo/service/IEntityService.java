package com.game.pojo.service;

import com.game.pojo.model.Entity;
import java.util.List;

public interface IEntityService {

    List<Entity> findAll();

    Entity findOne(Long id);

    List<Entity> getEntityList(long[] entityIds);

    Entity getEntityById(long id);

    void save(Entity entity);
}
