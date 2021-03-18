package cn.su.dao.link;

import java.util.List;

/**
 * @Author: su rui
 * @Date: 2021/3/12 14:34
 * @Description: 插入接口
 */
public interface InsertSqlInterface<T, E> extends SqlInterface<T, E> {
    InsertSqlInterface<T, E> into(String tableName);
    void obj( T data);
    Integer objBackWithId(T data);
    void batch(List<T> dataList);
}
