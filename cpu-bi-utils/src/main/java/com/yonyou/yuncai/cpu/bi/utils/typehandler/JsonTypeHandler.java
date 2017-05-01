package com.yonyou.yuncai.cpu.bi.utils.typehandler;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fengjqc on 2017/4/19.
 */
// 继承自BaseTypeHandler<Object> 使用Object是为了让JsonUtil可以处理任意类型
public class JsonTypeHandler extends BaseTypeHandler<Map<String, Object>> {
    private static final Logger logger = LoggerFactory.getLogger(JsonTypeHandler.class);
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter,
                                    JdbcType jdbcType) throws SQLException {
        ps.setObject(i,new JSONObject(parameter));
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        Map<String, Object> jsonObject = this.parse(rs.getObject(columnName));
        return jsonObject;
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Map<String, Object> jsonObject = this.parse(rs.getObject(columnIndex));
        return jsonObject;
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        Map<String, Object> jsonObject = this.parse(cs.getObject(columnIndex));
        return jsonObject;
    }

    public Map<String, Object> parse(Object json) {

        if (json == null || json.toString()==null||json.toString().length() == 0) {
            return null;
        }

        try {
            return  new JSONObject(((PGobject)json).getValue()).toMap();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

}