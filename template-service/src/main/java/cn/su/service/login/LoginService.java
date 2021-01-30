package cn.su.service.login;

import cn.su.dao.model.login.LoginResultVo;
import cn.su.dao.model.login.LoginVo;

/**
 * @Author: su rui
 * @Date: 2021/1/19 09:23
 * @Description: 登录
 */
public interface LoginService {
    LoginResultVo login(LoginVo loginVo);
}
