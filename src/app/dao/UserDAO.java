package app.dao;

import app.common.DBManager;
import app.dto.UserDTO;

import java.sql.*;

public class UserDAO {
    public boolean addUser(UserDTO user) {
        String sql = "INSERT INTO user (name, phone_number, carrier_id) VALUES (?, ?, ?)";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setInt(3, user.getCarrierId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserDTO getUserByPhone(String phoneNumber) {
        String sql = "SELECT * FROM user WHERE phone_number = ?";
        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new UserDTO(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("carrier_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 사용자 없음
    }
}