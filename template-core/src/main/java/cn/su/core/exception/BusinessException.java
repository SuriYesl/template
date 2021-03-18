package cn.su.core.exception;

import cn.su.core.constants.ResponseCodeEnum;

/**
 * @Author: su rui
 * @Date: 2021/1/19 11:55
 * @Description: 业务逻辑异常
 */
public class BusinessException extends BaseException {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum) {
        if (responseCodeEnum != null) {
            this.code = responseCodeEnum.getCode();
            this.message = responseCodeEnum.getMessage();
        }
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }
}
