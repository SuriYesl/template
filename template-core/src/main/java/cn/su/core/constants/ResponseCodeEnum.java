package cn.su.core.constants;

/**
 * @AUTHOR: sr
 * @DATE: Create In 22:57 2021/1/19 0019
 * @DESCRIPTION: 响应代码枚举
 */
public enum ResponseCodeEnum {
    SUCCESS("0000", "success"),
    BUSINESS_EXCEPTION("5000", "error"),
    LOGIN_FAIL("5000", "login fail"),
    LOGIN_FAIL_VERIFY("5000", "验证不通过"),
    LOGIN_FAIL_NO_ACCOUNT("5000", "不存在该账号，请确认账号密码是否正确"),
    LOGIN_FAIL_INVALID("5000", "账号异常，请确认账号状态是否正常"),
    SERVER_EXCEPTION("5001", "服务器繁忙，请稍后重试");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
