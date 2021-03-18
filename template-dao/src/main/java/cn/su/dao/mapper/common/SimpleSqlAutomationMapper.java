package cn.su.dao.mapper.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:35 2021/3/4 0004
 * @DESCRIPTION: 简易sql自动化mapper
 */
public interface SimpleSqlAutomationMapper {
    List<Map<String, Object>> query(Map<String, Object> param);
    void insert(Map<String, Object> param);
    Integer insertBackId(Map<String, Object> param);
    void insertBatch(@Param("halfSql") String halfSql, @Param("objectField") String objectField, @Param("list") List list);
    void update(Map<String, Object> param);
    void delete(Map<String, Object> param);
}
