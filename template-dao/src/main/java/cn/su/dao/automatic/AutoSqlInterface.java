package cn.su.dao.automatic;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 11:53 2021/2/14 0014
 * @DESCRIPTION: 自动化sql拼写接口
 */
public interface AutoSqlInterface<T> {
    void fieldToQuerying(String fields);
    void fieldToQuerying(List<String> fields);
    void fieldToQuerying(Map<String, String> fieldMap);
    void condition(String conditions);
    AutoSqlInterface eq(String field, Object value);
    AutoSqlInterface neq(String field, Object value);
    AutoSqlInterface lt(String field, Object value);
    AutoSqlInterface gt(String field, Object value);
    AutoSqlInterface ltAndEqual(String field, Object value);
    AutoSqlInterface gtAndEqual(String field, Object value);
    AutoSqlInterface and();
    AutoSqlInterface or();
    AutoSqlInterface leftParentheses();
    AutoSqlInterface rightParentheses();
    AutoSqlInterface orderBy(String orderRule);
    AutoSqlInterface groupBy(String groupRule);
    AutoSqlInterface limit(Integer startRow, Integer size);
    T selectObject();
    List<T> selectList();
    T insertObject(T data);
    T insertBatch(List<T> dataList);
    T updateObject(T data);
    void deleteObject();
}
