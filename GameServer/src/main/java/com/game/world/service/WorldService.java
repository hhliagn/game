package com.game.world.service;

import com.game.SpringContext;
import com.game.map.model.Map;
import com.game.pojo.model.Entity;
import com.game.role.player.model.Player;
import com.game.user.account.model.Account;
import com.game.user.mapInfo.model.MapInfo;
import com.game.user.mapInfo.service.IMapInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorldService implements IWorldService {

    private static final Logger logger = LoggerFactory.getLogger("world");

    public void initMapData() {
        SpringContext.getMapService().initMapData();
    }

    public void changeMap(String mapName) {
        SpringContext.getMapService().changeMap(mapName);
    }

    public String enterTheWorld(){
        try{
            Account curLoginAccount = SpringContext.getGlobalService().getCurLoginAccount();
            if (curLoginAccount == null){
                throw new RuntimeException("当前未登录");
            }
            String accountId = curLoginAccount.getAccountId();
            String curMap = SpringContext.getMapInfoService().getCurMap(accountId);
            return curMap;
        }catch (Exception e){
            logger.warn("进入世界出错");
            e.printStackTrace();
            throw new RuntimeException("进入世界出错");
        }
    }

    @Override
    public String printMap(String mapName) {
        Map map = SpringContext.getMapService().getMapByName(mapName);
        List<Entity> entityList = map.getEntityList();
        if (entityList == null || entityList.size() == 0){
            logger.warn("当前地图不存在实体");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("当前地图的实体有：");
        for (Entity entity : entityList) {
            String name = entity.getName();
            sb.append(name + " ");
        }
        sb.append("\n");

        sb.append("附近的地图有：");
        int[] mapIdNearbys = map.getMapIdNearbys();
        if (mapIdNearbys.length == 0){
            logger.warn("当前地图没有邻近的地图");
        }
        for (int mapIdNearby : mapIdNearbys) {
            Map mapNearby = SpringContext.getMapService().getMapById(mapIdNearby);
            sb.append(mapNearby.getId() + "-" + mapNearby.getName() + " ");
        }
        sb.append("\n");
        return sb.toString();
    }
}
