package cn.su.dao.link;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 21:09 2021/2/14 0014
 * @DESCRIPTION: 结果处理接口
 */
public interface ResultHandlerInterface {
    <R> R forObject(Class<R> clazz, List<Map<String, Object>> result);
    <R> List<R> forList(Class<R> clazz, List<Map<String, Object>> result);
}
