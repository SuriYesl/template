package cn.su.dao.link;

import java.util.List;
import java.util.Map;

/**
 * @Author: su rui
 * @Date: 2021/3/16 15:23
 * @Description: 数据库操作类
 */
public interface DatabaseOperationInterface {
    SelectSqlInterface select();
    SelectSqlInterface select(String fields);
    SelectSqlInterface select(List<String> fields);
    SelectSqlInterface select(Map<String, String> fieldMap);
    InsertSqlInterface insert();
    UpdateSqlInterface update(String tableName);
    DeleteSqlInterface delete();
}
