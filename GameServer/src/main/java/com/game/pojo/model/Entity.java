package com.game.pojo.model;

import com.game.pojo.entity.EntityEnt;

public class Entity {

    public long id;
    public int type;
    public String name;
    public boolean isAlive;

    private EntityEnt entityEnt;

    public static Entity valueOf(EntityEnt entityEnt){
        Entity entity = new Entity();
        entity.id = entityEnt.getId();
        entity.type = entityEnt.getType();
        entity.name = entityEnt.getName();
        entity.isAlive = entityEnt.isAlive();
        entity.entityEnt = entityEnt;
        return entity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public EntityEnt getEntityEnt() {
        return entityEnt;
    }

    public void setEntityEnt(EntityEnt entityEnt) {
        this.entityEnt = entityEnt;
    }
}
