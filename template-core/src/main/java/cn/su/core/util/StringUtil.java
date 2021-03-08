package cn.su.core.util;

import cn.su.core.constants.MathConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @AUTHOR: sr
 * @DATE: Create In 0:32 2021/1/18 0018
 * @Description:
 */
public class StringUtil {
    private final static Pattern linePattern = Pattern.compile("_(\\w)");
    private final static Pattern humpPattern = Pattern.compile("[A-Z]");
    private final static String LINE = "_";

    public static String getInitialsUp(String string) {
        if (NormHandleUtil.isEmpty(string)) {
            return "";
        }
        byte[] items;
        try {
            items = string.getBytes("UTF-8");
            items[0] = (byte) ((char) items[0] - 'a' + 'A');
            return new String(items, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String getInitialsDown(String string) {
        byte[] items;
        try {
            items = string.getBytes("UTF-8");
            items[0] = (byte) ((char) items[0] + 'a' - 'A');
            return new String(items, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String lineToHump(String str) {
        if (NormHandleUtil.isEmpty(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String humpToLine(String str) {
        if (NormHandleUtil.isEmpty(str)) {
            return str;
        }
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, LINE + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String stripNotSpaceHeadAndTail(String string) {
        string = string.trim();
        if (string.length() <= MathConstants.TWO) {
            return "";
        }
        string = string.substring(MathConstants.ONE, string.length() - MathConstants.ONE);
        return string;
    }

    public static boolean startOrEndWithMatchStringWithoutSpace(String string, String matchString) {
        string = string.trim();
        return string.startsWith(matchString) || string.endsWith(matchString);
    }
}
