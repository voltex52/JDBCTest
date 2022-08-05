package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/Users";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "voltex";
    public static Connection conn;

    public static void getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);
//            if (!conn.isClosed()) {
//                System.out.println("Соединение с БД установлено");
//            }
        } catch (SQLException e) {
            System.out.println("Соединение с БД не установлено");
        }
    }
}

