package com.game.scene.service;

import com.game.scene.Scene;
import com.game.scene.entity.MonsterDao;
import com.game.scene.entity.NpcDao;

public interface ISceneService {

    void enter(String accountId, int mapId);

    Scene getScene(long id);

    MonsterDao getMonsterDao();

    NpcDao getNpcDao();
}
