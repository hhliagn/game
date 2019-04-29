package com.game.core.id.service;

import com.game.core.id.dao.IdentifyDao;
import com.game.core.id.entity.IdentifyEnt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class IdentifyService {

    private static Logger logger = LoggerFactory.getLogger("ON-OFF");

    public enum IdentifyType {
        PLAYER(),
        ENTITY(),
    }

    @Autowired
    private IdentifyDao identifyDao;

    private ConcurrentHashMap<String,IdentifyEnt> name2Identify = new ConcurrentHashMap<>();

    public long getNextIdentify(IdentifyType type) {
        try {
            IdentifyEnt identifyEnt = name2Identify.get(type.name());
            if (identifyEnt == null){
                identifyEnt = identifyDao.findOne(type.name());
                if (identifyEnt == null){
                    logger.warn("对应的id类型不存在");
                    identifyEnt = new IdentifyEnt();
                    identifyEnt.setId(type.name());
                }
                IdentifyEnt oldIdentifyEnt = name2Identify.putIfAbsent(type.name(), identifyEnt);
                if (oldIdentifyEnt != null){
                    logger.warn("map中已存在该类型id");
                    identifyEnt = oldIdentifyEnt;
                }
            }
            Long old = identifyEnt.getValue();
            Long result = identifyEnt.getNextIdentify();
            Long now = identifyEnt.getValue();
            if (old != now) {
                identifyDao.save(identifyEnt);
                logger.info("保存id数据到数据库");
            }
            return result;
        }catch (Exception e){
            logger.info("获取id异常");
            e.printStackTrace();
            throw new RuntimeException("获取id异常");
        }
    }
}
