package cn.su.dao.link;

/**
 * @Author: su rui
 * @Date: 2021/3/12 14:35
 * @Description: 删除接口
 */
public interface DeleteSqlInterface extends SqlInterface {
    DeleteSqlInterface from(String tableName);
    DeleteSqlInterface where();
    DeleteSqlInterface where(String condition);
    DeleteSqlInterface eq(String field, Object value);
    DeleteSqlInterface neq(String field, Object value);
    DeleteSqlInterface lt(String field, Object value);
    DeleteSqlInterface gt(String field, Object value);
    DeleteSqlInterface ltAndEqual(String field, Object value);
    DeleteSqlInterface gtAndEqual(String field, Object value);
    DeleteSqlInterface and();
    DeleteSqlInterface or();
    DeleteSqlInterface leftParentheses();
    DeleteSqlInterface rightParentheses();
    void doDelete();
}
