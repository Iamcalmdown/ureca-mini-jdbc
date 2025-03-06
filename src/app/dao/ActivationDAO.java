package app.dao;

import app.common.DBManager;
import app.dto.ActivationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivationDAO {
    // 📌 개통 등록
    public void activateUser(int userId, int phoneId) {
        String sql = "INSERT INTO activation (user_id, phone_id, activation_date) VALUES (?, ?, NOW())";

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, phoneId);
            pstmt.executeUpdate();

            // 📉 개통 후 재고 감소
            new PhoneDAO().decreaseStock(phoneId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 📌 개통 내역 조회
    public List<ActivationDTO> getActivationHistory() {
        List<ActivationDTO> activations = new ArrayList<>();
        String sql = """
                SELECT a.activation_id, u.name, u.phone_number, p.model_name, c.carrier_name, a.activation_date
                FROM activation a
                JOIN user u ON a.user_id = u.user_id
                JOIN phone p ON a.phone_id = p.phone_id
                JOIN carrier c ON p.carrier_id = c.carrier_id
                ORDER BY a.activation_date DESC
                """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                activations.add(new ActivationDTO(
                        rs.getInt("activation_id"),
                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getString("model_name"),
                        rs.getString("carrier_name"),
                        rs.getTimestamp("activation_date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activations;
    }

    // 📌 개통 취소 (내역 삭제 + 재고 복구)
    public void cancelActivation(int activationId, int phoneId) {
        String sql = "DELETE FROM activation WHERE activation_id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, activationId);
            pstmt.executeUpdate();

            // 🔄 개통 취소 후 재고 증가
            new PhoneDAO().increaseStock(phoneId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}