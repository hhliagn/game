package com.game;

import com.game.scene.entity.*;
import com.game.scene.service.IMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GlobalService {

    private static Logger logger = LoggerFactory.getLogger(GlobalService.class);

    @Autowired
    private MonsterDao monsterDao;

    @Autowired
    private NpcDao npcDao;

    public void onStart(){
        logger.info("启动游戏");
        logger.info("初始化账号数据...");
        SpringContext.getAccountService().loadAllAccountInfo();
        logger.info("初始化地图数据...");
        SpringContext.getMapService().initMapData();
    }

    public void writeData(){

        //地图数据
        MapEnt mapEnt0 = MapEnt.valueOf(1, "启始之地", Arrays.asList(), Arrays.asList(11L), Arrays.asList(2));
        MapEnt mapEnt1 = MapEnt.valueOf(2, "村子", Arrays.asList(), Arrays.asList(21L, 22L), Arrays.asList(3, 4));
        MapEnt mapEnt2 = MapEnt.valueOf(3, "森林", Arrays.asList(31L, 32L, 33L, 34L, 35L), Arrays.asList(31L, 32L), Arrays.asList(2));
        MapEnt mapEnt3 = MapEnt.valueOf(4, "传说秘境", Arrays.asList(41L, 42L, 43L, 44L, 45L), Arrays.asList(41L), Arrays.asList(2));
        IMapService mapService = SpringContext.getMapService();
        mapService.saveMapEnt(mapEnt0);
        mapService.saveMapEnt(mapEnt1);
        mapService.saveMapEnt(mapEnt2);
        mapService.saveMapEnt(mapEnt3);

        //怪物数据
        MonsterEnt monsterEnt0 = MonsterEnt.valueOf(31L, "头狼", 3, 5, 5);
        MonsterEnt monsterEnt1 = MonsterEnt.valueOf(32L, "狼", 3, 5, 4);
        MonsterEnt monsterEnt2 = MonsterEnt.valueOf(33L, "狼", 3, 5, 6);
        MonsterEnt monsterEnt3 = MonsterEnt.valueOf(34L, "狼", 3, 4, 5);
        MonsterEnt monsterEnt4 = MonsterEnt.valueOf(35L, "狼", 3, 6, 5);

        MonsterEnt monsterEnt5 = MonsterEnt.valueOf(41L, "强盗", 4, 5, 5);
        MonsterEnt monsterEnt6 = MonsterEnt.valueOf(42L, "守卫", 4, 5, 4);
        MonsterEnt monsterEnt7 = MonsterEnt.valueOf(43L, "守卫", 4, 5, 6);
        MonsterEnt monsterEnt8 = MonsterEnt.valueOf(44L, "守卫", 4, 4, 5);
        MonsterEnt monsterEnt9 = MonsterEnt.valueOf(45L, "守卫", 4, 6, 5);
        monsterDao.save(monsterEnt0);
        monsterDao.save(monsterEnt1);
        monsterDao.save(monsterEnt2);
        monsterDao.save(monsterEnt3);
        monsterDao.save(monsterEnt4);
        monsterDao.save(monsterEnt5);
        monsterDao.save(monsterEnt6);
        monsterDao.save(monsterEnt7);
        monsterDao.save(monsterEnt8);
        monsterDao.save(monsterEnt9);

        //npc数据
        NpcEnt npcEnt0 = NpcEnt.valueOf(11L, "新手指导员", 1, 10, 10);
        NpcEnt npcEnt1 = NpcEnt.valueOf(21L, "村长", 2, 10, 10);
        NpcEnt npcEnt2 = NpcEnt.valueOf(22L, "铁匠", 2, 20, 20);
        NpcEnt npcEnt3 = NpcEnt.valueOf(31L, "守林人", 3, 10, 10);
        NpcEnt npcEnt4 = NpcEnt.valueOf(32L, "农夫", 3, 20, 20);
        NpcEnt npcEnt5 = NpcEnt.valueOf(41L, "神秘人", 4, 10, 10);
        npcDao.save(npcEnt0);
        npcDao.save(npcEnt1);
        npcDao.save(npcEnt2);
        npcDao.save(npcEnt3);
        npcDao.save(npcEnt4);
        npcDao.save(npcEnt5);
    }
}
