package cn.su.dao.link;

import java.util.Map;

/**
 * @Author: su rui
 * @Date: 2021/3/12 14:34
 * @Description: 更新 接口
 */
public interface UpdateSqlInterface extends SqlInterface {
    <P> UpdateSqlInterface set(P object);
    UpdateSqlInterface set(String column, Object value);
    UpdateSqlInterface set(Map<String, Object> fieldMap);
    UpdateSqlInterface where();
    UpdateSqlInterface where(String condition);
    UpdateSqlInterface eq(String field, Object value);
    UpdateSqlInterface neq(String field, Object value);
    UpdateSqlInterface lt(String field, Object value);
    UpdateSqlInterface gt(String field, Object value);
    UpdateSqlInterface ltAndEqual(String field, Object value);
    UpdateSqlInterface gtAndEqual(String field, Object value);
    UpdateSqlInterface and();
    UpdateSqlInterface or();
    UpdateSqlInterface leftParentheses();
    UpdateSqlInterface rightParentheses();
    void doUpdate();
}
