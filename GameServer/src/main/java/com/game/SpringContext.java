package com.game;

import com.game.core.id.service.IdentifyService;
import com.game.map.service.IMapService;
import com.game.pojo.service.IEntityService;
import com.game.publicsystem.common.service.GlobalService;
import com.game.role.player.service.IPlayerService;
import com.game.user.account.service.IAccountService;
import com.game.user.login.service.ILoginService;
import com.game.user.logout.ILogoutService;
import com.game.user.mapInfo.service.IMapInfoService;
import com.game.user.regiser.service.IRegisterService;
import com.game.world.service.IWorldService;
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
    private IdentifyService identifyService;

    @Autowired
    public IMapInfoService mapInfoService;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private GlobalService globalService;

    @Autowired
    private ILogoutService logoutService;

    @Autowired
    private IWorldService worldService;

    public static IWorldService getWorldService(){
        return instance.worldService;
    }

    public static ILogoutService getLogoutService(){
        return instance.logoutService;
    }

    public static GlobalService getGlobalService(){
        return instance.globalService;
    }

    public static IRegisterService getRegisterService(){
        return instance.registerService;
    }

    public static ILoginService getLoginService(){
        return instance.loginService;
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


