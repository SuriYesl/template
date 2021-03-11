package cn.su.dao.automatic;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:19 2021/3/4 0004
 * @DESCRIPTION: 简易sql自动化拼写
 */
public interface SimpleSqlAutomation<T, E> {
    AutoSqlInterface<T, E> select(Class<E> resultClass, String tableName);
    AutoSqlInterface<T, E> insert(T parameterObject, String tableName);
    AutoSqlInterface<T, E> insert(T parameterObject, Class<E> resultClass);
    AutoSqlInterface<T, E> insert(T parameterObject, Class<E> resultClass, String tableName);
    AutoSqlInterface<T, E> update();
    AutoSqlInterface<T, E> delete();
}
