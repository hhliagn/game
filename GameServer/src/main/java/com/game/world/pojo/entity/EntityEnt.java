package com.game.world.service.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Table(appliesTo = "entity", comment = "地图中的实体")
public class EntityEnt implements Serializable {

    @Id
    @Column(columnDefinition = "bigint(20) default 0 comment '实体id'", nullable = false)
    private long id;
    @Column(columnDefinition = "varchar(255) default 0 comment '实体名称'")
    private String name;
    @Column(columnDefinition = "int default 1 comment '实体状态'")
    private int alive;

    public int getAlive() {
        return alive;
    }

    @Column(columnDefinition = "int default 1 comment '实体类别'")
    private int type;

    public void setAlive(int alive) {
        this.alive = alive;
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

}
