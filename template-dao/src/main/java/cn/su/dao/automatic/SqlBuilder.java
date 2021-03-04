package cn.su.dao.automatic;

import cn.su.core.constants.MathConstants;
import cn.su.core.constants.SqlConstants;
import cn.su.core.exception.BusinessException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 13:37 2021/2/13 0013
 * @DESCRIPTION: sql语句构建类
 */
public class SqlBuilder {
    public String tableName;
    public String sql;
    public StringBuilder queryingFieldBuilder;
    public StringBuilder insertFieldBuilder;
    public StringBuilder updateFieldBuilder;
    public StringBuilder whereBuilder;
    public StringBuilder orderBuilder;
    public StringBuilder groupBuilder;
    public StringBuilder limitBuilder;
    public Map<String, Object> filedAndValueMap;
    public Map<String, Integer> filedCountMap;

    private void defaultInit() {
        tableName = "";
        sql = "";
        queryingFieldBuilder = new StringBuilder();
        insertFieldBuilder = new StringBuilder();
        updateFieldBuilder = new StringBuilder();
        whereBuilder = new StringBuilder();
        orderBuilder = new StringBuilder();
        groupBuilder = new StringBuilder();
        limitBuilder = new StringBuilder();
        filedAndValueMap = new LinkedHashMap<>();
        filedCountMap = new LinkedHashMap<>();
    }

    public SqlBuilder() {
        defaultInit();
    }

    public SqlBuilder(String tableName) {
        defaultInit();
        this.tableName = tableName;
    }

    public boolean containField(String field) {
        if (filedCountMap.size() != filedAndValueMap.size()) throw new RuntimeException("字段数量不一致，方法内部存在错误");
        return filedAndValueMap.containsKey(field) && filedCountMap.containsKey(field);
    }

    public Integer fieldCount(String field) {
        if (filedCountMap.size() != filedAndValueMap.size()) throw new RuntimeException("字段数量不一致，方法内部存在错误");
        if (!filedAndValueMap.containsKey(field) && !filedCountMap.containsKey(field)) {
            return MathConstants.ZERO;
        } else {
            return filedCountMap.get(field);
        }
    }

    public boolean endWithAndOr() {
        return queryingFieldBuilder.toString().trim().endsWith(SqlConstants.AND)
                || queryingFieldBuilder.toString().trim().endsWith(SqlConstants.OR);
    }

    public String valueField(String field, Object value) {
        Integer count = fieldCount(field) + 1;
        String fieldValue = count > 1 ? field + count : field;
        filedCountMap.put(field, count);
        filedAndValueMap.put(fieldValue, value);
        return fieldValue;
    }
}
