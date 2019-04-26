package com.game.map.model;

import com.game.SpringContext;
import com.game.map.entity.MapEnt;
import com.game.pojo.model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Map {

    public int id;
    public String name;
    public long[] entityIds;
    public int[] mapIdNearbys;

    private MapEnt mapEnt;

    public List<Entity> entityList = new ArrayList<Entity>();

    public static Map valueOf(MapEnt mapEnt){
        Map map = new Map();
        map.id = mapEnt.getId();
        map.name = mapEnt.getName();
        map.mapEnt = mapEnt;
        map.entityIds = mapEnt.doParseEntityIdList();
        map.entityList = SpringContext.getEntityService().getEntityList(map.entityIds);
        map.mapIdNearbys = mapEnt.doParseMapIdNearby();
        return map;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long[] getEntityIds() {
        return entityIds;
    }

    public void setEntityIds(long[] entityIds) {
        this.entityIds = entityIds;
    }

    public int[] getMapIdNearbys() {
        return mapIdNearbys;
    }

    public void setMapIdNearbys(int[] mapIdNearbys) {
        this.mapIdNearbys = mapIdNearbys;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public MapEnt getMapEnt() {
        return mapEnt;
    }

    public void setMapEnt(MapEnt mapEnt) {
        this.mapEnt = mapEnt;
    }
}
