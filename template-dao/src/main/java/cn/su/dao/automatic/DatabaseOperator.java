package cn.su.dao.automatic;

import cn.su.core.constants.MathConstants;
import cn.su.core.constants.SqlConstants;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;
import cn.su.dao.util.ResultHandlerInterface;
import cn.su.dao.util.SqlExecute;
import cn.su.dao.util.SqlExecuteInterface;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:23 2021/2/13 0013
 * @DESCRIPTION: 操作类
 */
public class DatabaseOperator<T, E> implements AutoSqlInterface<T, E> {
    private final T parameterObject;
    private final Class<E> resultClass;
    private final SqlBuilder sqlBuilder;
    private StringBuilder fieldBuilder;
    private final SqlExecuteInterface<E> sqlExecuteInterface;
    private final ResultHandlerInterface<E> resultHandlerInterface;
    private boolean humpToLine;

    public String getSql() {
        return sqlBuilder.sql;
    }

    public DatabaseOperator(Class resultClass) {
        this(null, resultClass, cn.su.dao.automatic.SqlBuildHelper.getClassTableName(resultClass), null);
    }

    public DatabaseOperator(T parameterObject, Class resultClass) {
        this(parameterObject, resultClass, cn.su.dao.automatic.SqlBuildHelper.getClassTableName(resultClass), null);
    }

    public DatabaseOperator(T paramObject, Class resultClass, String tableName) {
        this(paramObject, resultClass, tableName, null);
    }

    public DatabaseOperator(T paramObject, Class resultClass, String tableName, Integer type) {
        type = null == type ? 0 : type;
        this.parameterObject = paramObject;
        this.resultClass = resultClass;
        sqlBuilder = new SqlBuilder(tableName);
        sqlBuilder.type = type;
        sqlExecuteInterface = new SqlExecute();
        resultHandlerInterface = new ResultHandler<>();
        humpToLine = false;

        switch (type) {
            case 0 :
                fieldBuilder = sqlBuilder.queryingFieldBuilder;
                break;
            case 1 :
                fieldBuilder = sqlBuilder.insertFieldBuilder;
                break;
            case 2 :
                fieldBuilder = sqlBuilder.updateFieldBuilder;
                break;
            case 3 :
                fieldBuilder = sqlBuilder.deleteFieldBuilder;
                break;
            default:
                fieldBuilder = new StringBuilder();
                break;
        }
    }

    @Override
    public AutoSqlInterface fieldToQuerying(String fields) {
        fieldBuilder.append(fields);
        return this;
    }

    @Override
    public AutoSqlInterface fieldToQuerying(List<String> fields) {
        for (String field : fields) {
            field = humpToLine ? StringUtil.humpToLine(field) : field;
            fieldBuilder.append(SqlConstants.FLOAT_POINT)
                    .append(field).append(SqlConstants.FLOAT_POINT).append(SqlConstants.COMMA);
        }
        fieldBuilder.delete(fieldBuilder.length(), fieldBuilder.length());
        return this;
    }

    @Override
    public AutoSqlInterface fieldToQuerying(Map<String, String> fieldMap) {
        for (Map.Entry<String, String> field : fieldMap.entrySet()) {
            fieldBuilder.append(SqlConstants.FLOAT_POINT).append(field.getValue()).append(SqlConstants.FLOAT_POINT)
                    .append(SqlConstants.SPACE).append(SqlConstants.AS).append(SqlConstants.SPACE)
                    .append(SqlConstants.FLOAT_POINT).append(field.getKey()).append(SqlConstants.FLOAT_POINT)
                    .append(SqlConstants.COMMA);
        }
        fieldBuilder.delete(fieldBuilder.length(), fieldBuilder.length());
        return this;
    }

    @Override
    public AutoSqlInterface condition(String conditions) {
        sqlBuilder.whereBuilder.append(conditions);
        return this;
    }

    @Override
    public AutoSqlInterface eq(String field, Object value) {
        String fieldValue = sqlBuilder.valueField(field, value);
        if (!sqlBuilder.endWithAndOr()) sqlBuilder.whereBuilder.append(SqlConstants.AND);
        sqlBuilder.whereBuilder.append(SqlConstants.FLOAT_POINT).append(field).append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE).append(SqlConstants.EQUAL_SIGN).append(SqlConstants.SPACE)
                .append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(fieldValue).append(SqlConstants.RIGHT_BRACE)
                .append(SqlConstants.COMMA);
        return this;
    }

    @Override
    public AutoSqlInterface neq(String field, Object value) {
        String fieldValue = sqlBuilder.valueField(field, value);
        if (!sqlBuilder.endWithAndOr()) sqlBuilder.whereBuilder.append(SqlConstants.AND);
        sqlBuilder.whereBuilder.append(SqlConstants.FLOAT_POINT).append(field).append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE).append(SqlConstants.NOT_EQUAL_SIGN).append(SqlConstants.SPACE)
                .append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(fieldValue).append(SqlConstants.RIGHT_BRACE)
                .append(SqlConstants.COMMA);
        return this;
    }

    @Override
    public AutoSqlInterface lt(String field, Object value) {
        String fieldValue = sqlBuilder.valueField(field, value);
        if (!sqlBuilder.endWithAndOr()) sqlBuilder.whereBuilder.append(SqlConstants.AND);
        sqlBuilder.whereBuilder.append(SqlConstants.FLOAT_POINT).append(field).append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE).append(SqlConstants.LESS_THAN).append(SqlConstants.SPACE)
                .append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(fieldValue).append(SqlConstants.RIGHT_BRACE)
                .append(SqlConstants.COMMA);
        return this;
    }

    @Override
    public AutoSqlInterface gt(String field, Object value) {
        String fieldValue = sqlBuilder.valueField(field, value);
        if (!sqlBuilder.endWithAndOr()) sqlBuilder.whereBuilder.append(SqlConstants.AND);
        sqlBuilder.whereBuilder.append(SqlConstants.FLOAT_POINT).append(field).append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE).append(SqlConstants.GREATER_THAN).append(SqlConstants.SPACE)
                .append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(fieldValue).append(SqlConstants.RIGHT_BRACE)
                .append(SqlConstants.COMMA);
        return this;
    }

    @Override
    public AutoSqlInterface ltAndEqual(String field, Object value) {
        String fieldValue = sqlBuilder.valueField(field, value);
        if (!sqlBuilder.endWithAndOr()) sqlBuilder.whereBuilder.append(SqlConstants.AND);
        sqlBuilder.whereBuilder.append(SqlConstants.FLOAT_POINT).append(field).append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE).append(SqlConstants.LESS_THAN_AND_EQUAL).append(SqlConstants.SPACE)
                .append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(fieldValue).append(SqlConstants.RIGHT_BRACE)
                .append(SqlConstants.COMMA);
        return this;
    }

    @Override
    public AutoSqlInterface gtAndEqual(String field, Object value) {
        String fieldValue = sqlBuilder.valueField(field, value);
        if (!sqlBuilder.endWithAndOr()) sqlBuilder.whereBuilder.append(SqlConstants.AND);
        sqlBuilder.whereBuilder.append(SqlConstants.FLOAT_POINT).append(field).append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE).append(SqlConstants.GREATER_THAN_AND_EQUAL).append(SqlConstants.SPACE)
                .append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(fieldValue).append(SqlConstants.RIGHT_BRACE)
                .append(SqlConstants.COMMA);
        return this;
    }

    @Override
    public AutoSqlInterface and() {
        sqlBuilder.whereBuilder.append(SqlConstants.AND);
        return this;
    }

    @Override
    public AutoSqlInterface or() {
        sqlBuilder.whereBuilder.append(SqlConstants.OR);
        return this;
    }

    @Override
    public AutoSqlInterface leftParentheses() {
        sqlBuilder.whereBuilder.append(SqlConstants.LEFT_PARENTHESES);
        return this;
    }

    @Override
    public AutoSqlInterface rightParentheses() {
        sqlBuilder.whereBuilder.append(SqlConstants.RIGHT_PARENTHESES);
        return this;
    }

    @Override
    public AutoSqlInterface orderBy(String orderRule) {
        sqlBuilder.orderBuilder.append(orderRule);
        return this;
    }

    @Override
    public AutoSqlInterface groupBy(String groupRule) {
        sqlBuilder.groupBuilder.append(groupRule);
        return this;
    }

    @Override
    public AutoSqlInterface limit(Integer size) {
        size = null == size ? MathConstants.TEN : size;
        sqlBuilder.limitBuilder.append(SqlConstants.LIMIT).append(SqlConstants.SPACE)
                .append(MathConstants.ZERO).append(SqlConstants.COMMA).append(SqlConstants.SPACE).append(size);
        return this;
    }

    @Override
    public AutoSqlInterface limit(Integer startRow, Integer size) {
        startRow = null == startRow ? 0 : startRow;
        size = null == size ? MathConstants.TEN : size;
        sqlBuilder.limitBuilder.append(SqlConstants.LIMIT).append(SqlConstants.SPACE)
                .append(startRow).append(SqlConstants.COMMA).append(SqlConstants.SPACE).append(size);
        return this;
    }

    private String completeSql() {
        StringBuilder sql = new StringBuilder();
        //switch ()
        return null;
    }

    @Override
    public E forObject() {
        //Map<String, Object> params = new LinkedHashMap<>();
        //List<Map<String, Object>> result = sqlExecuteInterface.query(getSql().toString());
        //if (NormHandleUtil.isEmpty(result)) {
        //    return null;
        //}
        //if (result.size() > MathConstants.ONE) {
        //    throw new TooManyResultsException("search for one result but found" + result.size());
        //}
        //return resultHandlerInterface.forObject(this.resultClass, result);
        return null;
    }

    @Override
    public List<E> forList() {
        //List<Map<String, Object>> result = sqlExecuteInterface.search(getSql().toString());
        //if (NormHandleUtil.isEmpty(result)) {
        //    return new LinkedList<>();
        //}
        //return resultHandlerInterface.forList(this.resultClass, result);
        return null;
    }

    @Override
    public T insert(T data) {
        return null;
    }

    @Override
    public T insertBatch(List<T> dataList) {
        return null;
    }

    @Override
    public T update(T data) {
        return null;
    }

    @Override
    public void delete() {

    }
}
