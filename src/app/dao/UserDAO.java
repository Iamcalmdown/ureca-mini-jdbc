package app.dao;


import app.common.DBManager;
import app.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean addUser(UserDTO user) {
        String sql = "INSERT INTO user (name, phone_number, carrier_id) VALUES (?, ?, ?)";
        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setInt(3, user.getCarrierId());

            int result = pstmt.executeUpdate();
            return result > 0; // 성공 시 true 반환

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 실패 시 false 반환
        }
    }

    public int getUserIdByPhoneNumber(String phoneNumber) {
        String sql = "SELECT user_id FROM user WHERE phone_number = ?";
        int userId = -1;

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, phoneNumber);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
}

