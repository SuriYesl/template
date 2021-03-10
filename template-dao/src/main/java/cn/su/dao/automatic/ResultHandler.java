package cn.su.dao.automatic;

import cn.su.core.constants.SqlConstants;
import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;
import cn.su.dao.util.ResultHandlerInterface;
import cn.su.dao.util.SqlSpellUtil;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 20:42 2021/2/14 0014
 * @DESCRIPTION: sql结果处理
 */
public class ResultHandler<T> implements ResultHandlerInterface<T> {
    private T getObjectByMap(Class<?> clazz, Map<String, Object> oneResultMap) {
        T objectBo;
        try {
            objectBo = (T) clazz.newInstance();
            Field[] fields = SqlSpellUtil.getClassFieldArray(clazz);
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
    public T forObject(Class<T> clazz, List<Map<String, Object>> result) {
        return getObjectByMap(clazz, result.get(0));
    }

    @Override
    public List<T> forList(Class<T> clazz, List<Map<String, Object>> result) {
        List<T> list = new LinkedList<>();
        if (!NormHandleUtil.isEmpty(result)) {
            try {
                for (Map<String, Object> map : result) {
                    T objectBo = getObjectByMap(clazz, map);
                    list.add(objectBo);
                }
            } catch (Exception e) {
                throw new BusinessException("DaoHelper, method queryForList exception:", e);
            }
        }
        return list;
    }

    @Override
    public void update(T data) {

    }

    @Override
    public void insert(T data) {

    }

    @Override
    public void insertBatch(List<T> dataList) {

    }
}
