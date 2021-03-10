package cn.su.dao.model.login;


import cn.su.dao.model.common.BaseVo;

/**
 * @Author: su rui
 * @Date: 2021/2/7 15:02
 * @Description: 登录
 */
public class LoginVo extends BaseVo {
    private static final long serialVersionUID = -8703025493972772505L;
    private String accountName;
    private String accountPassword;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}
