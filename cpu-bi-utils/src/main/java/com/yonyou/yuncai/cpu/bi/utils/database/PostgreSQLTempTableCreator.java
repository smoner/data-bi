package com.yonyou.yuncai.cpu.bi.utils.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by fengjqc on 2017/4/8.
 */
public class PostgreSQLTempTableCreator implements TempTableCreator {
    private static final Logger logger =  LoggerFactory.getLogger(PostgreSQLTempTableCreator.class);
    private static Random rnd = new Random();

    public String createTempTable(Connection con, String TabName,
                                  String TabColumn, String... idx) throws SQLException {
        String sql;

        //因为临时表明需要配置到mybatis的配置文件中，这里不对表明做处理
//        TabName = TabName + "_" + Math.abs((long) rnd.nextInt());
        Statement stmt = null;
//        boolean sqlTrans_old = ((CrossDBConnection) con).isSQLTrans();
//        boolean addTimeStamp_old = ((CrossDBConnection) con).isAddTimeStamp();
        try {
//            ((CrossDBConnection) con).setSqlTrans(false);
//            ((CrossDBConnection) con).setAddTimeStamp(false);
            con.setAutoCommit(false);
            boolean b=con.getAutoCommit();
            stmt = con.createStatement();
            sql = "create  TEMP table " + TabName + "(" + TabColumn
                    + ") ON COMMIT drop";
            stmt.executeUpdate(sql);
            stmt.execute("select * from  Temp_cpu_bi_hisprice ");
            ResultSet resultSet = stmt.getResultSet();
            if(idx != null && idx.length > 0) {
                for (int i = 0; i < idx.length; i++) {
                    String IndColumn = idx[i];
                    if (IndColumn != null && IndColumn.trim().length() != 0) {
                        sql = "create index i_" + TabName  + "_" + i + " on " + TabName + "("
                                + IndColumn + ")";
                        stmt.executeUpdate(sql);
                    }
                }
            }
            return TabName;
        } catch (Exception e) {
            if (isTableExist(stmt, TabName)) {
                return TabName;
            } else {
                logger.error("HH First: create temporaty table error: "
                                + TabName, e);
                if (e instanceof SQLException) {
                    throw (SQLException) e;
                } else {
                    throw new SQLException(e);
                }
            }

        }finally {
//            ((CrossDBConnection) con).setSqlTrans(sqlTrans_old);
//            ((CrossDBConnection) con).setAddTimeStamp(addTimeStamp_old);
            if (stmt != null)
                stmt.close();
        }
    }

    private boolean isTableExist(Statement stmt, String table) {
        try {
            stmt.execute("delete from " + table);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
