package cn.su.dao.model.login;

import cn.su.dao.model.common.BaseVo;

/**
 * @Author: su rui
 * @Date: 2021/2/7 15:05
 * @Description: 登录结果
 */
public class LoginResultVo extends BaseVo {
    private static final long serialVersionUID = 3246802561635027293L;
    private String uuid;
    private String accountName;
    private String phone;
    private String email;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
