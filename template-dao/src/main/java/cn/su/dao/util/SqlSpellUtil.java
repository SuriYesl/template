package cn.su.dao.util;

import cn.su.core.constants.MathConstants;
import cn.su.core.constants.SqlConstants;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: su rui
 * @Date: 2021/1/26 14:55
 * @Description: 公用数据库语言生成工具类
 */
public class SqlSpellUtil {
    public static final Map<String, List<String>> classFieldStrMap = new ConcurrentHashMap<>();
    public static final Map<String, Field[]> classFieldMap = new ConcurrentHashMap<>();

    public static String getClassTableName(Class clazz) {
        return SqlConstants.TB + StringUtil.humpToLine(clazz.getSimpleName());
    }

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
        System.out.println("no cache for this class fields: " + clazz.getName());
        return fieldStr;
    }

    public static String getSearchSql(String tableName, Map<String, String> fieldMap, Map<String, Object> condition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SqlConstants.SELECT).append(SqlConstants.SPACE);
        for (Map.Entry<String, String> field : fieldMap.entrySet()) {
            field.setValue(NormHandleUtil.isEmpty(field.getValue()) ? field.getKey() : field.getValue());
            stringBuilder.append(field.getValue())
                    .append(SqlConstants.SPACE)
                    .append(SqlConstants.AS)
                    .append(SqlConstants.SPACE)
                    .append(field.getKey())
                    .append(SqlConstants.COMMA);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - MathConstants.ONE);
        stringBuilder.append(SqlConstants.SPACE)
                .append(SqlConstants.FROM)
                .append(SqlConstants.SPACE)
                .append(tableName);
        return stringBuilder.toString();
    }
}
