package cn.su.service.login.impl;

import cn.su.core.exception.BusinessException;
import cn.su.dao.mapper.login.LoginMapper;
import cn.su.dao.model.login.AccountVo;
import cn.su.dao.model.login.LoginResultVo;
import cn.su.dao.model.login.LoginVo;
import cn.su.service.login.LoginService;

import java.util.Optional;

/**
 * @Author: su rui
 * @Date: 2021/1/19 10:30
 * @Description: 登录模板
 */
public abstract class AbstractLoginService implements LoginService {
    private LoginMapper loginMapper;

    public AbstractLoginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    private LoginResultVo judgeAccountStatus(AccountVo accountVo) {
        return null;
    }

    @Override
    public LoginResultVo login(LoginVo loginVo) {
        Optional<AccountVo> accountVo = Optional.ofNullable(loginMapper.getAccountMessage());
        accountVo.orElseThrow(() -> new BusinessException());
        return null;
    }
}
