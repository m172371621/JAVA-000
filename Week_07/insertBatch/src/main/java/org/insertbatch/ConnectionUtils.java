package org.insertbatch;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接工具
 */
public class ConnectionUtils {

    private final static String classname = "com.mysql.jdbc.Driver";
    private final static String url = "jdbc:mysql://127.0.0.1:3306/test?useSSL=false";
    private final static String username = "root";
    private final static String password = "123456";

    private static Connection connection;

    public static Connection getConnection() throws Exception {
        Class.forName(classname);
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
