package cn.su.service.common.impl;

import cn.su.core.util.ClassUtil;
import cn.su.core.util.NormHandleUtil;
import cn.su.dao.util.SqlSpellUtil;
import cn.su.service.common.CommonSqlService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: su rui
 * @Date: 2021/1/26 14:52
 * @Description: 公用SQL服务实现
 */
@Service
public class CommonSqlServiceImpl implements CommonSqlService {
    private <T> List<String> searchFieldValues(List<String> values, Class<T> clazz) {
        if (!NormHandleUtil.isEmpty(values)) {
            return values;
        }
        return ClassUtil.getClassFields(clazz);
    }

    @Override
    public <T> T searchTarget(Class<T> clazz, List<String> values, Map<String, Object> params) {
        String tableName = SqlSpellUtil.getClassTableName(clazz);
        values = searchFieldValues(values, clazz);
        return null;
    }
}
