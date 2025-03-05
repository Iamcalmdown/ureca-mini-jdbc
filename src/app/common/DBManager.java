package app.common;

import java.sql.*;

public class DBManager {
    static String url = "jdbc:mysql://localhost:3306/mobile";
    static String username = "root";
    static String password = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("연결 성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void releaseConnection(PreparedStatement pstmt, Connection connection) {
        try {
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void releaseConnection(ResultSet resultSet, PreparedStatement pstmt, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
