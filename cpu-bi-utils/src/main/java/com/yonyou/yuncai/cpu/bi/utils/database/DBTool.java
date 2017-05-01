package com.yonyou.yuncai.cpu.bi.utils.database;

import com.yonyou.yuncai.cpu.bi.utils.pub.YCBiConnectionProxy;
import com.yonyou.yuncai.cpu.bi.utils.pub.exception.ExceptionUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by fengjqc on 2017/4/8.
 */
@Component
public class DBTool {
    private static final Logger logger = LoggerFactory.getLogger(DBTool.class);

    DataSource dataSource;
    public DBTool(DataSource dataSource){
        this.dataSource=dataSource;
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
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    public Connection getConnection() {
        Connection connection  = new YCBiConnectionProxy().getConnection(this.dataSource);;
        return connection;
    }

    /**
     * 获取数据源名称
     *
     * @return 数据源名称
     */
    public String getDataSourceName() {
//        String name = DataSourceCenter.getInstance().getSourceName();
        return "";
    }

    public static void closeStmt(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
        }
    }

    public static void closeRs(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (SQLException e) {
        }
    }

    public static void closeConnection(Connection con) {
//        try {
//            if (con != null) {
//                con.close();
//                con = null;
//            }
//        } catch (SQLException e) {
//        }
    }

}
