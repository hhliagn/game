package com.game.scene.entity;

import com.game.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Table(appliesTo = "monster", comment = "怪物信息")
public class MonsterEnt implements Serializable {

    @Id
    @Column(columnDefinition = "bigint default 0 comment '怪物id'", nullable = false)
    private long id;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE comment '怪物名称'", nullable = false)
    private String name;

    @Column(columnDefinition = "int default 0 comment '地图id'")
    private int mapId;

    @Column(columnDefinition = "int default 0 comment '出生点X'")
    private int x;

    @Column(columnDefinition = "int default 0 comment '出生点Y'")
    private int y;

    @Column(columnDefinition = "int default 0 comment '存活状态'")
    private int status;

    public static MonsterEnt valueOf(long id, String name, int mapId, int x, int y){
        MonsterEnt monsterEnt = new MonsterEnt();
        monsterEnt.setId(id);
        monsterEnt.setName(name);
        monsterEnt.setMapId(mapId);
        monsterEnt.setX(x);
        monsterEnt.setY(y);
        monsterEnt.setStatus(1);
        return monsterEnt;
    }

    public boolean isAlive(){
        if (status == 0) return false;
        if (status == 1) return true;
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
