package com.game.map.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "map")
public class MapEnt implements Serializable {

    @Id
    private int id;
    private String name;
    private String entityIdList;
    private String mapIdNearby;

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

    public String getEntityIdList() {
        return entityIdList;
    }

    public void setEntityIdList(String entityIdList) {
        this.entityIdList = entityIdList;
    }

    public String getMapIdNearby() {
        return mapIdNearby;
    }

    public void setMapIdNearby(String mapIdNearby) {
        this.mapIdNearby = mapIdNearby;
    }

    public long[] doParseEntityIdList(){
        String[] split = entityIdList.split(",");
        long[] entityIds = new long[split.length];
        for (int i = 0; i < split.length; i++){
            entityIds[i] = Long.valueOf(split[i]);
        }
        return entityIds;
    }

    public int[] doParseMapIdNearby(){
        String[] split = mapIdNearby.split(",");
        int[] mapIdNearBys = new int[split.length];
        for (int i = 0; i < split.length; i++){
            mapIdNearBys[i] = Integer.valueOf(split[i]);
        }
        return mapIdNearBys;
    }
}
