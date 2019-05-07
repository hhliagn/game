package com.game.role.player.model;

public class BasePlayerInfo {

    private long playerId;
    private String accountId;
    private int status;
    private String name;
    private int job;
    private int sex;

    public void refreshInfo(Player player){
        this.name = player.getName();
        this.status = player.getStatus();
        this.job = player.getJob();
        this.sex = player.getSex();
    }

    public void refreshJobInfo(Player player){
        this.job = player.getJob();
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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
        this.playerId = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
