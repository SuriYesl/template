package cn.su.dao.util;

import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 13:37 2021/2/13 0013
 * @DESCRIPTION: sql语句构建类
 */
public class SqlBuilder implements SqlBuilderInterface{
    private String primaryTable;
    private Map<String, String> tableConnectionTypeMaps;
    private Map<String, String> tableOnConditionMaps;
    private StringBuilder searchFields;
    private StringBuilder insertFields;
    private StringBuilder updateFields;
    private StringBuilder whereCondition;
    private StringBuilder orderCondition;
    private StringBuilder limitCondition;
    private StringBuilder groupCondition;

    public String getPrimaryTable() {
        return primaryTable;
    }

    public Map<String, String> getTableConnectionTypeMaps() {
        return tableConnectionTypeMaps;
    }

    public Map<String, String> getTableOnConditionMaps() {
        return tableOnConditionMaps;
    }

    public StringBuilder getSearchFields() {
        return searchFields;
    }

    public StringBuilder getInsertFields() {
        return insertFields;
    }

    public StringBuilder getUpdateFields() {
        return updateFields;
    }

    public StringBuilder getWhereCondition() {
        return whereCondition;
    }

    public StringBuilder getOrderCondition() {
        return orderCondition;
    }

    public StringBuilder getLimitCondition() {
        return limitCondition;
    }

    public StringBuilder getGroupCondition() {
        return groupCondition;
    }

    @Override
    public SqlBuilderInterface buildSqlFunction() {
        return this;
    }

    @Override
    public SqlBuilderInterface buildPrimaryTableName() {
        return this;
    }

    @Override
    public SqlBuilderInterface buildWhereCondition() {
        return this;
    }

    @Override
    public SqlBuilderInterface buildConnectionType() {
        return this;
    }

    @Override
    public SqlBuilderInterface buildRelationTableName() {
        return this;
    }

    @Override
    public SqlBuilderInterface buildOnCondition() {
        return this;
    }
}
