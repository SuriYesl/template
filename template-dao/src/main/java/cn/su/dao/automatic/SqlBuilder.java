package cn.su.dao.automatic;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 13:37 2021/2/13 0013
 * @DESCRIPTION: sql语句构建类
 */
public class SqlBuilder {
    public String tableName;
    public StringBuilder sql;
    public Map<String, Object> filedAndValueMap;
    public Map<String, Integer> filedCountMap;

    private void defaultInit() {
        tableName = "";
        sql = new StringBuilder();
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
}
