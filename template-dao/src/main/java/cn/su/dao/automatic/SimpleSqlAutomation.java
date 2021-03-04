package cn.su.dao.automatic;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:19 2021/3/4 0004
 * @DESCRIPTION: 简易sql自动化拼写
 */
public interface SimpleSqlAutomation<T, E> {
    AutoSqlInterface<T, E> select(Class resultClass);
    AutoSqlInterface<T, E> select(T paramObject, Class resultClass);
    AutoSqlInterface<T, E> select(T paramObject, Class resultClass, String tableName);
    AutoSqlInterface<T, E> insert();
    AutoSqlInterface<T, E> update();
    AutoSqlInterface<T, E> delete();
}
