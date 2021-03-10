package cn.su.dao.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间转换为LocalDateTime
 */
@MappedJdbcTypes(value = {JdbcType.TIMESTAMP, JdbcType.DATE, JdbcType.TIME})
@MappedTypes(value = {Timestamp.class, Date.class, Time.class, java.util.Date.class})
public class DateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime localDateTime, JdbcType jdbcType)
            throws SQLException {
        if (JdbcType.TIMESTAMP.equals(jdbcType)) {
            ps.setTimestamp(i, localDateTime == null ? null : Timestamp.valueOf(localDateTime));
        } else if (JdbcType.DATE.equals(jdbcType)) {
            ps.setDate(i, localDateTime == null ? null : Date.valueOf(localDateTime.toLocalDate()));
        } else if (JdbcType.TIME.equals(jdbcType)) {
            ps.setTime(i, localDateTime == null ? null : Time.valueOf(localDateTime.toLocalTime()));
        }
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String s) throws SQLException {
        Object obj = rs.getObject(s);
        return dateToLocalDateTime(obj);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int i) throws SQLException {
        Object obj = rs.getObject(i);
        return dateToLocalDateTime(obj);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int i) throws SQLException {
        Object obj = cs.getObject(i);
        return dateToLocalDateTime(obj);
    }

    private static LocalDateTime dateToLocalDateTime(Object obj) {
        if (obj == null) {
            return null;
        }
        java.util.Date date = (java.util.Date) obj;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
