package com.game.user.login.service;

import com.game.SpringContext;
import com.game.user.account.model.Account;
import com.game.user.account.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoginService implements ILoginService{

    private static Logger logger = LoggerFactory.getLogger("login");

    @Override
    public void login(String accountId, String password) {
        logger.info("用户登录");
        IAccountService accountService = SpringContext.getAccountService();
        Account account = accountService.getLoginAccount(accountId, password);
        if (account == null){
            logger.warn("账号不存在，请先创建");
            throw new RuntimeException("账号不存在，请先创建");
        }
        Date lastLogin = account.getNowLogin();
        Date lastLogout = account.getNowLogout();
        account.setLastLogin(lastLogin);
        account.setLastLogout(lastLogout);
        account.setNowLogin(new Date());
        accountService.saveAccount(account);
        SpringContext.getGlobalService().setCurLoginAccount(account);
    }
}