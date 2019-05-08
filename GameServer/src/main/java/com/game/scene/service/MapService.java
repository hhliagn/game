package com.game.scene.service;

import com.game.SpringContext;
import com.game.scene.dao.MapEntDao;
import com.game.scene.entity.MapEnt;
import com.game.user.mapInfo.entity.MapInfoEnt;
import com.game.user.mapInfo.service.IMapInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Component
public class MapService implements IMapService {

    private static final Logger logger = LoggerFactory.getLogger("map");

    @Autowired
    private MapEntDao mapEntDao;

    private HashMap<Integer, MapEnt> id2Map = new HashMap<>();

    private HashMap<String, MapEnt> name2Map = new HashMap<>();

    public void initMapData() {
        List<MapEnt> mapEntList = mapEntDao.loadAll();
        for (MapEnt mapEnt : mapEntList) {
            id2Map.put(mapEnt.getMapId(), mapEnt);
            name2Map.put(mapEnt.getMapName(), mapEnt);
        }
    }

    @Override
    public MapEnt getMapEnt(int mapId) {
        MapEnt mapEnt = mapEntDao.get(mapId);
        return mapEnt;
    }

    @Override
    public void saveMapEnt(int mapId) {
        MapEnt mapEnt = getMapEnt(mapId);
        mapEntDao.save(mapEnt);
    }

    public MapEnt getMapEntById(int id){
        return id2Map.get(id);
    }

    public MapEnt getMapEntByName(String mapName){
        return name2Map.get(mapName);
    }

    public boolean isPlayerCurMap(String accountId, int mapId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(accountId);
        if (mapInfoEnt == null) {
            throw new RuntimeException("账户对应的地图数据为空");
        }
        if (mapInfoEnt.getCurMapId() == mapId) {
            return true;
        }
        return false;
    }

    public void changeMap(String accountId, String mapName) {
        MapEnt mapEnt = getMapEntByName(mapName);
        if (mapEnt == null){
            throw new RuntimeException("地图不存在");
        }
        boolean b = canEnter(accountId, mapEnt.getMapId());
        if (b){
            leaveOldMap(accountId);
            enterNewMap(accountId, mapEnt.getMapId());
        }else {
            throw new RuntimeException("地图不在附近");
        }
    }

    @Override
    public int name2Id(String mapName) {
        MapEnt mapEnt = getMapEntByName(mapName);
        int mapId = mapEnt.getMapId();
        return mapId;
    }

    private void leaveOldMap(String accountId) {
        IMapInfoService mapInfoService = SpringContext.getMapInfoService();

        MapInfoEnt mapInfoEnt = mapInfoService.getMapInfoEnt(accountId);
        int curMapId = mapInfoEnt.getCurMapId();
        mapInfoEnt.setLastMapId(curMapId);

        mapInfoService.saveMapInfoEnt(accountId);
        logger.info("离开旧地图");
    }

    private boolean canEnter(String accountId, int mapId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(accountId);
        int curMapId = mapInfoEnt.getCurMapId();
        MapEnt curMap = getMapEnt(curMapId);
        Integer[] mapIdNearbys = (Integer[]) curMap.getMapNearByIds().toArray();
        for (int i = 0; i < mapIdNearbys.length; i++) {
            if (mapIdNearbys[i] == mapId){
                return true;
            }
        }
        return false;
    }

    private void enterNewMap(String accountId, int mapId) {
        IMapInfoService mapInfoService = SpringContext.getMapInfoService();
        MapInfoEnt mapInfoEnt = mapInfoService.getMapInfoEnt(accountId);
        mapInfoEnt.setCurMapId(mapId);
        mapInfoService.saveMapInfoEnt(mapInfoEnt);
        logger.info("进入新地图");
    }
}
