package cn.su.dao.automatic;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:18 2021/3/4 0004
 * @DESCRIPTION: mybatis简易sql自动化拼写
 */
public class SimpleSqlAutomationUtil<T, E> implements SimpleSqlAutomation<T, E>{
    @Override
    public AutoSqlInterface<T, E> select(Class<E> resultClass, String tableName) {
        DatabaseOperator<T, E> databaseOperator = new DatabaseOperator<>(null, resultClass, tableName);
        return databaseOperator;
    }

    @Override
    public AutoSqlInterface<T, E> insert(T parameterObject, String tableName) {
        DatabaseOperator<T, E> databaseOperator = new DatabaseOperator<>(parameterObject, tableName, 1);
        return databaseOperator;
    }

    @Override
    public AutoSqlInterface<T, E> insert(T parameterObject, Class<E> resultClass) {
        DatabaseOperator<T, E> databaseOperator = new DatabaseOperator<>(parameterObject, resultClass, 1);
        return databaseOperator;
    }

    @Override
    public AutoSqlInterface<T, E> insert(T parameterObject, Class<E> resultClass, String tableName) {
        DatabaseOperator<T, E> databaseOperator = new DatabaseOperator<>(parameterObject, resultClass, tableName, 1);
        return databaseOperator;
    }

    @Override
    public AutoSqlInterface<T, E> update() {
        return null;
    }

    @Override
    public AutoSqlInterface<T, E> delete() {
        return null;
    }
}
