package cn.su.dao.automatic;

import cn.su.core.constants.MathConstants;
import cn.su.core.constants.SqlConstants;
import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @AUTHOR: sr
 * @DATE: Create In 13:37 2021/2/13 0013
 * @DESCRIPTION: sql语句构建类
 */
public class SqlBuilder {
    public String tableName;
    public String sql;
    public Integer type;
    public StringBuilder sqlBuilder;
    public StringBuilder queryingFieldBuilder;
    public StringBuilder insertFieldBuilder;
    public StringBuilder updateFieldBuilder;
    public StringBuilder deleteFieldBuilder;
    public StringBuilder whereBuilder;
    public StringBuilder orderBuilder;
    public StringBuilder groupBuilder;
    public StringBuilder limitBuilder;
    public Map<String, Object> filedAndValueMap;
    public Map<String, Integer> filedCountMap;

    private void defaultInit() {
        tableName = "";
        sql = "";
        type = 0;
        sqlBuilder = new StringBuilder();
        queryingFieldBuilder = new StringBuilder();
        insertFieldBuilder = new StringBuilder();
        updateFieldBuilder = new StringBuilder();
        deleteFieldBuilder = new StringBuilder();
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

    private String simpleHandle(StringBuilder stringBuilder) {
        if (stringBuilder.toString().trim().endsWith(",")) {
            stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.length());
        }
        return stringBuilder.toString();
    }

    private String whereCondition() {
        if (NormHandleUtil.isEmpty(whereBuilder.toString())) return "";
        String withoutWhere = whereBuilder.toString().replace(SqlConstants.WHERE, "").trim();
        if (withoutWhere.startsWith(SqlConstants.AND)) withoutWhere = withoutWhere.substring(3).trim();
        if (withoutWhere.startsWith(SqlConstants.OR)) withoutWhere = withoutWhere.substring(2).trim();
        if (withoutWhere.endsWith(SqlConstants.COMMA)) withoutWhere = withoutWhere.substring(0, withoutWhere.lastIndexOf(",")).trim();
        return SqlConstants.WHERE + SqlConstants.SPACE + withoutWhere + SqlConstants.SPACE;
    }

    private String groupByCondition() {
        if (NormHandleUtil.isEmpty(groupBuilder.toString())) return "";
        return groupBuilder.toString() + SqlConstants.SPACE;
    }

    private String limitCondition() {
        if (NormHandleUtil.isEmpty(limitBuilder.toString())) return "";
        return limitBuilder.toString() + SqlConstants.SPACE;
    }

    private String orderCondition() {
        if (NormHandleUtil.isEmpty(orderBuilder.toString())) return "";
        return orderBuilder.toString() + SqlConstants.SPACE;
    }

    public String buildSql(StringBuilder stringBuilder) {
        switch (type) {
            case 0 :
                sqlBuilder.append(SqlConstants.SELECT).append(SqlConstants.SPACE);
                break;
            case 1 :
                sqlBuilder.append(SqlConstants.INSERT).append(SqlConstants.SPACE);
                break;
            case 2 :
                sqlBuilder.append(SqlConstants.UPDATE).append(SqlConstants.SPACE);
                break;
            case 3 :
                sqlBuilder.append(SqlConstants.DELETE).append(SqlConstants.SPACE);
                break;
        }

        sqlBuilder.append(simpleHandle(stringBuilder));
        sqlBuilder.append(SqlConstants.SPACE).append(SqlConstants.FROM).append(SqlConstants.SPACE).append(tableName).append(SqlConstants.SPACE);
        sqlBuilder.append(whereCondition());
        sqlBuilder.append(groupByCondition());
        sqlBuilder.append(limitCondition());
        sqlBuilder.append(orderCondition());
        sql = sqlBuilder.toString();
        System.out.println(sql);
        return sql;
    }

    public Map<String, String> getFieldMap(Class<?> clazz, boolean humpToLine) {
        List<String> fieldStrings = SqlBuildHelper.getClassFields(clazz);
        if (NormHandleUtil.isEmpty(fieldStrings)) {
            throw new BusinessException("value for selecting is empty");
        }
        return fieldStrings.parallelStream()
                .collect(Collectors.toMap(fieldString -> fieldString,
                        fieldString -> humpToLine ? StringUtil.humpToLine(fieldString) : fieldString));
    }
}
