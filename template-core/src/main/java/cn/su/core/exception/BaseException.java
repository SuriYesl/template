package cn.su.core.exception;

/**
 * @Author: su rui
 * @Date: 2021/1/19 11:52
 * @Description: 基础异常类
 */
public class BaseException extends RuntimeException{
    private static final long serialVersionUID = -943731652533310119L;
    public BaseException(){
        super();
    }
    public BaseException(Throwable cause){
        super(cause);
    }
}
