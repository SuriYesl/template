package cn.su.dao.link;

import cn.su.core.constants.MathConstants;
import cn.su.core.exception.BusinessException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: su rui
 * @Date: 2021/3/18 14:13
 * @Description: 删除操作
 */
public class DeleteSql implements DeleteSqlInterface {
    private StringBuilder sqlBuilder;
    private boolean hasCondition;
    private Map<String, Object> filedAndValueMap;
    private Map<String, Integer> filedCountMap;
    private SqlExecuteInterface sqlExecuteInterface;

    public DeleteSql() {
        sqlBuilder = new StringBuilder();
        hasCondition = false;
        filedAndValueMap = new HashMap<>();
        filedCountMap = new HashMap<>();
        sqlExecuteInterface = new SqlExecute();
        sqlBuilder.append(SqlConstants.DELETE).append(SqlConstants.SPACE);
    }

    @Override
    public DeleteSqlInterface from(String tableName) {
        sqlBuilder.append(SqlConstants.FROM).append(SqlConstants.SPACE).append(tableName).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public DeleteSqlInterface where() {
        sqlBuilder.append(SqlConstants.WHERE).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public DeleteSqlInterface where(String condition) {
        sqlBuilder.append(SqlConstants.WHERE).append(SqlConstants.SPACE).append(condition).append(SqlConstants.SPACE);
        return this;
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
    public DeleteSqlInterface eq(String field, Object value) {
        pieceCondition(field, value, SqlConstants.EQUAL_SIGN);
        return this;
    }

    @Override
    public DeleteSqlInterface neq(String field, Object value) {
        pieceCondition(field, value, SqlConstants.NOT_EQUAL_SIGN);
        return this;
    }

    @Override
    public DeleteSqlInterface lt(String field, Object value) {
        pieceCondition(field, value, SqlConstants.LESS_THAN);
        return this;
    }

    @Override
    public DeleteSqlInterface gt(String field, Object value) {
        pieceCondition(field, value, SqlConstants.GREATER_THAN);
        return this;
    }

    @Override
    public DeleteSqlInterface ltAndEqual(String field, Object value) {
        pieceCondition(field, value, SqlConstants.LESS_THAN_AND_EQUAL);
        return this;
    }

    @Override
    public DeleteSqlInterface gtAndEqual(String field, Object value) {
        pieceCondition(field, value, SqlConstants.GREATER_THAN_AND_EQUAL);
        return this;
    }

    @Override
    public DeleteSqlInterface and() {
        sqlBuilder.append(SqlConstants.AND).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public DeleteSqlInterface or() {
        sqlBuilder.append(SqlConstants.OR).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public DeleteSqlInterface leftParentheses() {
        sqlBuilder.append(SqlConstants.LEFT_PARENTHESES);
        return this;
    }

    @Override
    public DeleteSqlInterface rightParentheses() {
        sqlBuilder.append(SqlConstants.RIGHT_PARENTHESES);
        return this;
    }

    @Override
    public void doDelete() {
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("value", sqlBuilder.toString());
        param.putAll(filedAndValueMap);
        sqlExecuteInterface.delete(param);
    }
}
