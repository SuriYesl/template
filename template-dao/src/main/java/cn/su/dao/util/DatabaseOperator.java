package cn.su.dao.util;

import cn.su.core.constants.MathConstants;
import cn.su.core.constants.SqlConstants;
import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:23 2021/2/13 0013
 * @DESCRIPTION: 操作类
 */
public class DatabaseOperator<E> implements AutoSqlInterface<E> {
    private final Class clazz;
    private final Class<E> resultClass;
    private final SqlBuilder sqlBuilder;
    private final SqlExecuteInterface<E> sqlExecuteInterface;
    private final ResultHandlerInterface<E> resultHandlerInterface;

    public String getPrimaryTableName() {
        return sqlBuilder.primaryTable;
    }

    public StringBuilder getSqlSearchBuilder() {
        return sqlBuilder.searchFields;
    }

    public String getSearchSql() {
        return getSqlSearchBuilder().toString();
    }

    public DatabaseOperator(Class clazz) {
        this.clazz = clazz;
        resultClass = clazz;
        sqlBuilder = new SqlBuilder(SqlBuildHelper.getClassTableName(clazz));
        sqlExecuteInterface = new SqlExecute();
        resultHandlerInterface = new ResultHandler<>();
    }

    @Override
    public AutoSqlInterface select() {
        getSqlSearchBuilder().append(SqlConstants.SELECT).append(SqlConstants.SPACE);
        return this;
    }

    private Map<String, String> getFieldMap() {
        List<String> fieldStrings = SqlBuildHelper.getClassFields(this.clazz);
        if (NormHandleUtil.isEmpty(fieldStrings)) {
            throw new BusinessException("value for selecting is empty");
        }
        return fieldStrings.parallelStream()
                .collect(Collectors.toMap(fieldString -> fieldString, fieldString -> StringUtil.humpToLine(fieldString)));
    }

    private void searchFields(Map<String, String> fieldMap) {
        for (Map.Entry<String, String> field : fieldMap.entrySet()) {
            getSqlSearchBuilder().append(SqlConstants.FLOAT_POINT)
                    .append(field.getValue())
                    .append(SqlConstants.FLOAT_POINT)
                    .append(SqlConstants.SPACE)
                    .append(SqlConstants.AS)
                    .append(SqlConstants.SPACE)
                    .append(SqlConstants.FLOAT_POINT)
                    .append(field.getKey())
                    .append(SqlConstants.FLOAT_POINT)
                    .append(SqlConstants.COMMA);
        }
        getSqlSearchBuilder().deleteCharAt(getSqlSearchBuilder().length() - MathConstants.ONE).append(SqlConstants.SPACE);
    }

    @Override
    public AutoSqlInterface defaultSelect() {
        select();
        searchFields(getFieldMap());
        from();
        getSqlSearchBuilder().append(getPrimaryTableName()).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public AutoSqlInterface automaticSelect() {
        select();
        searchFields(getFieldMap());
        from();
        getSqlSearchBuilder().append(getPrimaryTableName()).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public AutoSqlInterface automaticSelect(String fields) {
        select();
        getSqlSearchBuilder().append(fields).append(SqlConstants.SPACE);
        from();
        getSqlSearchBuilder().append(getPrimaryTableName()).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public AutoSqlInterface automaticSelect(List<String> fields) {
        return this;
    }

    @Override
    public AutoSqlInterface automaticSelect(Map<String, String> fields) {
        return this;
    }

    @Override
    public AutoSqlInterface from() {
        getSqlSearchBuilder().append(SqlConstants.FROM + SqlConstants.SPACE);
        return this;
    }

    @Override
    public AutoSqlInterface where() {
        getSqlSearchBuilder().append(SqlConstants.WHERE + SqlConstants.SPACE);
        return this;
    }

    @Override
    public AutoSqlInterface where(String condition) {
        getSqlSearchBuilder().append(SqlConstants.WHERE + SqlConstants.SPACE).append(condition);
        return this;
    }

    @Override
    public AutoSqlInterface eq(String field, String value) {
        getSqlSearchBuilder().append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.EQUAL_SIGN)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public AutoSqlInterface neq(String field, String value) {
        getSqlSearchBuilder().append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.NOT_EQUAL_SIGN)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public AutoSqlInterface lt(String field, String value) {
        getSqlSearchBuilder().append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.LESS_THAN)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public AutoSqlInterface gt(String field, String value) {
        getSqlSearchBuilder().append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.GREATER_THAN)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public AutoSqlInterface ltAndEqual(String field, String value) {
        getSqlSearchBuilder().append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.LESS_THAN_AND_EQUAL)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public AutoSqlInterface gtAndEqual(String field, String value) {
        getSqlSearchBuilder().append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.GREATER_THAN_AND_EQUAL)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public AutoSqlInterface and() {
        getSqlSearchBuilder().append(SqlConstants.AND + SqlConstants.SPACE);
        return this;
    }

    @Override
    public AutoSqlInterface or() {
        getSqlSearchBuilder().append(SqlConstants.OR + SqlConstants.SPACE);
        return this;
    }

    private String getDynamicFieldString(String... fields) {
        StringBuilder fieldSb = new StringBuilder();
        for (String field : fields) {
            fieldSb.append(field).append(", ");
        }

        if (fieldSb.length() > 0) {
            fieldSb.delete(fieldSb.length() - MathConstants.ONE, fieldSb.length());
        }
        return fieldSb.toString();
    }

    @Override
    public AutoSqlInterface orderBy(String... fields) {
        getSqlSearchBuilder().append(SqlConstants.ORDER_BY + SqlConstants.SPACE).append(getDynamicFieldString(fields));
        return this;
    }

    @Override
    public AutoSqlInterface groupBy(String... fields) {
        getSqlSearchBuilder().append(SqlConstants.GROUP_BY + SqlConstants.SPACE).append(getDynamicFieldString(fields));
        return this;
    }

    @Override
    public AutoSqlInterface limit(Integer startRow, Integer size) {
        getSqlSearchBuilder().append(SqlConstants.LIMIT + SqlConstants.SPACE)
                .append(null == startRow ? 0 : startRow)
                .append(SqlConstants.COMMA)
                .append(SqlConstants.SPACE)
                .append(null == size ? Integer.MAX_VALUE : size);
        return this;
    }

    @Override
    public AutoSqlInterface leftJoin() {
        return null;
    }

    @Override
    public AutoSqlInterface on() {
        getSqlSearchBuilder().append(SqlConstants.ON + SqlConstants.SPACE);
        return this;
    }

    @Override
    public AutoSqlInterface underLine() {
        getSqlSearchBuilder().append(SqlConstants.UNDER_LINE);
        return this;
    }

    @Override
    public AutoSqlInterface comma() {
        getSqlSearchBuilder().append(SqlConstants.COMMA);
        return this;
    }

    @Override
    public AutoSqlInterface floatPoint() {
        getSqlSearchBuilder().append(SqlConstants.FLOAT_POINT);
        return this;
    }

    @Override
    public AutoSqlInterface leftParentheses() {
        getSqlSearchBuilder().append(SqlConstants.LEFT_PARENTHESES);
        return this;
    }

    @Override
    public AutoSqlInterface rightParentheses() {
        getSqlSearchBuilder().append(SqlConstants.RIGHT_PARENTHESES);
        return this;
    }

    @Override
    public E selectObject() {
        List<Map<String, Object>> result = sqlExecuteInterface.search(getSearchSql());
        if (NormHandleUtil.isEmpty(result)) {
            return null;
        }
        if(result.size() > MathConstants.ONE){
            throw new TooManyResultsException("search for one result but found" + result.size());
        }
        return resultHandlerInterface.forObject(this.resultClass, result);
    }

    @Override
    public List<E> selectList() {
        List<Map<String, Object>> result = sqlExecuteInterface.search(getSearchSql());
        if (NormHandleUtil.isEmpty(result)) {
            return new LinkedList<>();
        }
        return resultHandlerInterface.forList(this.resultClass, result);
    }

    @Override
    public E insertObject(E data) {
        return null;
    }

    @Override
    public E insertBatch(List<E> dataList) {
        return null;
    }

    @Override
    public E updateObject(E data) {
        return null;
    }

    @Override
    public void deleteObject() {

    }
}
