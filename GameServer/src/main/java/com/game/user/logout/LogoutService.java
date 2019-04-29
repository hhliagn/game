package com.game.user.logout;

import com.game.SpringContext;
import com.game.user.account.model.Account;
import com.game.user.account.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class LogoutService implements ILogoutService{

    private static Logger logger = LoggerFactory.getLogger("ON-OFF");

    @Override
    public void logout(String accountId) {
        try {
            IAccountService accountService = SpringContext.getAccountService();
            Account account = accountService.getAccount(accountId);
            if (account == null){
                logger.info("登出账户不存在");
                throw new RuntimeException("登出账户不存在");
            }
            account.setLastLogout(new Date());
            accountService.saveAccount(account);
            logger.info("登出成功！");
            throw new RuntimeException("登出成功！");
        } catch (Exception e) {
            logger.info("登出失败！");
            throw new RuntimeException("登出失败！");
        }
    }
}
