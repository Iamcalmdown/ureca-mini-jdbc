package app.dao;

import app.common.DBManager;
import app.dto.UserDTO;

import java.sql.*;

public class UserDAO {
    public boolean addUser(UserDTO user) {
        String sql = "INSERT INTO user (name, phone_number, carrier_id) VALUES (?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setInt(3, user.getCarrierId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ 추가된 부분: 전화번호로 userId 가져오는 메서드
    public int getUserIdByPhone(String phoneNumber) {
        String sql = "SELECT user_id FROM user WHERE phone_number = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id"); // user_id 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 사용자 없음
    }
}