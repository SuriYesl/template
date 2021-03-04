package cn.su.dao.automatic;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:18 2021/3/4 0004
 * @DESCRIPTION: mybatis简易sql自动化拼写
 */
public class SimpleSqlAutomationHelper<T, E> implements SimpleSqlAutomation<T, E>{
    @Override
    public AutoSqlInterface<T, E> select(Class resultClass) {
        DatabaseOperator<T, E> databaseOperator = new DatabaseOperator<>(resultClass);
        return databaseOperator;
    }

    @Override
    public AutoSqlInterface<T, E> select(T paramObject, Class resultClass) {
        DatabaseOperator<T, E> databaseOperator = new DatabaseOperator<>(paramObject, resultClass);
        return databaseOperator;
    }

    @Override
    public AutoSqlInterface<T, E> select(T paramObject, Class resultClass, String tableName) {
        DatabaseOperator<T, E> databaseOperator = new DatabaseOperator<>(paramObject, resultClass, tableName);
        return databaseOperator;
    }

    @Override
    public AutoSqlInterface<T, E> insert() {
        return null;
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
