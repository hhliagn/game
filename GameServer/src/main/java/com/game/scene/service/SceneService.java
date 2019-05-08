package com.game.scene.service;

import com.game.SpringContext;
import com.game.core.id.service.IdentifyService;
import com.game.scene.Scene;
import com.game.scene.dao.MonsterDao;
import com.game.scene.dao.NpcDao;
import com.game.user.account.model.BaseAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SceneService implements ISceneService {

    @Autowired
    private MonsterDao monsterDao;
    @Autowired
    private NpcDao npcDao;

    public Scene enter(String accountId, int mapId) {
        Scene scene = new Scene();
        long id = SpringContext.getIdentifyService().getNextIdentify(IdentifyService.IdentifyType.SCENE);
        scene.setId(id);
        scene.setAccountId(accountId);
        scene.setMapId(mapId);
        init(scene);
        return scene;
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
        scene.setBaseAccountInfo(baseAccountInfo);
    }
}
