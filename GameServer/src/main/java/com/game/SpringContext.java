package com.game;

import com.game.core.id.service.IdentifyService;
import com.game.role.player.service.IPlayerService;
import com.game.scene.service.IMapService;
import com.game.scene.service.ISceneService;
import com.game.user.account.service.IAccountService;
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
    private IdentifyService identifyService;

    @Autowired
    public IMapInfoService mapInfoService;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private GlobalService globalService;

    @Autowired
    private IMapService mapService;

    @Autowired
    private ISceneService sceneService;

    public static ISceneService getSceneService(){
        return instance.sceneService;
    }

    public static IMapService getMapService(){
        return instance.mapService;
    }

    public static GlobalService getGlobalService(){
        return instance.globalService;
    }

    public static IAccountService getAccountService(){
        return instance.accountService;
    }

    public static IPlayerService getPlayerService(){
        return instance.playerService;
    }

    public static IdentifyService getIdentifyService(){
        return instance.identifyService;
    }

    public static IMapInfoService getMapInfoService(){
        return instance.mapInfoService;
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


