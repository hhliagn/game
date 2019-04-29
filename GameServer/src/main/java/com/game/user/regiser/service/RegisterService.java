package com.game.user.regiser.service;

import com.game.SpringContext;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Component;

@Component
public class RegisterService implements IRegisterService{

    @Override
    public void register(String accountId, String nickName) {
        SpringContext.getAccountService().createAccount(accountId,nickName);
    }
}
