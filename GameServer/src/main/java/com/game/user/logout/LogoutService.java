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

    private static Logger logger = LoggerFactory.getLogger("logout");

    @Override
    public void logout() {
        try {
            Account account = SpringContext.getGlobalService().getCurLoginAccount();
            if (account == null){
                logger.warn("登出账户不存在");
            }
            account.setNowLogout(new Date());
            SpringContext.getAccountService().saveAccount(account);
            SpringContext.getGlobalService().setCurLoginAccount(null);
        } catch (Exception e) {
            logger.warn("登出失败！");
            throw new RuntimeException("登出失败！");
        }
    }
}
