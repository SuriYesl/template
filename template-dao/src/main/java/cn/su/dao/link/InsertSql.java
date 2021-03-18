package cn.su.dao.link;

import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.StringUtil;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: su rui
 * @Date: 2021/3/15 14:19
 * @Description: 插入工具
 */
public class InsertSql<T, E> implements InsertSqlInterface<T, E> {
    private StringBuilder sqlBuilder;
    public Map<String, Object> fieldValueMap;
    private SqlExecuteInterface<T> sqlExecuteInterface;

    public InsertSql() {
        sqlBuilder = new StringBuilder();
        fieldValueMap = new HashMap<>();
        sqlExecuteInterface = new SqlExecute<>();
        sqlBuilder.append(SqlConstants.INSERT).append(SqlConstants.SPACE);
    }

    private List<String> pieceInsertSql(int type, T data) {
        List<String> strings = new ArrayList<>();
        try {
            Class<T> clazz = (Class<T>) data.getClass();
            Field[] fields = SqlBuildHelper.getClassFieldArray(clazz);
            sqlBuilder.append(SqlConstants.LEFT_PARENTHESES);
            StringBuilder valueBuilder = new StringBuilder();
            for (Field field : fields) {
                sqlBuilder.append(SqlConstants.FLOAT_POINT).append(StringUtil.humpToLine(field.getName())).append(SqlConstants.FLOAT_POINT)
                        .append(SqlConstants.COMMA);
                if (type == 0) {
                    valueBuilder.append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE)
                            .append(field.getName()).append(SqlConstants.RIGHT_BRACE)
                            .append(SqlConstants.COMMA);
                    field.setAccessible(true);
                    fieldValueMap.put(field.getName(), field.get(data));
                } else {
                    valueBuilder.append(SqlConstants.HASH).append(SqlConstants.LEFT_BRACE).append(SqlConstants.ITEM_POINT)
                            .append(field.getName()).append(SqlConstants.RIGHT_BRACE)
                            .append(SqlConstants.COMMA);
                }
            }
            valueBuilder.delete(valueBuilder.length() - 1, valueBuilder.length());
            sqlBuilder.delete(sqlBuilder.length() - 1, sqlBuilder.length())
                    .append(SqlConstants.RIGHT_PARENTHESES).append(SqlConstants.SPACE)
                    .append(SqlConstants.VALUES).append(SqlConstants.SPACE);
            strings.add(sqlBuilder.toString());
            strings.add(valueBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取插入数据值出错", e);
        }
        return strings;
    }

    @Override
    public InsertSqlInterface<T, E> into(String tableName) {
        sqlBuilder.append(SqlConstants.INTO).append(SqlConstants.SPACE).append(tableName).append(SqlConstants.SPACE);
        return this;
    }

    private void preInsert(T data) {
        if (null == data) throw new NullPointerException("插入数据为空");
    }

    @Override
    public void obj(T data) {
        preInsert(data);
        List<T> dataList = new LinkedList<>();
        dataList.add(data);
        batch(dataList);
    }

    @Override
    public Integer objBackWithId(T data) {
        preInsert(data);
        List<String> strings = pieceInsertSql(0, data);
        String sql = strings.get(0) + SqlConstants.LEFT_PARENTHESES + strings.get(1) + SqlConstants.RIGHT_PARENTHESES;
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("value", sql);
        param.putAll(fieldValueMap);
        sqlExecuteInterface.insertBackId(param);
        return Integer.valueOf(param.get("id").toString());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batch(List<T> dataList) {
        if (NormHandleUtil.isEmpty(dataList)) throw new NullPointerException("插入数据为空");
        List<String> strings = pieceInsertSql(1, dataList.get(0));
        sqlExecuteInterface.insertBatch(strings.get(0), strings.get(1), dataList);
    }
}
