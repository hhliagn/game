package com.game.user.mapInfo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mapinfo")
public class MapInfoEnt implements Serializable {

    @Id
    private int id;
    private String accountId;
    private int curMapId;
    private int lastMapId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getCurMapId() {
        return curMapId;
    }

    public void setCurMapId(int curMapId) {
        this.curMapId = curMapId;
    }

    public int getLastMapId() {
        return lastMapId;
    }

    public void setLastMapId(int lastMapId) {
        this.lastMapId = lastMapId;
    }
}
