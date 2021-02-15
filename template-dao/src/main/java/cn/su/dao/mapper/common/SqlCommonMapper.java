package cn.su.dao.mapper.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:00 2021/1/27 0027
 * @DESCRIPTION: 通用sql
 */
public interface SqlCommonMapper {
    List<Map<String, Object>> search(@Param("sqlString") String sqlString);

    Integer superCommonInsert(Map<String, Object> param);

    Integer superCommonUpdate(Map<String, Object> param);

    void insertTargetListBySql(@Param("tableName") String tableName, @Param("insertFields") String insertFields,
                               @Param("fieldValues") String fieldValues, @Param("dataList") List<?> dataList);
}
