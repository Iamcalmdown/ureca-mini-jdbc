package app.common;

import java.sql.*;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3306/mobile";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // 데이터베이스 연결
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void releaseConnection(PreparedStatement pstmt, Connection connection) {
        releaseConnection(null, pstmt, connection);
    }

    // ResultSet, PreparedStatement, Connection 해제 (중복 제거)
    public static void releaseConnection(ResultSet resultSet, PreparedStatement pstmt, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}