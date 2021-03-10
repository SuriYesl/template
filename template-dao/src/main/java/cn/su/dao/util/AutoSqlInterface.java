package cn.su.dao.util;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 11:53 2021/2/14 0014
 * @DESCRIPTION: 自动化sql拼写接口
 */
public interface AutoSqlInterface<T> {
    AutoSqlInterface select();
    AutoSqlInterface defaultSelect();
    AutoSqlInterface automaticSelect();
    AutoSqlInterface automaticSelect(String fields);
    AutoSqlInterface automaticSelect(List<String> fields);
    AutoSqlInterface automaticSelect(Map<String, String> fields);
    AutoSqlInterface from();
    AutoSqlInterface where();
    AutoSqlInterface where(String condition);
    AutoSqlInterface eq(String field, String value);
    AutoSqlInterface neq(String field, String value);
    AutoSqlInterface lt(String field, String value);
    AutoSqlInterface gt(String field, String value);
    AutoSqlInterface ltAndEqual(String field, String value);
    AutoSqlInterface gtAndEqual(String field, String value);
    AutoSqlInterface and();
    AutoSqlInterface or();
    AutoSqlInterface orderBy(String... fields);
    AutoSqlInterface groupBy(String... fields);
    AutoSqlInterface limit(Integer startRow, Integer size);
    AutoSqlInterface leftJoin();
    AutoSqlInterface on();
    AutoSqlInterface underLine();
    AutoSqlInterface comma();
    AutoSqlInterface floatPoint();
    AutoSqlInterface leftParentheses();
    AutoSqlInterface rightParentheses();
    T selectObject();
    List<T> selectList();
    T insertObject(T data);
    T insertBatch(List<T> dataList);
    T updateObject(T data);
    void deleteObject();
}
