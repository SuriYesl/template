package cn.su.dao.util;

import java.util.List;

/**
 * @AUTHOR: sr
 * @DATE: Create In 22:42 2021/1/27 0027
 * @DESCRIPTION: sql接口
 */
public interface SqlInterface {
    SqlInterface find();
    SqlInterface where();
    SqlInterface or();
    SqlInterface and();
    SqlInterface from();
    SqlInterface select();
    SqlInterface underLine();
    SqlInterface comma();
    SqlInterface floatPoint();
    SqlInterface space();
    SqlInterface semicolon();
    SqlInterface asterisk();
    SqlInterface orderBy();
    SqlInterface groupBy();
    SqlInterface limit();
    SqlInterface leftJoin();
    SqlInterface on();
    <T extends Object> T forOneResult();
    <T extends Object> List<T> forResultList();
    Integer forCount();
}
