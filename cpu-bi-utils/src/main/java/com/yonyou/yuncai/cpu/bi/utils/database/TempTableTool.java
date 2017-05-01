package com.yonyou.yuncai.cpu.bi.utils.database;

import com.yonyou.yuncai.cpu.bi.utils.pub.AssertUtils;
import com.yonyou.yuncai.cpu.bi.utils.pub.JavaType;
import com.yonyou.yuncai.cpu.bi.utils.pub.SqlBuilder;
import com.yonyou.yuncai.cpu.bi.utils.pub.YCBiConnectionProxy;
import com.yonyou.yuncai.cpu.bi.utils.pub.exception.ExceptionUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by fengjqc on 2017/4/8.
 */
@Component
public class TempTableTool {
    private static final Logger logger = LoggerFactory.getLogger(TempTableTool.class);

    DataSource dataSource;

    public TempTableTool(DataSource dataSource){
        this.dataSource=dataSource;
    }
//    public Connection getConnection(){
//        Connection con= null;
//        try {
//            con = new YCBiConnectionProxy().getConnection();
//            String dbname = null;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            ExceptionUtils.wrappException(e);
//        }
//        return con;
//    }
    public Connection getConnection(){
        Connection con= null;
        try {
            con = new YCBiConnectionProxy().getConnection(this.dataSource);
            String dbname = null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            ExceptionUtils.wrappException(e);
        }
        return con;
    }

//    public void createTempTable( ) throws SQLException {
//        try {
//            Connection con = this.getConnection();
//            String dbname = null;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            ExceptionUtils.wrappException(e);
//        }
//    }

    /**
     * 创建临时表
     *
     * @param tablename 表名
     * @param columns 列名
     * @param columnTypes 列类型
     * @return 临时表名
     */
    public String getTempTable(String tablename, String[] columns,
                               String[] columnTypes) {
        return this.getTempTable(tablename, columns, columnTypes, null);
    }

    /**
     * 创建临时表并且插入数据
     *
     * @param tablename 表名
     * @param columns 列名
     * @param columnTypes 列类型
     * @param types 要插入临时表的数据的数据类型
     * @param data 要插入临时表的数据
     * @return 临时表名
     */
    public String getTempTable(String tablename, String[] columns,
                               String[] columnTypes, JavaType[] types, List<List<Object>> data) {
        this.validate(columns, columnTypes, types);

        String name = this.getTempTable(tablename, columns, columnTypes);
        if(name==null){
            ExceptionUtils.wrappException("创建临时表失败！临时表名："+tablename);
        }
        if (data.size() == 0) {
            return name;
        }

        this.insertData(columns, columnTypes, types, data, name);
        return name;
    }

    /**
     * 创建临时表
     *
     * @param tablename 表名
     * @param columns 列名
     * @param columnTypes 列类型
     * @param indexColumns 索引列名
     * @return 临时表名
     */
    public String getTempTable(String tablename, String[] columns,
                               String[] columnTypes, String[] indexColumns) {
        SqlBuilder sql = new SqlBuilder();
        int length = columns.length;
        for (int i = 0; i < length; i++) {
            sql.append(columns[i]);
            sql.append(" ");
            sql.append(columnTypes[i]);
            sql.append(",");
        }
        sql.append(" ts varchar(100)");

        String index = null;
        if (indexColumns != null) {
            SqlBuilder indexSql = new SqlBuilder();
            for (String column : indexColumns) {
                indexSql.append(column);
                indexSql.append(",");
            }
            indexSql.deleteLastChar();
            index = indexSql.toString();
        }
        String name = null;
        TempTable tempTable = new TempTable(dataSource);
        Connection connection = null;
        try {
            connection = this.getConnection();
            name = tempTable.createTempTable(connection, tablename, sql.toString(), index);
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        finally {
            if (connection != null) {
//                try {
//                    connection.close();
//                }
//                catch (SQLException ex) {
//                    logger.error(ex.getMessage());
//                }
            }
        }

        return name;
    }

    /**
     * 创建临时表并且插入数据
     *
     * @param tablename 表名
     * @param columns 列名
     * @param columnTypes 列类型
     * @param types 要插入临时表的数据的数据类型
     * @param data 要插入临时表的数据
     * @return 临时表名
     */
    public String getTempTable(String tablename, String[] columns,
                               String[] columnTypes, String[] indexColumns, JavaType[] types,
                               List<List<Object>> data,DataSource dataSource) {
        this.validate(columns, columnTypes, types);

        String name =
                this.getTempTable(tablename, columns, columnTypes, indexColumns);
        if (data.size() == 0) {
            return name;
        }

        this.insertData(columns, columnTypes, types, data, name);
        return name;
    }

    private void insertData(String[] columns, String[] columnTypes,
                            JavaType[] types, List<List<Object>> data, String name) {
        SqlBuilder sql = new SqlBuilder();
        sql.append(" insert into ");
        sql.append(name);
        sql.startParentheses();

        SqlBuilder valueSql = new SqlBuilder();
        int length = columns.length;
        for (int i = 0; i < length; i++) {
            sql.append(columns[i]);
            sql.append(",");

            valueSql.append("?,");
        }
        sql.deleteLastChar();
        valueSql.deleteLastChar();

        sql.endParentheses();

        sql.append(" values");
        sql.startParentheses();
        sql.append(valueSql.toString());
        sql.endParentheses();

        // 将空值转换为~
        this.processNullData(columnTypes, data);
        DataAccessUtils dataAccessUtils = new DataAccessUtils(dataSource);
        dataAccessUtils.update(sql.toString(), types, data);
    }

    private void processNullData(String[] columnTypes, List<List<Object>> data) {
        List<Integer> list = new ArrayList<Integer>();
        int length = columnTypes.length;
        for (int i = 0; i < length; i++) {
            if (columnTypes[i].equalsIgnoreCase("varchar(20)")
                    || columnTypes[i].equalsIgnoreCase("varchar(101)")
                    || columnTypes[i].equalsIgnoreCase("varchar(36)")) {
                list.add(Integer.valueOf(i));
            }
        }
        // 没有需要转换的列
        if (list.size() == 0) {
            return;
        }
        for (List<Object> row : data) {
            for (Integer index : list) {
                Object obj = row.get(index.intValue());
                if (obj == null) {
                    row.set(index.intValue(), "~");
                }
            }
        }
    }

    @SuppressWarnings("null")
    private void validate(String[] columns, String[] columnTypes, JavaType[] types) {
        AssertUtils.assertValue(columns != null, "");
        AssertUtils.assertValue(columnTypes != null, "");
        AssertUtils.assertValue(types != null, "");
        AssertUtils.assertValue(columns.length == types.length, "");
        AssertUtils.assertValue(columns.length == columnTypes.length, "");
    }

}
