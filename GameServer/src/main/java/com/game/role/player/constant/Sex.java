package com.game.role.player.constant;

public enum  Sex {
    MAN(1),
    WOMAN(2);

    private final int id;

    Sex(int id) {
        this.id = id;
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
}
