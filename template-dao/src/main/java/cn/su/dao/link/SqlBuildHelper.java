package cn.su.dao.link;

import cn.su.core.constants.MathConstants;
import cn.su.core.util.NormHandleUtil;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @AUTHOR: sr
 * @DATE: Create In 11:15 2021/2/14 0014
 * @DESCRIPTION: sql语句构建辅助类
 */
public class SqlBuildHelper {
    public static Map<String, List<String>> classFieldStrMap = new ConcurrentHashMap<>();
    public static Map<String, Field[]> classFieldMap = new ConcurrentHashMap<>();

    public static Field[] getClassFieldArray(Class clazz) {
        if (classFieldMap.containsKey(clazz.getName()) && !NormHandleUtil.isEmpty(classFieldMap.get(clazz.getName()))) {
            return classFieldMap.get(clazz.getName());
        }
        Field[] fields = clazz.getDeclaredFields();
        classFieldMap.put(clazz.getName(), fields);
        return fields;
    }

    public static List<String> getClassFields(Class clazz) {
        if (classFieldStrMap.containsKey(clazz.getName()) && !NormHandleUtil.isEmpty(classFieldStrMap.get(clazz.getName()))) {
            return classFieldStrMap.get(clazz.getName());
        }

        List<String> fieldStr = new LinkedList<>();
        Field[] fields = null;
        if (classFieldMap.containsKey(clazz.getName()) && !NormHandleUtil.isEmpty(classFieldMap.get(clazz.getName()))) {
            fields = classFieldMap.get(clazz.getName());
        } else {
            fields = clazz.getDeclaredFields();
            classFieldMap.put(clazz.getName(), fields);
        }

        for (Field field : fields) {
            if (SqlConstants.SERIAL_VERSION_UID.equals(field.getName())) {
                continue;
            }
            fieldStr.add(field.getName());
        }
        classFieldStrMap.put(clazz.getName(), fieldStr);
        return fieldStr;
    }

    public static boolean endWithAndOr(String sql) {
        return sql.trim().endsWith(SqlConstants.AND)
                || sql.trim().endsWith(SqlConstants.OR);
    }
}
