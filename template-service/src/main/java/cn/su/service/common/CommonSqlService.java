package cn.su.service.common;

import java.util.List;
import java.util.Map;

/**
 * @Author: su rui
 * @Date: 2021/1/26 14:45
 * @Description: 公用SQL服务
 */
public interface CommonSqlService {
    <T extends Object> T searchTarget(Class<T> clazz, List<String> values, Map<String, Object> params);
}
