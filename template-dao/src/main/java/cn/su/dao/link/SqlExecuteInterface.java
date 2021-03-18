package cn.su.dao.link;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 10:18 2021/2/15 0015
 * @DESCRIPTION: 执行接口
 */
public interface SqlExecuteInterface<T> {
    List<Map<String, Object>> query(Map<String, Object> param);
    void insert(Map<String, Object> param);
    Integer insertBackId(Map<String, Object> param);
    void insertBatch(String halfSql, String objectField, List<T> dataList);
    void update(Map<String, Object> param);
    void delete(Map<String, Object> param);
}
