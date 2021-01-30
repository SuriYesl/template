package cn.su.dao.mapper.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:00 2021/1/27 0027
 * @DESCRIPTION: 通用sql
 */
public interface SqlMapper {
    List<Map<String, Object>> search(@Param("sqlString") String sqlString);
}
