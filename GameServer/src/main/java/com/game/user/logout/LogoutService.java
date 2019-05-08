package com.game.user.logout;

import com.game.SpringContext;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class LogoutService implements ILogoutService{

    private static Logger logger = LoggerFactory.getLogger("logout");

    @Override
    public void logout(String accountId) {
        Date date = new Date();
        Account account = SpringContext.getAccountService().getAccount(accountId);
//        Account account = accountEnt.getAccount();
        account.setLastLogout(date);
        account.getBaseAccountInfo().setLogoutTime(date);
//        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(account.getAccountId());
//        accountEnt.setAccount(account);
        SpringContext.getAccountService().saveAccount(account);
//        SpringContext.getGlobalService().setCurLoginAccount(null);
        logger.info("用户登出");
    }
}
