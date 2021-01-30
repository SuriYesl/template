package cn.su.core.constants;

/**
 * @AUTHOR: sr
 * @DATE: Create In 22:57 2021/1/19 0019
 * @DESCRIPTION: 响应代码枚举
 */
public enum ResponseCodeEnum {
    SUCCESS("0000", "success"),
    BUSINESS_EXCEPTION("5000", "error");

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
