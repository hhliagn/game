package com.game.publicsystem.common.service;

import com.game.SpringContext;
import com.game.map.model.Map;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import com.game.user.mapInfo.model.MapInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.java2d.pipe.hw.AccelDeviceEventListener;

@Component
public class GlobalService {

    private static Logger logger = LoggerFactory.getLogger("ON-OFF");

    private Account curLoginAccount = null;

    public void onStart(){

        logger.info("启动游戏");

        logger.info("初始化账号数据。。。");
        SpringContext.getAccountService().loadAllAccountInfo();

        logger.info("初始化地图数据。。。");
        SpringContext.getMapService().initMapData();
    }

    public void setCurLoginAccount(Account account){
        this.curLoginAccount = account;
    }

    public Account getCurLoginAccount(){
        return curLoginAccount;
    }

    public String printAccount() {
        Account account = SpringContext.getGlobalService().getCurLoginAccount();
        if (account == null){
            throw new RuntimeException("当前未登录");
        }
        String accountId = account.getAccountId();
        StringBuilder sb = new StringBuilder();
        sb.append("账户ID：" + account.getAccountId() + "\n");
        sb.append("账户昵称：" + account.getNickName() + "\n");
        String curMap = SpringContext.getMapInfoService().getCurMap(accountId);
        sb.append("当前地图：" + curMap + "\n");
        String lastMap = SpringContext.getMapInfoService().getLastMap(accountId);
        sb.append("上个地图：" + lastMap + "\n");
        sb.append("上次登录：" + account.getLastLogin() + "\n");
        sb.append("上次登出：" + account.getLastLogout());
        return sb.toString();
    }
}
