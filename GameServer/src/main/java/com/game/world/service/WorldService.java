package com.game.world.service;

import com.game.SpringContext;
import com.game.world.service.map.entity.MapEnt;
import com.game.world.service.pojo.entity.EntityEnt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorldService implements IWorldService {

    private static final Logger logger = LoggerFactory.getLogger("world");

    @Override
    public void initMapData() {
        SpringContext.getMapService().initMapData();
    }

    @Override
    public void changeMap(String mapName) {
        SpringContext.getMapService().changeMap(mapName);
    }

    @Override
    public String printMap(String mapName) {
//        MapEnt curMap = SpringContext.getWorldService().getCurMap();
//        String mapName = curMap.getName();
        MapEnt mapEnt = SpringContext.getMapService().getMapEntByName(mapName);
        List<EntityEnt> entities = SpringContext.getMapService().getEntities(mapEnt.getId());
        if (entities == null || entities.size() == 0){
            throw new RuntimeException("当前地图不存在实体");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("当前地图的实体有：");
        for (EntityEnt entityEnt : entities) {
            String name = entityEnt.getName();
            sb.append(name + " ");
        }
        sb.append("\n");

        sb.append("附近的地图有：");
        int[] mapIdNearbys = mapEnt.doParseMapIdNearby();
        if (mapIdNearbys.length == 0){
            throw new RuntimeException("当前地图没有邻近的地图");
        }
        for (int mapId : mapIdNearbys) {
            MapEnt map = SpringContext.getMapService().getMapEnt(mapId);
            sb.append(map.getId() + "-" + map.getName() + " ");
        }
        return sb.toString();
    }

//    @Override
//    public MapEnt getCurMap() {
//        Account curLoginAccount = SpringContext.getGlobalService().getCurLoginAccount();
//        if (curLoginAccount == null){
//            throw new RuntimeException("当前未登录");
//        }
//        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(curLoginAccount.getAccountId());
//        MapEnt mapEnt = SpringContext.getMapService().getMapEnt(mapInfoEnt.getCurMapId());
//        return mapEnt;
//    }

//    public String printAccount() {
//        Account account = SpringContext.getGlobalService().getCurLoginAccount();
//        if (account == null){
//            throw new RuntimeException("当前未登录");
//        }
//        String accountId = account.getAccountId();
//        StringBuilder sb = new StringBuilder();
//        sb.append("账户ID：" + account.getAccountId() + "\n");
//        sb.append("账户昵称：" + account.getNickName() + "\n");
//        String curMap = getCurMap().getName();
//        sb.append("当前地图：" + curMap + "\n");
//        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(accountId);
//        String lastMap = SpringContext.getMapService().getMapEnt(mapInfoEnt.getLastMapId()).getName();
//        sb.append("上个地图：" + lastMap + "\n");
//        return sb.toString();
//    }
}
