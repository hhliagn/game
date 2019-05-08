package com.game.core.id.entity;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "identify", comment = "各模块id生成")
public class IdentifyEnt {

    private static final int STEP = 100;

    @Id
    @Column(columnDefinition = "varchar(64) CHARSET SET utf8 COLLATE utf8_bin comment '功能name'", nullable = false)
    private String id;

    @Column(columnDefinition = "bigint(20) default 0 comment '下一次启动服务从这个数字开始递增'", nullable = false)
    private volatile long value;

    @Transient
    private long now;

    public Long getNextIdentify(){
        if (now == 0){
            now = value;
        }
        if (now == value){
            value += STEP;
        }
        return ++now;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
        this.now = now;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
