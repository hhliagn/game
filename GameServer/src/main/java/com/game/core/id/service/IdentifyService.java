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

    private static Logger logger = LoggerFactory.getLogger("id");

    public enum IdentifyType {
        PLAYER(),
        SCENE(),
        ;
    }

    @Autowired
    private IdentifyDao identifyDao;

    public long getNextIdentify(IdentifyType type) {
        try {
            IdentifyEnt identifyEnt = identifyDao.findOne(type.name());
            if (identifyEnt == null){
                identifyEnt = new IdentifyEnt();
                identifyEnt.setId(type.name());
            }
            Long old = identifyEnt.getValue();
            Long result = identifyEnt.getNextIdentify();
            Long now = identifyEnt.getValue();
            if (old != now) {
                identifyDao.save(identifyEnt);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取id异常");
        }
    }
}
