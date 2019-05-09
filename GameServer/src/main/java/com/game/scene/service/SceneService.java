package com.game.scene.service;

import com.game.SpringContext;
import com.game.core.id.service.IdentifyService;
import com.game.scene.Scene;
import com.game.scene.entity.MonsterDao;
import com.game.scene.entity.NpcDao;
import com.game.user.account.model.BaseAccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SceneService implements ISceneService {

    private static Logger logger = LoggerFactory.getLogger("scene");

    @Autowired
    private MonsterDao monsterDao;
    @Autowired
    private NpcDao npcDao;

    private Map<Long, Scene> id2Scene = new HashMap<>();

    public void enter(String accountId, int mapId) {
        Scene scene = new Scene();
        long id = SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.SCENE);
        scene.setId(id);
        scene.setAccountId(accountId);
        scene.setMapId(mapId);
        init(scene);
        addScene(scene);
    }

    public Scene getScene(long id){
        return id2Scene.get(id);
    }

    private void addScene(Scene scene) {
        id2Scene.put(scene.getId(), scene);
    }

    private void init(Scene scene) {
        int mapId = scene.getMapId();
        String accountId = scene.getAccountId();
        scene.setMonsterList(monsterDao.findByMapId(mapId));
        scene.setNpcList(npcDao.findByMapId(mapId));
        BaseAccountInfo baseAccountInfo = SpringContext.getAccountService().getBaseAccountInfo(accountId);
        baseAccountInfo.setX(1);
        baseAccountInfo.setY(1);
        baseAccountInfo.setSceneId(scene.getId());
        baseAccountInfo.setMapId(scene.getMapId());
        scene.setBaseAccountInfo(baseAccountInfo);
        logger.info("进入场景");
    }

    public MonsterDao getMonsterDao() {
        return monsterDao;
    }

    public NpcDao getNpcDao() {
        return npcDao;
    }
}
