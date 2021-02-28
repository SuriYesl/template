package cn.su.service.login;


import cn.su.dao.model.login.LoginResultVo;
import cn.su.dao.model.login.LoginVo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: su rui
 * @Date: 2021/2/7 15:09
 * @Description: 登录
 */
public interface LoginService {
    Map<String, LoginService> loginServiceMap = new HashMap<>();
    LoginResultVo login(LoginVo loginVo);
}
