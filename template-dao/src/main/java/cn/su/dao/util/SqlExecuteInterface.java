package cn.su.dao.util;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 10:18 2021/2/15 0015
 * @DESCRIPTION: 执行接口
 */
public interface SqlExecuteInterface<T> {
    List<Map<String, Object>> search(@Param("sqlValue") String sqlValue);
    void insert(T data);
    void insertBatch(List<T> dataList);
    void update(T data);
    void delete();
}
