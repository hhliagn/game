package com.game.map.service;

import com.game.SpringContext;
import com.game.map.dao.MapEntDao;
import com.game.map.entity.MapEnt;
import com.game.map.model.Map;
import com.game.pojo.model.Entity;
import com.game.role.player.model.Player;
import com.game.user.mapInfo.model.MapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

@Component
public class MapService implements IMapService {

    @Autowired
    private MapEntDao mapEntDao;

    private HashMap<Integer, Map> id2Map = new HashMap<Integer, Map>();

    public void initMapData() {
        List<MapEnt> mapEntList = mapEntDao.findAll();
        for (MapEnt mapEnt : mapEntList) {
            Map map = Map.valueOf(mapEnt);
            id2Map.put(map.getId(),map);
        }
    }

    public Map getMapById(int id){
        return id2Map.get(id);
    }

    public void save(Map map){
        mapEntDao.save(map);
    }

    public List<Entity> getAllEntity(int mapId) {
        Map map = getMapById(mapId);
        map.getEntityList();;
        return null;

    }

    public boolean isPlayerCurMap(Player player, int mapId) {
        MapInfo mapInfo = SpringContext.getMapInfoService().getMapInfo(player.getAccountId());
        if (mapInfo.getCurMapId() == mapId){
            return true;
        }else {
            return false;
        }
    }
}
