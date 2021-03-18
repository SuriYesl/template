package cn.su.dao.link;

import cn.su.core.constants.MathConstants;
import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: su rui
 * @Date: 2021/3/12 14:32
 * @Description: 查询实现
 */
public class SelectSql implements SelectSqlInterface {
    private boolean humpToLine;
    private Class resultClass;
    private StringBuilder sqlBuilder;
    private boolean hasCondition;
    public Map<String, Object> filedAndValueMap;
    public Map<String, Integer> filedCountMap;
    private SqlExecuteInterface sqlExecuteInterface;
    private ResultHandlerInterface resultHandlerInterface;

    public void setResultClass(Class resultClass) {
        this.resultClass = resultClass;
    }

    public void setHumpToLine(boolean humpToLine) {
        this.humpToLine = humpToLine;
    }

    private void defaultInit() {
        humpToLine = true;
        sqlBuilder = new StringBuilder();
        hasCondition = false;
        filedAndValueMap = new HashMap<>();
        filedCountMap = new HashMap<>();
        sqlExecuteInterface = new SqlExecute();
        resultHandlerInterface = new ResultHandler();
        sqlBuilder.append(SqlConstants.SELECT).append(SqlConstants.SPACE);
    }

    public SelectSql() {
        defaultInit();
        sqlBuilder.append(SqlConstants.ASTERISK).append(SqlConstants.SPACE);
    }

    public SelectSql(String fields) {
        defaultInit();
        sqlBuilder.append(fields).append(SqlConstants.SPACE);
    }

    public SelectSql(List<String> fields) {
        defaultInit();
        sqlBuilder.append(StringUtil.stripNotSpaceHeadAndTail(fields.toString())).append(SqlConstants.SPACE);
    }

    private String mapToString(Map<String, String> fieldMap) {
        StringBuilder tempBuilder = new StringBuilder();
        for (Map.Entry<String, String> map : fieldMap.entrySet()) {
            tempBuilder.append(SqlConstants.FLOAT_POINT).append(map.getKey()).append(SqlConstants.FLOAT_POINT).append(SqlConstants.SPACE)
                    .append(SqlConstants.AS).append(SqlConstants.SPACE)
                    .append(SqlConstants.FLOAT_POINT).append(map.getValue()).append(SqlConstants.FLOAT_POINT).append(SqlConstants.COMMA);
        }
        tempBuilder.delete(tempBuilder.length() - 1, tempBuilder.length()).append(SqlConstants.SPACE);
        return tempBuilder.toString();
    }

    public SelectSql(Map<String, String> fieldMap) {
        defaultInit();
        sqlBuilder.append(mapToString(fieldMap));
    }

    private Integer fieldCount(String field) {
        if (filedCountMap.size() != filedAndValueMap.size()) throw new RuntimeException("字段数量不一致，方法内部存在错误");
        if (!filedAndValueMap.containsKey(field) && !filedCountMap.containsKey(field)) {
            return MathConstants.ZERO;
        } else {
            return filedCountMap.get(field);
        }
    }

    private String valueField(String field, Object value) {
        int count = fieldCount(field) + 1;
        String fieldValue = count > 1 ? field + count : field;
        filedCountMap.put(field, count);
        filedAndValueMap.put(fieldValue, value);
        return fieldValue;
    }

    private void prePieceCondition() {
        if (!sqlBuilder.toString().contains(SqlConstants.FROM))
            throw new BusinessException("5555", "sql语句不合法，在条件查询之前缺少 from tb字段");
        if (!sqlBuilder.toString().contains(SqlConstants.WHERE))
            sqlBuilder.append(SqlConstants.WHERE).append(SqlConstants.SPACE);
        if (hasCondition && !SqlBuildHelper.endWithAndOr(sqlBuilder.toString()))
            sqlBuilder.append(SqlConstants.AND).append(SqlConstants.SPACE);
    }

    private void pieceCondition(String field, Object value, String sign) {
        prePieceCondition();
        String fieldValue = valueField(field, value);
        sqlBuilder.append(SqlConstants.FLOAT_POINT).append(field).append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE).append(sign).append(SqlConstants.SPACE)
                .append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(fieldValue).append(SqlConstants.RIGHT_BRACE)
                .append(SqlConstants.SPACE);
        hasCondition = true;
    }

    @Override
    public SelectSqlInterface from(String tableName) {
        sqlBuilder.append(SqlConstants.FROM).append(SqlConstants.SPACE).append(tableName).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public SelectSqlInterface where() {
        sqlBuilder.append(SqlConstants.WHERE).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public SelectSqlInterface where(String condition) {
        sqlBuilder.append(SqlConstants.WHERE).append(SqlConstants.SPACE).append(condition).append(SqlConstants.SPACE);
        hasCondition = true;
        return this;
    }

    @Override
    public SelectSqlInterface orderBy(String orderRule) {
        sqlBuilder.append(SqlConstants.ORDER_BY).append(SqlConstants.SPACE).append(orderRule).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public SelectSqlInterface groupBy(String groupRule) {
        sqlBuilder.append(SqlConstants.GROUP_BY).append(SqlConstants.SPACE).append(groupRule).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public SelectSqlInterface limit(Integer size) {
        return limit(null, size);
    }

    @Override
    public SelectSqlInterface limit(Integer startRow, Integer size) {
        startRow = null == startRow ? 0 : startRow;
        size = null == size ? MathConstants.TEN : size;
        sqlBuilder.append(SqlConstants.LIMIT).append(SqlConstants.SPACE)
                .append(startRow).append(SqlConstants.COMMA).append(SqlConstants.SPACE).append(size);
        return this;
    }

    private void preExecuteSql() {
        if (resultClass == null) throw new RuntimeException("result class error: 没有指定查询返回值类型");
        if (sqlBuilder.toString().contains(SqlConstants.ASTERISK)) {
            List<String> fields = SqlBuildHelper.getClassFields(this.resultClass);
            if (NormHandleUtil.isEmpty(fields)) throw new RuntimeException("返回类型没有明确的成员变量");
            Map<String, String> fieldMap = fields.parallelStream().collect(
                    Collectors.toMap(field -> humpToLine ? StringUtil.humpToLine(field) : field, field -> field));
            sqlBuilder.replace(sqlBuilder.indexOf(SqlConstants.ASTERISK), sqlBuilder.indexOf(SqlConstants.ASTERISK) + 1, mapToString(fieldMap));
        }
    }

    private List<Map<String, Object>> sqlExecuteResult() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("value", sqlBuilder.toString());
        params.putAll(filedAndValueMap);
        return sqlExecuteInterface.query(params);
    }

    @Override
    public <R> R forObject() {
        preExecuteSql();
        List<Map<String, Object>> result = sqlExecuteResult();
        if (NormHandleUtil.isEmpty(result)) {
            return null;
        }
        if (result.size() > MathConstants.ONE) {
            throw new TooManyResultsException("search for one result but found" + result.size());
        }
        ResultHandlerInterface resultHandlerInterface = new ResultHandler();
        return (R) resultHandlerInterface.forObject(this.resultClass, result);
    }

    @Override
    public <R> R forObject(Class<R> resultClass) {
        this.resultClass = resultClass;
        return forObject();
    }

    @Override
    public <R> List<R> forList() {
        preExecuteSql();
        List<Map<String, Object>> result = sqlExecuteResult();
        if (NormHandleUtil.isEmpty(result)) {
            return new LinkedList<>();
        }
        return resultHandlerInterface.forList(this.resultClass, result);
    }

    @Override
    public <R> List<R> forList(Class<R> resultClass) {
        this.resultClass = resultClass;
        return forList();
    }

    @Override
    public SelectSqlInterface eq(String field, Object value) {
        pieceCondition(field, value, SqlConstants.EQUAL_SIGN);
        return this;
    }

    @Override
    public SelectSqlInterface neq(String field, Object value) {
        pieceCondition(field, value, SqlConstants.NOT_EQUAL_SIGN);
        return this;
    }

    @Override
    public SelectSqlInterface lt(String field, Object value) {
        pieceCondition(field, value, SqlConstants.LESS_THAN);
        return this;
    }

    @Override
    public SelectSqlInterface gt(String field, Object value) {
        pieceCondition(field, value, SqlConstants.GREATER_THAN);
        return this;
    }

    @Override
    public SelectSqlInterface ltAndEqual(String field, Object value) {
        pieceCondition(field, value, SqlConstants.LESS_THAN_AND_EQUAL);
        return this;
    }

    @Override
    public SelectSqlInterface gtAndEqual(String field, Object value) {
        pieceCondition(field, value, SqlConstants.GREATER_THAN_AND_EQUAL);
        return this;
    }

    @Override
    public SelectSqlInterface and() {
        sqlBuilder.append(SqlConstants.AND).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public SelectSqlInterface or() {
        sqlBuilder.append(SqlConstants.OR).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public SelectSqlInterface leftParentheses() {
        sqlBuilder.append(SqlConstants.LEFT_PARENTHESES);
        return this;
    }

    @Override
    public SelectSqlInterface rightParentheses() {
        sqlBuilder.append(SqlConstants.RIGHT_PARENTHESES);
        return this;
    }
}
