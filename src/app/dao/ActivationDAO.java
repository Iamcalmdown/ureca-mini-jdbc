package app.dao;

import app.common.DBManager;
import app.dto.ActivationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivationDAO {
    // 개통 등록
    public void activateUser(int userId, int phoneId) {
        String getUserCarrierSql = "SELECT carrier_id FROM user WHERE user_id = ?";
        String getPhoneCarrierSql = "SELECT carrier_id FROM phone WHERE phone_id = ?";
        String insertActivationSql = """
                INSERT INTO activation (user_id, phone_id, previous_carrier_id, new_carrier_id, activation_date) 
                VALUES (?, ?, ?, ?, NOW())
                """;
        String updateUserCarrierSql = "UPDATE user SET carrier_id = ? WHERE user_id = ?";

        try (Connection connection = DBManager.getConnection()) {
            int previousCarrierId = -1;
            int newCarrierId = -1;

            // 🔹 기존 사용자 통신사 가져오기
            try (PreparedStatement pstmt = connection.prepareStatement(getUserCarrierSql)) {
                pstmt.setInt(1, userId);
                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    previousCarrierId = resultSet.getInt("carrier_id");
                }
            }

            // 개통할 기기의 통신사 가져오기
            try (PreparedStatement pstmt = connection.prepareStatement(getPhoneCarrierSql)) {
                pstmt.setInt(1, phoneId);
                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    newCarrierId = resultSet.getInt("carrier_id");
                }
            }

            // 개통 정보 추가
            try (PreparedStatement pstmt = connection.prepareStatement(insertActivationSql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, phoneId);
                pstmt.setInt(3, previousCarrierId);
                pstmt.setInt(4, newCarrierId);
                pstmt.executeUpdate();
            }

            // 사용자 테이블 통신사 업데이트
            try (PreparedStatement pstmt = connection.prepareStatement(updateUserCarrierSql)) {
                pstmt.setInt(1, newCarrierId);
                pstmt.setInt(2, userId);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 개통 내역 조회에서 user_id와 phone_id를 추가하여 가져오기
    public List<ActivationDTO> getActivationHistory() {
        List<ActivationDTO> activations = new ArrayList<>();
        String sql = """
                SELECT a.activation_id, u.user_id, u.name, u.phone_number, p.phone_id, p.model_name, 
                       c1.carrier_name AS previous_carrier, c2.carrier_name AS new_carrier, a.activation_date
                FROM activation a
                JOIN user u ON a.user_id = u.user_id
                JOIN phone p ON a.phone_id = p.phone_id
                JOIN carrier c1 ON a.previous_carrier_id = c1.carrier_id
                JOIN carrier c2 ON a.new_carrier_id = c2.carrier_id
                ORDER BY a.activation_date DESC
                """;

        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                activations.add(new ActivationDTO(
                        resultSet.getInt("activation_id"),
                        resultSet.getInt("user_id"), // ✅ user_id 추가
                        resultSet.getString("name"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("phone_id"), // ✅ phone_id 추가
                        resultSet.getString("model_name"),
                        resultSet.getString("previous_carrier"), // 기존 통신사
                        resultSet.getString("new_carrier"),      // 변경된 통신사
                        resultSet.getTimestamp("activation_date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activations;
    }

    // 개통 취소 (내역 삭제 + 재고 복구 + 사용자 삭제)
    public boolean cancelActivation(int activationId, int userId, int phoneId) {
        String deleteActivationSql = "DELETE FROM activation WHERE activation_id = ?";
        String deleteUserSql = "DELETE FROM user WHERE user_id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmtActivation = connection.prepareStatement(deleteActivationSql);
             PreparedStatement pstmtUser = connection.prepareStatement(deleteUserSql)) {

            // 개통 내역 삭제
            pstmtActivation.setInt(1, activationId);
            int deletedActivation = pstmtActivation.executeUpdate();

            // 사용자 삭제
            pstmtUser.setInt(1, userId);
            int deletedUser = pstmtUser.executeUpdate();

            // 개통 취소 후 재고 증가
            boolean stockUpdated = new PhoneDAO().increaseStock(phoneId);

            // 작업 수행 완료 시 true 반환
            return (deletedActivation > 0 && deletedUser > 0 && stockUpdated);

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 실패 시 false 반환
        }
    }
}