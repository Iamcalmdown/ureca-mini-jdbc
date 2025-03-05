package app.dao;


import app.common.DBManager;
import app.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
    public void addUser(UserDTO user) {
        String sql = "INSERT INTO users (name, phone_number, carrier_id) VALUES (?, ?, ?)";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setInt(3, user.getCarrierId());

            pstmt.executeUpdate();
            System.out.println("사용자 추가 성공: " + user.getName());
        } catch (SQLException e) {
            System.out.println("사용자 추가 실패!");
            e.printStackTrace();
        }
    }
}