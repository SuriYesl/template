package cn.su.dao.util;

import cn.su.core.util.SpringContextUtil;
import cn.su.dao.mapper.common.SqlCommonMapper;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 10:16 2021/2/15 0015
 * @DESCRIPTION: 执行sql语句
 */
public class SqlExecute<T> implements SqlExecuteInterface<T> {
    private static final SqlCommonMapper commonMapper = SpringContextUtil.getBean(SqlCommonMapper.class);

    @Override
    public List<Map<String, Object>> search(String sqlValue) {
        return commonMapper.search(sqlValue);
    }

    @Override
    public void insert(T data) {

    }

    @Override
    public void insertBatch(List<T> dataList) {

    }

    @Override
    public void update(T data) {

    }

    @Override
    public void delete() {

    }
}
