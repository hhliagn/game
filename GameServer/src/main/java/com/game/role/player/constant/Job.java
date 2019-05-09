package com.game.role.player.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Job {

    WARRIOR(1, "战士"),
    MAGICIAN(2, "法师"),
    TAOIST(3, "道士"),
    ;

    private final int value;
    private final String name;

    private static Map<Integer, List<String>> job2NamesMap = new HashMap<>(3);


    Job(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Job valueOf(int value){
        for (Job job : values()) {
            if (job.getValue() == value){
                return job;
            }
        }
        throw new RuntimeException("没有匹配的职业");
    }

    public String getName(int sex){
        List<String> names = job2NamesMap.get(this.getValue());
        String name = names.get(sex - 1);
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Map<Integer, List<String>> getJob2NamesMap() {
        return job2NamesMap;
    }

    public static void setJob2NamesMap(Map<Integer, List<String>> job2NamesMap) {
        Job.job2NamesMap = job2NamesMap;
    }
}
