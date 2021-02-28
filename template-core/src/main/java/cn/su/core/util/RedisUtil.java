package cn.su.core.util;

import cn.su.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Title: RedisUtil.java
 * @Description: Redis 访问操作工具类
 * @author: LongTeng
 * @email:
 * @date: 2019年12月21日 下午3:22:29
 * @version: 1.0
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Long SUCCESS = 1L;

    /**
     * @param key
     * @Author: LongTeng
     * @Description: 获取字符串
     * @Date: 2020/1/21
     * @return: java.lang.String
     **/
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void saveString(String key, String val) {
        redisTemplate.opsForValue().set(key, val);
    }

    /**
     * @param key
     * @param val
     * @param seconds 秒
     * @Author LongTeng
     * @Description TODO
     * @Date 2020/5/20 17:18
     * @Return void
     */
    public void saveString(String key, String val, int seconds) {
        redisTemplate.opsForValue().set(key, val, seconds, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param object
     * @param seconds 秒
     * @Author: LongTeng
     * @Description: 保存对象数据到缓存（并设置失效时间）
     * @Date: 2020/1/20
     * @return: void
     **/
    public <T> void saveObject(String key, T object, int seconds) {
        redisTemplate.opsForValue().set(key, object, seconds, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @Author: LongTeng
     * @Description: 获取对象
     * @Date: 2020/1/20
     * @return: java.lang.Object
     **/
    public <T> T getObject(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key
     * @Author: LongTeng
     * @Description: 判断是否缓存了数据
     * @Date: 2020/1/20
     * @return: boolean
     **/
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @Author: LongTeng
     * @Description: 从缓存中删除数据
     * @Date: 2020/1/20
     * @return: void
     **/
    public void delKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * @param keys
     * @Author LongTeng
     * @Description TODO 批量删除key
     * @Date 2020/6/14 9:15
     * @Return void
     */
    public void delKeys(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * @param key
     * @param seconds 秒
     * @Author: LongTeng
     * @Description: 设置超时时间
     * @Date: 2020/1/20
     * @return: void
     **/
    public void expire(String key, int seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    public <T> T lpop(String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
        return redisTemplate.execute(script, keys, args);
    }

    /**
     * @param pattern
     * @Author LongTeng
     * @Description TODO 回去复合pattern正则的keys
     * @Date 2020/6/13 21:50
     * @Return Set<String>
     */
    public Set<String> scan(String pattern, int limit) {
        long finalLimit = limit <= 0 ? 1000 : limit;
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            try (Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder()
                    .match(pattern)
                    .count(finalLimit)
                    .build())) {
                while (cursor.hasNext()) {
                    keysTmp.add(new String(cursor.next(), StandardCharsets.UTF_8));
                }
            } catch (Exception e) {
                throw new BusinessException(e);
            }
            return keysTmp;
        });
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
