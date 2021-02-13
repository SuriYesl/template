package cn.su.dao.util;

import cn.su.core.constants.MathConstants;
import cn.su.core.constants.SqlConstants;
import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.SpringContextUtil;
import cn.su.core.util.StringUtil;
import cn.su.dao.entity.BaseBo;
import cn.su.dao.mapper.common.SqlCommonMapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin2.util.SystemUtil;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: su rui
 * @Date: 2021/1/28 09:44
 * @Description: sql
 */
public class SqlHelper<T> implements SqlInterface {
    private static final String UPDATE_TIME = "update_time";
    private static final String VALUE = "value";
    private final Class<T> clazz;
    private StringBuilder sqlSpell = new StringBuilder();
    private StringBuilder searchValues = new StringBuilder();
    private static final SqlCommonMapper commonMapper = SpringContextUtil.getBean(SqlCommonMapper.class);

    public SqlHelper(Class<T> clazz) {
        this.clazz = clazz;
        if (this.clazz == null) {
            throw new BusinessException("class object is null");
        }
    }

    private Map<String, String> getFieldMap() {
        List<String> fieldStrings = SqlSpellUtil.getClassFields(this.clazz);
        if (NormHandleUtil.isEmpty(fieldStrings)) {
            throw new BusinessException("value for selecting is empty");
        }
        return fieldStrings.parallelStream()
                .collect(Collectors.toMap(fieldString -> fieldString, fieldString -> StringUtil.humpToLine(fieldString)));
    }

    private String getSearchValues(Map<String, String> fieldMap) {
        for (Map.Entry<String, String> field : fieldMap.entrySet()) {
            searchValues.append(SqlConstants.FLOAT_POINT)
                    .append(field.getValue())
                    .append(SqlConstants.FLOAT_POINT)
                    .append(SqlConstants.SPACE)
                    .append(SqlConstants.AS)
                    .append(SqlConstants.SPACE)
                    .append(SqlConstants.FLOAT_POINT)
                    .append(field.getKey())
                    .append(SqlConstants.FLOAT_POINT)
                    .append(SqlConstants.COMMA);
        }
        searchValues.deleteCharAt(searchValues.length() - MathConstants.ONE);
        return searchValues.toString();
    }

    @Override
    public SqlInterface find() {
        String tableName = SqlSpellUtil.getClassTableName(this.clazz);
        Map<String, String> fieldMap = getFieldMap();
        String searchValues = getSearchValues(fieldMap);
        select();
        sqlSpell.append(searchValues).append(SqlConstants.SPACE);
        from();
        sqlSpell.append(tableName).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface count() {
        String tableName = SqlSpellUtil.getClassTableName(this.clazz);
        select();
        sqlSpell.append(SqlConstants.COUNT);
        from();
        sqlSpell.append(tableName).append(SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface select() {
        sqlSpell.append(SqlConstants.SELECT +SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface from() {
        sqlSpell.append(SqlConstants.FROM + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface where() {
        sqlSpell.append(SqlConstants.WHERE + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface eq(String field, String value) {
        sqlSpell.append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.EQUAL_SIGN)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public SqlInterface neq(String field, String value) {
        sqlSpell.append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.NOT_EQUAL_SIGN)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public SqlInterface lt(String field, String value) {
        sqlSpell.append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.LESS_THAN)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public SqlInterface gt(String field, String value) {
        sqlSpell.append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.GREATER_THAN)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public SqlInterface ltAndEqual(String field, String value) {
        sqlSpell.append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.LESS_THAN_AND_EQUAL)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public SqlInterface gtAndEqual(String field, String value) {
        sqlSpell.append(SqlConstants.FLOAT_POINT)
                .append(field)
                .append(SqlConstants.FLOAT_POINT)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.GREATER_THAN_AND_EQUAL)
                .append(SqlConstants.SPACE)
                .append(SqlConstants.SINGLE_QUOTED)
                .append(value)
                .append(SqlConstants.SINGLE_QUOTED);
        return this;
    }

    @Override
    public SqlInterface and() {
        sqlSpell.append(SqlConstants.AND + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface or() {
        sqlSpell.append(SqlConstants.OR + SqlConstants.SPACE);
        return this;
    }

    private String getDynamicFieldString(String... fields) {
        StringBuilder fieldSb = new StringBuilder();
        for (String field : fields) {
            fieldSb.append(field).append(", ");
        }

        if (fieldSb.length() > 0) {
            fieldSb.delete(fieldSb.length() - MathConstants.ONE, fieldSb.length());
        }
        return fieldSb.toString();
    }

    @Override
    public SqlInterface orderBy(String... fields) {
        sqlSpell.append(SqlConstants.ORDER_BY + SqlConstants.SPACE).append(getDynamicFieldString(fields));
        return this;
    }

    @Override
    public SqlInterface groupBy(String... fields) {
        sqlSpell.append(SqlConstants.GROUP_BY + SqlConstants.SPACE).append(getDynamicFieldString(fields));
        return this;
    }

    @Override
    public SqlInterface limit(Integer startRow, Integer size) {
        sqlSpell.append(SqlConstants.LIMIT + SqlConstants.SPACE)
                .append(null == startRow ? 0 : startRow)
                .append(SqlConstants.COMMA)
                .append(SqlConstants.SPACE)
                .append(null == size ? Integer.MAX_VALUE : size);
        return this;
    }

    @Override
    public SqlInterface leftJoin() {
//        sqlSpell.append(SqlConstants.FROM + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface on() {
        sqlSpell.append(SqlConstants.ON + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface underLine() {
        sqlSpell.append(SqlConstants.UNDER_LINE);
        return this;
    }

    @Override
    public SqlInterface comma() {
        sqlSpell.append(SqlConstants.COMMA);
        return this;
    }

    @Override
    public SqlInterface floatPoint() {
        sqlSpell.append(SqlConstants.FLOAT_POINT);
        return this;
    }

    @Override
    public <T extends BaseBo> void update(T data) {
        if (!sqlSpell.toString().contains(SqlConstants.WHERE)) {
            throw new BusinessException("no condition for update, it is not allowed");
        }
        String tableName = SqlSpellUtil.getClassTableName(this.clazz);
        Field[] fields = SqlSpellUtil.getClassFieldArray(this.clazz);
        StringBuilder column = new StringBuilder();
        Map<String, Object> updateParam = new LinkedHashMap<>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (!SqlConstants.SERIAL_VERSION_UID.equals(field.getName())) {
                    Object object = field.get(data);
                    String columnName = StringUtil.humpToLine(field.getName());
                    if (object == null) {
                        if (UPDATE_TIME.equals(columnName)) {
                            object = LocalDateTime.now();
                        }
                    }
                    columnName = SqlConstants.FLOAT_POINT + columnName + SqlConstants.FLOAT_POINT;
                    column.append(columnName).append(" = ").append("#{").append(columnName).append("},");
                    updateParam.put(columnName, object);
                }
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            }
            throw new BusinessException("DaoHelper, method update exception:", e);
        }

        column.replace(column.length() - 1, column.length(), "");
        column.append(SqlConstants.SPACE).append(sqlSpell.toString());

        String sql = "update " + tableName + " set " + column;
        updateParam.put(VALUE, sql);
        try {
            Integer updateFlag = commonMapper.superCommonUpdate(updateParam);
            if (updateFlag != 1) {
                throw new BusinessException("The network is busy, please refresh the page");
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException("DaoHelper, method update exception:", e);
            }
        }
    }

    private <T> T getObjectByMap(Map<String, Object> oneResultMap) {
        T objectBo;
        try {
            objectBo = (T) this.clazz.newInstance();
            Field[] fields = SqlSpellUtil.getClassFieldArray(this.clazz);
            for (Field field : fields) {
                field.setAccessible(true);
                if (!SqlConstants.SERIAL_VERSION_UID.equals(field.getName())) {
                    if (oneResultMap.containsKey(field.getName())) {
                        field.set(objectBo, oneResultMap.get(field.getName()));
                    } else if (oneResultMap.containsKey(StringUtil.humpToLine(field.getName()))){
                        field.set(objectBo, oneResultMap.get(StringUtil.humpToLine(field.getName())));
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException("SqlHelper, method forOneResult exception:", e);
        }
        return objectBo;
    }

    @Override
    public <T extends BaseBo> T forOneResult() {
        List<Map<String, Object>> list = commonMapper.getTargetBySql(sqlSpell.toString());
        if (NormHandleUtil.isEmpty(list)) {
            return null;
        }
        if(list.size() > MathConstants.ONE){
            throw new TooManyResultsException("search for one result but found" + list.size());
        }
        return getObjectByMap(list.get(0));
    }

    @Override
    public List<T> forResultList() {
        List<Map<String, Object>> list = commonMapper.getTargetBySql(sqlSpell.toString());
        List<T> boList = new LinkedList<>();
        if (!NormHandleUtil.isEmpty(list)) {
            try {
                for (Map<String, Object> map : list) {
                    T objectBo = getObjectByMap(map);
                    boList.add(objectBo);
                }
            } catch (Exception e) {
                throw new BusinessException("DaoHelper, method queryForList exception:", e);
            }
        }
        return boList;
    }

    private void fillInsertFieldAndValues(Map<String, String> fieldMap, List<String> insertFields, List<String> fieldValues) {
        for (Map.Entry<String, String> field : fieldMap.entrySet()) {
            insertFields.add(SqlConstants.FLOAT_POINT + field.getValue() + SqlConstants.FLOAT_POINT);
            fieldValues.add(SqlConstants.HASH + SqlConstants.LEFT_BRACE + SqlConstants.ITEM_POINT + field.getKey() + SqlConstants.RIGHT_BRACE);
        }
    }

    private void doInsert(List<T> dataList) {
        String tableName = SqlSpellUtil.getClassTableName(this.clazz);
        Map<String, String> fieldMap = SqlSpellUtil.getClassFields(this.clazz).parallelStream().collect(
                Collectors.toMap(field -> field, StringUtil::humpToLine));
        List<String> insertFields = new LinkedList<>();
        List<String> fieldValues = new LinkedList<>();
        fillInsertFieldAndValues(fieldMap, insertFields, fieldValues);
        String insertFieldString = StringUtil.stripNotSpaceHeadAndTail(insertFields.toString());
        String fieldValueString = StringUtil.stripNotSpaceHeadAndTail(fieldValues.toString());
        System.out.println("INSERT INTO " + tableName + " (" + insertFieldString + ")" + " VALUES" + "(" + fieldValueString + ")");
        commonMapper.insertTargetListBySql(tableName, insertFieldString, fieldValueString, dataList);
    }

    public T insert(T data) {
        if (null == data) {
            throw new BusinessException("data is null");
        }
        List<T> dataList = new LinkedList<>();
        dataList.add(data);
        doInsert(dataList);
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<T> dataList) {
        if (NormHandleUtil.isEmpty(dataList)) {
            throw new BusinessException("insert data list is empty");
        }
        doInsert(dataList);
    }
}
