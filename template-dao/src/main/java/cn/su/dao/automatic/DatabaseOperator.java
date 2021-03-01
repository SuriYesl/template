package cn.su.dao.automatic;

import cn.su.core.constants.MathConstants;
import cn.su.core.util.NormHandleUtil;
import cn.su.dao.util.ResultHandlerInterface;
import cn.su.dao.util.SqlExecute;
import cn.su.dao.util.SqlExecuteInterface;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:23 2021/2/13 0013
 * @DESCRIPTION: 操作类
 */
public class DatabaseOperator<T, E> implements AutoSqlInterface<E> {
    private final T parameterObject;
    private final Class<E> resultClass;
    private final SqlBuilder sqlBuilder;
    private final SqlExecuteInterface<E> sqlExecuteInterface;
    private final ResultHandlerInterface<E> resultHandlerInterface;

    public StringBuilder getSql() {
        return sqlBuilder.sql;
    }

    public DatabaseOperator(Class resultClass) {
        this(null, resultClass, cn.su.dao.automatic.SqlBuildHelper.getClassTableName(resultClass));
    }

    public DatabaseOperator(T parameterObject, Class resultClass) {
        this(parameterObject, resultClass, cn.su.dao.automatic.SqlBuildHelper.getClassTableName(resultClass));
    }

    public DatabaseOperator(T paramObject, Class resultClass, String tableName) {
        this.parameterObject = paramObject;
        this.resultClass = resultClass;
        sqlBuilder = new SqlBuilder(tableName);
        sqlExecuteInterface = new SqlExecute();
        resultHandlerInterface = new ResultHandler<>();
    }


    @Override
    public void fieldToQuerying(String fields) {

    }

    @Override
    public void fieldToQuerying(List<String> fields) {

    }

    @Override
    public void fieldToQuerying(Map<String, String> fieldMap) {

    }

    @Override
    public void condition(String conditions) {

    }

    @Override
    public AutoSqlInterface eq(String field, Object value) {
        return null;
    }

    @Override
    public AutoSqlInterface neq(String field, Object value) {
        return null;
    }

    @Override
    public AutoSqlInterface lt(String field, Object value) {
        return null;
    }

    @Override
    public AutoSqlInterface gt(String field, Object value) {
        return null;
    }

    @Override
    public AutoSqlInterface ltAndEqual(String field, Object value) {
        return null;
    }

    @Override
    public AutoSqlInterface gtAndEqual(String field, Object value) {
        return null;
    }

    @Override
    public AutoSqlInterface and() {
        return null;
    }

    @Override
    public AutoSqlInterface or() {
        return null;
    }

    @Override
    public AutoSqlInterface leftParentheses() {
        return null;
    }

    @Override
    public AutoSqlInterface rightParentheses() {
        return null;
    }

    @Override
    public AutoSqlInterface orderBy(String orderRule) {
        return null;
    }

    @Override
    public AutoSqlInterface groupBy(String groupRule) {
        return null;
    }

    @Override
    public AutoSqlInterface limit(Integer startRow, Integer size) {
        return null;
    }

    @Override
    public E selectObject() {
        List<Map<String, Object>> result = sqlExecuteInterface.search(getSql().toString());
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
        List<Map<String, Object>> result = sqlExecuteInterface.search(getSql().toString());
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
