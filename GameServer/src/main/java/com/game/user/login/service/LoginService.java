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

    private static Logger logger = LoggerFactory.getLogger("ON-OFF");

    @Override
    public void login(String accountId) {
        IAccountService accountService = SpringContext.getAccountService();
        Account account = accountService.getAccount(accountId);
        if (account == null){
            logger.info("账户不存在，请先创建");
            throw new RuntimeException("账户不存在，请先创建");
        }
        account.setLastLogin(new Date());
        accountService.saveAccount(account);
        logger.info("登录成功");
    }
}
