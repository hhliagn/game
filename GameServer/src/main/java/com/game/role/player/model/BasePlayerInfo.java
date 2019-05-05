package com.game.role.player.model;

public class BasePlayerInfo {

    private Long playerId;
    private String accountId;
    private int status;

    public BasePlayerInfo() {
    }

    public BasePlayerInfo(Long playerId, String accountId, int status) {
        this.playerId = playerId;
        this.accountId = accountId;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return playerId;
    }

    public void setId(Long id) {
        this.playerId = playerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
