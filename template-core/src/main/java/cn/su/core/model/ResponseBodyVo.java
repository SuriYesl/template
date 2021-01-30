package cn.su.core.model;

import java.io.Serializable;

/**
 * @AUTHOR: sr
 * @DATE: Create In 22:39 2021/1/19 0019
 * @DESCRIPTION: 响应体
 */
public class ResponseBodyVo<T> implements Serializable {
    private String code;
    private String message;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
