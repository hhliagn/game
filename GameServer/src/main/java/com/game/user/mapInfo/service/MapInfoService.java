package com.game.user.mapInfo.service;

import com.game.SpringContext;
import com.game.map.model.Map;
import com.game.user.mapInfo.dao.MapInfoDao;
import com.game.user.mapInfo.entity.MapInfoEnt;
import com.game.user.mapInfo.model.MapInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleUnresolved;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MapInfoService implements IMapInfoService {

    private static final Logger logger = LoggerFactory.getLogger("mapInfo");

    @Autowired
    private MapInfoDao mapInfoDao;

    private ConcurrentHashMap<String, MapInfo> accountId2MapInfo = new ConcurrentHashMap<>();

    public MapInfo getMapInfo(String accountId){
        try {
            MapInfo mapInfo = accountId2MapInfo.get(accountId);
            if (mapInfo != null){
                return mapInfo;
            }
            MapInfoEnt mapInfoEnt = mapInfoDao.findOne(accountId);
            mapInfo = MapInfo.valueOf(mapInfoEnt);
            addMapInfo(accountId, mapInfo);
            return mapInfo;
        } catch (Exception e) {
            logger.warn("获取用户地图信息出错");
            e.printStackTrace();
            throw new RuntimeException("获取用户地图信息出错");
        }
    }

    private void addMapInfo(String accountId, MapInfo mapInfo) {
        accountId2MapInfo.put(accountId, mapInfo);
    }

    public void saveMapInfo(MapInfo mapInfo){
        try {
            mapInfoDao.save(mapInfo);
            accountId2MapInfo.put(mapInfo.getAccountId(),mapInfo);
        } catch (Exception e) {
            logger.warn("保存用户地图信息出错");
            e.printStackTrace();
            throw new RuntimeException("保存用户地图信息出错");
        }
    }

    public void createMapInfo(String accountId){
        MapInfo mapInfo = new MapInfo();
        mapInfo.setAccountId(accountId);
        mapInfo.setCurMapId(1);
        mapInfo.setLastMapId(1);
        saveMapInfo(mapInfo);
        logger.info("新增新用户地图数据");
        addMapInfo(accountId, mapInfo);
    }

    public String getCurMap(String accountId){
        try{
            IMapInfoService mapInfoService = SpringContext.getMapInfoService();
            MapInfo mapInfo = mapInfoService.getMapInfo(accountId);
            if (mapInfo == null) {
                logger.warn("账户对应的地图信息不存在");
            }

            int curMapId = mapInfo.getCurMapId();
            Map curMap = SpringContext.getMapService().getMapById(curMapId);
            if (curMap == null){
                logger.warn("地图不存在");
            }
            String name = curMap.getName();
            return name;
        }catch (Exception e){
            logger.warn("获取账号当前地图出错");
            e.printStackTrace();
            throw new RuntimeException("获取账号当前地图出错");
        }
    }

    public String getLastMap(String accountId){
        try {
            MapInfo mapInfo = SpringContext.getMapInfoService().getMapInfo(accountId);
            int lastMapId = mapInfo.getLastMapId();
            Map map = SpringContext.getMapService().getMapById(lastMapId);
            String lastMap = map.getName();
            return lastMap;
        } catch (Exception e) {
            logger.warn("获取账户上一个地图出错");
            e.printStackTrace();
            throw new RuntimeException("获取账户上一个地图出错");
        }
    }
}
