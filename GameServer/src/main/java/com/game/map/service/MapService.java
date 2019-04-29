package com.game.map.service;

import com.game.SpringContext;
import com.game.map.dao.MapEntDao;
import com.game.map.entity.MapEnt;
import com.game.map.model.Map;
import com.game.pojo.model.Entity;
import com.game.role.player.model.Player;
import com.game.user.account.model.Account;
import com.game.user.mapInfo.model.MapInfo;
import com.game.user.mapInfo.service.IMapInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

@Component
public class MapService implements IMapService {

    private static final Logger logger = LoggerFactory.getLogger("map");

    @Autowired
    private MapEntDao mapEntDao;

    private HashMap<Integer, Map> id2Map = new HashMap<Integer, Map>();

    private HashMap<String, Map> name2Map = new HashMap<>();

    public void initMapData() {
        try {
            List<MapEnt> mapEntList = mapEntDao.findAll();
            for (MapEnt mapEnt : mapEntList) {
                Map map = Map.valueOf(mapEnt);
                id2Map.put(map.getId(),map);
                name2Map.put(map.getName(),map);
            }
        } catch (Exception e) {
            logger.warn("初始化地图出错");
            e.printStackTrace();
            throw new RuntimeException("初始化地图出错");
        }
    }

    public Map getMapById(int id){
        return id2Map.get(id);
    }

    public void save(Map map){
        try {
            mapEntDao.save(map);
        } catch (Exception e) {
            logger.warn("保存地图数据出错");
            e.printStackTrace();
            throw new RuntimeException("保存地图数据出错");
        }
    }

    public List<Entity> getAllEntity(int mapId) {
        Map map = getMapById(mapId);
        if (map == null){
            logger.warn("相应地图不存在");
            throw new RuntimeException("相应地图不存在");
        }
        List<Entity> entityList = map.getEntityList();
        if (entityList == null){
            logger.warn("地图实体集合为null");
            throw new RuntimeException("地图实体集合为null");
        }
        return entityList;
    }

    public boolean isPlayerCurMap(String accountId, int mapId) {
        MapInfo mapInfo = SpringContext.getMapInfoService().getMapInfo(accountId);
        if (mapInfo == null) {
            logger.warn("账户对应的地图数据为空");
            throw new RuntimeException("账户对应的地图数据为空");
        }
        if (mapInfo.getCurMapId() == mapId) {
            return true;
        }
        return false;
    }

    public void changeMap(String mapName) {
        Account curLoginAccount = SpringContext.getGlobalService().getCurLoginAccount();
        if (curLoginAccount == null){
            throw new RuntimeException("当前未登录");
        }
        Map map = SpringContext.getMapService().getMapByName(mapName);
        if (map == null){
            throw new RuntimeException("地图不存在");
        }
        String accountId = curLoginAccount.getAccountId();
        leaveOldMap(accountId);
        canEnter(accountId,map.getId());
        enterNewMap(accountId,map.getId());
    }

    public void leaveOldMap(String accountId) {
        try {
            IMapInfoService mapInfoService = SpringContext.getMapInfoService();
            MapInfo mapInfo = mapInfoService.getMapInfo(accountId);
            int curMapId = mapInfo.getCurMapId();
            mapInfo.setLastMapId(curMapId);
            mapInfoService.saveMapInfo(mapInfo);
        } catch (Exception e) {
            logger.warn("离开旧地图出错");
            e.printStackTrace();
            throw new RuntimeException("离开旧地图出错");
        }
    }

    public boolean canEnter(String accountId, int mapId) {
        try {
            MapInfo mapInfo = SpringContext.getMapInfoService().getMapInfo(accountId);
            int curMapId = mapInfo.getCurMapId();
            Map map = SpringContext.getMapService().getMapById(curMapId);
            int[] mapIdNearbys = map.getMapIdNearbys();
            for (int i = 0; i < mapIdNearbys.length; i++) {
                if (mapIdNearbys[i] == mapId){
                    return true;
                }
            }
        }catch (Exception e){
            logger.warn("判断能否进入新地图出错");
            e.printStackTrace();
            throw new RuntimeException("判断能否进入新地图出错");
        }
        return false;
    }

    public void enterNewMap(String accountId, int mapId ) {
        try {
            IMapInfoService mapInfoService = SpringContext.getMapInfoService();
            MapInfo mapInfo = mapInfoService.getMapInfo(accountId);
            mapInfo.setCurMapId(mapId);
            mapInfoService.saveMapInfo(mapInfo);
        } catch (Exception e) {
            logger.warn("进入新地图出错");
            e.printStackTrace();
            throw new RuntimeException("进入新地图出错");
        }
    }

    @Override
    public String getMap(int mapId) {
        try {
            Map map = getMapById(mapId);
            if (map == null){
                logger.warn("地图不存在");
            }
            String name = map.getName();
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("地图不存在");
        }
    }

    @Override
    public Map getMapByName(String mapName) {
        Map map = name2Map.get(mapName);
        if (map == null){
            logger.warn("对应名称的地图不存在");
        }
        return map;
    }
}
