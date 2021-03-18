package cn.su.dao.link;

import cn.su.core.constants.MathConstants;
import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: su rui
 * @Date: 2021/3/18 10:00
 * @Description: 更新操作
 */
public class UpdateSql implements UpdateSqlInterface {
    private boolean humpToLine;
    private boolean hasCondition;
    private StringBuilder sqlBuilder;
    private static final String ADDITIONAL = "SuRiKey";
    public Map<String, Object> fieldValueMap;
    public Map<String, Integer> filedCountMap;
    private SqlExecuteInterface sqlExecuteInterface;

    public void setHumpToLine(boolean humpToLine) {
        this.humpToLine = humpToLine;
    }

    public UpdateSql(String tableName) {
        humpToLine = true;
        hasCondition = false;
        sqlBuilder = new StringBuilder();
        fieldValueMap = new LinkedHashMap<>();
        filedCountMap = new LinkedHashMap<>();
        sqlExecuteInterface = new SqlExecute<>();
        sqlBuilder.append(SqlConstants.UPDATE).append(SqlConstants.SPACE).append(tableName).append(SqlConstants.SPACE);
    }

    private <P> void objectToFieldValueMap(P object) {
        Field[] fields = SqlBuildHelper.getClassFieldArray(object.getClass());
        if (NormHandleUtil.isEmpty(fields)) throw new BusinessException("没有字段需要更新");
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                fieldValueMap.put(ADDITIONAL + field.getName(), field.get(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("反射获取字段值出错", e);
        }
    }

    private void buildSqlOfSettingField() {
        sqlBuilder.append(SqlConstants.SET).append(SqlConstants.SPACE);
        for (Map.Entry<String, Object> map : fieldValueMap.entrySet()) {
            String originalField = map.getKey().replace(ADDITIONAL, "");
            String humpField = originalField.contains(SqlConstants.UNDER_LINE) ? StringUtil.lineToHump(originalField) : originalField;
            sqlBuilder.append(SqlConstants.FLOAT_POINT).append(humpToLine ? StringUtil.humpToLine(humpField) : humpField)
                    .append(SqlConstants.FLOAT_POINT).append(SqlConstants.SPACE).append(SqlConstants.EQUAL_SIGN).append(SqlConstants.SPACE)
                    .append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(map.getKey()).append(SqlConstants.RIGHT_BRACE)
                    .append(SqlConstants.COMMA);
        }
        sqlBuilder.delete(sqlBuilder.length() - 1, sqlBuilder.length()).append(SqlConstants.SPACE);
    }

    @Override
    public <P> UpdateSqlInterface set(P object) {
        objectToFieldValueMap(object);
        buildSqlOfSettingField();
        return this;
    }

    @Override
    public UpdateSqlInterface set(String column, Object value) {
        column = ADDITIONAL + column;
        fieldValueMap.put(column, value);
        buildSqlOfSettingField();
        return this;
    }

    @Override
    public UpdateSqlInterface set(Map<String, Object> fieldMap) {
        if (NormHandleUtil.isEmpty(fieldMap)) throw new BusinessException("更新字段为空");
        for (Map.Entry<String, Object> tempMap : fieldMap.entrySet()) {
            fieldValueMap.put(ADDITIONAL + tempMap.getKey(), tempMap.getValue());
        }
        buildSqlOfSettingField();
        return this;
    }

    @Override
    public UpdateSqlInterface where() {
        sqlBuilder.append(SqlConstants.WHERE).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public UpdateSqlInterface where(String condition) {
        sqlBuilder.append(SqlConstants.WHERE).append(SqlConstants.SPACE).append(condition).append(SqlConstants.SPACE);
        return this;
    }

    private Integer fieldCount(String field) {
        if (!filedCountMap.containsKey(field)) {
            return MathConstants.ZERO;
        } else {
            return filedCountMap.get(field);
        }
    }

    private String valueField(String field, Object value) {
        int count = fieldCount(field) + 1;
        String fieldValue = count > 1 ? field + count : field;
        filedCountMap.put(field, count);
        fieldValueMap.put(fieldValue, value);
        return fieldValue;
    }

    private void prePieceCondition() {
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
    public UpdateSqlInterface eq(String field, Object value) {
        pieceCondition(field, value, SqlConstants.EQUAL_SIGN);
        return this;
    }

    @Override
    public UpdateSqlInterface neq(String field, Object value) {
        pieceCondition(field, value, SqlConstants.NOT_EQUAL_SIGN);
        return this;
    }

    @Override
    public UpdateSqlInterface lt(String field, Object value) {
        pieceCondition(field, value, SqlConstants.LESS_THAN);
        return this;
    }

    @Override
    public UpdateSqlInterface gt(String field, Object value) {
        pieceCondition(field, value, SqlConstants.GREATER_THAN);
        return this;
    }

    @Override
    public UpdateSqlInterface ltAndEqual(String field, Object value) {
        pieceCondition(field, value, SqlConstants.LESS_THAN_AND_EQUAL);
        return this;
    }

    @Override
    public UpdateSqlInterface gtAndEqual(String field, Object value) {
        pieceCondition(field, value, SqlConstants.GREATER_THAN_AND_EQUAL);
        return this;
    }

    @Override
    public UpdateSqlInterface and() {
        sqlBuilder.append(SqlConstants.AND).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public UpdateSqlInterface or() {
        sqlBuilder.append(SqlConstants.OR).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public UpdateSqlInterface leftParentheses() {
        sqlBuilder.append(SqlConstants.LEFT_PARENTHESES);
        return this;
    }

    @Override
    public UpdateSqlInterface rightParentheses() {
        sqlBuilder.append(SqlConstants.RIGHT_PARENTHESES);
        return this;
    }

    @Override
    public void doUpdate() {
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("value", sqlBuilder.toString());
        param.putAll(fieldValueMap);
        sqlExecuteInterface.update(param);
    }
}
