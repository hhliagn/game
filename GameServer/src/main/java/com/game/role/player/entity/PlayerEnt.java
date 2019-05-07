package com.game.role.player.entity;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.user.account.model.BaseAccountInfo;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "player")
@org.hibernate.annotations.Table(appliesTo = "player", comment = "玩家信息")
public class PlayerEnt implements Serializable {

    @Transient
    private volatile Player player;

    @Id
    @Column(columnDefinition = "bigint default 0 comment '玩家Id'", nullable = false)
    private long id;

    //@Column(columnDefinition = "varchar(255) CHARSET SET utf8 COLLATE utf8_bin DEFAULT NULL comment '玩家姓名'")
    //private String name;

    @Column(columnDefinition = "varchar(255) CHARSET SET utf8 COLLATE utf8_bin DEFAULT NULL comment '账号Id'")
    private String accountId;

    @Column(columnDefinition = "int default 1 comment '存活状态'")
    private int alive;

    @Column(columnDefinition = "varchar(255) CHARSET SET utf8 COLLATE utf8_bin DEFAULT NULL comment '玩家姓名'")
    private String name;

    @Column(columnDefinition = "int default 1 comment '职业'", nullable = false)
    private int job;

    @Column(columnDefinition = "int default 1 comment '性别'", nullable = false)
    private int sex;

    //private volatile int level;

    //private volatile long exp;

    //private long levelUpTime;

    @Column(columnDefinition = "timestamp comment '创建玩家时间'")
    private Date createTime;

    //private volatile long battleScore;

    public static PlayerEnt valueOf(long id, String accountId, String name, int job, int sex) {
        PlayerEnt playerEnt = new PlayerEnt();
        playerEnt.id = id;
        playerEnt.accountId = accountId;
        playerEnt.name = name;
        playerEnt.job = job;
        playerEnt.sex = sex;
        playerEnt.createTime = new Date();
        playerEnt.setAlive(1);
        BaseAccountInfo baseAccountInfo = SpringContext.getAccountService().getBaseAccountInfo(accountId);
        Player player = Player.valueOf(playerEnt, baseAccountInfo);
        playerEnt.setPlayer(player);
        return playerEnt;
    }

    public boolean doSerialize(){
        return true;
    }

    public boolean doDeserialize(){
        BaseAccountInfo baseAccountInfo = SpringContext.getAccountService().getBaseAccountInfo(accountId);
        Player player = Player.valueOf(this, baseAccountInfo);
        this.setPlayer(player);
        return true;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
