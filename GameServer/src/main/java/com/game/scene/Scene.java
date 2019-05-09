package com.game.scene;

import com.game.scene.entity.MonsterEnt;
import com.game.scene.entity.NpcEnt;
import com.game.user.account.model.BaseAccountInfo;
import java.util.ArrayList;
import java.util.List;

public class Scene {
//    private int[][] pos;
    private long id;
    private int mapId;
    private String accountId;
    private List<MonsterEnt> monsterList = new ArrayList<>();
    private List<NpcEnt> npcList = new ArrayList<>();
    private BaseAccountInfo baseAccountInfo;
    private String message;

    public void move(int x, int y){
        baseAccountInfo.setX(x);
        baseAccountInfo.setY(y);
        search();
    }

    ///////////getter setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<MonsterEnt> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<MonsterEnt> monsterList) {
        this.monsterList = monsterList;
    }

    public List<NpcEnt> getNpcList() {
        return npcList;
    }

    public void setNpcList(List<NpcEnt> npcList) {
        this.npcList = npcList;
    }

    public BaseAccountInfo getBaseAccountInfo() {
        return baseAccountInfo;
    }

    public void setBaseAccountInfo(BaseAccountInfo baseAccountInfo) {
        this.baseAccountInfo = baseAccountInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void search(){
        int x = baseAccountInfo.getX();
        int y = baseAccountInfo.getY();
        for (MonsterEnt monsterEnt : monsterList) {
            if (Math.abs(x - monsterEnt.getX()) < 2 || Math.abs(y - monsterEnt.getY()) < 2){
                attack(monsterEnt.getName());
            }
        }
        for (NpcEnt npcEnt : npcList) {
            if (Math.abs(x - npcEnt.getX()) < 2 || Math.abs(y - npcEnt.getY()) < 2){
                talk(npcEnt.getName());
            }
        }
    }

    private void talk(String name) {
        setMessage("遇到npc " + name + ",对话" + "\n" + "对话结束");
    }

    private void attack(String name) {
        setMessage("遇到怪物 " + name + "，攻击" + "\n" + "杀死怪物");
    }
}
