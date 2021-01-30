package cn.su.core.util;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 0:32 2021/1/18 0018
 * @Description: 常用处理，如判空等
 */
public class NormHandleUtil {
    public static <T> boolean isEmpty(T object) {
        if (null == object) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String string) {
        if (null == string || string.isEmpty() || string.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static <T> boolean isEmpty(List<?> list) {
        if (null == list || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map<Object, Object> map) {
        if (null == map || map.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object[] array) {
        if (null == array || array.length == 0) {
            return true;
        }
        return false;
    }

    public static Integer selfOrNullToZero(Integer number) {
        return null == number ? 0 : number;
    }
}
