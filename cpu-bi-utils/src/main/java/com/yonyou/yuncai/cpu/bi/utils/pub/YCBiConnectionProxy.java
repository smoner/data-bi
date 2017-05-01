package com.yonyou.yuncai.cpu.bi.utils.pub;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;

/**
 * Created by fengjqc on 2017/4/15.
 */
public class YCBiConnectionProxy {
    public Connection getConnection( DataSource dataSource){
        Connection connection = DataSourceUtils.getConnection(dataSource);
        return connection;
    }
}
