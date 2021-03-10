package cn.su.dao.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 13:37 2021/2/13 0013
 * @DESCRIPTION: sql语句构建类
 */
public class SqlBuilder {
    public String primaryTable;
    public Map<String, String> tableConnectionTypeMaps;
    public Map<String, String> tableOnConditionMaps;
    public StringBuilder searchFields;
    public StringBuilder insertFields;
    public StringBuilder updateFields;
    public StringBuilder whereCondition;
    public StringBuilder orderCondition;
    public StringBuilder limitCondition;
    public StringBuilder groupCondition;

    private void defaultInit() {
        primaryTable = "";
        tableConnectionTypeMaps = new HashMap<>();
        tableOnConditionMaps = new HashMap<>();
        searchFields = new StringBuilder();
        insertFields = new StringBuilder();
        updateFields = new StringBuilder();
        whereCondition = new StringBuilder();
        orderCondition = new StringBuilder();
        limitCondition = new StringBuilder();
        groupCondition = new StringBuilder();
    }

    public SqlBuilder() {
        defaultInit();
    }

    public SqlBuilder(String tableName) {
        defaultInit();
        primaryTable = tableName;
    }
}
