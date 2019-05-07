package com.game.world.service.map.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Table(appliesTo = "map", comment = "地图信息")
public class MapEnt implements Serializable {

    @Id
    @Column(columnDefinition = "int default 0 comment '地图id'")
    private int id;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '地图名称'")
    private String name;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '地图中实体集合'")
    private String entityIdList;
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '附近地图Id'")
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
