package cn.su.dao.util;

/**
 * @AUTHOR: sr
 * @DATE: Create In 14:28 2021/2/13 0013
 * @DESCRIPTION: sql语句构造类接口
 */
public interface SqlBuilderInterface {
    SqlBuilderInterface buildSqlFunction();
    SqlBuilderInterface buildPrimaryTableName();
    SqlBuilderInterface buildWhereCondition();
    SqlBuilderInterface buildConnectionType();
    SqlBuilderInterface buildRelationTableName();
    SqlBuilderInterface buildOnCondition();
}
