package cn.su.dao.link;

import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 20:42 2021/2/14 0014
 * @DESCRIPTION: sql结果处理
 */
public class ResultHandler implements ResultHandlerInterface {
    private <R> R getObjectByMap(Class<?> clazz, Map<String, Object> oneResultMap) {
        R objectBo;
        try {
            objectBo = (R) clazz.newInstance();
            Field[] fields = SqlBuildHelper.getClassFieldArray(clazz);
            for (Field field : fields) {
                field.setAccessible(true);
                if (!SqlConstants.SERIAL_VERSION_UID.equals(field.getName())) {
                    if (oneResultMap.containsKey(field.getName())) {
                        field.set(objectBo, oneResultMap.get(field.getName()));
                    } else if (oneResultMap.containsKey(StringUtil.humpToLine(field.getName()))){
                        field.set(objectBo, oneResultMap.get(StringUtil.humpToLine(field.getName())));
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException("SqlHelper, method forOneResult exception:", e);
        }
        return objectBo;
    }

    @Override
    public <R> R forObject(Class<R> clazz, List<Map<String, Object>> result) {
        return getObjectByMap(clazz, result.get(0));
    }

    @Override
    public <R> List<R> forList(Class<R> clazz, List<Map<String, Object>> result) {
        List<R> list = new LinkedList<>();
        if (!NormHandleUtil.isEmpty(result)) {
            try {
                for (Map<String, Object> map : result) {
                    R objectBo = getObjectByMap(clazz, map);
                    list.add(objectBo);
                }
            } catch (Exception e) {
                throw new BusinessException("ResultHandler, method queryForList exception:", e);
            }
        }
        return list;
    }
}
