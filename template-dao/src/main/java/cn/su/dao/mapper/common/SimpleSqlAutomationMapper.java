package cn.su.dao.mapper.common;

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
    void update(Map<String, Object> param);
}
