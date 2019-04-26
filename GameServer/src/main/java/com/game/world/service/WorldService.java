package com.game.world.service;

import com.game.SpringContext;
import com.game.map.model.Map;
import com.game.role.player.model.Player;
import com.game.user.mapInfo.model.MapInfo;
import com.game.user.mapInfo.service.IMapInfoService;
import org.springframework.stereotype.Component;

@Component
public class WorldService implements IWorldService {

    public void initMapData() {
        SpringContext.getMapService().initMapData();
    }

    public void changeMap(Player player,int mapId) {
        String accountId = player.getAccountId();
        leaveOldMap(accountId,mapId);
        canEnter(accountId,mapId);
        enterNewMap(accountId,mapId);
    }

    public void leaveOldMap(String accountId, int mapId) {
        IMapInfoService mapInfoService = SpringContext.getMapInfoService();
        MapInfo mapInfo = mapInfoService.getMapInfo(accountId);
        int curMapId = mapInfo.getCurMapId();
        mapInfo.setLastMapId(curMapId);
        mapInfoService.saveMapInfo(mapInfo);
    }

    public boolean canEnter(String accountId, int mapId) {
        MapInfo mapInfo = SpringContext.getMapInfoService().getMapInfo(accountId);
        int curMapId = mapInfo.getCurMapId();
        Map map = SpringContext.getMapService().getMapById(curMapId);
        int[] mapIdNearbys = map.getMapIdNearbys();
        for (int i = 0; i < mapId; i++) {
            if (mapIdNearbys[i] == mapId){
                return true;
            }
        }
        return false;
    }

    public void enterNewMap(String accountId, int mapId) {
        IMapInfoService mapInfoService = SpringContext.getMapInfoService();
        MapInfo mapInfo = mapInfoService.getMapInfo(accountId);
        mapInfo.setCurMapId(mapId);
        mapInfoService.saveMapInfo(mapInfo);
    }
}
