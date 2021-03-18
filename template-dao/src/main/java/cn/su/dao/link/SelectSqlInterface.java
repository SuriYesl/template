package cn.su.dao.link;

import java.util.List;

/**
 * @Author: su rui
 * @Date: 2021/3/12 14:28
 * @Description: 查询接口
 */
public interface SelectSqlInterface extends SqlInterface {
    SelectSqlInterface from(String tableName);
    SelectSqlInterface where();
    SelectSqlInterface where(String condition);
    SelectSqlInterface orderBy(String orderRule);
    SelectSqlInterface groupBy(String groupRule);
    SelectSqlInterface limit(Integer size);
    SelectSqlInterface limit(Integer startRow, Integer size);
    <R> R forObject();
    <R> R forObject(Class<R> resultClass);
    <R> List<R> forList();
    <R> List<R> forList(Class<R> resultClass);
    SelectSqlInterface eq(String field, Object value);
    SelectSqlInterface neq(String field, Object value);
    SelectSqlInterface lt(String field, Object value);
    SelectSqlInterface gt(String field, Object value);
    SelectSqlInterface ltAndEqual(String field, Object value);
    SelectSqlInterface gtAndEqual(String field, Object value);
    SelectSqlInterface and();
    SelectSqlInterface or();
    SelectSqlInterface leftParentheses();
    SelectSqlInterface rightParentheses();
}
