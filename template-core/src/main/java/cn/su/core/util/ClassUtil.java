package cn.su.core.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @AUTHOR: sr
 * @DATE: Create In 21:27 2021/1/27 0027
 * @DESCRIPTION: 类对象工具类
 */
public class ClassUtil {
    public static final Map<String, List<String>> classFieldMap = new ConcurrentHashMap<>();

    public static List<String> getClassFields(Class clazz) {
        if (classFieldMap.containsKey(clazz.getSimpleName()) && !NormHandleUtil.isEmpty(classFieldMap.get(clazz.getSimpleName()))) {
            return classFieldMap.get(clazz.getSimpleName());
        }

        List<String> fieldStr = new LinkedList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldStr.add(field.getName());
        }
        classFieldMap.put(clazz.getName(), fieldStr);
        return fieldStr;
    }
}
