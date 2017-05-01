package com.yonyou.yuncai.cpu.bi.utils.database;

import com.yonyou.yuncai.cpu.bi.utils.pub.JavaType;
import com.yonyou.yuncai.cpu.bi.utils.pub.ValueUtils;
import com.yonyou.yuncai.cpu.bi.utils.pub.YCBiConnectionProxy;
import com.yonyou.yuncai.cpu.bi.utils.pub.exception.ExceptionUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengjqc on 2017/4/8.
 */
@Component
public class DataAccessUtils {
    private static final Logger logger = LoggerFactory.getLogger(DataAccessUtils.class);

    /**
     * 从数据库中能够读取的最大纪录数。-1表示不限制纪录的读取
     */
    private static final int MAX_ROWS = -1;

    /**
     * sql语句更新数据库时是否自动增加时间戳
     */
    private boolean autoSetTS = true;

    /**
     * sql语句查询数据库时最大可返回的纪录行
     */
    private int maxRows = DataAccessUtils.MAX_ROWS;

    private DataSource dataSource;
    /**
     * 数据访问工具类默认构造函数
     */
    public DataAccessUtils(DataSource dataSource) {
        // 默认构造函数
        this.dataSource =dataSource;
    }
    public DataAccessUtils( ) {
        // 默认构造函数
    }

    /**
     * 设置更新数据库的时候是否自动在sql语句中刷新时间戳。为否时，更新数据库的sql不会自动拼写更新时间戳的语句
     *
     * @param autoSetTS 是否自动刷新时间戳
     */
    public DataAccessUtils(boolean autoSetTS,DataSource dataSource) {
        this.autoSetTS = autoSetTS;
    }

    /**
     * 根据传入的sql语句查询数据库，返回离线的数据集
     *
     * @param sql 查询sql语句
     * @return 离线的数据集
     */
    public IRowSet query(String sql) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Object[]> list = new ArrayList<Object[]>();
        int count = -1;
        int rowcount = 0;
        try {
            connection = new YCBiConnectionProxy().getConnection(dataSource);
            stmt = connection.createStatement();

            // 设置结果集
            if (this.maxRows> 0) {
                stmt.setMaxRows(this.maxRows);
            }else {
                stmt.setMaxRows(0);
            }

            rs = stmt.executeQuery(sql);
            count = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] rows = new Object[count];
                for (int i = 0; i < count; i++) {
                    rows[i] = rs.getObject(i + 1);
                }
                list.add(rows);
                rowcount++;
                if (this.maxRows != DataAccessUtils.MAX_ROWS
                        && rowcount >= this.maxRows) {
                    break;
                }
            }
        }
        catch (SQLException ex) {
            ExceptionUtils.wrappException(ex);
        }
        finally {
            this.closeDB(connection, stmt, rs);
        }
        int size = list.size();
        Object[][] data = new Object[size][count];
        data = list.toArray(data);
        IRowSet rowSet = new RowSet(data);
        return rowSet;
    }

    /**
     * 设置当前可以从数据库中返回的最大纪录数。-1表示不限制读取的数量
     *
     * @param maxRows 当前可以从数据库中返回的最大纪录数
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    /**
     * 更新数据库
     *
     * @param sql 更新数据库sql
     * @return 更新的纪录数
     */
    public int update(String sql,DataSource dataSource) {
        Connection connection = null;
        Statement stmt = null;
        int result = -1;
        try {
            connection = new YCBiConnectionProxy().getConnection(dataSource);
//            connection.setAddTimeStamp(this.autoSetTS);

            stmt = connection.createStatement();
            result = stmt.executeUpdate(sql);
        }
        catch (SQLException ex) {
            ExceptionUtils.wrappException(ex);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                }
//                catch (SQLException ex) {
//                    logger.error(ex.getMessage());
//                }
//            }
        }
        return result;
    }

    /**
     * 用参数sql语句更新数据库
     *
     * @param sql 参数化的更新sql语句
     * @param types 参数类型
     * @param data 参数值列表
     */
    public void update(String sql, JavaType[] types, List<List<Object>> data) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection =new YCBiConnectionProxy().getConnection(dataSource);
//            connection.setAddTimeStamp(this.autoSetTS);

            stmt = connection.prepareStatement(sql);
            this.boundleValue(stmt, types, data);
            stmt.executeBatch();
        }
        catch (SQLException ex) {
            ExceptionUtils.wrappException(ex);
        }
//        finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                }
//                catch (SQLException ex) {
//                    logger.error(ex.getMessage());
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                }
//                catch (SQLException ex) {
//                    logger.error(ex.getMessage());
//                }
//            }
//        }
    }

    private void boundleStringTypeValue(PreparedStatement stmt, int index,
                                        Object value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.CHAR);
        }
        else {
            String ret = ValueUtils.getString(value);
            stmt.setString(index, ret);
        }
    }

    private void boundleValue(PreparedStatement stmt, int index, JavaType type,
                              Object value) throws SQLException {
        if ((type == JavaType.Double) || (type == JavaType.BigDecimal)) {
            if (value == null) {
                stmt.setNull(index, Types.NUMERIC);
            }
            else {
                BigDecimal ret = ValueUtils.getBigDecimal(value);
                stmt.setBigDecimal(index, ret);
            }
        }
        else if (type == JavaType.String) {
            this.boundleStringTypeValue(stmt, index, value);
        }
        else if ((type == JavaType.Date) || (type == JavaType.DateTime)
                || (type == JavaType.Time) || (type == JavaType.LiteralDate)) {
            this.boundleStringTypeValue(stmt, index, value);
        }
        else if (type == JavaType.Integer) {
            if (value == null) {
                stmt.setNull(index, Types.INTEGER);
            }
            else {
                int ret = ValueUtils.getInt(value);
                stmt.setInt(index, ret);
            }
        }
        else if (type == JavaType.Boolean) {
            Boolean ret = ValueUtils.getBoolean(value);
            stmt.setString(index, ret.booleanValue() ? "Y" : "N");
        }
        else if (type == JavaType.Flag) {
            if (value == null) {
                stmt.setNull(index, Types.INTEGER);
            }
//            else {
//                MDEnum enumValue = (MDEnum) value;
//                int ret = ((Integer) enumValue.value()).intValue();
//                stmt.setInt(index, ret);
//            }
        }
        else if (type == JavaType.StringEnum) {
            if (value == null) {
                stmt.setNull(index, Types.CHAR);
            }
//            else {
//                MDEnum enumValue = (MDEnum) value;
//                String ret = (String) enumValue.value();
//                stmt.setString(index, ret);
//            }
        }
        else if (type == JavaType.Object) {
            if (value == null) {
                stmt.setNull(index, Types.BLOB);
            } else {
                //BLOB类型的JavaType转换为Object，数据比较大时，oracle会转换成LONG存储，超过LONG的范围会报错，
                //因此，判断Object类型数据是String时，转换为byte[]来进行存储，查询出来后需要再转为String
                //2014-02-20 gcwty mail
                if (value instanceof String) {
                    stmt.setBytes(index, ((String) value).getBytes());
                } else {
                    stmt.setObject(index, value);
                }
            }
        }
        else {
            ExceptionUtils.unSupported();
        }
    }

    private void boundleValue(PreparedStatement stmt, JavaType[] types,
                              List<List<Object>> data) {
        int length = types.length;
        try {
            for (List<Object> row : data) {
                for (int i = 0; i < length; i++) {
                    Object value = row.get(i);
                    this.boundleValue(stmt, i + 1, types[i], value);
                }
                stmt.addBatch();
            }
        }
        catch (SQLException ex) {
            ExceptionUtils.wrappException(ex);
        }
    }

    private void closeDB(Connection connection, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
//        if (connection != null) {
//            try {
//                connection.close();
//            }
//            catch (SQLException ex) {
//                logger.error(ex.getMessage());
//            }
//        }
    }


}
