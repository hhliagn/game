package com.game.role.player.constant;

public enum  Sex {

    MAN(1, "男"),
    WOMAN(2, "女"),
    ;

    private int id;
    private String name;

    Sex(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sex valueOf(int sexId){
        for (Sex sex : values()) {
            if (sex.id == sexId){
                return sex;
            }
        }
        return Sex.MAN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
