package com.yonyou.yuncai.cpu.bi.utils.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by fengjqc on 2017/4/8.
 */
public interface TempTableCreator {
    public	String createTempTable(Connection con, String tableName, String columns, String... indexs) throws SQLException;
}
