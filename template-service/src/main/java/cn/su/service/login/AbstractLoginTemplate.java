package cn.su.service.login;

import cn.su.core.constants.ResponseCodeEnum;
import cn.su.core.exception.BusinessException;
import cn.su.core.util.RedisUtil;
import cn.su.dao.entity.login.Account;
import cn.su.dao.model.login.LoginInfoToRedis;
import cn.su.dao.model.login.LoginResultVo;
import cn.su.dao.model.login.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @Author: su rui
 * @Date: 2021/2/7 15:19
 * @Description: 登录模板
 */
public abstract class AbstractLoginTemplate implements LoginService {
    @Autowired
    private RedisUtil redisUtil;

    public abstract Account queryForAccount(LoginVo loginVo);
    private boolean checkAccountStatus(Account account) {
        return true;
    }

    private String createTokenString(String account) {
        String token = "";
        return token;
    }

    private void saveLoginInfoToRedis(String token, Account account) {
        LoginInfoToRedis loginInfoToRedis = new LoginInfoToRedis();
        loginInfoToRedis.setAccountName(account.getAccountName());
        loginInfoToRedis.setAccountPassword(account.getAccountPassword());
        loginInfoToRedis.setEmail(account.getEmail());
        loginInfoToRedis.setPhone(account.getPhone());
        loginInfoToRedis.setToken(token);
        loginInfoToRedis.setUuid(account.getUuid());
        //redisUtil.saveObject(IRedisConstants.LOGIN_ACCOUNT_INFO + token, loginInfoToRedis, IRedisConstants.TOKEN_EXPIRE_TIME);
    }

    private LoginResultVo loginResult(String token, Account account) {
        LoginResultVo loginResultVo = new LoginResultVo();
        loginResultVo.setToken(token);
        loginResultVo.setUuid(account.getUuid());
        loginResultVo.setAccountName(account.getAccountName());
        loginResultVo.setEmail(account.getEmail());
        loginResultVo.setPhone(account.getPhone());
        return loginResultVo;
    }

    @Override
    public LoginResultVo login(LoginVo loginVo) {
        Optional<Account> account = Optional.ofNullable(queryForAccount(loginVo));
        account.orElseThrow(() -> new BusinessException(ResponseCodeEnum.LOGIN_FAIL_NO_ACCOUNT));

        if (!checkAccountStatus(account.get())) {
            throw new BusinessException(ResponseCodeEnum.LOGIN_FAIL_INVALID);
        }
        String token = createTokenString(account.get().getAccountName());
        saveLoginInfoToRedis(token, account.get());
        return loginResult(token, account.get());
    }
}
