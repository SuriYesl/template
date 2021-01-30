package cn.su.dao.entity;

import cn.su.dao.util.SqlHelper;
import org.apache.poi.ss.formula.functions.T;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:40 2021/1/28 0028
 * @DESCRIPTION: 基础实体类
 */
public class BaseBo {
    public SqlHelper getSqlHelper(Class<T> clazz) {
        return new SqlHelper<>(clazz);
    }
}
