package cn.su.dao.link;

import java.util.List;
import java.util.Map;

/**
 * @Author: su rui
 * @Date: 2021/3/17 16:45
 * @Description: 数据库操作
 */
public class DatabaseOperation implements DatabaseOperationInterface{
    @Override
    public SelectSqlInterface select() {
        return new SelectSql();
    }

    @Override
    public SelectSqlInterface select(String fields) {
        return new SelectSql(fields);
    }

    @Override
    public SelectSqlInterface select(List<String> fields) {
        return new SelectSql(fields);
    }

    @Override
    public SelectSqlInterface select(Map<String, String> fieldMap) {
        return new SelectSql(fieldMap);
    }

    @Override
    public InsertSqlInterface insert() {
        return new InsertSql<>();
    }

    @Override
    public UpdateSqlInterface update(String tableName) {
        return new UpdateSql(tableName);
    }

    @Override
    public DeleteSqlInterface delete() {
        return new DeleteSql();
    }
}
