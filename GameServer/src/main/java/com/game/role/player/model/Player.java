package com.game.role.player.model;

import com.game.role.player.entity.PlayerEnt;

public class Player {

    private long id;
    private String accountId;
    private boolean alive;

    private PlayerEnt playerEnt;

    public static Player valueOf(PlayerEnt playerEnt){
        Player player = new Player();
        player.id = playerEnt.getId();
        player.accountId = playerEnt.getAccountId();
        player.alive = playerEnt.isAlive();
        player.playerEnt = playerEnt;
        return player;
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

    public PlayerEnt getPlayerEnt() {
        return playerEnt;
    }

    public void setPlayerEnt(PlayerEnt playerEnt) {
        this.playerEnt = playerEnt;
    }
}
