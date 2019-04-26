package com.game;

import com.game.map.service.IMapService;
import com.game.pojo.service.IEntityService;
import com.game.user.mapInfo.service.IMapInfoService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class SpringContext implements ApplicationContextAware {

    @Autowired
    public static ApplicationContext applicationContext;

    @Autowired
    public IEntityService entityService;

    @Autowired
    public IMapService mapService;

    @Autowired
    public IMapInfoService mapInfoService;

    public static IMapInfoService getMapInfoService(){
        return instance.mapInfoService;
    }

    public static IMapService getMapService() {
        return instance.mapService;
    }

    public static IEntityService getEntityService() {
        return instance.entityService;
    }

    private static SpringContext instance;

    @PostConstruct
    private final void init(){
        instance = this;
    }

    public static SpringContext getInstance(){
        return instance;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext(){
        return applicationContext;
    }
}


