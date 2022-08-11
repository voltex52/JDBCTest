package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/Users";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "voltex";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn;

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);
            if (!conn.isClosed()) {
                System.out.println("Соединение с БД установлено");
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Соединение с БД не установлено");
        }
        return conn;

    }
}

