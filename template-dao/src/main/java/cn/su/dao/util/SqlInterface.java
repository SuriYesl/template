package cn.su.dao.util;


import cn.su.dao.entity.common.BaseBo;

import java.util.List;

/**
 * @Author: su rui
 * @Date: 2021/1/28 10:21
 * @Description: sql
 */
public interface SqlInterface {
    SqlInterface find();
    SqlInterface count();
    SqlInterface select();
    SqlInterface from();
    SqlInterface where();
    SqlInterface eq(String field, String value);
    SqlInterface neq(String field, String value);
    SqlInterface lt(String field, String value);
    SqlInterface gt(String field, String value);
    SqlInterface ltAndEqual(String field, String value);
    SqlInterface gtAndEqual(String field, String value);
    SqlInterface and();
    SqlInterface or();
    SqlInterface orderBy(String... fields);
    SqlInterface groupBy(String... fields);
    SqlInterface limit(Integer startRow, Integer size);
    SqlInterface leftJoin();
    SqlInterface on();
    SqlInterface underLine();
    SqlInterface comma();
    SqlInterface floatPoint();
    <T extends BaseBo> T forOneResult();
    <T extends BaseBo> List<T> forResultList();
    <T extends BaseBo> void update(T data);
//    <T extends BaseBo> T insert(T data);
//    <T extends BaseBo> void insertBatch(List<T> dataList);
}
