package cn.su.core.util;


import cn.su.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.util.regex.Pattern;

public class SystemUtil {
    private final static Logger logger = LoggerFactory.getLogger(SystemUtil.class);

    public static String CURRENT_PROFILE = null;
    public static final Runtime RUNTIME = Runtime.getRuntime();
    public static final String PID;
    private final static Pattern linePattern = Pattern.compile("_(\\w)");
    private final static Pattern humpPattern = Pattern.compile("[A-Z]");
    private final static String X_Real_IP = "X-Real-IP";
    private final static String X_Forwarded_For = "X-Forwarded-For";
    private final static String unKnown = "unKnown";
    private final static String Proxy_Client_IP = "Proxy-Client-IP";
    private final static String WL_Proxy_Client_IP = "WL-Proxy-Client-IP";
    private final static String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    private final static String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    private final static String comma = ",";
    private final static String LINE = "_";

    static {
        PID = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    private static final SystemUtil instance = new SystemUtil();

    public SystemUtil getInstance() {
        return instance;
    }

    public static void sleepThread(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new BusinessException(e);
        }
    }

    public static String getIpAddress(ServletRequest servletRequest) {
        HttpServletRequest request;
        if (servletRequest == null) {
            request = getRequest();
        } else {
            request = (HttpServletRequest) servletRequest;
        }
        String ip;
        try {
            // 获取用户真实地址
            String realIp = request.getHeader(X_Real_IP);
            // 获取多次代理后的IP字符串值
            String xFor = request.getHeader(X_Forwarded_For);
            if (!NormHandleUtil.isEmpty(xFor) && !unKnown.equalsIgnoreCase(xFor)) {
                // 多次反向代理后会有多个IP值，第一个是用户真实的IP地址
                int index = xFor.indexOf(comma);
                if (index >= 0) {
                    return xFor.substring(0, index);
                } else {
                    return xFor;
                }
            }
            ip = realIp;
            if (NormHandleUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getHeader(Proxy_Client_IP);
            }
            if (NormHandleUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getHeader(WL_Proxy_Client_IP);
            }
            if (NormHandleUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getHeader(HTTP_CLIENT_IP);
            }
            if (NormHandleUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getHeader(HTTP_X_FORWARDED_FOR);
            }
            if (NormHandleUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return ip;
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static void setProfile(String profile) {
        CURRENT_PROFILE = profile;
    }

    public static String getProfile() {
        return CURRENT_PROFILE;
    }
}
