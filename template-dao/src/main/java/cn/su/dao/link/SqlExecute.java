package cn.su.dao.link;

import cn.su.core.util.SpringContextUtil;
import cn.su.dao.mapper.common.SimpleSqlAutomationMapper;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 10:16 2021/2/15 0015
 * @DESCRIPTION: 执行sql语句
 */
public class SqlExecute<T> implements SqlExecuteInterface<T> {
    private static final SimpleSqlAutomationMapper mapper = SpringContextUtil.getBean(SimpleSqlAutomationMapper.class);

    @Override
    public List<Map<String, Object>> query(Map<String, Object> param) {
        return mapper.query(param);
    }

    @Override
    public void insert(Map<String, Object> param) {
        mapper.insert(param);
    }

    @Override
    public Integer insertBackId(Map<String, Object> param) {
        return mapper.insertBackId(param);
    }

    @Override
    public void insertBatch(String halfSql, String objectField, List<T> dataList) {
        mapper.insertBatch(halfSql, objectField, dataList);
    }

    @Override
    public void update(Map<String, Object> param) {
        mapper.update(param);
    }

    @Override
    public void delete(Map<String, Object> param) {
        mapper.delete(param);
    }
}
