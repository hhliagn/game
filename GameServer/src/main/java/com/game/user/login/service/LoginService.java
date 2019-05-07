package com.game.user.login.service;

import com.game.SpringContext;
import com.game.role.player.model.BasePlayerInfo;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import com.game.user.account.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class LoginService implements ILoginService{

    private static Logger logger = LoggerFactory.getLogger("login");

    @Autowired
    private IAccountService accountService;

    @Override
    public void login(String accountId, String password) {
        BaseAccountInfo baseAccountInfo = accountService.getBaseAccountInfo(accountId);
        if (baseAccountInfo == null){
            accountService.createAccount(accountId, password);
            baseAccountInfo = accountService.getBaseAccountInfo(accountId);
        }

        Account loginAccount = SpringContext.getAccountService().getLoginAccount(accountId, password);
        if (loginAccount == null){
            throw new RuntimeException("密码错误");
        }

        BasePlayerInfo recentPlayerInfo = baseAccountInfo.getRecentPlayerInfo();
        if (recentPlayerInfo == null){
            return;
        }
        long playerId = recentPlayerInfo.getPlayerId();
        SpringContext.getPlayerService().login(playerId);

        logger.info("用户登录");
//        doAfterLogin(accountId);
    }

//    private void doAfterLogin(String accountId) {
////        //设置新的登录时间
////        Date date = new Date();
////        Account account = SpringContext.getAccountService().getAccount(accountId);
//////        Account account = accountEnt.getAccount();
////        account.setLastLogin(date);
////        account.getBaseAccountInfo().setLoginTime(date);
////        SpringContext.getAccountService().saveAccount(account);
////        //将登录后的用户放入缓存
////        SpringContext.getGlobalService().setCurLoginAccount(account);
////    }

    //登录后页面需要显示的简要信息-后面可以创建新类LoginPlayerInfo
    private List<BasePlayerInfo> getLoginPlayerInfos(String accountId){
        List<BasePlayerInfo> basePlayerInfos
                = SpringContext.getPlayerService().getBasePlayerInfos(accountId);
        if (basePlayerInfos != null){
            Collections.sort(basePlayerInfos, (o1, o2) -> {
                if (o1.getId() - o2.getId() >= 0){
                    return 1;
                }else {
                    return -1;
                }
            });

            return basePlayerInfos;
        }

        return Collections.emptyList();
    }
}