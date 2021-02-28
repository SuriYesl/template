package cn.su.service.login.impl;

import cn.su.dao.entity.login.Account;
import cn.su.dao.model.login.LoginVo;
import cn.su.service.login.AbstractLoginTemplate;
import cn.su.service.login.LoginService;
import org.springframework.stereotype.Service;

/**
 * @Author: su rui
 * @Date: 2021/2/7 15:09
 * @Description: 登录
 */
@Service
public class LoginServiceImpl extends AbstractLoginTemplate {
    public LoginServiceImpl() {
        LoginService.loginServiceMap.putIfAbsent("account", this);
    }
    @Override
    public Account queryForAccount(LoginVo loginVo) {
//        String encodePassword = EncryptionUtil.md5(loginVo.getAccountPassword(),
//                loginVo.getAccountPassword() + IRedisConstants.ACCOUNT_PASSWORD_SALT);
//        return new Account().getSqlHelper(Account.class).find().where()
//                .eq("account_name", loginVo.getAccountName()).and()
//                .eq("account_password", loginVo.getAccountPassword()).forOneResult();
        return null;
    }
}
