package com.game.scene.entity;

import com.game.SpringContext;
import com.game.common.util.JsonUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.List;

@Entity(name = "map")
@org.hibernate.annotations.Table(appliesTo = "map", comment = "地图信息")
public class MapEnt implements Serializable {

    @Id
    @Column(columnDefinition = "bigint default 0 comment '地图id'", nullable = false)
    private int mapId;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE comment '地图名称'", nullable = false)
    private String mapName;

    @Lob
    @Column(columnDefinition = "blob comment '地图中存在的Npc Id数据'")
    private byte[] existNpcidsData;

    @Lob
    @Column(columnDefinition = "blob comment '地图中存在的怪物 Id数据'")
    private byte[] existMonsterIdsData;

    @Lob
    @Column(columnDefinition = "blob comment '附近的地图数据'")
    private byte[] mapNearByIdsData;

    private transient List<Long> existNpcIds;

    private transient List<Long> existMonsterIds;

    private transient List<Integer> mapNearByIds;

    public static MapEnt valueOf(int mapId, String mapName,
                                 List<Long> existMonsterIds,
                                 List<Long> existNpcIds,
                                 List<Integer> mapNearByIds){
        MapEnt mapEnt = new MapEnt();
        mapEnt.setMapId(mapId);
        mapEnt.setMapName(mapName);
        mapEnt.setExistNpcIds(existNpcIds);
        mapEnt.setExistMonsterIds(existMonsterIds);
        mapEnt.setMapNearByIds(mapNearByIds);
        SpringContext.getMapService().saveMapEnt(mapEnt);
        return mapEnt;
    }

    public void doSerialize(){
        this.existNpcidsData = JsonUtils.toNoCompressBytes(existNpcIds);
        this.existMonsterIdsData = JsonUtils.toNoCompressBytes(existMonsterIds);
        this.mapNearByIdsData = JsonUtils.toNoCompressBytes(mapNearByIds);
    }

    public void doDeserialize(){
        this.existNpcIds = JsonUtils.bytes2Object(existNpcidsData, List.class);
        this.existMonsterIds = JsonUtils.bytes2Object(existMonsterIdsData, List.class);
        this.mapNearByIds = JsonUtils.bytes2Object(mapNearByIdsData, List.class);
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public List<Long> getExistNpcIds() {
        return existNpcIds;
    }

    public void setExistNpcIds(List<Long> existNpcIds) {
        this.existNpcIds = existNpcIds;
    }

    public List<Long> getExistMonsterIds() {
        return existMonsterIds;
    }

    public void setExistMonsterIds(List<Long> existMonsterIds) {
        this.existMonsterIds = existMonsterIds;
    }

    public byte[] getExistNpcidsData() {
        return existNpcidsData;
    }

    public void setExistNpcidsData(byte[] existNpcidsData) {
        this.existNpcidsData = existNpcidsData;
    }

    public byte[] getExistMonsterIdsData() {
        return existMonsterIdsData;
    }

    public void setExistMonsterIdsData(byte[] existMonsterIdsData) {
        this.existMonsterIdsData = existMonsterIdsData;
    }

    public List<Integer> getMapNearByIds() {
        return mapNearByIds;
    }

    public void setMapNearByIds(List<Integer> mapNearByIds) {
        this.mapNearByIds = mapNearByIds;
    }

    public byte[] getMapNearByIdsData() {
        return mapNearByIdsData;
    }

    public void setMapNearByIdsData(byte[] mapNearByIdsData) {
        this.mapNearByIdsData = mapNearByIdsData;
    }
}
