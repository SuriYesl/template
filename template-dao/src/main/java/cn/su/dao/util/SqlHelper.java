package cn.su.dao.util;

import cn.su.core.constants.MathConstants;
import cn.su.core.constants.SqlConstants;
import cn.su.core.exception.BusinessException;
import cn.su.core.util.NormHandleUtil;
import cn.su.core.util.SpringContextUtil;
import cn.su.core.util.StringUtil;
import cn.su.dao.mapper.common.SqlMapper;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @AUTHOR: sr
 * @DATE: Create In 22:48 2021/1/27 0027
 * @DESCRIPTION: sql工具
 */
public class SqlHelper<T> implements SqlInterface {
    private Class<T> clazz;
    private StringBuilder sqlSpell = new StringBuilder();
    private StringBuilder searchFields = new StringBuilder();
    private static final SqlMapper sqlMapper = SpringContextUtil.getBean(SqlMapper.class);

    public SqlHelper(Class<T> clazz) {
        this.clazz = clazz;
        if (this.clazz == null) {
            throw new BusinessException("class object is null");
        }
    }

    private Map<String, String> getFieldMap() {
        List<String> fieldStrings = SqlSpellUtil.getClassFields(clazz);
        if (NormHandleUtil.isEmpty(fieldStrings)) {
            throw new BusinessException("search for null");
        }
        Map<String, String> fieldMap = fieldStrings.parallelStream()
                .collect(Collectors.toMap(fieldString -> fieldString, fieldString -> StringUtil.humpToLine(fieldString)));
        return fieldMap;
    }

    private String getSearchValues(Map<String, String> fieldMap) {
        for (Map.Entry<String, String> field : fieldMap.entrySet()) {
            searchFields.append(field.getValue())
                    .append(SqlConstants.SPACE)
                    .append(SqlConstants.AS)
                    .append(SqlConstants.SPACE)
                    .append(field.getKey())
                    .append(SqlConstants.COMMA);
        }
        searchFields.deleteCharAt(searchFields.length() - MathConstants.ONE);
        return searchFields.toString();
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
    public SqlInterface and() {
        sqlSpell.append(SqlConstants.AND + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface or() {
        sqlSpell.append(SqlConstants.OR + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface orderBy() {
        sqlSpell.append(SqlConstants.ORDER_BY + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface groupBy() {
        sqlSpell.append(SqlConstants.GROUP_BY + SqlConstants.SPACE);
        return this;
    }

    @Override
    public SqlInterface limit() {
        sqlSpell.append(SqlConstants.LIMIT + SqlConstants.SPACE);
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
    public <T> T forOneResult() {
        List<Map<String, Object>> list = sqlMapper.search(sqlSpell.toString());
        if (NormHandleUtil.isEmpty(list)) {
            return null;
        }
        if(list.size() > MathConstants.ONE){
            throw new TooManyResultsException("search for one result but found" + list.size());
        }
        T objectBo;
        try {
            objectBo = (T) this.clazz.newInstance();
            Field[] fields = SqlSpellUtil.getClassFieldArray(this.clazz);
            for (Field field : fields) {
                if (!SqlConstants.SERIAL_VERSION_UID.equals(field.getName())) {
                    if (list.get(0).containsKey(field.getName())) {
                        field.set(objectBo, list.get(0).get(field.getName()));
                    } else if (list.get(0).containsKey(StringUtil.humpToLine(field.getName()))){
                        field.set(objectBo, list.get(0).get(StringUtil.humpToLine(field.getName())));
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException("SqlHelper, method forOneResult exception:", e);
        }
        return objectBo;
    }

    @Override
    public <T> List<T> forResultList() {
        return null;
    }

    @Override
    public Integer forCount() {
        return null;
    }

    @Override
    public SqlInterface underLine() {
        return this;
    }

    @Override
    public SqlInterface comma() {
        return this;
    }

    @Override
    public SqlInterface floatPoint() {
        return this;
    }

    @Override
    public SqlInterface space() {
        return this;
    }

    @Override
    public SqlInterface semicolon() {
        return this;
    }

    @Override
    public SqlInterface asterisk() {
        return this;
    }
}
