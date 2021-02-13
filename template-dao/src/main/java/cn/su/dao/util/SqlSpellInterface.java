package cn.su.dao.util;

import java.util.List;

/**
 * @AUTHOR: sr
 * @DATE: Create In 13:20 2021/2/13 0013
 * @DESCRIPTION: sql拼写接口
 */
public interface SqlSpellInterface extends SqlInterface {
    SqlSpellInterface setSearchFields(List<String> fields);
    SqlSpellInterface setPrimaryTableName();
    SqlSpellInterface setWhereCondition();
    SqlSpellInterface setConnectionType();
    SqlSpellInterface addRelationTableName();
    SqlSpellInterface setOnCondition();
    SqlSpellInterface setInsertFields();
    SqlSpellInterface setUpdateFields();
    SqlSpellInterface delete();
}
