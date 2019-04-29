package com.game.role.player.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class PlayerEnt {

    @Id
    private long id;
    private String accountId;
    private boolean alive;

    public static PlayerEnt valueOf(long id, String accountId) {
        PlayerEnt playerEnt = new PlayerEnt();
        playerEnt.id = id;
        playerEnt.accountId = accountId;
        playerEnt.alive = true;
        return playerEnt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
