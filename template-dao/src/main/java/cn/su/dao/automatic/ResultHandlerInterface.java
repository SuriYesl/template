package cn.su.dao.automatic;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 21:09 2021/2/14 0014
 * @DESCRIPTION: 结果处理接口
 */
public interface ResultHandlerInterface<T> {
    T forObject(Class<T> clazz, List<Map<String, Object>> result);
    List<T> forList(Class<T> clazz, List<Map<String, Object>> result);
    void update(T data);
    void insert(T data);
    void insertBatch(List<T> dataList);
}
