package com.game.core.id.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "identify")
public class IdentifyEnt {

    private static final int STEP = 100;

    @Id
    private String id;

    @Transient
    private long now;

    private volatile long value;

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
