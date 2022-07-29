package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/testDB";
    private static final String user = "root";
    private static final String password = "12345678";
    private static Connection connect;

    public static Connection getConnection() {
        try {
            connect = DriverManager.getConnection(url, user, password);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return connect;
    }

    public static void connectionClose() {
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
